<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="section.edit" /></title>
</head>
<body>

	<form:form modelAttribute="section" action="section/handyworker/edit.do">
		<form:hidden path="tutorialId"/>
		<!-- Title -->
		<p>
			 <form:label path="title">
				<spring:message code="section.title" />
			</form:label>
			<form:input placeholder="${tutorial.title}" path="title" />
			<form:errors path="title" />
		</p>

		<!-- Summary -->
		<p>
			<form:label path="text">
				<spring:message code="section.text" />
			</form:label>
			<form:input placeholder="${section.text}" path="text" />
			<form:errors path="text" />
		</p>
		<!-- Pictures -->
		<p>
		<form:label path="pictures">
				<spring:message code="section.pictures" />
			</form:label>
		<form:textarea placeholder="${section.pictures}" path="pictures" />
			<form:errors path="pictures" /> 
		</p>
		
		<input type="submit" name="save"
		value="<spring:message code="section.save" />"/>
		
		<input type="button" name="cancel"
		value="<spring:message code="section.cancel" />"
		onclick="javascript: relativeRedir('tutorial/display.do?tutorialId=${ tutorialId }');" />

	</form:form>
	
</body>
</html>