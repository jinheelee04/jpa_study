package jpabook.jpashop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.OAS_30) // open api spec 3.0
                .groupName("API Group")
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("jpabook.jpashop.api"))
                .paths(PathSelectors.any())
                .build();
                // .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("API Documentation")
                .description("개발시 사용되는 서버 API에 대한 연동 문서입니다.")
                .license(null)
                .licenseUrl(null)
                .version("1.0").build();
    }
}