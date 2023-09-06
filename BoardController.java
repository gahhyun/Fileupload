package com.ottt.ottt.controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.ottt.ottt.dao.login.LoginUserDao;
import com.ottt.ottt.domain.PageResolver;
import com.ottt.ottt.domain.SearchItem;
import com.ottt.ottt.dto.ArticleDTO;
import com.ottt.ottt.dto.FileDTO;
import com.ottt.ottt.dto.UserDTO;
import com.ottt.ottt.service.board.BoardServiceImpl;
import com.ottt.ottt.service.board.FileServiceImpl;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardServiceImpl boardService;
	@Autowired
	FileServiceImpl fileService;
	@Autowired
	LoginUserDao loginUserDao;
	
	//글 목록
	@GetMapping(value = "/board")
	public String boardList(Integer article_no,HttpSession session, SearchItem sc, Model m) throws Exception {

		try {
			int totalCnt = boardService.getCount(sc);
			m.addAttribute("totalCnt", totalCnt);
			
			PageResolver pageResolver = new PageResolver(totalCnt, sc);
			
			List<ArticleDTO> list = boardService.getPage(sc);
			m.addAttribute("list", list);
			m.addAttribute("pr",pageResolver);
			
			if(session.getAttribute("id") !=null) {
				/* 방식이 이해가 안감 - 중간의 String을 쓰는 이유는? */
				UserDTO userDTO = loginUserDao.select((String) session.getAttribute("id"));
				m.addAttribute("userDTO", userDTO);
			}
			
		} catch (Exception e) {e.printStackTrace();}
		
		return "/board/board";

	}
	
	
	@GetMapping("/board/read")
	public String read(Integer article_no, SearchItem sc, Model m, HttpSession session) {

		try {
			
			ArticleDTO articleDTO = boardService.getArticle(article_no);
			boardService.hitCount(article_no);
			
			List<FileDTO> fileList = fileService.selectFileList(articleDTO);
			
			m.addAttribute("articleDTO", articleDTO);
			m.addAttribute("fileList", fileList);
			
			if(session.getAttribute("id") != null) {
				UserDTO userDTO = loginUserDao.select((String) session.getAttribute("id"));
				m.addAttribute("userDTO", userDTO);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/board/board";
		}
		return "/board/boardPost";
	}
	
	
	
	@GetMapping("/board/write")
	public String write(Model m) {
		m.addAttribute("mode", "new");
		return "/board/boardPost";
	}
	
	@PostMapping("/board/write")
	public String writePost(ArticleDTO articleDTO, RedirectAttributes rattr, Model m, HttpSession session) {

			System.out.println(">>>>>>>>>>>/board/write>>>>>>>>>>");
			System.out.println("/board/write articleDTO >>>>>>>>>>> "+articleDTO.toString());
	
			String writer = (String) session.getAttribute("id");
	
			UserDTO userDTO = loginUserDao.select(writer);
	
			articleDTO.setUser_no(userDTO.getUser_no());
			articleDTO.setBaseball(String.join(",", articleDTO.getBaseballArray()));
			articleDTO.setCategory(articleDTO.getCategory());
			articleDTO.setSex(articleDTO.getSex());

			try {
	
					if(boardService.insert(articleDTO) != 1) {
							throw new Exception("WRITE FAIL!");
					}
					
					//마지막 아티클번호 가져오는 sql 쿼리문을 추가해야함.
					Integer getArticle_no = boardService.selectLastArticleNo();		
					fileService.uploadFiles(articleDTO.getUpfiles(), getArticle_no);
					
					
					
					//fileService.uploadFiles(articleDTO.getUpFiles(), getArticle_no);		//null에다가 마지막 아티클넘버 가져와야함
					
					rattr.addFlashAttribute("msg", "WRT_OK");
					
					return "redirect:/board/board";
				
			} catch (Exception e) {
	
					e.printStackTrace();
					m.addAttribute("mode", "new");			//글쓰기 모드
					m.addAttribute("articleDTO", articleDTO);	//등록하려던 내용을 보여줘야함
					m.addAttribute("msg", "WRT_ERR");
		
					return "/board/boardPost";
			
			}
			
	}
	
	
	@PostMapping("/board/update")
	public String modify(ArticleDTO articleDTO, RedirectAttributes rattr, Model m, HttpSession session, Integer page, Integer pageSize) {
		try {


			articleDTO.setBaseball(String.join(",", articleDTO.getBaseballArray()));
			int a = boardService.update(articleDTO);

			if(a != 1) {
				throw new Exception("Update failed");
			}
			
			Integer getArticle_no =boardService.getArticle(articleDTO.getArticle_no()).getArticle_no();		
			fileService.uploadFiles(articleDTO.getUpfiles(), getArticle_no);

			rattr.addAttribute("page", page);
			rattr.addAttribute("pageSize", pageSize);
			rattr.addFlashAttribute("msg", "MOD_OK");
			return "redirect:/board/board/read?page="+page+"&pageSize="+pageSize+"&article_no="+articleDTO.getArticle_no();

		} catch (Exception e) {

			e.printStackTrace();
			m.addAttribute(articleDTO);
			m.addAttribute("page", page);
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("msg", "MOD_ERR");		
			return "/board/boardPost";
		}
	}
	
	@PostMapping("/board/delete")
	public String remove(Integer article_no, Integer page, Integer pageSize, RedirectAttributes rattr) {	
		String msg = "DEL_OK";
		try {
			if(boardService.delete(article_no) != 1) {
				throw new Exception("Delete failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "DEL_ERR";
		}
		
		rattr.addAttribute("page", page);
		rattr.addAttribute("pageSize", pageSize);
		rattr.addFlashAttribute("msg", msg);
		
		return "redirect:/board/board";
	}
	
	

		//기본 파일업로드 경로
		public static final String ROOT_FILE_PATH = "D:/workspace/OTTT0719/src/main/webapp/resources/upload";
	
		@GetMapping("/board/download")
		public ResponseEntity<Resource> downloadFile(@RequestParam("file_no") Integer file_no) throws Exception {
			
			// 파일 정보를 가져옴 (이 예시에서는 FileService를 사용하여 파일 정보를 가져옴)
			FileDTO fileDTO = fileService.selectFile(file_no);

			if (fileDTO != null) {
				// 파일 데이터를 읽어와 ByteArrayResource로 변환
	            Path filePath = Paths.get(fileDTO.getFile_path());

	            try {
	            	
	            	byte[] fileBytes = Files.readAllBytes(filePath);
	            	ByteArrayResource byteArrayResource = new ByteArrayResource(fileBytes);
	                
	                HttpHeaders headers = new HttpHeaders();
	                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	                String encodedFileName = UriUtils.encode(fileDTO.getFile_name(), StandardCharsets.UTF_8);
	                headers.setContentDispositionFormData("attachment", encodedFileName);
	                
	                return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);

	            } catch (IOException e) {
	            
	            	// 파일 읽기 중에 오류 발생 시 처리
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	            }
	        } else {
	            // 파일이 없는 경우에 대한 처리
	            return ResponseEntity.notFound().build();
	        }
	    }
		
		@GetMapping("/board/deletefile")
	    public String  deleteFile(@RequestParam("file_no") Integer file_no, RedirectAttributes rattr, ArticleDTO articleDTO ) throws Exception {
		    try {
		        FileDTO fileDTO = fileService.selectFile(file_no);
		       
		
		        if (fileDTO != null) {
		            try {
		                // 파일을 물리적으로 삭제
		                Path filePath = Paths.get(fileDTO.getFile_path());
		                Files.deleteIfExists(filePath);
	
		                // DB에서 파일 정보 삭제
		                fileService.deleteFile(file_no);
		                
		            } catch (IOException e) {
		            }
		        } 

	    	} catch (Exception e) {
	    }

	    return  "redirect:/board/board"; // 파일 삭제 결과를 표시할 JSP 페이지로 이동
	}
}