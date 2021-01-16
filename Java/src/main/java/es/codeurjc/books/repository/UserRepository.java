package es.codeurjc.books.repository;

import es.codeurjc.books.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByNick(String nick);

}