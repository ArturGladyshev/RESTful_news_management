package com.repository;



import com.model.Comment;
import com.model.News;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;




@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "SELECT * FROM COMMENTS WHERE ID_NEWS = ?1",
            countQuery = "SELECT count(*) FROM COMMENTS WHERE ID_NEWS = ?1",
            nativeQuery = true)
    Page<Comment> findById_news(Long id_news, Pageable pageable);



}
