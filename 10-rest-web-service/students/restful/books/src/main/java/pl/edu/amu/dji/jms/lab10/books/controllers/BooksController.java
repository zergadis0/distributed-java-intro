/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab10.books.controllers;

import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.amu.dji.jms.lab10.books.model.Book;
import pl.edu.amu.dji.jms.lab10.books.repository.BookRepository;

/**
 *
 * @author Uczelnia
 */
@RestController
public class BooksController {
    @Autowired
    BookRepository bookRepository;
    
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public List<Book> books() {
        return Lists.newArrayList(bookRepository.findAll());
    }
    
    @RequestMapping(value="/books/{id}", method = RequestMethod.GET)
    public Book getBook(@PathVariable String id) {
        return bookRepository.findOne(id);
    }
    
    @RequestMapping(value="/books", method = RequestMethod.POST)
    public void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
    
    @RequestMapping(value="/books/{id}", method = RequestMethod.PUT)
    public void updateBook(@PathVariable String id, @RequestBody Book book) {
        Book bookToUpdate = bookRepository.findOne(id);
        bookToUpdate = book;
        bookRepository.save(bookToUpdate);
    }
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such book")
    @RequestMapping(value="/books/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable String id) {
        if (bookRepository.findOne(id) == null)
            
        bookRepository.delete(id);
    }
}
