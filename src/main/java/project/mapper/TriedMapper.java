package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.TriedCommentDto;
import project.dto.TriedDto;

@Mapper
public interface TriedMapper {
	
	// 1. 조회
	// 1-1,2. 어디까지 카테고리 최신순, 페이지수
	public List<TriedDto> selectTriedRecent(TriedDto triedDto) throws Exception;

	public int selectTriedRecentTotalPage(TriedDto triedDto) throws Exception;

	// 1-1,2. 어디까지 카테고리 조회순, 페이지수
	public List<TriedDto> selectTriedCount(TriedDto triedDto) throws Exception;

	public int selectTriedCountTotalPage(TriedDto triedDto) throws Exception;

	// 1-1,2. 어디까지 카테고리 추천순, 페이지수
	public List<TriedDto> selectTriedRecommend(TriedDto triedDto) throws Exception;

	public int selectTriedRecommendTotalPage(TriedDto triedDto) throws Exception;

	// 1-3. 어디까지 디테일 조회
	public TriedDto selectTriedDetail(int triedIdx) throws Exception;
	
	// 1-3-1. 어디까지 조회수 추가
	public void triedCnt(int triedIdx) throws Exception;

	// 2. 입력
	// 어디까지 게시글 작성
	public int insertTried(TriedDto triedDto) throws Exception;

	// 3. 수정
	// 어디까지 게시글 수정
	public int updateTried(TriedDto triedDto) throws Exception;

	// 4. 삭제
	// 어디까지 게시글 삭제
	public int deleteTried(int triedIdx) throws Exception;
	
	// 5-1. 어디까지 댓글 조회
	public List<TriedCommentDto> selectTriedComment(int triedIdx) throws Exception;
	
	// 5-2. 어디까지 댓글 입력
	public int insertTriedComment(TriedCommentDto triedComment) throws Exception;
	
	// 5-3. 어디까지 댓글 수정
	public int updateTriedComment(TriedCommentDto triedComment) throws Exception;
	
	// 5-4. 어디까지 댓글 삭제
	public int deleteTriedComment(int triedCommentIdx) throws Exception;
	
	// 어디까지 리스트 조회(삭제예정)
	public List<TriedDto> selectTriedList() throws Exception;

	// 어디까지 업로드(삭제예정)
	public int uploadTried(TriedDto triedDto) throws Exception;

	// 어디까지 사진가져오기(삭제예정)
	public byte[] getImageData(String triedImg) throws Exception;
	
	// 6-1. 어디까지 게시글 유저 추천 확인
	public int selectTriedRcmdByUserId(@Param("triedIdx") int triedIdx, @Param("userId") String userId) throws Exception;
	
	// 6-2. 어디까지 게시글 추천 추가
	public int insertTriedRcmd(@Param("triedIdx") int triedIdx, @Param("userId") String userId) throws Exception;
	
	// 6-3. 어디까지 게시글 추천 삭제
	public int deleteTriedRcmd(@Param("triedIdx") int triedIdx, @Param("userId") String userId) throws Exception;
	
	// 6-4. 어디까지 게시글 추천수 조회
	public int selectTriedRcmdCount(@Param("triedIdx")int triedIdx) throws Exception;

}
