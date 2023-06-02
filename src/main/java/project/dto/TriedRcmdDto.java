package project.dto;

import lombok.Data;

@Data
public class TriedRcmdDto {
	
	private int triedRcmdIdx;	//추천인덱스
	private int triedIdx;		//어디까지 글 인덱스(외래키)
	private String userId;		//유저아이디(외래키)

}
