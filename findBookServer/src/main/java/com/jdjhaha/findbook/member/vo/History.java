package com.jdjhaha.findbook.member.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class History {
	@Id
	@GeneratedValue
	private Long serial;
	private String id;
	private String keyword;
	private String time;
	
	public History() {
		super();
	}

	public History(String id, String keyword, String time) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
