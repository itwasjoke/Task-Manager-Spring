package com.itwasjoke.effectivemobile.service;

import com.itwasjoke.effectivemobile.DTO.CommentRequestDTO;

public interface CommentService {
    void createComment(String email, CommentRequestDTO commentRequestDTO);
    void deleteComment(String email, Long id);
}
