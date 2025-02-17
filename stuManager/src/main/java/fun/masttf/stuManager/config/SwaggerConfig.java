package fun.masttf.stuManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;


@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createrRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("fun.masttf.stuManager.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("studentmanger APIs")
                .description("studentmanger APIs")
                .termsOfServiceUrl("http://localhost:8083/")
                .version("1.0")
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("token", "token", "header");
    }

    // 设置需要登录认证的路径
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()) // 你可以选择只对某些路径启用安全
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        // 注意这里的key名称必须与apiKey()中的名称一致
        return Arrays.asList(new SecurityReference("token", authorizationScopes));
    }
}
