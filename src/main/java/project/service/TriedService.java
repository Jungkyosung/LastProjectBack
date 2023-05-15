package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.dto.TriedDto;
import project.mapper.TriedMapper;

@Service
public class TriedService {
	
	@Autowired
	private TriedMapper mapper;

	public List<TriedDto> selectTriedList() throws Exception {
		return mapper.selectTriedList();
	}

	public int insertTried(TriedDto triedDto) throws Exception {
		return mapper.insertTried(triedDto);
	}

	public TriedDto selectTriedDetail(int triedIdx) throws Exception {
		mapper.triedCnt(triedIdx); // 조회수를 증가
		return mapper.selectTriedDetail(triedIdx); // 게시판 상세 내용 조회
	}

	public int updateTried(TriedDto triedDto) throws Exception {
		return mapper.updateTried(triedDto);
	}

	public int deleteTried(int triedIdx) throws Exception {
		return mapper.deleteTried(triedIdx);
	}
	public int uploadTried(TriedDto triedDto, MultipartFile[] files) throws Exception {
		return mapper.uploadTried(triedDto);
	}

	public byte[] getImageData(String triedImg) throws Exception {
		return mapper.getImageData(triedImg);
	}

	public List<TriedDto> selectTriedRecent(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecent(triedDto);
	}

	public List<TriedDto> selectTriedCount(TriedDto triedDto) throws Exception {
		return mapper.selectTriedCount(triedDto);
	}

	public List<TriedDto> selectTriedRecommend(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecommend(triedDto);
	}
}
