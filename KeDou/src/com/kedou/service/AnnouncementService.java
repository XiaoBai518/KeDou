package com.kedou.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedou.dao.AnnouncementDao;
import com.kedou.entity.Announcement;

@Service
public class AnnouncementService {
	@Resource
	private AnnouncementDao announcementDao;

	public AnnouncementDao getAnnouncementDao() {
		return announcementDao;
	}

	public void setAnnouncementDao(AnnouncementDao announcementDao) {
		this.announcementDao = announcementDao;
	}
	//添加公告
	public void save(String accTitle,String accContent,int busId){
		announcementDao.insert(accTitle, accContent,busId);
		System.out.println("21");
	}
	//查询公告
	public List<Announcement> find(){
		return announcementDao.findAll();
	}
	//to修改公告
	public Announcement toedit(int accId){
		return announcementDao.toedit(accId);
	}
	//真正修改
	public void edit(int accId,Announcement announcement){
		announcementDao.edit(accId, announcement);
	}
	
	//删除公告
	public void del(int accId){
		announcementDao.del(accId);
	}
	//根据accId查询
	public Announcement findById(int accId){
		return announcementDao.findById(accId);
	}
}
