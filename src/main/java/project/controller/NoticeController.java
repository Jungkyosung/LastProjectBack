package project.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.NoticeDto;
import project.service.NoticeService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoticeController {
	
	@Autowired
	NoticeService service;

	// 공지사항 리스트 조회
	@GetMapping("/api/noticelist")
	public ResponseEntity<List<NoticeDto>> listNotice() throws Exception {
		List<NoticeDto> NoticetDto = service.listNoticeDto();
		if (NoticetDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(NoticetDto);
		}
	}

	// 공지사항 글별 상세페이지 조회
	@GetMapping("/api/notice/{noticeIdx}")
	public ResponseEntity<NoticeDto> noticeDetail(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
		NoticeDto noticeDto = service.noticeDetail(noticeIdx);
		if (noticeDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(noticeDto);
		}
	}

	// 공지사항 작성
	@PostMapping("/api/notice/write")
	public ResponseEntity<String> insertNotice(@RequestBody NoticeDto noticeDto) throws Exception {
		int insertedCount = service.insertNotice(noticeDto);
		if (insertedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성을 실패하였습니다");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("작성을 성공하였습니다");
		}
	}

	// 공지사항 삭제
	@DeleteMapping("/api/notice/{noticeIdx}")
	public ResponseEntity<Integer> deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
		int deletedCount = service.deleteNotice(noticeIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}

	// 공지사항 수정
	@PutMapping("/api/notice/update/{noticeIdx}")
	public ResponseEntity<Integer> updateNotice(@PathVariable("noticeIdx") int noticeIdx,
			@RequestBody NoticeDto noticeDto) throws Exception {
		noticeDto.setNoticeIdx(noticeIdx);
		int updatedCount = service.updateNotice(noticeDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}
}
