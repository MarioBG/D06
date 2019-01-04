
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="report.moment" var="moment"></spring:message>
<spring:message code="report.description" var="description"></spring:message>

<display:table name="reportsFM" id="row" requestURI="report/listFinalMode.do"
	pagesize="5">

	<display:column property="moment" titleKey="${moment}"></display:column>
	<display:column property="description" titleKey="${description}"></display:column>

	<display:column>
		<a href="report/attachments.do?reportId=${row.id}"> <spring:message
				code="report.attachments"></spring:message></a>
	</display:column>


</display:table>