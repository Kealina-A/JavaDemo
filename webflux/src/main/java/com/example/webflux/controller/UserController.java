package com.example.webflux.controller;


import com.example.webflux.domain.User;
import com.example.webflux.repository.UserRepository;
import com.example.webflux.util.CheckUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;

    public UserController (UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Flux<User> getAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/stream/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return repository.findAll();
    }

    @PostMapping("")
    public Mono<User> createUser(@Valid @RequestBody User user) {
        CheckUtil.checkName(user.getName());
        user.setId(null);
        return this.repository.save(user);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        return this.repository.findById(id)
                .flatMap(u->this.repository.delete(u)
                        // 没有返回值用then
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @Valid @RequestBody User user) {

        return this.repository.findById(id)
                // flatMap: 操作数据
                .flatMap(u->{
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return this.repository.save(u);
                })
                // map: 转换数据
                .map(u-> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id 查找用户，不存在返回404
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id) {
        return this.repository.findById(id)
                .map(u-> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄查找用户
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable int start, @PathVariable int end) {
        return this.repository.findAllByAgeBetween(start,end);
    }

    /**
     * 根据年龄查找用户
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/stream/age/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAge(@PathVariable int start, @PathVariable int end) {
        return this.repository.findAllByAgeBetween(start,end);
    }

    /**
     * 得到20-30的用户
     * @return
     */
    @GetMapping("/old")
    public Flux<User> oldUser() {
        return this.repository.oldUser();
    }

    /**
     * 得到20-30的用户
     * @return
     */
    @GetMapping("/stream/old")
    public Flux<User> streamOldUser() {
        return this.repository.oldUser();
    }

}
