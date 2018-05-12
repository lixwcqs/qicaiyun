package com.cqs.qicaiyun.conf;

import com.cqs.qicaiyun.modules.handler.CalculatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by cqs on 2018/4/16.
 */
@Configuration
public class RouterFunctionConf {

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> routerFunction(final CalculatorHandler calculatorHandler) {
        return route(RequestPredicates.path("/calculator"), request ->
                request.queryParam("operator").map(operator ->
                        Mono.justOrEmpty(ReflectionUtils.findMethod(CalculatorHandler.class, operator, ServerRequest.class))
                                .flatMap(method -> (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request))
                                .switchIfEmpty(ServerResponse.badRequest().build())
                                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))
                        .orElse(ServerResponse.badRequest().build()));
    }

//    @Bean
//    @Autowired
//    public RouterFunction<ServerResponse> routerFunction2(final UserHandler handler) {
//        return route(GET("/u2/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getUser)
//                        .andRoute(GET("/u2/list").and(accept(MediaType.APPLICATION_JSON)), handler::listPeople)
//                        .andRoute(POST("/u2").and(contentType(MediaType.APPLICATION_JSON)), handler::createUser);

//    }


}
