package com.tsi.books.controllers;


import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.tsi.books.dtos.requests.UserRequestDto;
import com.tsi.books.dtos.responses.CommentDetailsResponseDto;
import com.tsi.books.dtos.responses.UserResponseDto;
import com.tsi.books.services.UserService;
import java.net.URI;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping({"/", ""})
  public ResponseEntity<Collection<UserResponseDto>> list() {
    return ResponseEntity.ok(this.userService.findAll());
  }


  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
    return ResponseEntity.ok(this.userService.findById(id));
  }

  @PostMapping({"/", ""})
  public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
    UserResponseDto user = this.userService.save(userRequestDto);
    URI location = fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

    return ResponseEntity.created(location).body(user);
  }

  @PutMapping({"/{id}/email"})
  public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
      @RequestBody UserRequestDto user) {
    return ResponseEntity.ok(this.userService.updateMail(id, user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UserResponseDto> delete(@PathVariable Long id) {
    return ResponseEntity.ok(this.userService.deleteById(id));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<Collection<CommentDetailsResponseDto>> getComments(@PathVariable Long id) {
    return ResponseEntity.ok(this.userService.findCommentsById(id));
  }
}
