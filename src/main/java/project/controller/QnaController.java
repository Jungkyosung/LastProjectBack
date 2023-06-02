package project.controller;

import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.QnaCommentDto;
import project.dto.QnaDto;
import project.service.QnaService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QnaController {

	@Autowired
	QnaService service;

	// QNA 작성
	@PostMapping("/api/qna/write")
	public ResponseEntity<String> insertQna(@RequestBody QnaDto qnaDto) throws Exception {
		int insertedCount = service.insertQna(qnaDto);
		if (insertedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성을 실패하였습니다");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("작성을 성공하였습니다");
		}
	}

	// QNA 삭제
	@DeleteMapping("/api/qna/{qnaIdx}")
	public ResponseEntity<Integer> deleteQna(@PathVariable("qnaIdx") int qnaIdx) throws Exception {
		int deletedCount = service.deleteQna(qnaIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}

	// QNA 수정
	@PutMapping("/api/qna/update/{qnaIdx}")
	public ResponseEntity<Integer> updateQna(@PathVariable("qnaIdx") int qnaIdx, @RequestBody QnaDto qnaDto)
			throws Exception {
		qnaDto.setQnaIdx(qnaIdx);
		int updatedCount = service.updateQna(qnaDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}

	// QNA 페이징 및 검색
	@GetMapping("/api/qnalistbypage")
	public ResponseEntity<List<QnaDto>> listQnaDtoByPages(@RequestParam("pages") int pages,
			@RequestParam("search") String search) throws Exception {

		String decodeSearch = URLDecoder.decode(search, "UTF-8");
		List<QnaDto> QnaDto = service.listQnaDtoByPages(pages, decodeSearch);

		if (QnaDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(QnaDto);
		}
	}

	// QNA 페이지수 조회
	@GetMapping("/api/qnapagecount")
	public ResponseEntity<Integer> listPageCount(@RequestParam("pages") int pages,
			@RequestParam("search") String search) throws Exception {
		String decodeSearch = URLDecoder.decode(search, "UTF-8");

		int pCount = service.listQnaDtoSearchPageCount(decodeSearch);

		return ResponseEntity.status(HttpStatus.OK).body(pCount);
	}

	// QNA 상세페이지 COMMENTS 리스트
	@GetMapping("/api/qna/{qnaIdx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> qnaDetail(@PathVariable("qnaIdx") int qnaIdx) throws Exception {
		List<QnaCommentDto> list = service.selectCommentList(qnaIdx);
		QnaDto qnaDto = service.selectQnaInfo(qnaIdx);

		Map<String, Object> result = new HashMap<>();

		result.put("selectCommentList", list);
		result.put("selectQnaInfo", qnaDto);
		if (result != null && result.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// QNA COMMENTS 등록
	@PostMapping("/api/qna/comments/write/{qnaIdx}")
	public ResponseEntity<Map<String, Object>> insertcomments(@RequestBody QnaCommentDto qnaCommentDto,
			@PathVariable("qnaIdx") int qnaIdx) throws Exception {
		int insertedCount = 0;
		try {
			qnaCommentDto.setQnaIdx(qnaIdx);
			insertedCount = service.insertComment(qnaCommentDto);
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
				result.put("commentIdx", qnaCommentDto.getQnaCommentIdx());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}
}
