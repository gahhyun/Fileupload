package com.ottt.ottt.dao.board;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ottt.ottt.dto.FileDTO;

@Repository
public  class FileDaoImpl implements FileDao {
	
	@Autowired
	private SqlSession session;
	private static String namespace = "com.ottt.ottt.dao.board.boardMapper.";
	
	
	@Override
	public int insertFile(FileDTO fileDTO) throws Exception {
		// TODO Auto-generated method stub
		return session.insert(namespace+"insertFile", fileDTO);
	}	



}
