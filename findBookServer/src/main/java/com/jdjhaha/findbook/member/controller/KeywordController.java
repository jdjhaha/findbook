package com.jdjhaha.findbook.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jdjhaha.findbook.member.dao.KeywordRepository;
import com.jdjhaha.findbook.member.vo.Keyword;

@Controller
public class KeywordController {
	
	@Autowired
	private KeywordRepository keywordRepository;
	
	@RequestMapping("/keyword")
	public ModelAndView memberRegist(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		List<Keyword> keywordList = keywordRepository.findByCountDesc();
		
		mav.addObject("keywordList", keywordList);
		mav.setViewName("keyword");
		return mav;
	}
	
}
