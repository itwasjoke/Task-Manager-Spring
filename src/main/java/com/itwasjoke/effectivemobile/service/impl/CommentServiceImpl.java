package com.itwasjoke.effectivemobile.service.impl;

import com.itwasjoke.effectivemobile.DTO.CommentRequestDTO;
import com.itwasjoke.effectivemobile.entity.Comment;
import com.itwasjoke.effectivemobile.entity.Task;
import com.itwasjoke.effectivemobile.entity.User;
import com.itwasjoke.effectivemobile.exception.comment.CommentNotFoundException;
import com.itwasjoke.effectivemobile.exception.task.TaskNoAccessException;
import com.itwasjoke.effectivemobile.repository.CommentRepository;
import com.itwasjoke.effectivemobile.service.CommentService;
import com.itwasjoke.effectivemobile.service.TaskService;
import com.itwasjoke.effectivemobile.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    final private CommentRepository commentRepository;
    final private UserService userService;
    final private TaskService taskService;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, TaskService taskService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public void createComment(String email, CommentRequestDTO commentRequestDTO) {
        Task task = taskService.getTaskById(commentRequestDTO.taskId());
        User user = userService.getUser(email);
        if (taskService.taskIsAvailable(task, user)) {
            Comment comment = new Comment();
            comment.setTask(task);
            comment.setUser(user);
            comment.setText(commentRequestDTO.text());
            commentRepository.save(comment);
        } else {
            throw new TaskNoAccessException("For task with ID " + task.getId() + " no access");
        }
    }

    @Override
    public void deleteComment(String email, Long id) {
        Optional<Comment> optionalComment = commentRepository.findCommentById(id);
        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            User user = userService.getUser(email);
            if (taskService.taskIsAvailable(comment.getTask(), user)){
                commentRepository.delete(comment);
            } else {
                throw new TaskNoAccessException("For task with ID " + comment.getTask().getId() + " no access");
            }
        } else {
            throw new CommentNotFoundException("No comment found in deleteComment() func");
        }
    }
}
