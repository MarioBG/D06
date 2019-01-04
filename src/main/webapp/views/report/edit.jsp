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
<title><spring:message code="report.edit" /></title>
</head>
<body>

	<form:form modelAttribute="report" action="report/referee/edit.do">
		<form:hidden path="id" />
		<form:hidden path="version" />


		<!-- Moment -->
		<form:label path="moment">
			<spring:message code="report.moment" />
		</form:label>
		<form:input placeholder="${report.moment}" path="moment" />
		<form:errors path="moment" />

		<!-- Description -->
		<form:label path="description">
			<spring:message code="report.description" />
		</form:label>
		<form:input placeholder="${report.description}" path="description" />
		<form:errors path="description" />

		<!-- Attachments -->
		<form:label path="attachments">
			<spring:message code="report.attachments" />
		</form:label>
		<form:textarea placeholder="${report.attachments}" path="attachments" />
		<form:errors path="attachments" />
		
		
	<input type="submit" name="save"
		value="<spring:message code="report.save" />" />

	<input type="submit" name="saveFinalMode"
		value="<spring:message code="report.save.finalmode" />" />

	<input type="submit" name="delete"
		value="<spring:message code="report.delete" />" />

	<input type="submit" name="cancel"
		value="<spring:message code="report.cancel" />"
		onclick="javascript: relativeRedir('report/referee/list.do');" />

	</form:form>

</body>
</html>