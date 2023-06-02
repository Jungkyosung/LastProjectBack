package project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import project.dto.IdealrealDto;
import project.dto.RcmdDto;
import project.service.IdealrealService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IdealrealController {
	
	final String UPLOAD_DIR = "C:/java/upload/";
	
	@Autowired
	private IdealrealService service;

	// (1-1)이상과 현실 목록 출력
	@GetMapping("/api/listidealreal")
	public ResponseEntity<List<IdealrealDto>> listidealreal() throws Exception {
		List<IdealrealDto> list = service.selectIdealRealList();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// (4-1) 게시판 페이징
	@GetMapping("/api/listidealreal/{page}")
	public ResponseEntity<List<IdealrealDto>> listidealrealpage(@PathVariable("page") int page) throws Exception {
		List<IdealrealDto> list = service.selectIdealRealListPage(page);
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	// (4-2) 좋아요 게시판 페이징
	@GetMapping("/api/listidealrealwithlike/{page}")
	public ResponseEntity<List<IdealrealDto>> listidealrealwithlikepage(@PathVariable("page") int page)
			throws Exception {
		List<IdealrealDto> list = service.selectIdealRealListWithLikePage(page);
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// (4-3) 게시판 페이징 리스트
	@GetMapping("/api/listidealrealpagecount")
	public ResponseEntity<Integer> listidealrealpagecount() throws Exception {
		int pageCount = service.listidealrealpagecount();
		if (pageCount != 0) {
			return ResponseEntity.status(HttpStatus.OK).body(pageCount);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// (3-3) 좋아요 수 리스트
	@GetMapping("/api/listidealrealwithlike")
	public ResponseEntity<List<IdealrealDto>> listidealrealwithlike() throws Exception {
		List<IdealrealDto> list = service.selectIdealRealListWithLike();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// (1-2)이상과 현실 글쓰기
	@PostMapping("/api/listidealreal/write")
	public ResponseEntity<String> insertidealreal(@RequestBody IdealrealDto idealrealDto) throws Exception {
		try {
			System.out.println(idealrealDto);
			service.insertIdealreal(idealrealDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록오류");
		}
		return ResponseEntity.status(HttpStatus.OK).body("정상처리");
	}

	// (2-1)이상과 현실 상세페이지 조회
	@GetMapping("/api/listidealreal/detail/{idealrealIdx}")
	public ResponseEntity<IdealrealDto> idealrealDetail(@PathVariable("idealrealIdx") int idealrealIdx)
			throws Exception {
		IdealrealDto idealrealDto = service.selectIdealrealDetail(idealrealIdx);
		if (idealrealDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(idealrealDto);
		}
	}

	// (2-3-1)이상과 현실 글 수정에 사진 넣기(/reupload 에서 바꿈)
	@PutMapping("/api/idealreal/{idealrealIdx}")
	public ResponseEntity<String> reuploadIdealreal(@PathVariable("idealrealIdx") int idealrealIdx,
			@RequestPart(value = "idealrealIdealImg", required = false) MultipartFile[] idealrealIdealImg,
			@RequestPart(value = "idealrealRealImg", required = false) MultipartFile[] idealrealRealImg,
			@RequestPart(value = "data", required = false) IdealrealDto data) throws Exception {

		List<Map<String, String>> resultList = resaveFiles(idealrealIdealImg);
		for (Map<String, String> result : resultList) {
			String idealImg = result.get("savedFileName");
			data.setIdealrealIdealImg(idealImg);
		}

		resultList = resaveFiles(idealrealRealImg);
		for (Map<String, String> result : resultList) {
			String realImg = result.get("savedFileName");
			data.setIdealrealRealImg(realImg);
		}

		service.updateIdealreal(data);

		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	private List<Map<String, String>> resaveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>>>>>>" + originFileName);
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

	// (2-4)이상과 현실 글 삭제
	@DeleteMapping("/api/listidealreal/{idealrealIdx}")
	public ResponseEntity<Integer> deleteIdealreal(@PathVariable("idealrealIdx") int idealrealIdx) throws Exception {
		int deletedCount = service.deleteIdealreal(idealrealIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}

	// (1-3)이상과 현실 글 쓰기에 사진 넣기(/upload에서 바꿈)
	@PostMapping("/api/idealreal")
	public ResponseEntity<String> uploadIdealreal(
			@RequestPart(value = "idealrealIdealImg", required = false) MultipartFile[] idealrealIdealImg,
			@RequestPart(value = "idealrealRealImg", required = false) MultipartFile[] idealrealRealImg,
			@RequestPart(value = "data", required = false) IdealrealDto data) throws Exception {

		List<Map<String, String>> resultList = saveFiles(idealrealIdealImg);
		for (Map<String, String> result : resultList) {
			String idealImg = result.get("savedFileName");
			data.setIdealrealIdealImg(idealImg);
		}

		resultList = saveFiles(idealrealRealImg);
		for (Map<String, String> result : resultList) {
			String realImg = result.get("savedFileName");
			data.setIdealrealRealImg(realImg);
		}

		service.insertIdealreal(data);

		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	private List<Map<String, String>> saveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>>>>>>" + originFileName);
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

//	// (2-2)이상과 현실 글에 사진 가져오기
//	@GetMapping("/api/getimage/{filename}")
//	public void getImage(@PathVariable("filename") String filename, HttpServletResponse response) throws Exception {
//		// Image를 읽어서 전달
//		FileInputStream fis = null;
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//
//		try {
//			response.setHeader("Content-Disposition", "inline;");
//
//			byte[] buf = new byte[1024];
//			fis = new FileInputStream(UPLOAD_DIR + filename);
//			bis = new BufferedInputStream(fis);
//			bos = new BufferedOutputStream(response.getOutputStream());
//			int read;
//			while ((read = bis.read(buf, 0, 1024)) != -1) {
//				bos.write(buf, 0, read);
//			}
//		} finally {
//			bos.close();
//			bis.close();
//			fis.close();
//		}
//	}

	// (3-1) 좋아요 상세
	@GetMapping("/api/{idealrealIdx}/getlike")
	public ResponseEntity<Integer> openGetLike(@PathVariable("idealrealIdx") int idealrealIdx) throws Exception {
		int count = service.selectLikesCount(idealrealIdx);
//		if (count == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(rcmdDto);
//		}
		return ResponseEntity.status(HttpStatus.OK).body(count);
	}

	// (3-2) 사용자 : 좋아요 수 업데이트
	@PostMapping("/api/{rcmdIdx}/like")
	public ResponseEntity<Integer> insertLike(@PathVariable("rcmdIdx") int rcmdIdx, @RequestBody RcmdDto rcmdDto)
			throws Exception {
		int updatedCount = service.updateLikesCount(rcmdDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}

	// (3-3) 좋아요 취소
	@DeleteMapping("/api/{rcmdIdx}/unlike")
	public ResponseEntity<Integer> deleteLike(@PathVariable("rcmdIdx") int rcmdIdx, @RequestBody RcmdDto rcmdDto)
			throws Exception {
		int updatedCount = service.deleteLikesCount(rcmdDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}
	
	
	// (3-4) 사용자 좋아요 체크
	@GetMapping("/api/listidealreal/detail/likecheck/{idealrealIdx}/{userId}")
	public ResponseEntity<Integer> userLikeCheck(@PathVariable("idealrealIdx") int idealrealIdx, @PathVariable("userId") String userId)
			throws Exception {
		userId = userId.replace("-",".");
		int likeCheckCount = service.selectLikesCountByUser(idealrealIdx, userId);
		if (likeCheckCount != 1) {
			return ResponseEntity.status(HttpStatus.OK).body(likeCheckCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(likeCheckCount);
		}
	}
}
