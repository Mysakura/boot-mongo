package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Getter
@Setter
// 指定集合名称
@Document(collection = "my_book")
// 创建索引
@CompoundIndexes(@CompoundIndex(name = "my_index", def = "{name:1}"))
public class Book implements Serializable {

    @Id
    private Long id;

    private String name;

    private Double price;
}
