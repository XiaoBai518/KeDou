package com.kedou.service.visitNumber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.business.BusinessDaoImpl;
import com.kedou.dao.visitNumber.VisitNumberDaoImpl;
import com.kedou.entity.Business;
import com.kedou.entity.BusinessVisitNumber;

@Service
@Transactional(readOnly=false)
public class VisitNumberServiceImpl {

	@Resource
	private VisitNumberDaoImpl visitNumberDaoImpl;
	@Resource
	private BusinessDaoImpl businessDaoImpl;
	
	/**
	 * 新增数据
	 * @param bvn
	 * @throws Exception 
	 */
	public void addBusinessVistNumber(BusinessVisitNumber bvn) throws Exception {
		this.visitNumberDaoImpl.save(bvn);
		
	}
	/**
	 * 每天初始化 商家店铺访问量Map
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,BusinessVisitNumber> initMap() throws Exception {
		System.out.println("初始化Map开始");
		
		Map<Integer,BusinessVisitNumber> visitNumberMap = new HashMap<Integer,BusinessVisitNumber>();
		if(this.visitNumberDaoImpl.findCount()==0) {
			System.out.println("表中无数据");
			//表中无数据
			//获得所有商家
			List<Business> busList = this.businessDaoImpl.findAll();
			for(Business bus:busList) {
				BusinessVisitNumber bvn = new BusinessVisitNumber(bus.getBusId());
				//向数据库插入今日浏览量初始信息		
				this.visitNumberDaoImpl.save(bvn);		
				//放入缓存
				visitNumberMap.put(bus.getBusId(), bvn);	
			}
		}else {
			//检查数据库中是否已有今日的记录
			Date today = new Date();
			Date DBdate = this.visitNumberDaoImpl.findLast().getDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String stoday = format.format(today);
			String sDbdate =format.format(DBdate);
			if(!stoday.equals(sDbdate)){
				System.out.println("表中没有今日数据");
			 //表中没有今日没有数据
				//获得所有商家
				List<Business> busList = this.businessDaoImpl.findAll();
				for(Business bus:busList) {
					BusinessVisitNumber bvn = new BusinessVisitNumber(bus.getBusId());
					//向数据库插入今日浏览量初始信息					
					this.visitNumberDaoImpl.save(bvn);			
					//放入缓存
					visitNumberMap.put(bus.getBusId(), bvn);	
				}
			}else {
				System.out.println("表中已有今日数据");
				//表中已有今日数据
					List<BusinessVisitNumber> bvnList = this.getVisitNumberByDate(today);
					for(BusinessVisitNumber bvn : bvnList) {
						visitNumberMap.put(bvn.getBusId(), bvn);
					}
				}
			}
		
		System.out.println("初始化Map结束");
		return visitNumberMap;
	}
	/**
	 * 更新数据信息
	 * @param bvnMap
	 * @throws Exception 
	 */
//	public void UpdateMap(Map<Integer,BusinessVisitNumber> bvnMap) throws Exception {
//		if(bvnMap!=null) {
//			Iterator i = bvnMap.entrySet().iterator();
//			while (i.hasNext()) {
//				Map.Entry entry = (Map.Entry) i.next();
//				BusinessVisitNumber val = (BusinessVisitNumber)entry.getValue();
//				//数据库更新操作
//			this.visitNumberDaoImpl.update(val);	
//	
//		}
//		}
//	}
	
	/**
	 * 根据时间获取浏览量实体
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public List<BusinessVisitNumber> getVisitNumberByDate(Date date) throws Exception {
		 List<BusinessVisitNumber> list = this.visitNumberDaoImpl.getVisitNumberByDate(date);
		 return list;
	}
	
}
