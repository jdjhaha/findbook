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
@RequestMapping("/member/*")
public class MemberRegistController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@RequestMapping("/regist")
	public ModelAndView memberRegist(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		KeyPair keyPair = CipherUtil.genRSAKeyPair();
		
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		HttpSession session = request.getSession();
		session.setAttribute("privateKey", privateKey);
		
		// 공개키를 Base64 인코딩한 문자일을 만듭니다.
		byte[] bytePublicKey = publicKey.getEncoded();
		String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
		
		mav.setViewName("memberRegist");
		mav.addObject("publicKey", base64PublicKey);
		
		return mav;
	}
	
	@RequestMapping("/checkDuplId")
	public @ResponseBody Map<String,Object> checkDuplId(HttpServletRequest request, String encryptedId) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");
		
		String id = CipherUtil.decryptRSA(encryptedId, privateKey);
		Optional<Member> member = memberRepository.findById(id);
		
		String duplYn = (member.isPresent()?"Y":"N");
		
		resultMap.put("id", id);
		resultMap.put("duplYn", duplYn);
		
		return resultMap;
	}
	
	@RequestMapping("/add")
	public @ResponseBody Map<String,Object> add(HttpServletRequest request, String encryptedId, String password) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");
		
		String id = CipherUtil.decryptRSA(encryptedId, privateKey);
		Optional<Member> member = memberRepository.findById(id);
		if(!member.isPresent()) {
			Member addMember = new Member(id, password);
			memberRepository.save(addMember);
			resultMap.put("registYn", "Y");
		}
		
		return resultMap;
	}
}
