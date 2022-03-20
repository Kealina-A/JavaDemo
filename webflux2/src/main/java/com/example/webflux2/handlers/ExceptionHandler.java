package com.example.webflux2.handlers;

import com.example.webflux.exceptions.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {


    @Override
    public Mono<Void> handle (ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String s = toStr(ex);
        DataBuffer db = response.bufferFactory().wrap(s.getBytes());
        return response.writeWith(Mono.just(db));
    }

    public static String toStr (Throwable ex) {
        // 已知异常
        if (ex instanceof CheckException) {
            CheckException e = (CheckException)ex;
            return e.getFieldName()+" ：错误值 "+e.getFieldValue();
        }
        // 未知异常
        else {
            ex.printStackTrace();
            return ex.toString();
        }
    }

}
