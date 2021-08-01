package com.service;

import com.model.*;
import com.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NewsServiceImp implements NewsService{

    private final NewsRepository newsRepository;

  @Autowired
    public NewsServiceImp (NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

   @Override
    public Page<News> allNews(Pageable pageable) {
      return newsRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> allCommentsByNews(long id_news, Pageable pageable) {
        return newsRepository.findById_news(id_news, pageable);
    }

    @Override
    public void createComment(Comment comment) {
        Optional<News> optional = newsRepository.findById(comment.getId_news());
   if (optional.isPresent()) {
      News news = optional.get();
      news.addComment(comment);
      newsRepository.save(news);
      }
    }

    @Override
    public List<Comment> readAllComments() {
        List<Comment> commentList = new ArrayList<>();
        for (News news : newsRepository.findAll()) {
                commentList.addAll(news.getComments());
            }
        return commentList;
    }


    @Override
    public Comment readComment(Long id) {
       for(News news : newsRepository.findAll()) {
          Comment comment = news.findComment(id);
       if (comment != null) {
           return comment;
       }
       }
        return null;
    }


    @Override
    public boolean updateComment(Comment comment, Long id) {
        for (News news : newsRepository.findAll()) {
            Comment foundComment = news.findComment(id);
            if (foundComment != null) {
                news.replaceComment(comment, news.getIndexComment(foundComment));
                newsRepository.save(news);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean deleteComment(Long id) {
        for (News news : newsRepository.findAll()) {
            Comment foundComment = news.findComment(id);
            if (foundComment != null) {
                news.deleteComment(news.getIndexComment(foundComment));
               newsRepository.save(news);
                return true;
            }
        }
        return false;
    }


    @Override
    public void createNews(News news) {
        newsRepository.save(news);
    }


    @Override
    public List<News> readAllNews() {
        return newsRepository.findAll();
    }


    @Override
    public Optional<News> readNews(long id) {
        return newsRepository.findById(id);
    }

    @Override
    public boolean updateNews(News news, Long id) {
         Optional<News> optional = newsRepository.findById(id);
            if (optional.isPresent()) {
                News foundNews = optional.get();
           news.setId(foundNews.getId());
                newsRepository.save(news);
                return true;
            }
            else { return false; }
        }


    @Override
    public boolean deleteNews(Long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
