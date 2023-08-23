package com.ottt.ottt.service.board;

import java.util.List;

import com.ottt.ottt.domain.SearchItem;
import com.ottt.ottt.dto.ArticleDTO;

public interface BoardService {

	List<ArticleDTO> getPage(SearchItem sc) throws Exception;
	ArticleDTO getArticle(Integer article_no) throws Exception;
	int getCount(SearchItem sc) throws Exception;

	int insert(ArticleDTO articleDTO) throws Exception;
	int update(ArticleDTO articleDTO) throws Exception;
	int delete(Integer article_no) throws Exception;	
	
	int hitCount(Integer article_no) throws Exception;
	
	int selectLastArticleNo() throws Exception;
	
}
