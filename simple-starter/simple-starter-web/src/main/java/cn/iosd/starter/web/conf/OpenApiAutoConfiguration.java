package cn.iosd.starter.web.conf;


import cn.iosd.starter.web.properties.OpenApiProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ok1996
 */
@Configuration
public class OpenApiAutoConfiguration {

    @Resource
    private OpenApiProperties openApiProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String BASE_PATH = "/**";

    private static final String COMPONENT_SECURITY_SCHEME_KEY = "AccessToken";

    @Bean
    public GroupedOpenApi userApi() {

        return GroupedOpenApi.builder()
                .group(titleName())
                .pathsToMatch(BASE_PATH)
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title(titleName())
                .description(openApiProperties.getDescription())
                .version(openApiProperties.getVersion());
        Contact contact = new Contact();
        contact.setName(openApiProperties.getContact().getName());
        contact.setEmail(openApiProperties.getContact().getEmail());
        contact.setUrl(openApiProperties.getContact().getUrl());
        info.contact(contact);

        OpenAPI openApi = new OpenAPI();
        openApi.info(new Info()
                .title(openApiProperties.getTitle())
                .description(openApiProperties.getDescription())
                .contact(contact)
                .version(openApiProperties.getVersion()));
        openApi.components(components())
                .addSecurityItem(new SecurityRequirement()
                        .addList(COMPONENT_SECURITY_SCHEME_KEY));

        return openApi;
    }

    private Components components() {
        Map<String, SecurityScheme> securitySchemeMap = new HashMap<>(16);
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        securityScheme.setName("Authorization");
        securityScheme.in(SecurityScheme.In.HEADER);
        securitySchemeMap.put(COMPONENT_SECURITY_SCHEME_KEY, securityScheme);
        return new Components().securitySchemes(securitySchemeMap);
    }

    private String titleName() {
        if (StringUtils.isBlank(openApiProperties.getTitle())) {
            return applicationName;
        }
        return openApiProperties.getTitle();
    }

}
