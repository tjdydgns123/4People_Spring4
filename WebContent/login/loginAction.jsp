<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%
	List<Map<String,Object>> loginList = (List<Map<String,Object>>)request.getAttribute("loginList");
	String id =null;
	String name = null;
	for(Map<String,Object> rMap:loginList) {
	for(String key:rMap.keySet()) {
		session.setAttribute(key, rMap.get(key));
		if(key.equals("MEM_EMAIL")){
			id = (String)rMap.get(key);
		}
		else if(key.equals("MEM_NAME"));
		    name = (String)rMap.get(key);
	}
}
	if(id!=null&&name!=null){
		
	%>
	
	
	
	<script>
		location.href="../intro/main.jsp";
	</script>
	<%
	} else if(loginList.size()==0||loginList==null){%>
	<script>
		alert("로그인실패");
		history.go(-1);
	</script>
	<%
	}
	%>
	
			
