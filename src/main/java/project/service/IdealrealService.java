package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.IdealrealDto;
import project.dto.RcmdDto;
import project.mapper.IdealrealMapper;

@Service
public class IdealrealService {

	@Autowired
	private IdealrealMapper mapper;
	
	//1. 이상과현실 리스트 조회
	public List<IdealrealDto> selectIdealRealList() throws Exception {
		return mapper.selectIdealRealList();
	}
	
	//1-1. 이상과현실 리스트 조회(좋아요 순)
	public List<IdealrealDto> selectIdealRealListWithLike() throws Exception {
		return mapper.selectIdealRealListWithLike();
	}
	
	//1-2. 이상과현실 리스트 최신순 조회(페이징)
	public List<IdealrealDto> selectIdealRealListPage(int page) throws Exception {
		page = (page-1) * 8;
		return mapper.selectIdealRealListPage(page);
	}

	//1-3. 이상과현실 리스트 추천순 조회(페이징)
	public List<IdealrealDto> selectIdealRealListWithLikePage(int page) throws Exception {
		page = (page-1) * 8;
		return mapper.selectIdealRealListWithLikePage(page);
	}
	
	//1-4. 게시판 페이지 수 조회
	public int listidealrealpagecount() throws Exception {
		return mapper.listidealrealpagecount();
	}
		
	//2. 이상과현실 상세조회
	public IdealrealDto selectIdealrealDetail(int idealrealIdx) throws Exception {
		mapper.updatecnt(idealrealIdx);
		return mapper.selectIdealrealDetail(idealrealIdx);
	}

	//3. 이상과현실 입력
	public void insertIdealreal(IdealrealDto idealrealDto) throws Exception {
		mapper.insertIdealreal(idealrealDto);
	}

	//4. 이상과현실 수정
	public int updateIdealreal(IdealrealDto idealrealDto) throws Exception {
		return mapper.updateIdealreal(idealrealDto);
	}

	//5. 이상과현실 삭제
	public int deleteIdealreal(int idealrealIdx) throws Exception {
		return mapper.deleteIdealreal(idealrealIdx);
	}
	
	//6. 좋아요 수 조회
	public Integer selectLikesCount(int idealrealIdx) throws Exception {
		Integer count = mapper.selectLikesCount(idealrealIdx);
		return count != null ? count : 0;
	}

	//6-1. 좋아요 추가
	public int updateLikesCount(RcmdDto rcmdDto) throws Exception {
		return mapper.updateLikesCount(rcmdDto);
	}
	
	//6-2. 좋아요 취소
	public int deleteLikesCount(RcmdDto rcmdDto) throws Exception {
		return mapper.deleteLikesCount(rcmdDto);
	}
	
	//6-3. 좋아요 누른 유저인지 확인
	public int selectLikesCountByUser(int idealrealIdx, String userId) throws Exception{
		return mapper.selectLikesCountByUser(idealrealIdx, userId);
	}

	//7. 게시글 수정 사진 저장
	public int reuploadIdealreal(IdealrealDto idealrealDto) throws Exception {
		return mapper.reuploadIdealreal(idealrealDto);
	}
}
