package com.tsi.books.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String resume;
  private String author;
  private String editorial;
  private Integer publicationYear;
  @OneToMany(mappedBy = "book",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  public Book(String title, String resume, String author, String editorial,
      Integer publicationYear) {
    this.title = title;
    this.resume = resume;
    this.author = author;
    this.editorial = editorial;
    this.publicationYear = publicationYear;
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setBook(this);
  }

  public boolean removeComment(Long commentId) {
    return comments.removeIf(c -> c.getId().equals(commentId));
  }
}
