package com.example.webflux.controller;

import com.example.webflux.common.BaseResponse;
import com.example.webflux.common.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文件上传下载
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    private final Path BASE_PATH = Paths.get("./files/");

    @PostMapping(value = "upload/single")
    public Mono<ResponseEntity<BaseResponse<Void>>> uploadSingleFile(@RequestPart(name = "file") Mono<FilePart> filePartMono) {
        createDir(BASE_PATH);

        return filePartMono
                .doOnNext(fp-> log.info("上传文件："+fp.filename()))
                .flatMap(fp-> {
                    try {
                        Path file = Files.createFile(BASE_PATH.resolve(System.currentTimeMillis()+"_"+fp.filename()), permissionAttr());
                        return fp.transferTo(file.toFile())
                                .then(Mono.just(new ResponseEntity<>(Result.success(),HttpStatus.OK)));
                    } catch (Exception e) {
                        log.error("单文件上传异常：",e);
                        return Mono.just(new ResponseEntity<>(Result.fail(),HttpStatus.BAD_REQUEST));
                    }
                })
                .defaultIfEmpty(new ResponseEntity<>(Result.fail(),HttpStatus.BAD_REQUEST));
    }


    @PostMapping(value = "upload/multi")
    public Mono<ResponseEntity> uploadFiles(@RequestPart("files") Flux<FilePart> files)  {

        createDir(BASE_PATH);
        return files.flatMap(fp -> {
                try {
                    Path file = Files.createFile(BASE_PATH.resolve(System.currentTimeMillis()+"_"+fp.filename()), permissionAttr());
                    return fp.transferTo(file.toFile())
                            .then(Mono.just(file.toString()));
                } catch (Exception e) {
                    log.error("多文件上传异常：",e);
                    return Mono.empty();
                }
        }).collect(Collectors.toList()).flatMap(
                strings -> Mono.just(new ResponseEntity<Object>(Result.success(strings),HttpStatus.OK))
        );
    }

    private ResponseEntity<byte[]> download() throws IOException {

        String fileName="x.txt";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment ", fileName);
        headers.setConnection("keep-alive");
        headers.add("Cache-Control", "public, max-age=0");
        headers.add("Content-Type", "application/");
        return new ResponseEntity<>(Files.readAllBytes(BASE_PATH.resolve(fileName)), headers, HttpStatus.CREATED);

    }

    @SneakyThrows
    private static void createDir(Path dirPath) {
        if (!Files.exists(dirPath,LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(dirPath,permissionAttr());
        }
    }

    private static FileAttribute<Set<PosixFilePermission> > permissionAttr () {
        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
        return PosixFilePermissions
                .asFileAttribute(permissions);

    }

}
