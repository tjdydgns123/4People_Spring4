package com.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.forpeople.Controller;
import com.util.HashMapBinder;

public class LoginController implements Controller {
	LoginLogic l_logic = new LoginLogic();
	String command =null;
	Logger logger =  Logger.getLogger(LoginController.class);
	
	public LoginController() {
		
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String path=null;
		command = req.getParameter("command");
		//요청온 파라미터값을 pMap에 담아준다
		Map<String,Object> pMap = new HashMap<String,Object>();
		HashMapBinder binder = new HashMapBinder(req);
		binder.bind(pMap);
		//로그인 요청이 왔을떄
		if("login".equals(command)) {
			logger.info("LoginController login 호출");
			logger.info(pMap.size());
			//요청온 파라미터값들을 logger로 찍음.
			for(String key:pMap.keySet()) {
				logger.info("key="+key);
				logger.info("value="+pMap.get(key));
			}
			//DB에서 받아온 값을 List에 담아줌.
			List<Map<String,Object>> loginList=l_logic.loginCall(pMap);
			logger.info("loginList.size"+loginList.size());
			//DB에서 받아온 값들을 Logger에 찍어봄
			for(Map<String,Object> rMap:loginList) {
				for(String key:rMap.keySet()) {
					logger.info(key+" rMap:"+rMap.get(key));
				}
			}
			//넘어온 값들을 Web에 넘겨준다.
			req.setAttribute("loginList",loginList);
			//이동할 페이지.
			path="forward:loginAction.jsp";
		}
		//회원가입 요청이 들어왔을떄
		else if("signUp".equals(command)) {
			logger.info("LoginController signUp 호출");
			String mem_addr ="";
			for(String key:pMap.keySet()) {
				logger.info("key="+key);
				logger.info(pMap.get(key));
				if(key.equals("mem_addr1")||key.equals("mem_addr2")||key.equals("mem_addr3")) {
					mem_addr+=pMap.get(key);
				}
				
			}
			logger.info("mem_addr="+mem_addr);
			pMap.put("mem_addr", mem_addr);
			l_logic.signUp(pMap);
//			path="forward|login.jsp";
			path="forward:./login.jsp";
		}
		return path;
	}

}
