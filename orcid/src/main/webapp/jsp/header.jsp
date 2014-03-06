<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="anu" uri="http://www.anu.edu.au/taglib"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<anu:body />
<anu:banner id="" ssl="true" primaryTitle="ANU Orcid Generator/Updater" secondaryTitle="Information Technology Services" primaryTitleUrl="/" secondaryTitleUrl="/" />

<anu:menu showSearch="false" id="1108" shortTitle="Admin" ssl="true">
	<anu:submenu title="">
		<sec:authorize access="isAnonymous()">
			<li>Welcome Guest</li>
			<li><a href="<c:url value='/login'/>">Login</a></li>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<li>Welcome <sec:authentication property="principal.username"/></li>
			<li><a href="<c:url value='/logout'/>">Logout</a></li>
		</sec:authorize>
	</anu:submenu>
	<anu:submenu title="Orcid Generator/Updater">
		<li><a href="<c:url value='/' />">Home</a></li>
		<sec:authorize access="isAuthenticated()">
			<c:set var="username"><sec:authentication property="principal.username"/></c:set>
			<li><a href='<c:url value="/rest/uid/${username}" />'>My Profile</a></li>
		</sec:authorize>
	</anu:submenu>
</anu:menu>
