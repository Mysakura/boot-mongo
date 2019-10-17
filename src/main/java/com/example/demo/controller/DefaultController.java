package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;


@RestController
public class DefaultController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加
     * @param book
     * @return
     */
    @RequestMapping("/add")
    public List<Book> add(@RequestBody Book book){
        mongoTemplate.insert(book);
        return mongoTemplate.findAll(Book.class);
    }

    /**
     * 根据name更新，只更新匹配到的第一个数据
     * @param book
     * @return
     */
    @RequestMapping("/updateFirst")
    public List<Book> updateFirst(@RequestBody Book book){
        Query query = new Query(Criteria.where("name").is(book.getName()));
        Update update = new Update().set("name",book.getName()).set("price", book.getPrice());
        mongoTemplate.updateFirst(query, update, Book.class);
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * 根据name更新，更新匹配到的全部数据
     * @param book
     * @return
     */
    @RequestMapping("/updateMulti")
    public List<Book> updateMulti(@RequestBody Book book){
        Query query = new Query(Criteria.where("name").is(book.getName()));
        Update update = new Update().set("name",book.getName()).set("price", book.getPrice());
        mongoTemplate.updateMulti(query, update, Book.class);
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * 根据name更新匹配到的第一条数据，并返回未更新之前的数据
     * @param book
     * @return
     */
    @RequestMapping("/findAndModify")
    public Book findAndModify(@RequestBody Book book){
        Query query = new Query(Criteria.where("name").is(book.getName()));
        Update update = new Update().set("name",book.getName()).set("price", book.getPrice());
        return mongoTemplate.findAndModify(query, update, Book.class);
    }

    /**
     * 根据id删除数据
     * @param book
     * @return
     */
    @RequestMapping("/remove")
    public List<Book> remove(@RequestBody Book book){
        Query query = new Query(Criteria.where("id").is(book.getId()));
        mongoTemplate.remove(query, Book.class);
        return mongoTemplate.findAll(Book.class);
    }

    /**
     * 根据name查询，匹配所有数据删除并返回
     * @param book
     * @return
     */
    @RequestMapping("/findAllAndRemove")
    public List<Book> findAllAndRemove(@RequestBody Book book){
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.findAllAndRemove(query, Book.class);
    }

    /**
     * 根据name查询，匹配第一条数据删除并返回
     * @param book
     * @return
     */
    @RequestMapping("/findAndRemove")
    public Book findAndRemove(@RequestBody Book book){
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.findAndRemove(query, Book.class);
    }

    /**
     * 根据id查询
     * @param book
     * @return
     */
    @RequestMapping("/findById")
    public Book findById(@RequestBody Book book){
        return mongoTemplate.findById(book.getId(), Book.class);
    }

    /**
     * 条件查询
     * @param book
     * @return
     */
    @RequestMapping("/find")
    public List<Book> find(@RequestBody Book book){
        Query query = new Query(Criteria.where("price").lt(book.getPrice()));
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * and查询
     * @param book
     * @return
     */
    @RequestMapping("/findOfAnd")
    public List<Book> findOfAnd(@RequestBody Book book){
        Query query = new Query(
                new Criteria().andOperator(
                        Criteria.where("name").is(book.getName()),
                        Criteria.where("price").is(book.getPrice())
                )
        );
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * or查询
     * @param name1
     * @param name2
     * @return
     */
    @RequestMapping("/findOfOr")
    public List<Book> findOfOr(@RequestParam("a") String name1, @RequestParam("b") String name2){
        Query query = new Query(
                new Criteria().orOperator(
                                Criteria.where("name").is(name1),
                                Criteria.where("name").is(name2)
                        )
        );
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * 模糊查询
     * @param book
     * @return
     */
    @RequestMapping("/findOfLike")
    public List<Book> findOfLike(@RequestBody Book book){
        Pattern pattern = Pattern.compile("^.*" + book.getName() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * 条件查询，只返回一个
     * @param book
     * @return
     */
    @RequestMapping("/findOne")
    public Book findOne(@RequestBody Book book){
        Query query = new Query(Criteria.where("name").is(book.getName()));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<Book> findAll(){
        return mongoTemplate.findAll(Book.class);
    }

    /**
     * 分页查询(注意它的页码从0开始)
     * @param request
     * @return
     */
    @RequestMapping("/findAllPageable")
    public Page<Book> findAllPageable(@RequestBody BookRequest request){
        // 排序
        Sort sort = Sort.by(Sort.Direction.DESC, "price");
        // 页码信息
        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);
        // 查询
        Query query = new Query();
        long count = mongoTemplate.count(query, Book.class);
        List<Book> books = mongoTemplate.find(query.with(pageRequest), Book.class);
        Page<Book> bookPage = new PageImpl<>(books, pageRequest, count);
        return bookPage;
    }

}
