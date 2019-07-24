package com.jdjhaha.findbook.member.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jdjhaha.findbook.member.vo.History;

@Repository
public interface HistoryRepository extends CrudRepository<History,String>{
	
	@Query(value = "select a from History a where a.id = :id")
	List<History> findByMemberId(@Param("id") String id, Sort sort);
}
