package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.AdminDto;

@Mapper
public interface AdminMapper {

	//게시판별 월의 일자별 게시글 개수 조회
	public List<AdminDto> selectTravelcourseDayPostCount(@Param("startDay") String startDay, @Param("endDay") String endDay) throws Exception;
	public List<AdminDto> selectTriedDayPostCount(@Param("startDay") String startDay, @Param("endDay") String endDay) throws Exception;
	public List<AdminDto> selectAccompanyDayPostCount(@Param("startDay") String startDay, @Param("endDay") String endDay) throws Exception;
	public List<AdminDto> selectIdealrealDayPostCount(@Param("startDay") String startDay, @Param("endDay") String endDay) throws Exception;
	
	//게시판별 연의 월별 게시글 개수 조회
	public List<AdminDto> selectTravelcourseMonthPostCount(@Param("year") String year) throws Exception;
	public List<AdminDto> selectTriedMonthPostCount(@Param("year") String year) throws Exception;
	public List<AdminDto> selectAccompanyMonthPostCount(@Param("year") String year) throws Exception;
	public List<AdminDto> selectIdealrealMonthPostCount(@Param("year") String year) throws Exception;
	
}
