package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.NoticeDto;

@Mapper
public interface NoticeMapper {

	// 공지사항 리스트 조회
		public List<NoticeDto> listNoticeDto() throws Exception;

		// 공지사항 글별 상세페이지 조회
		public NoticeDto noticeDetail(int noticeIdx) throws Exception;

		// 공지사항 작성
		public int insertNotice(NoticeDto noticeDto) throws Exception;

		// 공지사항 삭제
		public int deleteNotice(int noticeIdx) throws Exception;

		// 공지사항 수정
		public int updateNotice(NoticeDto noticeDto) throws Exception;
}
