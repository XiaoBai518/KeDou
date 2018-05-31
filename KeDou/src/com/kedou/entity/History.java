package com.kedou.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="history")
/**
 * 访问历史 实体
 * @author 陈 
 *
 */
public class History {

		@Id
		@GeneratedValue(generator="increment_generator")
		@GenericGenerator(name="increment_generator", strategy="increment")
		private int id;
		private int userId;//用户ID
		private int courseId;//课程ID
	    private Date timestamp;//访问时间
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getCourseId() {
			return courseId;
		}
		public void setCourseId(int courseId) {
			this.courseId = courseId;
		}
		public Date getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}
	
	    

}
