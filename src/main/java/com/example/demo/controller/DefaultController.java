package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: boot-mongo
 * @description:
 * @author: 001977
 * @create: 2018-08-09 17:23
 */
@RestController
public class DefaultController {

    @Autowired
    private DefaultService defaultService;

    @RequestMapping("/last")
    public Book last(){
        return defaultService.findLast();
    }

    @RequestMapping("/add100")
    public List<Book> init(){
        Book last = defaultService.findLast();
        Long id = last == null ? 0L : last.getId();
        for (long i = id+1; i <= id+100; i++){
            defaultService.addBook(new Book(i, "Book-" + i));
        }
        return query(null);
    }

    @RequestMapping("/add")
    public List<Book> add(@RequestBody(required = false) Book book){
        defaultService.addBook(book);
        return defaultService.getBooks(book);
    }

    @RequestMapping("/list")
    public List<Book> query(@RequestBody(required = false) Book book){
        return defaultService.getBooks(book);
    }

    @RequestMapping("/getOne")
    public Book getOne(@RequestBody(required = false) Book book){
        return defaultService.findOne(book);
    }

    @RequestMapping("/getOneById")
    public Book getOneById(@RequestBody(required = false) Book book){
        return defaultService.findById(book);
    }

    /**
     * 不指定CollectionName
     * @param book
     * @return
     */
    @RequestMapping("/addWithNoCollectionName")
    public List<Book> addWithNoCollectionName(@RequestBody Book book){
        defaultService.addWithNoCollectionName(book);
        return defaultService.getBooksWithNoCollectionName(book);
    }

}
