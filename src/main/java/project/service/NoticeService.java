package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.NoticeDto;
import project.mapper.NoticeMapper;

@Service
public class NoticeService {

	@Autowired
	private NoticeMapper mapper;

	// 공지사항 리스트 조회
	public List<NoticeDto> listNoticeDto() throws Exception {
		return mapper.listNoticeDto();
	}

	// 공지사항 글별 상세페이지 조회
	public NoticeDto noticeDetail(int noticeIdx) throws Exception {

		return mapper.noticeDetail(noticeIdx);
	}

	// 공지사항 작성
	public int insertNotice(NoticeDto noticeDto) throws Exception {
		return mapper.insertNotice(noticeDto);
	}

	// 공지사항 삭제
	public int deleteNotice(int noticeIdx) throws Exception {
		return mapper.deleteNotice(noticeIdx);
	}

	// 공지사항 수정
	public int updateNotice(NoticeDto noticeDto) throws Exception {
		return mapper.updateNotice(noticeDto);
	}
}
