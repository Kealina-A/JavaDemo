package com.example.webflux2.util;


import com.example.webflux2.exceptions.CheckException;

import java.util.stream.Stream;

public class CheckUtil {

    private static final String[] INVALID_NAMES={"admin","superadmin"};

    public static void checkName(String value) {
        Stream.of(INVALID_NAMES).filter(name->name.equalsIgnoreCase(value))
                .findAny()
                .ifPresent(n->{
                    throw new CheckException("name",value);
                });
    }
}
