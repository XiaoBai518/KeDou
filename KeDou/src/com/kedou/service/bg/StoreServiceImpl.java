package com.kedou.service.bg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.bg.StoreDaoImpl;
import com.kedou.entity.Business;


@Service
@Transactional(readOnly=false)
public class StoreServiceImpl {
	@Resource
	private StoreDaoImpl storeDaoImpl;
	/**
	 * 锁定商家
	 * @param id
	 */
	@Transactional
	public void lockBus(int[] array) {
		for(int i=0;i<array.length;i++) {
			try {
				storeDaoImpl.lockBus(array[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 通过id查询商家实体
	 * @param id
	 */
	@Transactional
	public Business findBusById(int id){
		try {
		return	storeDaoImpl.findBusById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 储存修改后的商家实体
	 * @param bus
	 */
	@Transactional
	public void saveBus(Business bus) {
		try {
			storeDaoImpl.save(bus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 调用dao层查询商家方法
	 * @param account
	 * @return
	 */
	@Transactional
	public Business findBus(String account) {
		try {
			return storeDaoImpl.findBus(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @return
	 */
	public List<Business> searchAllBus(){
		try {
			return  storeDaoImpl.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
