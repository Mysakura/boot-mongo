package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;

/**
 * @program: boot-mongo
 * @description:
 * @author: 001977
 * @create: 2018-08-09 17:23
 */
public interface DefaultService {

    String COLLECTION_NAME = "aaa";

    void addBook(Book book);

    /**
     * 不指定collection，默认名字为实体类的名字
     * @param book
     */
    void addWithNoCollectionName(Book book);

    /**
     * 不指定collection，默认名字为实体类的名字
     * @param book
     */
    List<Book> getBooksWithNoCollectionName(Book book);

    void batchInsert(List<Book> list);

    long delBook(Book book);

    long updateBook(Book book);

    List<Book> getBooks(Book book);

    /**
     * findOne
     * @param book
     * @return
     */
    Book findOne(Book book);

    Book findById(Book book);

    List<Book> findAllAndRemove(Book book);

    Book findAndRemove(Book book);

    Book findAndModify(Book book);

    Book findLast();

}
