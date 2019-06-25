package number_lookup;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import com.bandwidth.engineering.correlator.enums.cache.IconectivStatus;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.containers.output.WaitingConsumer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.apache.http.HttpResponse;
import org.testcontainers.shaded.org.apache.http.client.methods.HttpGet;
import org.testcontainers.shaded.org.apache.http.client.methods.HttpUriRequest;
import org.testcontainers.shaded.org.apache.http.impl.client.HttpClientBuilder;
import org.testcontainers.shaded.org.apache.http.util.EntityUtils;


public class TestRest {

  static GenericContainer igniteServerContainer;
  static GenericContainer restApp;
  static IgniteClient ignite;
  static int numRecords = 1;
  static Network network = Network.newNetwork();

  static ObjectMapper mapper;
  private static String CACHE_NAME = "test_Cache1";

  private static Logger logger = LoggerFactory.getLogger(TestRest.class);

  @BeforeClass
  public static void setupIgnite() throws InterruptedException {
    igniteServerContainer =
        new GenericContainer("apacheignite/ignite:2.5.0")
            .withNetwork(network)
            .withNetworkAliases("ignite")
            .withExposedPorts(10800);

    igniteServerContainer.start();

    Thread.sleep(3000);



    try {
      igniteServerContainer.execInContainer("apache-ignite-fabric/bin/control.sh",  "--activate");
    } catch (IOException e) {
      e.printStackTrace();
    }

   ignite = Ignition.startClient(
        new ClientConfiguration().setAddresses(igniteServerContainer.getContainerIpAddress() + ":" + igniteServerContainer.getFirstMappedPort())
    );




    mapper = new ObjectMapper();



    populateCache();

    Map<String, String> env = new HashMap<>();
    env.put("cache_addresses", "ignite:" + 10800);
    env.put("cache_name", CACHE_NAME);
    restApp = new GenericContainer<>("test-number_lookup:latest").withEnv(env).withNetwork(network).withExposedPorts(8080);
    restApp.start();

    handleContainerLogger(restApp);
  }

  @AfterClass
  public static void kill()
  {
    igniteServerContainer.stop();
  }

  @AfterClass
  public static void clearCaches()
  {
    //ignite.destroyCache(CACHE_NAME);
  }

