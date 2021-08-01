package com.service;

import com.model.News;
import com.repository.NewsRepository;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NewsSearchServiceImp implements NewsSearchService{

    private final NewsRepository newsRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public NewsSearchServiceImp (NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> search(String text) {
        SearchSession searchSession = Search.session(entityManager);
        SearchScope<News> scope = searchSession.scope( News.class );
        SearchResult<News> result = searchSession.search( scope )
                .where( scope.predicate().match()
                        .fields( "title", "text", "comment_text", "username")
                        .matching( text )
                        .toPredicate() )
                .fetch( 20 );
        return result.hits();
    }
}
