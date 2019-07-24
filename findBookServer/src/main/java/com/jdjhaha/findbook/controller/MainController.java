package com.jdjhaha.findbook.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jdjhaha.findbook.service.ApiHelper;
import com.jdjhaha.findbook.util.StrUtil;
import com.jdjhaha.findbook.vo.SearchResultVO;
import com.jdjhaha.findbook.vo.SearchVO;

@Controller
public class MainController {
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("search_value", StrUtil.NVL(request.getParameter("search_value")));
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("title", StrUtil.NVL(request.getParameter("title")));
		mav.addObject("thumbnail", StrUtil.NVL(request.getParameter("thumbnail")));
		mav.addObject("contents", StrUtil.NVL(request.getParameter("contents")));
		mav.addObject("isbn", StrUtil.NVL(request.getParameter("isbn")));
		mav.addObject("authors", StrUtil.NVL(request.getParameter("authors")));
		mav.addObject("publisher", StrUtil.NVL(request.getParameter("publisher")));
		mav.addObject("datetime", StrUtil.NVL(request.getParameter("datetime")));
		mav.addObject("price", StrUtil.NVL(request.getParameter("price")));
		mav.addObject("sale_price", StrUtil.NVL(request.getParameter("sale_price")));
		mav.addObject("search_value", StrUtil.NVL(request.getParameter("search_value")));
		
		mav.setViewName("detail");
		return mav;
	}
	
	@RequestMapping("/search")
	public @ResponseBody Object search(HttpServletRequest request) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<>();
		
		Optional<HttpSession> session = Optional.ofNullable(request.getSession());
		session.ifPresent(session_m->{
			Optional<Object> logonId = Optional.ofNullable(session_m.getAttribute("logonId"));
			logonId.ifPresent(id->{
				
			});
		});
		
		int draw = StrUtil.NVLtint(request.getParameter("draw"), 0);
		int start = StrUtil.NVLtint(request.getParameter("start"), 0);
		int length = StrUtil.NVLtint(request.getParameter("length"), 0);
		String orderColumnIndex = StrUtil.NVL(request.getParameter("order[0][column]"), null);
		String orderColumn = StrUtil.NVL(request.getParameter("columns[" + orderColumnIndex + "][data]"), null);
		String orderDir = StrUtil.NVL(request.getParameter("order[0][dir]"), null);
		String searchColumn = StrUtil.NVL(request.getParameter("searchColumn"), null);
		String searchValue = StrUtil.NVL(request.getParameter("search[value]"), null);
		String searchStartDate = StrUtil.NVL(request.getParameter("searchStartDate"), null);
		String searchEndDate = StrUtil.NVL(request.getParameter("searchEndDate"), null);
		String detailSearch = StrUtil.NVL(request.getParameter("detailSearch"), null);

		SearchVO searchVO = new SearchVO();

		searchVO.setDraw(StrUtil.NVL(draw, "0"));
		searchVO.setStart(StrUtil.NVL(start, "0"));
		searchVO.setLength(StrUtil.NVL(length, "0"));
		searchVO.setOrderColumn(orderColumn);
		searchVO.setOrderDir(orderDir);
		searchVO.setSearchColumn(searchColumn);
		searchVO.setSearchValue(searchValue);
		searchVO.setSearchStartDate(searchStartDate);
		searchVO.setSearchEndDate(searchEndDate);
		searchVO.setDetailSearch(detailSearch);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("query", searchValue);
		paramMap.put("page", String.valueOf((start/length)+1));
		paramMap.put("size", String.valueOf(length));
		
		Optional<SearchResultVO> result = ApiHelper.requestApi(paramMap);
		result.ifPresent(resultVO->{
			searchVO.setData(resultVO.getDocuments());
			searchVO.setRecordsTotal(resultVO.getMeta().getTotal_count());
			searchVO.setRecordsFiltered(resultVO.getMeta().getTotal_count());
		});
		
		return searchVO;
	}

}
