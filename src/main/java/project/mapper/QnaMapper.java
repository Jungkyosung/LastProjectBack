package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.QnaCommentDto;
import project.dto.QnaDto;

@Mapper
public interface QnaMapper {

	// QNA 작성
	public int insertQna(QnaDto qnaDto) throws Exception;

	// QNA 삭제
	public int deleteQna(int qnaIdx) throws Exception;

	// QNA 수정
	public int updateQna(QnaDto qnaDto) throws Exception;

	// QNA 페이지별 조회
	public List<QnaDto> listQnaDtoByPages(@Param("pages") int pages, @Param("search") String search) throws Exception;

	// QNA 페이지수 조회
	public int listQnaDtoSearchPageCount(@Param("search") String search) throws Exception;

	// QNA 상세페이지 COMMENT 리스트
	public List<QnaCommentDto> selectCommentList(int qnaIdx) throws Exception;

	// QNA 상세페이지 조회
	public QnaDto selectQnaInfo(int qnaIdx) throws Exception;

	// QNA 상세페이지 COMMENT 등록
	public int insertComment(QnaCommentDto qnaCommentDto) throws Exception;
}