  @Test
  public void test_Server_Response_To_Proper_URL()
  {

    String query = "/number?PhoneNumber=919360";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.OK.value()));

  }

  @Test
  public void test_Server_Response_To_Malformed_URL()
  {
    String query = "/number?Phone%20Number=919360";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void test_Server_Response_To_Single_Number_With_Invalid_Characters()
  {
    String query = "/number?PhoneNumber=919[360";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void test_Server_Response_To_Single_Number_With_Invalid_Length()
  {
    String query = "/number?PhoneNumber=919";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void test_Server_Response_To_Mixed_ValidInvalid_PhoneNumbers()
  {
    String query = "/number?PhoneNumber=919360,56fg69,435@43,5678943,321";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void test_Server_Response_To_Multiple_Valid_PhoneNumbers()
  {
    String query = "/number?PhoneNumber=919360,567455A,8907685";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.OK.value()));
  }

  @Test
  public void test_JSON_Response_To_Single_UnResolvable_PhoneNumber() throws IOException {
    String query = "/number?PhoneNumber=6039529";
    HttpResponse response = getUrlResponse(query);

    String json = EntityUtils.toString(response.getEntity());

    JsonNode jsonNode = mapper.readTree(json).get(0);
    testSingleValid(jsonNode, "603952A", false, null);
    assertThat(jsonNode.path("rateCenters").size(), equalTo(0));

  }


  @Test
  public void test_JSON_Response_To_Single_Invalid_PhoneNumber() throws IOException {
    String query = "/number?PhoneNumber=919a360";
    HttpResponse response = getUrlResponse(query);
    String json = EntityUtils.toString(response.getEntity());
    JsonNode jsonNode = mapper.readTree(json).get(0);

    testSingleInvalid(jsonNode, "919a360", "1");
  }

  @Test
  public void test_JSON_Response_To_Mixed_Valid_Invalid_PhoneNumbers() throws IOException {
    String query = "/number?PhoneNumber=919a360,919360,567345A,43,675!";
    HttpResponse response = getUrlResponse(query);
    String json = EntityUtils.toString(response.getEntity());
    JsonNode parent = mapper.readTree(json);
    testSingleInvalid(parent.get(0), "919a360", "1");
    testSingleInvalid(parent.get(1), "43", "4");
    testSingleInvalid(parent.get(2), "675!", "5");
  }

  @Test
  public void test_JSON_Response_To_Single_Valid_PhoneNumber() throws IOException, InterruptedException {

    printAllKeys();
    String query = "/number?PhoneNumber=9999990";
    HttpResponse response = getUrlResponse(query);

    String json = EntityUtils.toString(response.getEntity());
    JsonNode parent = mapper.readTree(json).get(0);
    RateCenter rc = new RateCenter("ABC0", "DFG0", "HIJ0", "LMN0", "OP0", "QR0", null, LocalDateTime.of(2019, 06, 23, 5, 30));
    testSingleValid(parent, "9999990", true, rc);

  }

  private static void handleContainerLogger(GenericContainer container)
  {
    Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger);
    container.followOutput(logConsumer);
    WaitingConsumer waitingConsumer = new WaitingConsumer();
    ToStringConsumer toStringConsumer = new ToStringConsumer();
    Consumer<OutputFrame> composedConsumer = toStringConsumer.andThen(waitingConsumer);
    container.followOutput(composedConsumer);
  }

  private static void printAllKeys()
  {
    List<String> keys = new ArrayList<>();
    try
    {
      ignite.cache(CACHE_NAME).query(new ScanQuery<>(null)).forEach(entry -> keys.add((String) entry.getKey()));
    }
    catch (NoSuchElementException  e) {
      e.printStackTrace();
    }

    for(String key : keys)
    {
      System.out.println(key);
    }

  }


  @Test
  public void test_JSON_Response_To_Multiple_Valid_PhoneNumbers()
  {

  }

  private HttpResponse getUrlResponse(String query)
  {
    int port = restApp.getMappedPort(8080);
    String request = "http://127.0.0.1:" + port + query;
    HttpUriRequest httpRequest = new HttpGet(request);


    HttpResponse response;
    try {
      response = HttpClientBuilder.create().build().execute(httpRequest);
    } catch (IOException e) {
      e.printStackTrace();
      response = null;
    }

    return response;
  }

  private static void populateCache()
  {
    ClientCache<String, TreeSet<RateCenter>> cache = ignite.getOrCreateCache(CACHE_NAME);

    for(int i = 0; i < numRecords; i++)
    {
      TreeSet<RateCenter> rcIterable = new TreeSet<>();
      final IconectivStatus status = IconectivStatus.from(null);

      RateCenter rc = new RateCenter("ABC" + i, "DFG" + i, "HIJ" + i, "LMN" + i, "OP" + i, "QR" + i, status,
          LocalDateTime.of(2019, 06, 23, 5, 30));

      String phoneNumber = "999999" + i;
      rcIterable.add(rc);

      for(String c : ignite.cacheNames())
      {
        System.out.println(c);
      }

      cache.put(phoneNumber + "0", rcIterable);

    }
  }

  private void testSingleInvalid(JsonNode jsonNode, String query, String queryID)
  {
    assertThat(jsonNode.path("query").textValue(), equalTo(query));
    assertThat(jsonNode.path("jsonType").textValue(), equalTo("$INVALID"));
    assertThat(jsonNode.path("queryID").textValue(), equalTo(queryID));
  }


  private void testSingleValid(JsonNode jsonNode, String query, boolean recordFound, RateCenter rc)
  {
    assertThat(jsonNode.path("query").textValue(), equalTo(query));
    assertThat(jsonNode.path("jsonType").textValue(), equalTo("$VALID"));
    assertThat(jsonNode.path("recordFound").booleanValue(), equalTo(recordFound));

    if(rc != null)
    {
      JsonNode rateCenter = jsonNode.path("rateCenters").get(0);
      assertThat(rateCenter.path("lata").textValue(), equalTo(rc.getLata()));
      assertThat(rateCenter.path("ocn").textValue(), equalTo(rc.getOcn()));
      assertThat(rateCenter.path("rateCenter").textValue(), equalTo(rc.getRateCenter()));
    }

  }



}
