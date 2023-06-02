package project.dto;

import lombok.Data;

@Data
public class TravelcourseCommentDto {

	private int travelcourseCommentIdx;	//여행코스 댓글 인덱스
	private String travelcourseComment; // 댓글 내용
	private String travelcourseCommentTime; // 댓글 작성 시간
	private String userId;					//유저 아이디(외래키)
	private String userNickname;			//유저닉네임(조인)
	private String userImg;					//유저 프사(조인)
	private int travelcourseIdx;			//여행코스 인덱스(외래키)
	
}
