<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="sponsorship" requestURI="sponsorship/sponsor/list.do"
	id="row">

	<!-- Action links -->
	<!-- Attributes -->

	<%-- <display:column>
		<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}"><spring:message
				code="sponsorship.edit" /></a>
	</display:column> --%>

	<spring:message code="sponsorship.bannerURL" var="bannerURL" />
	<display:column title="${bannerURL}"
		sortable="false" >
		<img src="<jstl:out value="${row.banner}"/>" style="max-width:92px; max-height:64px; width:auto; height:auto;"/>
		</display:column>
		
	<spring:message code="sponsorship.infoPageLink" var="infoPageLink" />
	<display:column title="${infoPageLink}"
		sortable="false" >
		<a href="<jstl:out value="${row.link}"/>"><jstl:out value="${row.link}"/></a>
		</display:column>

	<spring:message code="sponsorship.creditCard" var="creditCard" />
	<display:column property="creditCard.brandName" title="${creditCard}"
		sortable="false" />
		
		<spring:message code="sponsorship.tutorial" var="trip" />
	<display:column property="tutorial.title" title="${trip}"
		sortable="false" />
		
	<display:column>
			<a href="tutorial/display.do?tutorialId=<jstl:out value="${row.tutorial.id}"/>"><spring:message code="sponsorship.showTutorial"/></a><br/>
	</display:column>
</display:table>

<a href="sponsorship/sponsor/create.do"><spring:message
		code="sponsorship.create" /></a>


