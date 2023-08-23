package com.ottt.ottt.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottt.ottt.dao.board.BoardDaoImpl;
import com.ottt.ottt.domain.SearchItem;
import com.ottt.ottt.dto.ArticleDTO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDaoImpl boardDao;

	@Override
	public List<ArticleDTO> getPage(SearchItem sc) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectPage(sc);
	}

	@Override
	public ArticleDTO getArticle(Integer article_no) throws Exception {
		ArticleDTO articleDTO = boardDao.select(article_no);
		return articleDTO;
	}

	@Override
	public int getCount(SearchItem sc) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.count(sc);
	}
	
	
	@Override
	public int insert(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.insert(articleDTO);
	}


	@Override
	public int update(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.update(articleDTO);
	}

	@Override
	public int delete(Integer article_no) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.delete(article_no);
	}

	@Override
	public int hitCount(Integer article_no) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.hitCount(article_no);
	}

	@Override
	public int selectLastArticleNo() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectLastArticleNo();
	}
	
	

}
