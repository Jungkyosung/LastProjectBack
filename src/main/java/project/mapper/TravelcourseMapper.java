package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.TravelcourseCommentDto;
import project.dto.TravelcourseDetailDto;
import project.dto.TravelcourseDetailListDto;
import project.dto.TravelcourseDto;
import project.dto.TravelcourseImgDto;
import project.dto.TravelcourseListDto;

@Mapper
public interface TravelcourseMapper {

	//1-1.여행코스 입력(제목, 시작일, 끝일, 작성자)
	int insertCourse(TravelcourseDto travelcourseDto) throws Exception;

	//1-1-1. 여행코스 DAY별 입력 (DAY, Day설명, 여행코스idx)
	int insertCourseDay(TravelcourseDetailListDto travelcourseDetailListDto) throws Exception;
	
	//1-1-2. 여행코스 DAY별 Dayinfo별 입력 (order, lat, lng, placeName, listIdx)
	int insertCourseDayinfo(List<TravelcourseDetailDto> travelcourseDetailDto) throws Exception;
	
	//1-1-3. 여행코스 이미지 입력
	int insertCourseImg(TravelcourseImgDto travelcourseImgDto) throws Exception;
	
	//1-2. 여행코스 list 조회
	List<TravelcourseListDto> selectCourseList() throws Exception;
	
	//1-2-1. 여행코스 list 조회
	List<TravelcourseDto> selectTravelcourse(@Param("pages") int pages, @Param("search") String search ) throws Exception;
	
	//1-2-2. 여행코스 Day정보 조회
	List<TravelcourseDetailListDto> selectTravelcourseList(@Param("pages") int pages, @Param("search") String search ) throws Exception;
	
	//1-2-3. 여행코스 Dayinfo정보 조회
	List<TravelcourseDetailDto> selectTravelcourseDetail(@Param("pages") int pages, @Param("search") String search ) throws Exception;
	
	//1-2-4. 여행코스 이미지 정보 조회(글별 하나씩만 리스트용)
	List<TravelcourseImgDto> selectTravelcourseImg(@Param("pages") int pages, @Param("search") String search ) throws Exception;
	
	//검색어 기준 총 페이지수 조회
	int selectTravelcourseTotalPages(@Param("search") String search) throws Exception;
	
	//1-3-1. 여행코스 개별 detail 조회
	TravelcourseListDto selectCourseDetail(int travelcourseDtoIdx) throws Exception;

	//1-3-2. 여행코스 개별 detail img 조회
	List<TravelcourseImgDto> selectCourseDetailImg(int travelcourseIdx) throws Exception;
	

	int updateCourse(TravelcourseDto travelcourseDto) throws Exception;

	//여행코스 목록에서 삭제
	int deleteCourse(int travelcourseIdx) throws Exception;
	
	//2-1. 여행코스 댓글 조회
	List<TravelcourseCommentDto> selectTravelcourseComment(int travelcourseIdx) throws Exception;
	
	//2-2. 여행코스 댓글 입력
	int insertTravelcourseComment(TravelcourseCommentDto travelcourseComment) throws Exception;
	
	//2-3. 여행코스 댓글 수정
	int updateTravelcourseComment(TravelcourseCommentDto travelcourseComment) throws Exception;
	
	//2-4. 여행코스 댓글 삭제
	int deleteTravelcourseComment(int travelcourseCommentIdx) throws Exception;
}
