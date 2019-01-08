
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="banner">
		<spring:message code="sponsorship.bannerURL" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br/>
	
	
	<form:label path="link">
		<spring:message code="sponsorship.infoPageLink" />:
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link" />
	<br/>

	<%-- <form:label path="creditCard">
		<spring:message code="sponsorship.creditCard"/>:
	</form:label>
	<form:select path="creditCard">
		<form:option value="0">----</form:option>
		<form:options
			items="${creditCards}"
			itemLabel="number"
			itemValue="id"
		/>
	</form:select>
	<form:errors cssClass="error" path="creditCard"/>
	Si la lista de tarjetas esta vacia, debemos crear una nueva
	<jstl:if test="${empty creditCards}">
		<a href="creditCard/manager/create.do"><spring:message code="sponsorship.create"/></a>
	</jstl:if> --%>
	<fieldset>
	<legend><spring:message code="sponsorship.introduceCreditCard"/></legend><br/>
	
	<form:label path="creditCard.holderName">
		holderName:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br/>
	
	<form:label path="creditCard.brandName">
		brandName:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br/>

	<form:label path="creditCard.number">
		number:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number"/>
	<br/>

	<form:label path="creditCard.expirationMonth">
		expirationMonth:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br/>

	<form:label path="creditCard.expirationYear">
		expirationYear:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br/>

	<form:label path="creditCard.CVV">
		cvv:
	</form:label>
	<form:input path="creditCard.CVV" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br/>
	</fieldset>

	<br/>
	<jstl:choose>
	<jstl:when test="${ empty chosenTutorial }">
		<form:label path="tutorial">
			<spring:message code="sponsorship.tutorial"/>:
		</form:label>
		<form:select path="tutorial">
			<form:option value="0">----</form:option>
			<form:options
				items="${tutorials}"
				itemLabel="title"
				itemValue="id"
			/>
		</form:select>
		<form:errors cssClass="error" path="tutorial"/>
	</jstl:when>
	<jstl:otherwise>
			<spring:message code="sponsorship.tutorial"/>: <jstl:out value="${ chosenTutorial.title }"/>
	</jstl:otherwise>
	</jstl:choose>
	
	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="sponsorship.save" />" />&nbsp; 

	<%-- SOLO SE PUEDE ELIMINAR SI ESTAMOS EDITANDO, NO SI ESTAMOS CREANDO --%>
	<jstl:if test="${sponsorship.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="sponsorship.delete" />"
		onclick="return confirm('<spring:message code="sponsorship.confirm.delete" />')" />&nbsp; 
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="sponsorship.cancel" />"
		onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');" />

</form:form>
