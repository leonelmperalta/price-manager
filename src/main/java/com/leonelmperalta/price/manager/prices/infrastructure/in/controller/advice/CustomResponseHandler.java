package com.leonelmperalta.price.manager.prices.infrastructure.in.controller.advice;

import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.advice.MetaData;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.advice.ResponseBody;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@ControllerAdvice(basePackages = "com.leonelmperalta.price.manager")
public class CustomResponseHandler implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        HttpStatus responseStatus = HttpStatus.valueOf(((ServletServerHttpResponse) response).getServletResponse().getStatus());

        if (responseStatus.is2xxSuccessful() && responseStatus != HttpStatus.PARTIAL_CONTENT) {
            ResponseBody customResponse = this.createResponseBody(request);
            customResponse.setData(body instanceof Collection ? body : Collections.singleton(body));
            return customResponse;
        } else if (body instanceof ResponseBody responseBody) {
            ResponseBody customResponse = this.createResponseBody(request);
            customResponse.setData(responseBody.getData());
            customResponse.setErrors(responseBody.getErrors());
            return customResponse;
        }
        return body instanceof Collection ? body : Collections.singletonList(body);
    }

    private ResponseBody createResponseBody(ServerHttpRequest request) {
        MetaData metaData = MetaData.builder()
                .method(request.getMethod().name())
                .operation(request.getURI().getPath())
                .build();
        return ResponseBody.builder()
                .metaData(metaData)
                .errors(new ArrayList<>())
                .build();
    }
}
