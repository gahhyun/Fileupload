package com.ottt.ottt.service.board;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.ottt.ottt.dao.board.FileDaoImpl;
import com.ottt.ottt.dto.ArticleDTO;
import com.ottt.ottt.dto.FileDTO;

@Service
public class FileServiceImpl implements FileService {
	
		//기본 파일업로드 경로
		public static final String ROOT_FILE_PATH = "D:/workspace/OTTT0719/src/main/webapp/resources/upload";
	
		@Autowired
		FileDaoImpl fileDaoImpl;
		
		@Override
		public void uploadFiles(MultipartFile[] files, Integer article_no) throws Exception {
		
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
				                //파일 now 시간
				                String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
				                try {
		                    
				                		file.transferTo(new File(ROOT_FILE_PATH + newFileName)); // 파일 저장
					                	System.out.println(" >>>>>>>>>>>>> originalFilename : "+originalFilename);
					                	System.out.println(" >>>>>>>>>>>>> extension : "+extension);
					                	System.out.println(" >>>>>>>>>>>>> newFileName : "+newFileName);
					                	System.out.println(" >>>>>>>>>>>>> uploadPath : "+uploadPath);
					                	System.out.println(" >>>>>>>>>>>>> fileSize : "+fileSize);
					                	
					                	FileDTO fileDTO = new FileDTO();
					                	fileDTO.setArticle_no(article_no);
					                	fileDTO.setFile_name(originalFilename);
					                	fileDTO.setFile_size(fileSize);
					                	fileDTO.setFile_ext(extension);
					                	fileDTO.setFile_path(uploadPath);
					                	fileDTO.setFile_re_name(newFileName);
					                	fileDTO.setFile_reg_date(currentTime);
					                	
					                	fileDaoImpl.insertFile(fileDTO);
					                	
		
					                	//빌터패턴으로 값을 DTO에 바인딩한다.
					                	/*

										FileDTO fileDTO = new FileDTO
										fileDTO.setArticle_no(article_no)
										fileDTO.setFile_name(originalFilename);
										fileDTO.setFile_size(originalFilename);
										fileDTO.setFile_re_name(newFileName);
										..
										..
										..
										
										dao,daoImpl, mapper.xml 작성하기
										
										
					                	fileDao.insertFile(fileDTO);

					                	*/
		                	
				                } 
				                
				               
				                
				                catch (IOException e) {
			                			e.printStackTrace();
				                }
						}
				}
		}

		@Override
		public List<FileDTO> selectFileList(ArticleDTO article_no) throws Exception {
			
			return fileDaoImpl.selectFileList(article_no);
		}





}
