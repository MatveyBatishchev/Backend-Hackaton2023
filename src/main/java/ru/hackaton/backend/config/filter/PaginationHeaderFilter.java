package ru.hackaton.backend.config.filter;

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

    private static final String totalCountHeader = "X-Total-Count";

    private static final String contentRangeHeader = "Content-Range";

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // Checks if this advice is applicable.
        // In this case it applies to any endpoint which returns a page.
        return PageWrapper.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public PageWrapper<?> beforeBodyWrite(PageWrapper<?> pageWrapper, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (pageWrapper != null) {
            response.getHeaders().add(totalCountHeader, String.valueOf(pageWrapper.getTotalCount()));
            response.getHeaders().add(contentRangeHeader, request.getURI().getPath().substring(5) + " */" + pageWrapper.getTotalCount());
        }
        return pageWrapper;
    }

}
