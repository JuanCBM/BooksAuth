package com.tsi.books.services.impl;

import com.tsi.books.models.Book;
import com.tsi.books.models.User;
import com.tsi.books.repositories.BookRepository;
import com.tsi.books.repositories.UserRepository;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  DatabaseInitializer(BookRepository bookRepository,
      UserRepository userRepository) {
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void initDatabase() {
    List<Book> books = Arrays.asList(
        new Book("El señor de los Anillos",
            "Un señor con un anillo para gobernarlos a todos", "Frodo", "Edithorial", 2019),
        new Book("Harry Potter", "Un señor con una varita para gobernarlos a todos",
            "Jarry", "ThorialEdit", 2020));
    bookRepository.saveAll(books);

    List<User> users = Arrays.asList(
        new User("jua_ma", "mimail@email.com",
            "$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP."),
        new User("jua_ma2", "mimail2@email.com",
            "$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP."));
    userRepository.saveAll(users);

  }
}
