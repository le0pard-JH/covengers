<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
    <%
    	String ctxPath = request.getContextPath();
    %>
    
    <jsp:include page="../covengers_header.jsp"></jsp:include>
${message}



	<button type="button" onclick="">주문 내역 페이지로 돌아가기</button>
	<button type="button" onclick="javascript:location.href='<%= ctxPath%>/product/showReview.com'">리뷰 게시판 이동</button>
	



