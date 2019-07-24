package com.jdjhaha.findbook.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jdjhaha.findbook.member.vo.Keyword;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword,String>{
	
	@Query(nativeQuery = true, value = "select * from Keyword a ORDER BY count DESC LIMIT 10")
	List<Keyword> findByCountDesc();
}
