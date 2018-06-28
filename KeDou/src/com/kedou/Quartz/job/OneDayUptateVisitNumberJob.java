package com.kedou.Quartz.job;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.kedou.entity.BusinessVisitNumber;
import com.kedou.service.visitNumber.VisitNumberServiceImpl;

public class OneDayUptateVisitNumberJob extends QuartzJobBean{

	private EhCacheCacheManager cacheManager;
	
	private SessionFactory sessionFactory;  

	private VisitNumberServiceImpl visitNumberServiceImpl;

	public void setVisitNumberServiceImpl(VisitNumberServiceImpl visitNumberServiceImpl) {
		this.visitNumberServiceImpl = visitNumberServiceImpl;
	}
	public void setCacheManager(EhCacheCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}



	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		SessionHolder sessionHolder =  
	            (SessionHolder) TransactionSynchronizationManager.getResource(getSessionFactory());  
	        boolean existingTransaction = sessionHolder != null;  
	        Session session;  
	        if (existingTransaction) {  
	            session = sessionHolder.getSession();  
	        } else {  
	            session = openSession();  
	            TransactionSynchronizationManager.bindResource(getSessionFactory(), new SessionHolder(session));  
	        }  
	         try {    
	                executeTransactional(arg0);    
	            } catch (HibernateException ex) {    
	                ex.printStackTrace();    
	                throw ex;    
	            } finally {    
	                if (existingTransaction) {    
	                   System.out.println("Not closing pre-bound Hibernate Session after TransactionalQuartzTask");    
	                } else {    
	                    SessionFactoryUtils.closeSession(session);  
	                    TransactionSynchronizationManager    
	                            .unbindResource(getSessionFactory());                   
	                }    
	            }   
		
	}
	  public void executeTransactional(JobExecutionContext ctx)   {    
			        //每天0.00 将今日店铺访问量  保存到数据库  并刷新今日店铺访问量
			System.out.println("执行中");
			Cache cache = cacheManager.getCache("visitNumberCache");
			if(cache.get("visitNumberMap")!=null) {
				Map<Integer,BusinessVisitNumber> bvnMap = (Map<Integer,BusinessVisitNumber>)cache.get("visitNumberMap").get();
				Iterator i = bvnMap.entrySet().iterator();
				
				Transaction tr = null;
				Session session = getSessionFactory().openSession();
				tr = session.beginTransaction();
				
				while (i.hasNext()) {
					Map.Entry entry = (Map.Entry) i.next();
					BusinessVisitNumber val = (BusinessVisitNumber)entry.getValue();
					//数据库更新操作
						session.update(val);
				}
				tr.commit();
				session.close();
				
				//清空Map
				try {
					bvnMap = this.visitNumberServiceImpl.initMap();
				} catch (Exception e) {
					// 查找Business表时出错
					e.printStackTrace();
					//将 Map 设置为NULL
					bvnMap = null;
				}
				//刷新缓存
				cache.put("visitNumberMap", bvnMap);
			}
	  }    
			      
	 protected Session openSession() throws DataAccessResourceFailureException {  
			   try {  
			            Session session = getSessionFactory().openSession();  
			            session.setFlushMode(FlushMode.COMMIT);  
			            return session;  
			        }  
			        catch (HibernateException ex) {  
			            throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);  
			        }  
			    }  
			      
			    public SessionFactory getSessionFactory() {  
			        return sessionFactory;  
			    }  
			  
			    public void setSessionFactory(SessionFactory sessionFactory) {  
			        this.sessionFactory = sessionFactory;  
			    }  
}
