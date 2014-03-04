<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="anu" uri="http://www.anu.edu.au/taglib"%>

<anu:header id="1998" title="${it.grant.title}" description="description" subject="subject" respOfficer="Doug Moncur" respOfficerContact="mailto:doug.moncur@anu.edu.au"
	ssl="true">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/academic.css' />" />
	<script type="text/javascript" src="<c:url value='/js/academic.js' />"></script>
</anu:header>

<jsp:include page="/jsp/header.jsp" />

<anu:content layout="doublenarrow">
	<h1>Information to send to orcid</h1>
	<div class="info">
		<c:if test="${not empty it.orcid}">
		<label>Orcid:</label> ${it.orcid}<br/>
		</c:if>
		<label>Given Name:</label> ${it.message.orcidProfile.orcidBio.personalDetails.givenNames}<br/>
		<label>Family Name:</label> ${it.message.orcidProfile.orcidBio.personalDetails.familyName}<br/>
		<label>Credit Name:</label> ${it.message.orcidProfile.orcidBio.personalDetails.creditName.value}<br/>
		<c:forEach var="email" items="${it.message.orcidProfile.orcidBio.contactDetails.email}">
			<c:if test="${email.primary }">
				<label>Primary Email:</label>
			</c:if>
			<c:if test="${!email.primary }">
				<label>Secondary Email:</label>
			</c:if>
			${email.value}<br/>
		</c:forEach>
		<label>Description:</label><br/>
		${it.message.orcidProfile.orcidBio.biography.value}
	</div>

</anu:content>
<anu:content layout="narrow">

<anu:box style="solid">
	<ul class="nobullet">
		<c:if test="${not empty it.orcid}">
			<li><a href='<c:url value="/rest/uid/${it.extid}/export/orcid/add-works" />'>Export Publications to Orcid</a></li>
			<li><a href='<c:url value="/rest/uid/${it.extid}/export/orcid/update-works" />'>Update Publications in Orcid</a></li>
			
		</c:if>
		<c:if test="${empty it.orcid}">
			<li><a href='<c:url value="/rest/uid/${it.extid}/export/orcid/create?action=find" />'>Link Existing Orcid Profile</a></li>
			<li><a href='<c:url value="/rest/uid/${it.extid}/export/orcid/create?action=create" />'>Create Orcid Profile</a></li>
		</c:if>
		<li><a href='<c:url value="/rest/uid/${it.extid}/import" />'>Re-import Record</a></li>
	</ul>
</anu:box>

</anu:content>

<anu:content layout="doublewide">
<h2>Publications</h2>
<p class="small">Click the publication name to view more details</p>
<c:if test="${not empty it.message.orcidProfile.orcidActivities.orcidWorks}">
	<ul class=" nobullet">
		<c:forEach var="work" items="${it.message.orcidProfile.orcidActivities.orcidWorks.orcidWork}" varStatus="theCount">
			<li class="has-tooltip">
				<label>${work.workTitle.title}</label>
				<br/>
					<div class="bg-uni25 text-uni">
						<label>Title</label><br/>
						${work.workTitle.title}<br/>
						<label>Publication Name:</label><br/>
						${work.journalTitle.content}<br/>
						<label>Year:</label><br/>
						${work.publicationDate.year.value}<br/>
						<label>Authors</label>
						<ul>
							<c:forEach var="author" items="${work.workContributors.contributor}">
								<li>
									${author.creditName.value}
								</li>
							</c:forEach>
						</ul>
						<label>Identifiers:</label><br/>
						<c:if test="${not empty work.workExternalIdentifiers.workExternalIdentifier}">
							<ul>
								<c:forEach var="identifier" items="${work.workExternalIdentifiers.workExternalIdentifier}">
									<li>
										${identifier.workExternalIdentifierType} - ${identifier.workExternalIdentifierId}
									</li>
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${empty work.workExternalIdentifiers.workExternalIdentifier}">
							No Identifiers Found
						</c:if>
					</div>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty  it.message.orcidProfile.orcidActivities.orcidWorks}">
	No Publications Found
</c:if>
</anu:content>

<jsp:include page="/jsp/footer.jsp" />