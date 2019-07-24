package com.jdjhaha.findbook.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ExceptionHandlingController {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandle(HttpServletRequest request, Exception exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception",exception);
		mav.addObject("url",request.getRequestURI());
		mav.setViewName("error");
		return mav;
	}
}
