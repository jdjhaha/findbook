package com.jdjhaha.findbook.member.controller;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jdjhaha.findbook.member.dao.MemberRepository;
import com.jdjhaha.findbook.member.vo.Member;
import com.jdjhaha.findbook.util.CipherUtil;

@Controller
public class LoginController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		KeyPair keyPair = CipherUtil.genRSAKeyPair();
		
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		HttpSession session = request.getSession();
		session.setAttribute("privateKey", privateKey);
		
		// 공개키를 Base64 인코딩한 문자일을 만듭니다.
		byte[] bytePublicKey = publicKey.getEncoded();
		String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
		
		mav.addObject("publicKey", base64PublicKey);
		
		mav.setViewName("login");
		return mav;
	}
	
	@RequestMapping("/checkIn")
	public @ResponseBody Map<String,Object> checkDuplId(HttpServletRequest request, String encryptedId, String password) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");
		
		String id = CipherUtil.decryptRSA(encryptedId, privateKey);
		Optional<Member> member = memberRepository.findById(id);
		
		member.ifPresent(member_m->{
			Optional<String> pw = Optional.ofNullable(member_m.getPassword());
			pw.ifPresent(pw_m->{
				if(pw_m.equals(password)) {
					session.setAttribute("logon", "Y");
					session.setAttribute("logonId", id);
					resultMap.put("logon", "Y");
				}
			});
		});
		
		return resultMap;
	}
	
	@RequestMapping("/logout")
	public @ResponseBody Map<String,Object> logout(HttpServletRequest request) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<>();
		
		Optional<HttpSession> session = Optional.ofNullable(request.getSession());
		session.ifPresent(session_m->{
			session_m.invalidate();
			resultMap.put("logoutYn", "Y");
		});
		
		return resultMap;
	}
}
