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
<title><spring:message code="tutorial.create" /></title>
</head>
<body>
<form:form modelAttribute="section" action="section/handyworker/create.do">

		<form:hidden path="tutorialId"/>
		<form:hidden path="updateTime"/>
		<!-- Title -->
			 <form:label path="title">
				<spring:message code="section.title" />
			</form:label>
			<form:input path="title" />
			<form:errors path="title" />

		<!-- Summary -->
			<form:label path="text">
				<spring:message code="section.text" />
			</form:label>
			<form:input path="text" />
			<form:errors path="text" />

		<!-- Pictures -->
		<form:label path="pictures">
				<spring:message code="section.pictures" />
			</form:label>
		<form:textarea path="pictures" />
			<form:errors path="pictures" /> 

	</form:form>
	
	<input type="button" name="save"
	value="<spring:message code="tutorial.save" />"/>
	
	<input type="button" name="cancel"
	value="<spring:message code="tutorial.cancel" />"
	onclick="javascript: relativeRedir('tutorial/viewTutorial.do');" />

</body>
</html>