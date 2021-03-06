package com.tsi.books.services.impl;

import com.tsi.books.dtos.requests.UserRequestDto;
import com.tsi.books.dtos.responses.CommentDetailsResponseDto;
import com.tsi.books.dtos.responses.UserResponseDto;
import com.tsi.books.exceptions.UserAlreadyFoundException;
import com.tsi.books.exceptions.UserHasCommentsException;
import com.tsi.books.exceptions.UserNotFoundException;
import com.tsi.books.models.User;
import com.tsi.books.repositories.CommentRepository;
import com.tsi.books.repositories.UserRepository;
import com.tsi.books.services.UserService;
import java.util.Collection;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final ModelMapper modelMapper;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  UserServiceImpl(UserRepository userRepository, CommentRepository commentRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.modelMapper = new ModelMapper();
    this.userRepository = userRepository;
    this.commentRepository = commentRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserResponseDto findById(Long id) {
    User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    return this.modelMapper.map(user, UserResponseDto.class);
  }

  @Override
  public User findByNick(String nick) {
    return this.userRepository.findByNick(nick).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserResponseDto save(UserRequestDto userRequestDto) {
    if (this.userRepository.findByNick(userRequestDto.getNick()).isPresent()) {
      throw new UserAlreadyFoundException();
    }
    User user = this.modelMapper.map(userRequestDto, User.class);
    user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
    this.userRepository.save(user);

    return this.modelMapper.map(user, UserResponseDto.class);
  }

  @Override
  public UserResponseDto updateMail(Long id, UserRequestDto userRequestDto) {
    User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    user.setEmail(userRequestDto.getEmail());
    this.userRepository.save(user);

    return this.modelMapper.map(user, UserResponseDto.class);
  }

  @Override
  public UserResponseDto deleteById(Long id) {
    User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    if (CollectionUtils.isEmpty(this.findCommentsById(id))) {
      this.userRepository.deleteById(id);
    } else {
      throw new UserHasCommentsException();
    }

    return this.modelMapper.map(user, UserResponseDto.class);
  }

  @Override
  public Collection<CommentDetailsResponseDto> findCommentsById(Long id) {
    return this.commentRepository.findByUserId(id).stream()
        .map(comment -> this.modelMapper.map(comment, CommentDetailsResponseDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<UserResponseDto> findAll() {
    return this.userRepository.findAll().stream()
        .map(user -> this.modelMapper.map(user, UserResponseDto.class))
        .collect(Collectors.toList());
  }

}
