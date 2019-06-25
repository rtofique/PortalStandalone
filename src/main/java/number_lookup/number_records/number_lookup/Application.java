package number_lookup.number_records.number_lookup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//could be in different config class ( just the swagger)

@EnableSwagger2
@SpringBootApplication
public class Application {

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Docket apiDocket()
  {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("rest"))
        .paths(PathSelectors.regex("/.*"))
        .build().apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo()
  {
    return new ApiInfoBuilder()
        .version("0.1")
        .title("NPA-NXX Lookup")
        .description("Returns all the data in Bandwidth databases on given NPA-NXX numbers")
        .build();
  }
}
