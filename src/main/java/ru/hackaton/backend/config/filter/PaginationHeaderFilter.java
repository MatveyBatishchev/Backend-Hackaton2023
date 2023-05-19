package ru.hackaton.backend.config.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.hackaton.backend.util.PageWrapper;

@ControllerAdvice
public class PaginationHeaderFilter implements ResponseBodyAdvice<PageWrapper<?>> {

    @Value("${headers.pagination}")
    private String paginationHeader;

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // Checks if this advice is applicable.
        // In this case it applies to any endpoint which returns a page.
        return PageWrapper.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public PageWrapper<?> beforeBodyWrite(PageWrapper<?> pageWrapper, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (pageWrapper != null) {
            response.getHeaders().add(paginationHeader, String.valueOf(pageWrapper.getTotalCount()));
        }
        return pageWrapper;
    }

}
