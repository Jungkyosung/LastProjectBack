package project.dto;

import lombok.Data;

@Data
public class TravelcourseImgDto {

	private int travelcourseImgIdx; //인덱스번호
	private String travelcourseImg; //이미지 이름
	private int travelcourseIdx;	//여행코스 인덱스(외래키)
	
}
