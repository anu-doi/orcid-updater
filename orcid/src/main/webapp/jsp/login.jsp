<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="anu" uri="http://www.anu.edu.au/taglib"%>

<anu:header id="1998" title="Login" description="description" subject="subject" respOfficer="Doug Moncur" respOfficerContact="mailto:doug.moncur@anu.edu.au"
	ssl="true">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/default.css' />" />
</anu:header>

<jsp:include page="/jsp/header.jsp" />

<anu:content layout="doublewide">
	<c:url value="/login" var="loginUrl"/>
	<form action="${loginUrl}" method="post">       
		<c:if test="${param.error != null}">        
			<p>
				Invalid username and password.
			</p>
		</c:if>
		<c:if test="${param.logout != null}">       
			<p>
				You have been logged out.
			</p>
		</c:if>
		<p>
			<label for="username">Username</label>
			<input type="text" id="username" name="username"/>	
			<br/>
			<label for="password">Password</label>
			<input type="password" id="password" name="password"/>	
		</p>
		<input type="hidden"                        
				name="${_csrf.parameterName}"
				value="${_csrf.token}"/>
		<button type="submit" class="btn">Log in</button>
	</form>
</anu:content>

<jsp:include page="/jsp/footer.jsp" />