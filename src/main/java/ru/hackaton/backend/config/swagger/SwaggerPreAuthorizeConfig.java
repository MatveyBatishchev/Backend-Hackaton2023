package ru.hackaton.backend.config.swagger;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@Configuration
public class SwaggerPreAuthorizeConfig {
    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            Optional<PreAuthorize> preAuthorizeAnnotation = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
            StringBuilder sb = new StringBuilder();
            if (preAuthorizeAnnotation.isPresent()) {
                sb.append("Requires privileges and roles: **")
                        .append((preAuthorizeAnnotation.get())
                                .value()
                                .replaceAll("hasAuthority|\\(|\\)|'", "")
                                .replaceAll("hasAnyAuthority|\\(|\\)|'", "")
                                .replaceAll("#id == authentication.getPrincipal.getId", "USER.id == PathVariable.id")
                        )
                        .append("**");
            } else {
                sb.append("Requires privileges and roles: **none**");
            }
            sb.append("<br /><br />");
            if (operation.getDescription() != null) sb.append(operation.getDescription());
            operation.setDescription(sb.toString());
            return operation;
        };
    }
}