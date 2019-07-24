package com.jdjhaha.findbook.member.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Keyword {
	@Id
	private String keyword;
	private int count;
	
	public Keyword() {
		super();
	}

	public Keyword(String keyword, int count) {
		super();
		this.keyword = keyword;
		this.count = count;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
