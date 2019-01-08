
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="section.title" var="title" ></spring:message>
<spring:message code="section.text" var="text" ></spring:message>

<jstl:choose>
	<jstl:when test="${ not isNull }">
		
		<h3><spring:message code="tutorial.title"/>: <jstl:out value="${tutorial.title}"/></h3>
		<h3><spring:message code="tutorial.summary"/></h3>
		<p><jstl:out value="${tutorial.summary}"/></p>
		<h3><spring:message code="tutorial.updateTime"/></h3>
		<p><jstl:out value="${tutorial.updateTime}"/></p>
		<h3><spring:message code="tutorial.sections"/></h3>
		<display:table name="sections" id="row"
			requestURI="tutorial/display.do" pagesize="5">
			<security:authorize access="hasRole('HANDYWORKER')">
				<jstl:if test="${ isOwner }">
					<display:column>
						<a href="section/handyworker/edit.do?sectionId=${row.id}"> <spring:message
								code="section.edit"></spring:message>
						</a>
					</display:column>
					<display:column>
						<a href="section/handyworker/delete.do?sectionId=${row.id}"> <spring:message
								code="section.delete"></spring:message></a>
					</display:column>
				</jstl:if>
			</security:authorize>
			<display:column>
						<a href="section/display.do?sectionId=${row.id}"> <spring:message
								code="section.display"></spring:message></a>
					</display:column>
		
			<display:column property="title" title="${title}"></display:column>
			<display:column property="text" title="${text}"></display:column>
		
		</display:table>
		<security:authorize access="hasRole('HANDYWORKER')">
		<input type="button" name="create"
			value="<spring:message code="section.create" />"
			onclick="javascript: relativeRedir('section/handyworker/create.do?tutorialId=${ tutorial.id }');" />
		</security:authorize>
		<h3><spring:message code="tutorial.pictures"/></h3>
		<jstl:forEach items="${ tutorial.pictures }" var="picture">
			<p>
				<img src="<jstl:out value="${picture}"/>"/>
			</p>
		</jstl:forEach>
		<jstl:if test="${not empty advertisement }">
			<fieldset>
				<legend><spring:message code="sponsorship.advertisement"/></legend><br/>
				<a href="${ advertisement.link }"><img alt="" src="${ advertisement.banner }" style="transform: none;"></a>
			</fieldset>
		</jstl:if>
	</jstl:when>
	<jstl:otherwise>
		<h1><spring:message code="tutorial.notfound"/></h1>
		<h3><a href="tutorial/list.do"><spring:message code="master.page.back"/></a></h3>
	</jstl:otherwise>
</jstl:choose>
	
	