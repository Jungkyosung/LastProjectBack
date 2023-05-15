package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.TriedDto;

@Mapper
public interface TriedMapper {
	public List<TriedDto> selectTriedList() throws Exception;
	public int insertTried(TriedDto triedDto) throws Exception;
	public TriedDto selectTriedDetail(int triedIdx) throws Exception;
	void triedCnt(int triedIdx) throws Exception;
	int updateTried(TriedDto triedDto) throws Exception;
	int deleteTried(int triedIdx) throws Exception;
	int uploadTried(TriedDto triedDto) throws Exception;
	public byte[] getImageData(String triedImg) throws Exception;
	
	public List<TriedDto> selectTriedRecent(TriedDto triedDto) throws Exception;
	public List<TriedDto> selectTriedCount(TriedDto triedDto) throws Exception;
	public List<TriedDto> selectTriedRecommend(TriedDto triedDto) throws Exception;
}
