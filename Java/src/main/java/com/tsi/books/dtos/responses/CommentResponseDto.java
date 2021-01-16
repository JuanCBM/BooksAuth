package com.tsi.books.dtos.responses;

import lombok.Data;

@Data
public class CommentResponseDto {

    private Long id;
    private UserResponseDto user;
    private String content;
    private int rating;

}
