package project.dto;

import lombok.Data;

@Data
public class AdminDto {

	private int count;		//일자별 글 갯수
	private String day; 	//일자
	private String month;	//월자
}
