package com.tsi.books.services.impl;

import com.tsi.books.dtos.requests.BookRequestDto;
import com.tsi.books.dtos.requests.CommentRequestDto;
import com.tsi.books.dtos.responses.BookDetailsResponseDto;
import com.tsi.books.dtos.responses.BookResponseDto;
import com.tsi.books.exceptions.BookNotFoundException;
import com.tsi.books.exceptions.CommentNotFoundException;
import com.tsi.books.models.Book;
import com.tsi.books.models.Comment;
import com.tsi.books.models.User;
import com.tsi.books.repositories.BookRepository;
import com.tsi.books.services.BookService;
import com.tsi.books.services.UserService;
import java.util.Collection;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final UserService userService;
  private final ModelMapper modelMapper;

  BookServiceImpl(BookRepository bookRepository, UserService userService) {
    this.modelMapper = new ModelMapper();
    this.bookRepository = bookRepository;
    this.userService = userService;
  }

  @Override
  public Collection<BookResponseDto> findAll() {
    return this.bookRepository.findAll().stream()
        .map(book -> this.modelMapper.map(book, BookResponseDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public BookDetailsResponseDto save(BookRequestDto bookRequestDto) {
    Book book = this.modelMapper.map(bookRequestDto, Book.class);
    book = this.bookRepository.save(book);

    return this.modelMapper.map(book, BookDetailsResponseDto.class);
  }

  @Override
  public BookDetailsResponseDto findById(Long id) {
    Book book = this.bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    return this.modelMapper.map(book, BookDetailsResponseDto.class);
  }

  @Override
  public BookDetailsResponseDto deleteById(Long id) {
    Book book = this.bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    this.bookRepository.deleteById(id);
    return this.modelMapper.map(book, BookDetailsResponseDto.class);
  }

  @Override
  public BookDetailsResponseDto addComment(Long idBook, CommentRequestDto commentRequestDto) {

    Book book = this.bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);
    User user = this.userService.findByNick(commentRequestDto.getNick());

    Comment comment = this.modelMapper.map(commentRequestDto, Comment.class);
    comment.setUser(user);

    book.addComment(comment);
    this.bookRepository.save(book);

    return this.modelMapper.map(book, BookDetailsResponseDto.class);

  }

  @Override
  public BookDetailsResponseDto deleteComment(Long idBook, Long commentId) {
    Book book = this.bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);
    if (book.removeComment(commentId)) {
      this.bookRepository.save(book);
    } else {
      throw new CommentNotFoundException();
    }

    return this.modelMapper.map(book, BookDetailsResponseDto.class);
  }

  @Override
  public BookDetailsResponseDto update(Long idBook, BookRequestDto bookRequestDto) {
    Book bookDB = this.bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);

    Book book = this.modelMapper.map(bookRequestDto, Book.class);
    book.setId(idBook);
    book.setComments(bookDB.getComments());
    book = this.bookRepository.save(book);

    return this.modelMapper.map(book, BookDetailsResponseDto.class);
  }
}
