package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.QnaCommentDto;
import project.dto.QnaDto;
import project.mapper.QnaMapper;

@Service
public class QnaService {

	@Autowired
	private QnaMapper mapper;

	// QNA 작성
	public int insertQna(QnaDto qnaDto) throws Exception {
		return mapper.insertQna(qnaDto);
	}

	// QNA 삭제
	public int deleteQna(int qnaIdx) throws Exception {
		return mapper.deleteQna(qnaIdx);
	}

	// QNA 수정
	public int updateQna(QnaDto qnaDto) throws Exception {
		return mapper.updateQna(qnaDto);
	}

	// QNA 페이지별 조회
	public List<QnaDto> listQnaDtoByPages(int pages, String search) throws Exception {
		int offsetStart = (pages - 1) * 10;
		return mapper.listQnaDtoByPages(offsetStart, search);
	}

	// QNA 페이지수 조회
	public int listQnaDtoSearchPageCount(String search) throws Exception {
		return mapper.listQnaDtoSearchPageCount(search);
	}

	// QNA 상세페이지 COMMENT 리스트
	public List<QnaCommentDto> selectCommentList(int qnaIdx) throws Exception {
		return mapper.selectCommentList(qnaIdx);
	}

	// QNA 상세페이지 조회
	public QnaDto selectQnaInfo(int qnaIdx) throws Exception {
		return mapper.selectQnaInfo(qnaIdx);
	}

	// QNA 상세페이지 COMMENT 등록
	public int insertComment(QnaCommentDto qnaCommentDto) throws Exception {
		return mapper.insertComment(qnaCommentDto);
	}

}
