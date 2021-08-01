package com.model;






import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
@Indexed
public class News {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(cascade= CascadeType.ALL)
    @IndexedEmbedded
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "data")
    private Date data = new Date();;

    @Column(name = "title")
    @FullTextField
    private String title;

    @Column(name = "text")
    @FullTextField
    private String text;

    public News(String title, String text, List<Comment> comments) {
        this.title = title;
        this.text = text;
        this.comments = comments;
    }

  protected News () {
    }

public void addComment(Comment comment) {
        comments.add(comment);
}

    public Comment findComment(long id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                return comment;
            }
        }
            return null;
    }

  public void replaceComment(Comment comment, int index) {
        comments.set(index , comment);
  }

public int getIndexComment (Comment comment) {
        return comments.indexOf(comment);
}

  public void deleteComment(int id) {
        comments.remove(id);
}

  public long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date date) {
        this.data = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", comments=" + comments +
                ", data=" + data +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
