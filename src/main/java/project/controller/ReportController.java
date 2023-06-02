package project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.ReportDto;
import project.dto.UserDto;
import project.service.ReportService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportController {

	@Autowired
	private ReportService service;
	
	//1. 신고 입력
	@PostMapping("/api/report")
	public ResponseEntity<Integer> insertReportUser(@RequestBody ReportDto reportDto) throws Exception{
		
		int insertedCnt = service.insertReportUser(reportDto);
		
		if(insertedCnt == 0 ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertedCnt);
		}
		
	}
	
	// 2.최근 신고 내역 페이지별(30개) 조회 
	@GetMapping("/api/report/{page}")
	public ResponseEntity<List<ReportDto>> selectReportUserList(@PathVariable int page) throws Exception{
	
		List<ReportDto> reportList = service.selectReportUserList(page);
		
		if(reportList.size() == 0 ) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(reportList);
		}
	}
	
	// 2-1. 신고 내역 페이지수 조회
	@GetMapping("/api/report/pagecount")
	public ResponseEntity<Integer> selectReportPageCount() throws Exception{
	
		int reportPageCnt = service.selectReportPageCount();
		
		if(reportPageCnt == 0 ) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(reportPageCnt);
		}
	}
	
	// 3. 사용자 신고당한 횟수 및 게시판, 댓글 내역 조회
	@GetMapping("/api/report/reportedboardlist")
	public ResponseEntity<Map<String,Object>> selectReportedList(@RequestParam("reportReportedUser") String reportReportedUser) throws Exception{
	
		Map<String,Object> result = service.selectReportedList(reportReportedUser);
		
		if(result.size() == 0 ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}
	
	
	// 4. 피신고자 정지기한 입력
	@PutMapping("/api/report/suspension")
	public ResponseEntity<Integer> selectReportedSuspension(@RequestBody UserDto userDto) throws Exception{
		
		int insertedCnt = service.selectReportedSuspension(userDto);
		
		if(insertedCnt == 0 ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertedCnt);
		}
	}
	
	
	
}
