package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @program: boot-mongo
 * @description:
 * @author: 001977
 * @create: 2018-08-09 17:23
 */
@Getter
@Setter
/**
 * 指定集合名
 */
@Document(collection = "_book")
public class Book implements Serializable {

    private Long id;

    private String name;

    public Book() {
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
