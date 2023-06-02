package project.dto;

import lombok.Data;

@Data
public class RcmdDto {
	
	int rcmdIdx; // 좋아요 값
	String userId; // 사용자
	int idealrealIdx; // 글 인덱스 값
}
