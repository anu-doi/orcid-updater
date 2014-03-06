<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="anu" uri="http://www.anu.edu.au/taglib"%>

<anu:header id="1998" title="Error" description="description" subject="subject" respOfficer="Doug Moncur" respOfficerContact="mailto:doug.moncur@anu.edu.au"
	ssl="true">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/default.css' />" />
</anu:header>

<jsp:include page="/jsp/header.jsp" />

<anu:content layout="doublewide">
	<h1>Error</h1>
	<p>${it.error}</p>
</anu:content>

<jsp:include page="/jsp/footer.jsp" />