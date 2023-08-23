package com.ottt.ottt.service.board;

import org.springframework.web.multipart.MultipartFile;




public interface FileService {
	
	void uploadFiles(MultipartFile[] files, Integer article_no) throws Exception;


}
