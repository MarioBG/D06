
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

<h3><spring:message code="section.title"/>: <jstl:out value="${section.title}"/></h3>
<h3><spring:message code="section.text"/></h3>
<p><jstl:out value="${section.text}"/></p>
<h3><spring:message code="section.pictures"/></h3>
<jstl:forEach items="${ section.pictures }" var="picture">
	<p>
		<img src="<jstl:out value="${picture}"/>"/>
	</p>
</jstl:forEach>


<input type="button" name="create"
	value="<spring:message code="section.back" />"
	onclick="javascript: relativeRedir('tutorial/display.do?tutorialId=${tutorial.id}');" />
	
	