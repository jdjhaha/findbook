package com.jdjhaha.findbook.vo;

import java.util.ArrayList;

public class SearchResultVO {
	private ArrayList<BookVO> documents;
	private MetaVO meta;
	
	public ArrayList<BookVO> getDocuments() {
		return documents;
	}
	public void setDocuments(ArrayList<BookVO> documents) {
		this.documents = documents;
	}
	public MetaVO getMeta() {
		return meta;
	}
	public void setMeta(MetaVO meta) {
		this.meta = meta;
	}
	
	
}
