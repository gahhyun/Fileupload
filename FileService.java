package com.ottt.ottt.service.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ottt.ottt.dto.ArticleDTO;
import com.ottt.ottt.dto.FileDTO;




public interface FileService {
	
	void uploadFiles(MultipartFile[] files, Integer article_no) throws Exception;
	
	List<FileDTO> selectFileList(ArticleDTO article_no) throws Exception;


}
