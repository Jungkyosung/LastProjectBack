package project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import project.dto.TravelcourseCommentDto;
import project.dto.TravelcourseDetailListDto;
import project.dto.TravelcourseDto;
import project.dto.TravelcourseImgDto;
import project.dto.TravelcourseListDto;
import project.imgTransform.ImgTransform;
import project.service.TravelcourseService;

@Slf4j
@RestController
public class TravelcourseController {

	private ImgTransform imgTransform = new ImgTransform();
	
	@Autowired
	private TravelcourseService service;

	
	//1-1.여행코스 입력(제목, 시작일, 끝일, 작성자)
	@PostMapping("/api/course")
	public ResponseEntity<String> insertCourse(@RequestPart(value="courseImg", required=false) MultipartFile[] courseImg,
			@RequestPart(value="data", required=false) TravelcourseDto travelcourseDto ) throws Exception {
		
		try {
			// 코스 입력 후 인덱스 번호 가져옴
			int insertedCount = service.insertCourse(travelcourseDto);
			int travelcourseIdx = travelcourseDto.getTravelcourseIdx();

			System.out.println(">>>>>"+travelcourseDto);
			
			// travelcourseDto에서 Day별 list 추출
			List<TravelcourseDetailListDto> courseData = travelcourseDto.getTravelcourseDetailList();

			// courseData에 travelcourseIdx 입력하기
			courseData.forEach(course -> course.setTravelcourseIdx(travelcourseIdx));

			// Day별 인서트 한개 하고 dayinfo 입력하기
			// foreach로 하고 싶지만, 순서가 어려워서 for문으로 하겠음.
			for (int i = 0; i < courseData.size(); i++) {
				// 1개의 day를 입력하고 반환 idx값을 반환 받음.
				int courseInsertedCount = service.insertCourseDay(courseData.get(i));
				int travelcourseListIdx = courseData.get(i).getTravelcourseListIdx();

				// 해당 값을 dayinfo에 다시 입력해줌
				courseData.get(i).getDayinfo().forEach(dayinfo -> dayinfo.setTravelcourseListIdx(travelcourseListIdx));

				System.out.println(courseData.get(i).getDayinfo());

				// xml에서 foreach문으로 반복 입력함.
				int dayinfoInsertedCount = service.insertCourseDayinfo(courseData.get(i).getDayinfo());
			}

			//이미지 등록 파일 여러개 입력 가능.
			TravelcourseImgDto travelcourseImgDto = new TravelcourseImgDto();
			travelcourseImgDto.setTravelcourseIdx(travelcourseIdx);
			
			List<Map<String, String>> resultList = imgTransform.saveFiles(courseImg);
			for (Map<String,String> result : resultList) {
				String img = result.get("savedFileName");
				travelcourseImgDto.setTravelcourseImg(img);
				service.insertCourseImg(travelcourseImgDto);
			}
			
			
			
			System.out.println(">>>>>>>>>" + insertedCount);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록오류");
		}
		return ResponseEntity.status(HttpStatus.OK).body("정상처리");
	}

	
	//페이지 검색어 기준 조회
	@GetMapping("/api/course")
	public ResponseEntity<List<TravelcourseDto>> openCouseList2(@RequestParam("pages")int pages,
			@RequestParam("search")String search) throws Exception{
		
		//페이지수와 검색어 기준으로 조회
		List<TravelcourseDto> travelcourse = service.openCouseList2(pages, search);
		
		System.out.println(travelcourse);
		if (travelcourse.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(travelcourse);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}
	
	//페이지 검색어 기준 총 페이지수 조회
	@GetMapping("/api/course/totalpages")
	public ResponseEntity<Integer> selectTravelcourseTotalpages(@RequestParam("search")String search) throws Exception{
		
		//페이지수와 검색어 기준으로 조회
		int travelcourseTotalpages = service.selectTravelcourseTotalPages(search);

		if (travelcourseTotalpages > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(travelcourseTotalpages);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}
	
	//1-2-1. 여행코스 개별 detail 조회
	@GetMapping("/api/course/{travelcourseIdx}")
	public ResponseEntity<TravelcourseListDto> openCourseDetail1(@PathVariable("travelcourseIdx") int travelcourseIdx)
			throws Exception {
		TravelcourseListDto travelcourseDto = service.selectCourseDetail(travelcourseIdx);
		if (travelcourseDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(travelcourseDto);
		}
	}
	
	//1-2-2. 여행코스 detail 이미지 조회
	@GetMapping("/api/course/img/{travelcourseIdx}")
	public ResponseEntity<List<TravelcourseImgDto>> selectCourseDetailImg(@PathVariable("travelcourseIdx") int travelcourseIdx)
			throws Exception {
		
		List<TravelcourseImgDto> travelcourseimg = service.selectCourseDetailImg(travelcourseIdx);
		if (travelcourseimg == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(travelcourseimg);
		}
	}
	
	//1-3. 여행코스 수정
	@PutMapping("/api/course/{travelcourseIdx}")
	public ResponseEntity<Integer> updateCourse(@PathVariable("travelcourseIdx") int travelcourseIdx,
			@RequestBody TravelcourseDto travelcourseDto) throws Exception {
		travelcourseDto.setTravelcourseIdx(travelcourseIdx);
		int updatedCount = service.updateCourse(travelcourseDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}

	//1-4. 여행코스 삭제
	@DeleteMapping("/api/course/{travelcourseIdx}")
	public ResponseEntity<Integer> deleteCourse(@PathVariable("travelcourseIdx") int travelcourseIdx) throws Exception {
		int deletedCount = service.deleteCourse(travelcourseIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}
	
	//2-1. 여행코스 댓글 조회
	@GetMapping("/api/course/comment/{travelcourseIdx}")
	public ResponseEntity<List<TravelcourseCommentDto>> selectCourseComment(@PathVariable("travelcourseIdx") int travelcourseIdx) throws Exception {
		List<TravelcourseCommentDto> commentList = service.selectTravelcourseComment(travelcourseIdx);
		if (commentList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(commentList);
		}
	}
	
	//2-2. 여행코스 댓글 입력
	@PostMapping("/api/course/comment/{travelcourseIdx}")
	public ResponseEntity<Integer> insertCourseComment(@RequestBody TravelcourseCommentDto travelcourseComment) throws Exception {
		int insertedCount = service.insertTravelcourseComment(travelcourseComment);
		if (insertedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(insertedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertedCount);
		}
	}
	
	
	//2-3. 여행코스 댓글 수정
	@PutMapping("/api/course/comment/{travelcourseCommentIdx}")
	public ResponseEntity<Integer> updateCourseComment(@RequestBody TravelcourseCommentDto travelcourseComment) throws Exception {
		int updatedCount = service.updateTravelcourseComment(travelcourseComment);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}
	
	//2-4. 여행코스 댓글 삭제
	@DeleteMapping("/api/course/comment/{travelcourseCommentIdx}")
	public ResponseEntity<Integer> deleteCourseComment(@PathVariable("travelcourseCommentIdx") int travelcourseCommentIdx) throws Exception {
		int deletedCount = service.deleteTravelcourseComment(travelcourseCommentIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}
	
	
	

}
