package rest;

import com.bandwidth.engineering.correlator.dto.cache.RateCenter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.shaded.org.apache.http.HttpResponse;
import org.testcontainers.shaded.org.apache.http.client.methods.HttpGet;
import org.testcontainers.shaded.org.apache.http.client.methods.HttpUriRequest;
import org.testcontainers.shaded.org.apache.http.impl.client.HttpClientBuilder;


public class TestRest {

  static GenericContainer igniteServerContainer;
  static GenericContainer restApp;
  static IgniteClient ignite;
  static int numRecords;
  static Network network = Network.newNetwork();

  private static String CACHE_NAME = "test_Cache";


  @BeforeClass
  public static void setupIgnite()
  {
    igniteServerContainer =
        new GenericContainer("apacheignite/ignite:2.5.0").withNetwork(network).withNetworkAliases("ignite").withExposedPorts(10800);
    igniteServerContainer.start();
    ignite = Ignition.startClient(
        new ClientConfiguration().setAddresses(igniteServerContainer.getContainerIpAddress() + ":" + igniteServerContainer.getFirstMappedPort())
    );

    populateCache();
    Map<String, String> env = new HashMap<>();
    env.put("cache.addresses", "ignite:" + 10800);
    restApp = new GenericContainer<>("test-rest:latest").withEnv(env).withNetwork(network).withExposedPorts(8080);
    restApp.start();
  }

  @AfterClass
  public static void kill()
  {
    igniteServerContainer.stop();
  }

  @AfterClass
  public static void clearCaches()
  {
    ignite.destroyCache(CACHE_NAME);
  }

  @Test
  public void test_Server_Response_To_Properly_Formed_URL()
  {

    String query = "/number?PhoneNumber=919360";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.OK.value()));

  }

  @Test
  public void test_Server_Response_To_Improperly_Formed_URL()
  {
    String query = "/number?Phone%20Number=919360";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void test_Server_Response_To_Single_Invalid_Phone_Number()
  {
    String query = "/number?PhoneNumber=919[360";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void test_Server_Response_To_Mixed_Invalid_Phone_Numbers()
  {
    String query = "/number?PhoneNumber=919360,567869,435@43,5678943,321";
    HttpResponse response = getUrlResponse(query);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  private HttpResponse getUrlResponse(String query)
  {
    int port = restApp.getMappedPort(8080);
    String request = "http://127.0.0.1:" + port + query;
    HttpUriRequest httpRequest = new HttpGet(request);
    System.out.println(httpRequest.toString());

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
    ClientCache<String, RateCenter> cache = ignite.getOrCreateCache(CACHE_NAME);
    for(int i = 0; i < numRecords; i++)
    {
      RateCenter rc = new RateCenter("ABC" + i, "DFG" + i, "HIJ" + i, "LMN" + i, "OP" + i, "QR" + i, null,
          LocalDateTime.of(2019, 06, 23, 5, 30));

      String phoneNumber = "999999";
      cache.put(phoneNumber + i, rc);

    }
  }




  //run ignite in a container
  //run application in a container, both in the same network
  //both should have exposed ports
  //then populate ignite cache before tests run
  //be careful with

  private void compareResults()
  {

  }

  //agenda:
  //make request
  //get json result from request
  //compare with the result you need to be getting

  //figure out other testing

}
