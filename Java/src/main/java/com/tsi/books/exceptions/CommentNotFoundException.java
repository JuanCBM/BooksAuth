package com.tsi.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Comment not found")
public class CommentNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -2196488071426169507L;
}
