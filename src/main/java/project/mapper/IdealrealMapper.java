package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.IdealrealDto;
import project.dto.RcmdDto;

@Mapper
public interface IdealrealMapper {

	//1. 이상과현실 리스트 조회
	public List<IdealrealDto> selectIdealRealList() throws Exception;

	//1-1. 이상과현실 리스트 조회(좋아요 순)
	public List<IdealrealDto> selectIdealRealListWithLike() throws Exception;
	
	//1-2. 이상과현실 리스트 최신순 조회(페이징)
	public List<IdealrealDto> selectIdealRealListPage(int page) throws Exception;

	//1-3. 이상과현실 리스트 추천순 조회(페이징)
	public List<IdealrealDto> selectIdealRealListWithLikePage(int page) throws Exception;
	
	//1-4. 게시판 페이지 수 조회
	public int listidealrealpagecount() throws Exception;

	//2. 이상과현실 상세조회
	public IdealrealDto selectIdealrealDetail(int idealrealIdx) throws Exception;

	//2-1. 이상과현실 상세조회시 조회수 증가
	public void updatecnt(int idealrealIdx);

	//3. 이상과현실 입력
	public void insertIdealreal(IdealrealDto idealrealDto) throws Exception;

	//4. 이상과현실 수정
	public int updateIdealreal(IdealrealDto idealrealDto) throws Exception;

	//5. 이상과현실 삭제
	public int deleteIdealreal(int idealrealIdx) throws Exception;

	//6. 좋아요 수 조회
	public Integer selectLikesCount(int idealrealIdx) throws Exception;

	//6-1. 좋아요 추가
	public int updateLikesCount(RcmdDto rcmdDto) throws Exception;

	//6-2. 좋아요 취소
	public int deleteLikesCount(RcmdDto rcmdDto) throws Exception;
	
	//6-3. 좋아요 누른 유저인지 확인
	public int selectLikesCountByUser(@Param("idealrealIdx") int idealrealIdx, @Param("userId") String userId) throws Exception;
	
	//7. 게시글 수정 사진 저장
	public int reuploadIdealreal(IdealrealDto idealrealDto) throws Exception;
}
