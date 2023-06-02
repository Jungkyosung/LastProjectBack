package project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.AccompanyDto;
import project.dto.ChatDto;
import project.dto.IdealrealDto;
import project.dto.ReportDto;
import project.dto.TravelcourseCommentDto;
import project.dto.TravelcourseDto;
import project.dto.TriedCommentDto;
import project.dto.TriedDto;
import project.dto.UserDto;
import project.mapper.ReportMapper;

@Service
public class ReportService {

	@Autowired
	private ReportMapper mapper;
	
	//1. 신고 입력
	public int insertReportUser(ReportDto reportDto) throws Exception{
		return mapper.insertReportUser(reportDto);
	}
	
	//2.최근 신고 내역 페이지별(30개) 조회
	public List<ReportDto> selectReportUserList(int page) throws Exception{
		page = (page - 1) * 30;
		
		return mapper.selectReportUserList(page);
	}
	
	//2-1. 신고 내역 페이지수 조회
	public int selectReportPageCount() throws Exception{
		return mapper.selectReportPageCount();
	}
	
	// 3. 사용자 신고당한 횟수 및 게시판, 댓글 내역 조회
	public Map<String,Object> selectReportedList(String reportReportedUser) throws Exception{
		Map<String,Object> result = new HashMap<>();
		
		int reportedCnt = mapper.selectReportedCount(reportReportedUser);
		List<TravelcourseDto> travelcourseList = mapper.selectReportedTCList(reportReportedUser);
		List<TravelcourseCommentDto> travelcourseCommentList = mapper.selectReportedTCCList(reportReportedUser);
		List<TriedDto> triedList = mapper.selectReportedTrList(reportReportedUser);
		List<TriedCommentDto> triedCommentList = mapper.selectTrCList(reportReportedUser);
		List<AccompanyDto> accompanyList = mapper.selectAccList(reportReportedUser);
		List<IdealrealDto> idealrealList = mapper.selectIRList(reportReportedUser);
		List<ChatDto> chatList = mapper.selectChatList(reportReportedUser);
		
		result.put("reportedCount", reportedCnt);
		result.put("travelcourseList", travelcourseList);
		result.put("travelcourseCommentList", travelcourseCommentList);
		result.put("triedList", triedList);
		result.put("triedCommentList", triedCommentList);
		result.put("accompanyList", accompanyList);
		result.put("idealrealList", idealrealList);
		result.put("chatList", chatList);
		
		
		return result;
	}
	
	// 4. 피신고자 정지기한 입력
	public int selectReportedSuspension(UserDto userDto) throws Exception{
		return mapper.selectReportedSuspension(userDto);
	}
	
}
