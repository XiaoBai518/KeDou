package com.kedou.service.bg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.bg.ReservationDaoImpl;
import com.kedou.entity.Torder;

@Service
@Transactional(readOnly=false)
public class ReservationServiceImpl {
	@Resource
	private ReservationDaoImpl reservationDaoImpl;
	
	/**
	 * 锁定订单
	 * @param id
	 */
	@Transactional
	public void lockBus(int[] array) {
		for(int i=0;i<array.length;i++) {
			try {
				reservationDaoImpl.lockRe(array[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional
	public List<Torder> searchAllTorder(){
		try {
			return reservationDaoImpl.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
