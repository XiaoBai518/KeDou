package com.kedou.dao.torder;

import java.util.List;

import org.hibernate.query.Query;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Torder;

public class TorderDaoImpl extends BaseDao<Torder> {

	 /**
	 * 
	 * @desc 根据关键字查找预约
	 * @author yuanyuan
	 * @createDate 2018年3月29日
	 * @return List<Torder>
	 * @throws Exception
	 */
	public List<Torder> findByCheck(String check){
		String hpl = "from Torder where userName like ? or busName like ? or catName like ? ";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hpl);
		query.setParameter(0, "%"+check+"%");
		query.setParameter(1, "%"+check+"%");
		query.setParameter(2, "%"+check+"%");
		List<Torder> ocl = query.list();
		return ocl;
	}
}
