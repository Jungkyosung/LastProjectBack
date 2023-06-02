package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.dto.TriedCommentDto;
import project.dto.TriedDto;
import project.mapper.TriedMapper;

@Service
public class TriedService {

	@Autowired
	private TriedMapper mapper;

	// 1. 조회
	// 1-1,2. 어디까지 카테고리 최신순, 페이지수
	public List<TriedDto> selectTriedRecent(TriedDto triedDto) throws Exception {
		System.out.println(">>>>>>서비스단"+triedDto);
		return mapper.selectTriedRecent(triedDto);
	}

	public int selectTriedRecentTotalPage(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecentTotalPage(triedDto);
	}
	
	// 1-1,2. 어디까지 카테고리 조회순, 페이지수
	public List<TriedDto> selectTriedCount(TriedDto triedDto) throws Exception {
		return mapper.selectTriedCount(triedDto);
	}

	public int selectTriedCountTotalPage(TriedDto triedDto) throws Exception {
		return mapper.selectTriedCountTotalPage(triedDto);
	}
	
	// 1-1,2. 어디까지 카테고리 추천순, 페이지수
	public List<TriedDto> selectTriedRecommend(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecommend(triedDto);
	}

	public int selectTriedRecommendTotalPage(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecommendTotalPage(triedDto);
	}
	
	// 1-3. 어디까지 디테일 조회
	public TriedDto selectTriedDetail(int triedIdx) throws Exception {
		mapper.triedCnt(triedIdx); 					// 조회수를 증가
		return mapper.selectTriedDetail(triedIdx);  // 게시판 상세 내용 조회
	}
	
	// 2. 입력
	// 2-1. 입력
	public int insertTried(TriedDto triedDto) throws Exception {
		return mapper.insertTried(triedDto);
	}


	// 3. 수정
	public int updateTried(TriedDto triedDto) throws Exception {
		return mapper.updateTried(triedDto);
	}

	// 4. 삭제
	public int deleteTried(int triedIdx) throws Exception {
		return mapper.deleteTried(triedIdx);
	}

	// 5-1. 어디까지 댓글 조회
	public List<TriedCommentDto> selectTriedComment(int triedIdx) throws Exception{
		return mapper.selectTriedComment(triedIdx);
	}
	
	// 5-2. 어디까지 댓글 입력
	public int insertTriedComment(TriedCommentDto triedComment) throws Exception{
		return mapper.insertTriedComment(triedComment);
	}
	
	// 5-3. 어디까지 댓글 수정
	public int updateTriedComment(TriedCommentDto triedComment) throws Exception{
		return mapper.updateTriedComment(triedComment);
	}
	
	// 5-4. 어디까지 댓글 삭제
	public int deleteTriedComment(int triedCommentIdx) throws Exception{
		return mapper.deleteTriedComment(triedCommentIdx);
	}
	
	//업로드(삭제예정)
	public int uploadTried(TriedDto triedDto, MultipartFile[] files) throws Exception {
		return mapper.uploadTried(triedDto);
	}

	//이미지 가져오기(삭제예정)
	public byte[] getImageData(String triedImg) throws Exception {
		return mapper.getImageData(triedImg);
	}
	
	// 조회(삭제예정)
	public List<TriedDto> selectTriedList() throws Exception {
		return mapper.selectTriedList();
	}
	
	// 6-1. 어디까지 게시글 유저 추천 확인
	public int selectTriedRcmdByUserId(int triedIdx, String userId) throws Exception{
		return mapper.selectTriedRcmdByUserId(triedIdx, userId);
	}
	// 6-2. 어디까지 게시글 추천 추가
	public int insertTriedRcmd(int triedIdx, String userId) throws Exception{
		return mapper.insertTriedRcmd(triedIdx, userId);
	}
	
	// 6-3. 어디까지 게시글 추천 삭제
	public int deleteTriedRcmd(int triedIdx, String userId) throws Exception{
		return mapper.deleteTriedRcmd(triedIdx, userId);
	}
	
	// 6-4. 어디까지 게시글 추천수 조회
	public int selectTriedRcmdCount(int triedIdx) throws Exception{
		return mapper.selectTriedRcmdCount(triedIdx);
	}
	
	


}
