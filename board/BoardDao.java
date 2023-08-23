package com.ottt.ottt.dao.board;

import java.util.List;

import com.ottt.ottt.domain.SearchItem;
import com.ottt.ottt.dto.ArticleDTO;

public interface BoardDao {
	
	List<ArticleDTO> selectPage(SearchItem sc) throws Exception;
	ArticleDTO select(Integer article_no) throws Exception;
	int count(SearchItem sc) throws Exception;
	int insert(ArticleDTO articleDTO);
	int update(ArticleDTO articleDTO) throws Exception;
	int delete(Integer article_no) throws Exception;	
	
	int hitCount(Integer article_no) throws Exception;
	
	int selectLastArticleNo() throws Exception;
	

}
