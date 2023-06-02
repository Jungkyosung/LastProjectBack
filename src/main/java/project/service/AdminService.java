package project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.AdminDto;
import project.mapper.AdminMapper;

@Service
public class AdminService {

	@Autowired
	private AdminMapper mapper;
	
	public Map<String,Object> selectDayPostCount(String startDay, String endDay) throws Exception{
		
		Map<String,Object> postCountMap = new HashMap<>();
		
		List<AdminDto> travelcoursePostCount = mapper.selectTravelcourseDayPostCount(startDay, endDay);
		List<AdminDto> triedPostCount = mapper.selectTriedDayPostCount(startDay, endDay);
		List<AdminDto> accompanyPostCount = mapper.selectAccompanyDayPostCount(startDay, endDay);
		List<AdminDto> idealrealPostCount = mapper.selectIdealrealDayPostCount(startDay, endDay);
		
		postCountMap.put("travelcourse", travelcoursePostCount);
		postCountMap.put("tried", triedPostCount);
		postCountMap.put("accompany", accompanyPostCount);
		postCountMap.put("idealreal", idealrealPostCount);
		
		return postCountMap;
	}
	
	public Map<String,Object> selectMonthPostCount(String year) throws Exception{
		
		Map<String,Object> postCountMap = new HashMap<>();
		
		List<AdminDto> travelcoursePostCount = mapper.selectTravelcourseMonthPostCount(year);
		List<AdminDto> triedPostCount = mapper.selectTriedMonthPostCount(year);
		List<AdminDto> accompanyPostCount = mapper.selectAccompanyMonthPostCount(year);
		List<AdminDto> idealrealPostCount = mapper.selectIdealrealMonthPostCount(year);
		
		postCountMap.put("travelcourse", travelcoursePostCount);
		postCountMap.put("tried", triedPostCount);
		postCountMap.put("accompany", accompanyPostCount);
		postCountMap.put("idealreal", idealrealPostCount);
		
		return postCountMap;
	}
	
}
