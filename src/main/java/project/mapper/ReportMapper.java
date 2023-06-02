package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.AccompanyDto;
import project.dto.ChatDto;
import project.dto.IdealrealDto;
import project.dto.ReportDto;
import project.dto.TravelcourseCommentDto;
import project.dto.TravelcourseDto;
import project.dto.TriedCommentDto;
import project.dto.TriedDto;
import project.dto.UserDto;

@Mapper
public interface ReportMapper {

	//1. 신고 입력
	public int insertReportUser(ReportDto reportDto) throws Exception;
	
	//2.최근 신고 내역 페이지별(30개) 조회
	public List<ReportDto> selectReportUserList(int page) throws Exception;
	
	//2-1. 신고 내역 페이지수 조회
	public int selectReportPageCount() throws Exception;
	
	//3-1. 사용자 신고당한 횟수 조회
	public int selectReportedCount(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-2. 피신고자 여행코스 리스트 조회
	public List<TravelcourseDto> selectReportedTCList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-3. 피신고자 여행코스 댓글 리스트 조회
	public List<TravelcourseCommentDto> selectReportedTCCList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-4. 피신고자 어디까지 리스트 조회
	public List<TriedDto> selectReportedTrList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-5. 피신고자 어디까지 댓글 리스트 조회
	public List<TriedCommentDto> selectTrCList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-6. 피신고자 여행친구 리스트 조회
	public List<AccompanyDto> selectAccList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-7. 피신고자 이상과현실 리스트 조회
	public List<IdealrealDto> selectIRList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//3-8. 피신고자 채팅 리스트 조회
	public List<ChatDto> selectChatList(@Param("reportReportedUser") String reportReportedUser) throws Exception;
	
	//4. 피신고자 정지기한 입력
	public int selectReportedSuspension(UserDto userDto) throws Exception;
}
