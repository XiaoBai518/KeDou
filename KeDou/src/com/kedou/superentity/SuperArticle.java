package com.kedou.superentity;

import com.kedou.entity.Article;

public class SuperArticle {

	private Article article;
	private int collectionNum;
	
	
	public SuperArticle() {}
	public SuperArticle(Article article, int collectionNum) {
		super();
		this.article = article;
		this.collectionNum = collectionNum;
	}
	public Article getArticle() {
		return article;
	}
	public int getCollectionNum() {
		return collectionNum;
	}
	
	
}
