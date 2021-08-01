package com.service;

import com.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;


public interface NewsService {
    void createNews(News news);

    List<News> readAllNews();

    Optional<News> readNews(long id);

    boolean updateNews(News news, Long id);

    boolean deleteNews(Long id);

   Page<Comment> allCommentsByNews(long id_news, Pageable pageable);

    Page<News> allNews(Pageable pageable);

    void createComment(Comment comment);

    List<Comment> readAllComments();

    Comment readComment(Long id);

    boolean updateComment(Comment comment, Long id);

    boolean deleteComment(Long id);
}
