package com.jdjhaha.findbook.member.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jdjhaha.findbook.member.vo.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member,String>{}
