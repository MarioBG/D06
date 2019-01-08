
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="tutorial.title" var="title" ></spring:message>
<spring:message code="tutorial.updateTime" var="updateTime" ></spring:message>
<spring:message code="tutorial.summary" var="summary" ></spring:message>

<display:table name="tutorials" id="row"
	requestURI="${requestURI}" pagesize="5">
	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<a href="sponsorship/sponsor/create.do?tutorialId=${row.id}"><spring:message code="master.page.sponsorship.sponsorTutorial"/></a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${ requestURI == 'tutorial/handyworker/list.do' }">
			<display:column>
				<a href="tutorial/handyworker/edit.do?tutorialId=${row.id}"> <spring:message
						code="tutorial.edit"></spring:message>
				</a>
			</display:column>
			<display:column>
				<a href="tutorial/handyworker/delete.do?tutorialId=${row.id}"> <spring:message
						code="tutorial.delete"></spring:message></a>
			</display:column>
		</jstl:if>
	</security:authorize>

	<display:column>
				<a href="tutorial/display.do?tutorialId=${row.id}"> <spring:message
						code="tutorial.display"></spring:message></a></display:column>
	<display:column property="title" title="${title}"></display:column>
	<display:column property="updateTime" title="${updateTime}"></display:column>
	<display:column property="summary" title="t${summary}"></display:column>

</display:table>


<input type="button" name="create"
	value="<spring:message code="tutorial.create" />"
	onclick="javascript: relativeRedir('tutorial/handyworker/create.do');" />
	
	