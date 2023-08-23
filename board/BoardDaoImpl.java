package com.ottt.ottt.dao.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ottt.ottt.domain.SearchItem;
import com.ottt.ottt.dto.ArticleDTO;

@Repository
public class BoardDaoImpl implements BoardDao{
	
	@Autowired 
	private SqlSession session;
	private static String namespace = "com.ottt.ottt.dao.board.boardMapper.";
	

	@Override
	public List<ArticleDTO> selectPage(SearchItem sc) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+"selectList", sc);
	}

	@Override
	public ArticleDTO select(Integer article_no) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+"selectOne", article_no);
	}

	@Override
	public int count(SearchItem sc) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+"count", sc);
	}

	@Override
	public int insert(ArticleDTO articleDTO) {
		// TODO Auto-generated method stub
		return session.insert(namespace+"insert", articleDTO);
	}

	@Override
	public int update(ArticleDTO articleDTO) throws Exception {
		// TODO Auto-generated method stub
		return session.update(namespace+"update", articleDTO);
	}

	@Override
	public int delete(Integer article_no) throws Exception {
		// TODO Auto-generated method stub
		return session.delete(namespace+"delete", article_no);
	}

	@Override
	public int hitCount(Integer article_no) throws Exception {
		// TODO Auto-generated method stub
		return session.update(namespace+"hitCount", article_no);
	}

	@Override
	public int selectLastArticleNo() throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+"selectLastArticleNo");
	}

}
