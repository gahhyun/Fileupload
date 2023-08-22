package com.ottt.ottt.service.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ottt.ottt.dao.board.BoardDaoImpl;
import com.ottt.ottt.dao.board.FileDaoImpl;
import com.ottt.ottt.dto.FileDTO;

@Service
public class FileServiceImpl implements FileService {
	
	//기본 파일업로드 경로
	public static final String ROOT_FILE_PATH = "/Users/devmk/Desktop/STS_2/OTTT0719/src/main/webapp/resources/upload";
	
	@Autowired
	FileDaoImpl fileDao;

	@Override
	public void uploadFiles(MultipartFile[] files, Integer article_no) {
		
        for (MultipartFile file : files) {
        	
            if (!file.isEmpty()) {
            	//원본 파일명
                String originalFilename = file.getOriginalFilename();
                //파일 확장자
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                //암호화 파일명
                String newFileName = UUID.randomUUID().toString() + extension;
                //파일 사이즈
                long fileSize = file.getSize();
                //파일 업로드 경로
                String uploadPath = ROOT_FILE_PATH + newFileName;

                try {
                    
                	file.transferTo(new File(ROOT_FILE_PATH + newFileName)); // 파일 저장
                    
                	//빌터패턴으로 값을 DTO에 바인딩한다.
                	fileDao.insertFile(
	                	FileDTO.builder()
	                		.file_name(originalFilename)
	                		.file_re_name(newFileName)
	                		.file_path(uploadPath)
	                		.file_ext(extension)
	                		.file_size(fileSize)
	                		.article_no(article_no)
	                		.build()
                	);
                	
                	
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
		
	}

}
