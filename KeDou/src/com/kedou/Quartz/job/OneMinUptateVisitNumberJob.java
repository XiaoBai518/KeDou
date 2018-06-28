package com.kedou.Quartz.job;

import java.util.Iterator;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

public class OneMinUptateVisitNumberJob extends QuartzJobBean {

	private EhCacheCacheManager cacheManager;

	private SessionFactory sessionFactory;  

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
	  public void executeTransactional(JobExecutionContext ctx) {    
		  	//更新 商家浏览量
			Cache cache = cacheManager.getCache("visitNumberCache");
			if(cache.get("visitNumberMap")!=null) {
				//已有缓存
				Map<Integer,BusinessVisitNumber> bvnMap = (Map<Integer,BusinessVisitNumber>)cache.get("visitNumberMap").get();
			
				
				if(bvnMap!=null) {
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
				
				
				//刷新缓存
				cache.put("visitNumberMap", bvnMap);
			  }
			}
			//更新 文章阅读量
			Cache readcache = cacheManager.getCache("articleReadNumberCache");
			if(readcache.get("readNumberMap")!=null) {
				//已有缓存
				Map<Integer,Integer> readNumberMap = (Map<Integer,Integer>)readcache.get("readNumberMap").get();

				if(readNumberMap!=null) {
					Iterator i = readNumberMap.entrySet().iterator();
					Transaction tr = null;
					Session session = getSessionFactory().openSession();
					tr = session.beginTransaction();
					
					while (i.hasNext()) {
						Map.Entry entry = (Map.Entry) i.next();
						
						int readNumber = (int)entry.getValue();
						String hql = "update Article a set a.views= ?  where a.id=?";
						Query query = session.createQuery(hql);
						query.setParameter(0, readNumber);
						query.setParameter(1, (int)entry.getKey());
						//数据库更新操作
						query.executeUpdate();
						
					}
					tr.commit();
					session.close();
				
				
				//刷新缓存
				readcache.put("readNumberMap", readNumberMap);
			  }
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
