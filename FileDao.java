package com.ottt.ottt.dao.board;




import java.util.List;

import com.ottt.ottt.dto.ArticleDTO;
import com.ottt.ottt.dto.FileDTO;



public interface FileDao {
	
	int insertFile(FileDTO fileDTO) throws Exception;

	List<FileDTO> selectFileList(ArticleDTO article_no)throws Exception;
	
	//파일목록 화면에 가져오기 숙제
	//board.xml id="selectFileList" 결과값
	//board.xml > dao > service > controller > jsp
	//jsp forEach에 fileList라고 정의했으니 컨트롤러에서 fileList명으로 담으면됩니다
	//학생님 하실수있겠죠? 해보겠습니다!
	//넵 ㅎㅎ 그럼 또 연락주세요 
	// step1. 파일등록(저장) > step2. 파일목록표시 > step3. 파일다운로드 > step4. 파일삭제및 재등록
	//현재 스탭2입니다. 참고해주세요 네!! 그럼이만 ㅎㅎ
	
}
