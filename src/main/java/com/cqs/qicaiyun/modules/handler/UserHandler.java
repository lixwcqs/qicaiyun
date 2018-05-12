package com.cqs.qicaiyun.modules.handler;

import com.cqs.qicaiyun.mock.UserMock;
import com.cqs.qicaiyun.system.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class UserHandler {

        public Mono<ServerResponse> listPeople(ServerRequest request) {
                Flux<User> people = Flux.fromIterable(UserMock.newUsers());
                return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, User.class);
        }

        public Mono<ServerResponse> createUser(ServerRequest request) { 
                Mono<User> user = request.bodyToMono(User.class);
                return ServerResponse.ok().build();
        }

        public Mono<ServerResponse> getUser(ServerRequest request) { 
                int UserId = Integer.valueOf(request.pathVariable("id"));
                Mono<ServerResponse> notFound = ServerResponse.notFound().build();
                Mono<User> UserMono = Mono.just(UserMock.newUser());
                return UserMono
                                .flatMap(User -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(User)))
                                .switchIfEmpty(notFound);
        }
}