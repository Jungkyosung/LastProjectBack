package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.TravelcourseCommentDto;
import project.dto.TravelcourseDetailDto;
import project.dto.TravelcourseDetailListDto;
import project.dto.TravelcourseDto;
import project.dto.TravelcourseImgDto;
import project.dto.TravelcourseListDto;
import project.mapper.TravelcourseMapper;

@Service
public class TravelcourseService {

	@Autowired
	private TravelcourseMapper mapper;

	

	//1-1.여행코스 입력(제목, 시작일, 끝일, 작성자)
	public int insertCourse(TravelcourseDto travelcourseDto) throws Exception {
		return mapper.insertCourse(travelcourseDto);
	}
	
	//1-1-1. 여행코스 DAY별 입력 (DAY, Day설명, 여행코스idx)
	public int insertCourseDay(TravelcourseDetailListDto travelcourseDetailListDto) throws Exception{
		return mapper.insertCourseDay(travelcourseDetailListDto);
	}
	
	//1-1-2. 여행코스 DAY별 Dayinfo별 입력 (order, lat, lng, placeName, listIdx)
	public int insertCourseDayinfo(List<TravelcourseDetailDto> travelcourseDetailDto) throws Exception{
		return mapper.insertCourseDayinfo(travelcourseDetailDto);
	}
	
	//1-1-3. 여행코스 이미지 입력
	public int insertCourseImg(TravelcourseImgDto travelcourseImgDto) throws Exception{
		return mapper.insertCourseImg(travelcourseImgDto);
	}
	
	//1-2. 여행코스 list 조회
	public List<TravelcourseListDto> selectCourseList() throws Exception {
		return mapper.selectCourseList();
	}
	
	//1-2. 여행코스 list 조회2, 검색, 페이지
	public List<TravelcourseDto> openCouseList2(int pages, String search) throws Exception{
	
		pages = (pages - 1) * 15;
		
		List<TravelcourseDto> travelcourse = mapper.selectTravelcourse(pages, search);
		List<TravelcourseDetailListDto> travelcourseList = mapper.selectTravelcourseList(pages, search);
		List<TravelcourseDetailDto> travelcourseDetail = mapper.selectTravelcourseDetail(pages, search);
		List<TravelcourseImgDto> travelcourseImg = mapper.selectTravelcourseImg(pages, search);
		
		//list Idx 같으면 dayinfo에 추가해 줌.
		for (int i=0; i< travelcourseList.size(); i++) {
			List<TravelcourseDetailDto> tcd = new ArrayList<>();
			for(int j=0; j < travelcourseDetail.size(); j++) {
				if(travelcourseList.get(i).getTravelcourseListIdx() == travelcourseDetail.get(j).getTravelcourseListIdx()) {
					tcd.add(travelcourseDetail.get(j));
				} else {
					
				}
			}
			travelcourseList.get(i).setDayinfo(tcd);
		}
		
		// course Idx 같으면, list에 추가해 줌.
		for (int i=0; i< travelcourse.size(); i++) {
			List<TravelcourseDetailListDto> tcl = new ArrayList<>();
			for (int j=0; j< travelcourseList.size(); j++) {
				if(travelcourse.get(i).getTravelcourseIdx() == travelcourseList.get(j).getTravelcourseIdx()) {
					tcl.add(travelcourseList.get(j));
				} else {
					
				}
			}
			travelcourse.get(i).setTravelcourseDetailList(tcl);
		}
		
		for (int i=0; i< travelcourse.size(); i++) {
			for(int j=0; j< travelcourseImg.size(); j++) {
				if( travelcourse.get(i).getTravelcourseIdx() == travelcourseImg.get(j).getTravelcourseIdx()) {
					String img = travelcourseImg.get(j).getTravelcourseImg();
					travelcourse.get(i).setTravelcourseImg(img);
				} else {
					
				}
			}
		}
		
		
		return travelcourse;
	}
	
	//검색어 기준 총 페이지 수 조회
	public int selectTravelcourseTotalPages(String search) throws Exception{
		return mapper.selectTravelcourseTotalPages(search);
	}
				
	//1-3-1. 여행코스 개별 detail 조회
	public TravelcourseListDto selectCourseDetail(int travelcourseIdx) throws Exception {
		return mapper.selectCourseDetail(travelcourseIdx);
	}
	
	//1-3-2. 여행코스 개별 detail 이미지 조회
	public List<TravelcourseImgDto> selectCourseDetailImg(int travelcourseIdx) throws Exception{
		return mapper.selectCourseDetailImg(travelcourseIdx);
	}

	public int updateCourse(TravelcourseDto travelcourseDto) throws Exception {
		int updatedCount = mapper.updateCourse(travelcourseDto);
		return updatedCount;
	}

	//여행코스 목록에서 삭제
	public int deleteCourse(int travelcourseIdx) throws Exception {
		int deletedCount = mapper.deleteCourse(travelcourseIdx);
		return deletedCount;
	}
	
	//2-1. 여행코스 댓글 조회
	public List<TravelcourseCommentDto> selectTravelcourseComment(int travelcourseIdx) throws Exception{
		return mapper.selectTravelcourseComment(travelcourseIdx);
	}
	
	
	//2-2. 여행코스 댓글 입력
	public int insertTravelcourseComment(TravelcourseCommentDto travelcourseComment) throws Exception{
		return mapper.insertTravelcourseComment(travelcourseComment);
	}
	
	//2-3. 여행코스 댓글 수정
	public int updateTravelcourseComment(TravelcourseCommentDto travelcourseComment) throws Exception{
		return mapper.updateTravelcourseComment(travelcourseComment);
	}
	
	//2-4. 여행코스 댓글 삭제
	public int deleteTravelcourseComment(int travelcourseCommentIdx) throws Exception{
		return mapper.deleteTravelcourseComment(travelcourseCommentIdx);
	}

}
