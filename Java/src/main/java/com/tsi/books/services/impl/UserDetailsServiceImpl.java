package com.tsi.books.services.impl;

import com.tsi.books.models.User;
import com.tsi.books.repositories.UserRepository;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByNick(nick);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException(nick);
    }

    return new org.springframework.security.core.userdetails.User
        (user.get().getNick(), user.get().getPassword(), Collections.EMPTY_LIST);
  }

}
