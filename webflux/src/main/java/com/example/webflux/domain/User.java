package com.example.webflux.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;

    @Range (min=18, max=100)
    private int age;
}
