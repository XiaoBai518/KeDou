package com.kedou.superentity;

import java.util.List;

import com.kedou.entity.Answer;
import com.kedou.entity.Comment;

public class SuperComment {

	private Comment comment;
	private List<Answer> answer;
	
	public SuperComment(Comment c,List<Answer> a) {
		this.comment = c;
		this.answer =a;
	}
	
	public Comment getComment() {
		return comment;
	}
	public List<Answer> getAnswer() {
		return answer;
	}
	
	
	
	
}
