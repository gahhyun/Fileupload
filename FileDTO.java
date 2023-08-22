package com.ottt.ottt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor	//파라미터 없는 생성자를 생성하
@AllArgsConstructor	//모든 필드를 가진 생성자를 생성합니다.
public class FileDTO {
	private	Integer	article_no;		/*파일을 등록한 게시판 번호*/
	private	Integer file_no;		/*파일 키*/
	private	String file_name;		/*파일명*/
	private	String file_re_name;	/*파일암호화명*/
	private	String file_path;		/*파일경로*/
	private	String file_ext;		/*파일 확장자*/
	private	Long file_size;			/*파일 사이즈*/
	private	String file_reg_date;	/*파일등록일*/
}
