package com.model;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "comments")
public class Comment {


    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;


    @Column(name = "id_news")
    private long id_news;

    @Column(name = "data")
    private Date date = new Date();

    @Column(name = "text")
    @FullTextField(name = "comment_text")
    private String text;

    @Column(name = "username")
    @KeywordField
    private String username;

    public Comment( long id_news, String text, String username) {
        this.id_news = id_news;
        this.username = username;
        this.text = text;
    }

    protected Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId_news(long id_news) {
        this.id_news = id_news;
    }

    public void setDate(Date date) {
        this.date = new Date();
    }

    public long getId_news() {
        return id_news;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", id_news=" + id_news +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
