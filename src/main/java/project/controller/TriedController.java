package project.controller;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import project.dto.TriedCommentDto;
import project.dto.TriedDto;
import project.service.TriedService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TriedController {

	final String UPLOAD_DIR = "C:/java/upload/";

	@Autowired
	private TriedService service;


	// 1. 조회
	// 1-1. 어디까지 필터별 보기
	@GetMapping("/api/tried/{triedCategoryIdx}/{order}/{syear}/{lyear}/{pages}")
	public ResponseEntity<List<TriedDto>> selectTriedList(@PathVariable("triedCategoryIdx") int triedCategoryIdx,
			@PathVariable("order") String order, @PathVariable("syear") Date syear, @PathVariable("lyear") Date lyear,
			@PathVariable("pages") int pages) throws Exception {

		TriedDto triedDto = new TriedDto();
		pages = (pages - 1) * 10;
		triedDto.setPages(pages);
		triedDto.setTriedCategoryIdx(triedCategoryIdx);

		java.sql.Date sqlDate = new java.sql.Date(syear.getTime());
		triedDto.setSyear(sqlDate);

		sqlDate = new java.sql.Date(lyear.getTime());
		triedDto.setLyear(sqlDate);

		List<TriedDto> triedDtoList;

		System.out.println(">>>>>>"+triedDto.toString());
		
		if (order.equals("recent")) {

			triedDtoList = service.selectTriedRecent(triedDto);
		} else if (order.equals("count")) {

			triedDtoList = service.selectTriedCount(triedDto);
		} else if (order.equals("rcmd")) {

			triedDtoList = service.selectTriedRecommend(triedDto);
		} else {
			triedDtoList = service.selectTriedRecent(triedDto);
		}

		if (triedDtoList.size() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(triedDtoList);
		}
	}

	// 1-2. 어디까지 필터별 총 페이지 수
	@GetMapping("/api/tried/totalpage/{triedCategoryIdx}/{order}/{syear}/{lyear}/{pages}")
	public ResponseEntity<Integer> selectTriedListTotalPage(@PathVariable("triedCategoryIdx") int triedCategoryIdx,
			@PathVariable("order") String order, @PathVariable("syear") Date syear, @PathVariable("lyear") Date lyear,
			@PathVariable("pages") int pages) throws Exception {

		TriedDto triedDto = new TriedDto();
		pages = (pages - 1) * 10;
		triedDto.setPages(pages);
		triedDto.setTriedCategoryIdx(triedCategoryIdx);

		java.sql.Date sqlDate = new java.sql.Date(syear.getTime());
		triedDto.setSyear(sqlDate);

		sqlDate = new java.sql.Date(lyear.getTime());
		triedDto.setLyear(sqlDate);

		int totalPage;

		if (order == "recent") {
			totalPage = service.selectTriedRecentTotalPage(triedDto);
		} else if (order == "count") {
			totalPage = service.selectTriedCountTotalPage(triedDto);
		} else if (order == "recommend") {
			totalPage = service.selectTriedRecommendTotalPage(triedDto);
		} else {
			totalPage = service.selectTriedRecentTotalPage(triedDto);
		}

		if (totalPage == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(totalPage);
		}
	}

	// 1-3. 어디까지 상세페이지 조회
	@GetMapping("/api/tried/detail/{triedIdx}")
	public ResponseEntity<TriedDto> openTriedDetail(@PathVariable("triedIdx") int triedIdx) throws Exception {
		TriedDto triedDto = service.selectTriedDetail(triedIdx);
		if (triedDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(triedDto);
		}
	}


	//2. 어디까지 글쓰기 저장(upload에서 바꿈)
	@PostMapping("/api/tried")
	public ResponseEntity<String> uploadTried(
			@RequestPart(value = "triedImg", required = false) MultipartFile[] triedImg,
			@RequestPart(value = "data", required = false) TriedDto data) throws Exception {

		List<Map<String, String>> resultList = saveFiles(triedImg);
		for (Map<String, String> result : resultList) {
			String triedImage = result.get("savedFileName");
			data.setTriedImg(triedImage);
		}

		service.insertTried(data);

		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	//사진(다수) 저장 함수
	private List<Map<String, String>> saveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>" + originFileName);
				String savedFileName = System.currentTimeMillis() + originFileName;

				try {
					File f = new File(UPLOAD_DIR + savedFileName);
					mf.transferTo(f);

					Map<String, String> result = new HashMap<>();
					result.put("originalFileName", originFileName);
					result.put("savedFileName", savedFileName);
					resultList.add(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	//사진(단일) 저장 함수
	private String saveFile(MultipartFile file) {
		if (file != null) {
			String originFileName = file.getOriginalFilename();
			System.out.println(">>>>>>>>>>>>>" + originFileName);
			String savedFileName = System.currentTimeMillis() + originFileName;

			try {
				File f = new File(UPLOAD_DIR + savedFileName);
				file.transferTo(f);
				return savedFileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 3. 어디까지 글쓰기 사진 수정(reupload에서 바꿈)
	@PutMapping("/api/tried/{triedIdx}")
	public ResponseEntity<String> reuploadTried(@PathVariable("triedIdx") int triedIdx,
			@RequestPart(value = "updateImg", required = false) MultipartFile[] updateImg,
			@RequestPart(value = "data") TriedDto data) throws Exception {
		
		data.setTriedIdx(triedIdx);

		List<Map<String, String>> resultList = resaveFiles(updateImg);
		for (Map<String, String> result : resultList) {
			String triedImage = result.get("savedFileName");
			data.setTriedImg(triedImage);
		}

		int resultCnt = service.updateTried(data);

		if (resultCnt != 0) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	//사진 재저장 함수
	private List<Map<String, String>> resaveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>" + originFileName);
				String savedFileName = System.currentTimeMillis() + originFileName;
				//
//						try {
//							File f = new File(UPLOAD_DIR + ); // 3 ??
//							mf.transferTo(f);

				try {
					File dir = new File(UPLOAD_DIR);
					if (!dir.exists()) {
						dir.mkdir();
					}
					File f = new File(dir, savedFileName);
					mf.transferTo(f);

					Map<String, String> result = new HashMap<>();
					result.put("originalFileName", originFileName);
					result.put("savedFileName", savedFileName);
					resultList.add(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	// 4. 어디까지 삭제
	@DeleteMapping("/api/tried/{triedIdx}")
	public ResponseEntity<Integer> deleteTried(@PathVariable("triedIdx") int triedIdx) throws Exception {
		int deletedCount = service.deleteTried(triedIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}
	
	
	// 5-1. 어디까지 댓글 조회
	@GetMapping("/api/tried/comment/{triedIdx}")
	public ResponseEntity<List<TriedCommentDto>> selectTriedComment(@PathVariable("triedIdx") int triedIdx) throws Exception{
		
		
		List<TriedCommentDto> selectCommentList = service.selectTriedComment(triedIdx);
		if (selectCommentList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(selectCommentList);
		}
	}
	
	// 5-2. 어디까지 댓글 입력
	@PostMapping("/api/tried/comment/{triedIdx}")
	public ResponseEntity<Integer> insertTriedComment(@RequestBody TriedCommentDto triedComment) throws Exception{
		
		int insertedCnt = service.insertTriedComment(triedComment);
		if (insertedCnt == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(insertedCnt);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertedCnt);
		}
	}
	
	// 5-3. 어디까지 댓글 수정
	@PutMapping("/api/tried/comment/{triedCommentIdx}")
	public ResponseEntity<Integer> updateTriedComment(@RequestBody TriedCommentDto triedComment) throws Exception {
		int updatedCount = service.updateTriedComment(triedComment);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}
	
	// 5-4. 어디까지 댓글 삭제
	@DeleteMapping("/api/tried/comment/{triedCommentIdx}")
	public ResponseEntity<Integer> deleteTriedComment(@PathVariable("triedCommentIdx") int triedCommentIdx) throws Exception{
		
		int deletedCnt = service.deleteTriedComment(triedCommentIdx);
		if (deletedCnt == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCnt);
		}
	}
	
	// 6-1. 어디까지 게시글 유저 추천 확인
	@GetMapping("/api/tried/rcmd/user")
	public ResponseEntity<Integer> selectTriedRcmdByUserId(@RequestParam("triedIdx") int triedIdx, @RequestParam("userId") String userId) throws Exception{
	
		int checkRcmdCnt = service.selectTriedRcmdByUserId(triedIdx, userId);
		
		if (checkRcmdCnt == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(checkRcmdCnt);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(checkRcmdCnt);
		}
	}
	
	// 6-2. 어디까지 게시글 추천 추가
	@PostMapping("/api/tried/rcmd/user")
	public ResponseEntity<Integer> insertTriedRcmd(@RequestParam("triedIdx") int triedIdx, @RequestParam("userId") String userId) throws Exception{
	
		int insertedRcmdCnt = service.insertTriedRcmd(triedIdx, userId);
		
		if (insertedRcmdCnt == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(insertedRcmdCnt);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertedRcmdCnt);
		}
	}
	
	// 6-3. 어디까지 게시글 추천 삭제
	@DeleteMapping("/api/tried/rcmd/user")
	public ResponseEntity<Integer> deleteTriedRcmd(@RequestParam("triedIdx") int triedIdx, @RequestParam("userId") String userId) throws Exception{
	
		int deletedRcmdCnt = service.deleteTriedRcmd(triedIdx, userId);
		
		if (deletedRcmdCnt == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(deletedRcmdCnt);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedRcmdCnt);
		}
	}
	
	// 6-4. 어디까지 게시글 추천수 조회
	@GetMapping("/api/tried/rcmd/{triedIdx}")
	public ResponseEntity<Integer> selectTriedRcmdCount(@RequestParam("triedIdx") int triedIdx) throws Exception{
	
		int rcmdCnt = service.selectTriedRcmdCount(triedIdx);
		
		if (rcmdCnt == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(rcmdCnt);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(rcmdCnt);
		}
	}


}