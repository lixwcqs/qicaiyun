package com.cqs.qicaiyun.modules.controller;

import com.cqs.qicaiyun.system.entity.User;
import com.cqs.qicaiyun.system.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cqs on 2018/4/16.
 */
@RestController()
@RequestMapping("/flux")
@Log4j2
public class FluxHandler {

    @Resource
    private IUserService userService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
//    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {
        log.error("NOT FOUND");
    }

    @GetMapping("/l")
    public Flux<User> list() {
        return Flux.fromIterable(this.userService.selectList(null));
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") final long id) {
        return Mono.justOrEmpty(this.userService.selectById(id));
    }

//    @PostMapping("")
//    public Flux<User> create(@RequestBody final Flux<User>  users) {
//        return  users.doOnNext(user ->  );
//    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") final long id, @RequestBody final User user) {
        Objects.requireNonNull(user);
        user.setId(id);
        this.userService.insertOrUpdate(user);
        return Mono.just(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> delete(@PathVariable("id") final long id) {
        boolean b = this.userService.deleteById(id);
        return Mono.just(b);
    }

    @GetMapping("/rns")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }
}
