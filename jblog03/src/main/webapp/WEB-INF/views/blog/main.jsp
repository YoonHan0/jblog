<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${authUser.name }님의 이야기</h1>
			<ul>
				<c:choose>
					<c:when test='${empty authUser }'>
						<li><a href="">로그인</a></li>	
					</c:when>
					
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
						<li><a href="">블로그 관리</a></li>	
					</c:otherwise>
				</c:choose>
				
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${postVo.title }</h4>		<!-- post.title -->
					<p>									<!-- post.contents -->
						${postVo.contents }
					<p>
				</div>
				<ul class="blog-list">			<!-- post. title, reg_date -->
					<c:forEach items='${postList }' var='post' varStatus='status'>
						<li><a href="${pageContext.request.contextPath}/blog/main?no=${category.no }&postNo=${post.no }">${post.title }</a> <span>${post.regDate }</span>	</li>	
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.profile }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items='${categoryList }' var='category' varStatus='status'>
					<li><a href="${pageContext.request.contextPath}/blog/main?no=${category.no }&postNo=">${category.name }</a></li>
				</c:forEach>
				
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>