package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.service.DefaultService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @program: boot-mongo
 * @description:
 * @author: 001977
 * @create: 2018-08-09 17:24
 */
@Service
public class DefaultServiceImpl implements DefaultService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addBook(Book book) {
        mongoTemplate.insert(book,COLLECTION_NAME);
    }

    @Override
    public void addWithNoCollectionName(Book book) {
        mongoTemplate.insert(book);
    }

    @Override
    public List<Book> getBooksWithNoCollectionName(Book book) {
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.find(query,Book.class);
    }

    @Override
    public void batchInsert(List<Book> list) {
        mongoTemplate.insert(list,COLLECTION_NAME);
    }

    @Override
    public long delBook(Book book) {
        Query query = new Query(Criteria.where("name").is(book.getName()));
        DeleteResult result = mongoTemplate.remove(query, COLLECTION_NAME);
        return result.getDeletedCount();
    }

    @Override
    public long updateBook(Book book) {
        Query query = new Query(Criteria.where("id").is(book.getId()));
        Update update = new Update().set("name",book.getName());
        // 更新一个
        //UpdateResult result = mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        // 更新全部
        UpdateResult result = mongoTemplate.updateMulti(query,update,COLLECTION_NAME);
        return result.getModifiedCount();
    }

    @Override
    public List<Book> getBooks(Book book) {
        if (book == null){
            return mongoTemplate.findAll(Book.class,COLLECTION_NAME);
        }
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.find(query,Book.class,COLLECTION_NAME);
    }

    @Override
    public Book findOne(Book book) {
        Query query = new Query(Criteria.where("name").is(book.getName()));

        return mongoTemplate.findOne(query,Book.class,COLLECTION_NAME);
    }

    @Override
    public Book findById(Book book) {
        return mongoTemplate.findById(book.getId(),Book.class,COLLECTION_NAME);
    }

    @Override
    public List<Book> findAllAndRemove(Book book) {
        Query query = new Query(Criteria.where("name").is(book.getName()));
        List<Book> allAndRemove = mongoTemplate.findAllAndRemove(query, Book.class, COLLECTION_NAME);
        return allAndRemove;
    }

    @Override
    public Book findAndRemove(Book book) {
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.findAndRemove(query, Book.class, COLLECTION_NAME);
    }

    @Override
    public Book findAndModify(Book book) {
        Query query = new Query(Criteria.where("id").is(book.getId()));
        Update update = new Update().set("name",book.getName());
        return mongoTemplate.findAndModify(query,update,Book.class,COLLECTION_NAME);
    }

    @Override
    public Book findLast() {
        // 先sort,后skip再limit
        Query query = new Query(Criteria.where("id").lt(Long.MAX_VALUE)).with(Sort.by(Sort.Direction.DESC,"id")).skip(0).limit(1);
        return mongoTemplate.findOne(query,Book.class,COLLECTION_NAME);
    }
}
