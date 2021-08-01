package com.controller;

import com.model.*;
import com.service.NewsService;
import com.service.NewsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class NewsController {

private final NewsService newsService;
private final NewsSearchService searchService;

    @Autowired
    public NewsController(NewsService newsService, NewsSearchService searchService) {
        this.newsService = newsService;
        this.searchService = searchService;
    }


    @Autowired private EntityLinks links;

    @GetMapping("/news/search")
    public ResponseEntity<List<News>> fullTextSearch(String text) {
        List<News> newsList = searchService.search(text);
        return newsList != null && !newsList.isEmpty()
                ? new ResponseEntity<>(newsList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <PagedResources<News>> allNews(Pageable pageable,
                                                             PagedResourcesAssembler assembler) {
        Page<News> newsPage = newsService.allNews(pageable);
        PagedResources<News> pagedResources = assembler.toResource(newsPage,
                ControllerLinkBuilder.linkTo(NewsController.class).slash("/news").withSelfRel());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", this.createLinkHeader(pagedResources));
        return new ResponseEntity<> (assembler.toResource(newsPage,
                ControllerLinkBuilder.linkTo(NewsController.class).slash("/news").withSelfRel()),
                responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/news/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <PagedResources<Comment>> allCommentsByNews( long id_news, Pageable pageable,
                                                             PagedResourcesAssembler assembler) {
        Page<Comment> commentsPage = newsService.allCommentsByNews(id_news, pageable);
        PagedResources<Comment> pagedResources = assembler.toResource(commentsPage,
                ControllerLinkBuilder.linkTo(NewsController.class).slash("/news/comments").withSelfRel());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", this.createLinkHeader(pagedResources));
        return new ResponseEntity<> (assembler.toResource(commentsPage,
                ControllerLinkBuilder.linkTo(NewsController.class).slash("/news/comments").withSelfRel()),
                responseHeaders, HttpStatus.OK);
    }

    private String createLinkHeader(PagedResources<?> pagedResources) {
        final StringBuilder linkHeader = new StringBuilder();
        linkHeader.append(buildLinkHeader(pagedResources.getLinks("first")
                .get(0).getHref(), "first"));
        linkHeader.append(", ");
        linkHeader.append(buildLinkHeader(pagedResources.getLinks("next")
                .get(0).getHref(), "next"));
        return linkHeader.toString();
    }

    public static String buildLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }


    @PostMapping(value = "/news")
    public ResponseEntity<?> createNews(@RequestBody News news) {
        newsService.createNews(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/news")
    public ResponseEntity<List<News>> readNews() {
        final List<News> newsList = newsService.readAllNews();
        return newsList != null && !newsList.isEmpty()
                ? new ResponseEntity<>(newsList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/news/{id}")
    public ResponseEntity<News> readNews(@PathVariable(name = "id") long id) {
        final Optional<News> news = newsService.readNews(id);
        return news.isPresent() != false
                ? new ResponseEntity<>(news.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable(name = "id") long id, @RequestBody News news) {
        final boolean updated = newsService.updateNews(news, id);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable(name = "id") long id) {
        final boolean deleted = newsService.deleteNews(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping(value = "/news/comments")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        newsService.createComment(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/news/comments")
    public ResponseEntity<List<Comment>> readComment() {
        final List<Comment> commentList = newsService.readAllComments();
        return commentList != null && !commentList.isEmpty()
                ? new ResponseEntity<>(commentList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/news/comments/{id}")
    public ResponseEntity<Comment> readComment(@PathVariable(name = "id") long id) {
        final Comment comment = newsService.readComment(id);
        return comment != null
                ? new ResponseEntity<>(comment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/news/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "id") long id, @RequestBody Comment comment) {
        final boolean updated = newsService.updateComment(comment, id);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/news/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") long id) {
        final boolean deleted = newsService.deleteComment(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
