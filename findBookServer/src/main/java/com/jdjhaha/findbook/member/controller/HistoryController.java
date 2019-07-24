package com.jdjhaha.findbook.member.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jdjhaha.findbook.member.dao.HistoryRepository;
import com.jdjhaha.findbook.member.dao.KeywordRepository;
import com.jdjhaha.findbook.member.vo.History;
import com.jdjhaha.findbook.member.vo.Keyword;

@Controller
public class HistoryController {
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private KeywordRepository keywordRepository;
	
	@RequestMapping("/myHistory")
	public ModelAndView memberRegist(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		Optional<HttpSession> session = Optional.ofNullable(request.getSession());
		session.ifPresent(session_m->{
			Optional<Object> logonId = Optional.ofNullable(session_m.getAttribute("logonId"));
			logonId.ifPresent(id->{
				List<History> historyList = historyRepository.findByMemberId(String.valueOf(id), Sort.by(Sort.Direction.DESC, "time"));
				mav.addObject("historyList", historyList);
			});
		});
		
		mav.setViewName("myHistory");
		return mav;
	}
	
	@RequestMapping("/historyAdd")
	public @ResponseBody Map<String,Object> historyAdd(HttpServletRequest request, String keyword) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<>();
		
		Optional<HttpSession> session = Optional.ofNullable(request.getSession());
		session.ifPresent(session_m->{
			Optional<Object> logonId = Optional.ofNullable(session_m.getAttribute("logonId"));
			logonId.ifPresent(id->{
				History history = new History(String.valueOf(id), keyword, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				historyRepository.save(history);
				
				Optional<Keyword> selKeyword = keywordRepository.findById(keyword);
				selKeyword.ifPresent(selKeyword_m->{
					selKeyword_m.setCount(selKeyword_m.getCount()+1);
					keywordRepository.save(selKeyword_m);
				});
				if(!selKeyword.isPresent()) {
					Keyword addKeyword = new Keyword(keyword, 1);
					keywordRepository.save(addKeyword);
				}
			});
		});
		
		return resultMap;
	}
}
