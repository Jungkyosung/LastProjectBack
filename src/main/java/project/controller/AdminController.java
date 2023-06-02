package project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.service.AdminService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

	@Autowired
	private AdminService service;

	// 월의 일자별 게시글별 개수 조회
	@GetMapping("/api/admin/daypostcnt")
	public ResponseEntity<Map<String, Object>> selectDayPostCount(@RequestParam("startDay") String startDay,
			@RequestParam("endDay") String endDay) throws Exception {

		Map<String, Object> result = service.selectDayPostCount(startDay, endDay);

		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

	// 연도의 월별 게시글별 개수 조회
	@GetMapping("/api/admin/monthpostcnt")
	public ResponseEntity<Map<String, Object>> selectMonthPostCount(@RequestParam("year") String year)
			throws Exception {

		Map<String, Object> result = service.selectMonthPostCount(year);

		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

}
