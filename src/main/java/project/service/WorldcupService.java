package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.IdealworldcupDto;
import project.dto.IdealworldcupStaticDto;
import project.dto.RawinfoDto;
import project.mapper.WorldcupMapper;

@Service
public class WorldcupService {

	@Autowired
	private WorldcupMapper worldcupMapper;

	
	public int insertWorldCup(IdealworldcupDto idealworldcupDto) throws Exception {
		return worldcupMapper.insertWorldcup(idealworldcupDto);
	}

	
	public int insertStaticWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception {
		return worldcupMapper.insertStaticWorldcup(idealworldcupStaticDto);
	}
	
	
	
	public int idealworldcupStaticWincnt(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception {
	    int idealworldcupStaticWncnt = worldcupMapper.idealworldcupStaticWincnt(idealworldcupStaticDto);
	    return idealworldcupStaticWncnt;
	}

	
	public IdealworldcupDto selectWorldcupDetail(int idealworldcupIdx) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<IdealworldcupDto> selectWorldcupList() throws Exception {
		return worldcupMapper.selectWorldcupList();
	}

	
	public int insertWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public List<RawinfoDto> selectRawinfoList() throws Exception{
		return worldcupMapper.selectRawinfoList();
	}
	
	public List<RawinfoDto> selectRawinfoListByCategory(int triedCategoryIdx) throws Exception {
		return worldcupMapper.selectRawinfoListByCategory(triedCategoryIdx);
	}

	
	public int updateRawinfoWincnt(int rawinfoIdx) {
		return worldcupMapper.updateRawinfoWincnt(rawinfoIdx);
	}
	
	public RawinfoDto selectRawinfowinDetail(int rawinfoIdx) throws Exception {
		return worldcupMapper.selectRawinfowinDetail(rawinfoIdx);
	}
	
	public int selectTotalwincnt(int triedCategoryIdx) throws Exception{
		return worldcupMapper.selectTotalwincnt(triedCategoryIdx);
	}
	
}
