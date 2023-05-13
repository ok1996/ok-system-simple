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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author ok1996
 */
@Configuration
public class OpenApiAutoConfiguration {
    @Autowired
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
        Contact contact = new Contact();
        contact.setName(openApiProperties.getContact().getName());
        contact.setUrl(openApiProperties.getContact().getUrl());
        contact.setEmail(openApiProperties.getContact().getEmail());

        OpenAPI openApi = new OpenAPI().info(new Info()
                .title(titleName())
                .description(openApiProperties.getDescription())
                .contact(contact)
                .version(openApiProperties.getVersion()));

        openApi.addSecurityItem(new SecurityRequirement().addList(COMPONENT_SECURITY_SCHEME_KEY));
        openApi.components(new Components().securitySchemes(Collections.singletonMap(COMPONENT_SECURITY_SCHEME_KEY,
                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                        .name("Authorization")
                        .in(SecurityScheme.In.HEADER))));
        return openApi;
    }

    private String titleName() {
        return StringUtils.isNotBlank(openApiProperties.getTitle()) ? openApiProperties.getTitle() : applicationName;
    }

}
