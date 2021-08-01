package com.service;

import com.model.News;
import java.util.List;

public interface NewsSearchService {
List<News> search(String text);
}
