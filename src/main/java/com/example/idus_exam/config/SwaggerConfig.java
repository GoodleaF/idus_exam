package com.example.idus_exam.config;

import com.example.idus_exam.config.filter.LoginFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointCustomizer(ApplicationContext applicationContext) {
        // 스프링 시큐리티 필터 체인을 가져옵니다.
        FilterChainProxy springSecurityFilterChain = applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);

        return openApi -> {
            // 필터 체인에서 LoginFilter가 있는지 확인합니다.
            for (SecurityFilterChain filterChain : springSecurityFilterChain.getFilterChains()) {
                Optional<LoginFilter> filter = filterChain.getFilters().stream()
                        .filter(LoginFilter.class::isInstance)
                        .map(LoginFilter.class::cast)
                        .findAny();
                if (filter.isPresent()) {
                    // LoginFilter가 존재하면, /auth/login 엔드포인트에 대한 Swagger 문서를 추가합니다.
                    Operation operation = new Operation();

                    // 요청 본문 스키마 정의 (로그인 시 email, password를 받습니다.)
                    Schema<?> schema = new ObjectSchema()
                            .addProperty("email", new StringSchema())
                            .addProperty("password", new StringSchema());
                    RequestBody requestBody = new RequestBody()
                            .content(new Content().addMediaType("application/json", new MediaType().schema(schema)));
                    operation.setRequestBody(requestBody);

                    // 응답 설정
                    ApiResponses responses = new ApiResponses();
                    responses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    responses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                    operation.setResponses(responses);

                    // 태그와 요약 설정
                    operation.addTagsItem("회원 기능");
                    operation.summary("로그인 기능");

                    // /auth/login 경로에 POST 메서드로 추가
                    PathItem pathItem = new PathItem().post(operation);
                    openApi.getPaths().addPathItem("/auth/login", pathItem);
                }
            }
        };
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("idus_exam API")
                .description("idus_exam 프로젝트의 API 문서")
                .version("1.0.0");
    }
}
