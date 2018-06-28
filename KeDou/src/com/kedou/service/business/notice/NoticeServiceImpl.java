package com.kedou.service.business.notice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.business.notice.NoticeDaoImpl;
import com.kedou.entity.BusinessNotice;

@Service
@Transactional(readOnly=false)
public class NoticeServiceImpl {

	@Resource
	private NoticeDaoImpl noticeDaoImpl;
	
	
	/**
	 * 添加公告
	 * @param notice
	 * @throws Exception
	 */
	public void addNotice(BusinessNotice notice) throws Exception {
		this.noticeDaoImpl.save(notice);
	}
	
	/**
	 * 根据机构ID 查找相应公告
	 * @param busid
	 * @return
	 */
	public List<BusinessNotice> findNoticeByBusid(int busid) {
		return this.noticeDaoImpl.findNoticeByBusid(busid);
	}
}
