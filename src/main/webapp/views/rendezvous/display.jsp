<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<br/>
<br/>
<h1><spring:message code="rendezvous.name"/>:&nbsp;<jstl:out value="${rendezvous.name}" /></h1>
<br/>

<b><spring:message code="rendezvous.description"/>:&nbsp;</b><jstl:out value="${rendezvous.description}" />
<br/>

<b><spring:message code="rendezvous.moment"/>:&nbsp;</b><jstl:out value="${rendezvous.moment}" />
<br/>

<b><spring:message code="rendezvous.picture"/>:&nbsp;</b><jstl:out value="${rendezvous.picture}" />
<br/>

<b><spring:message code="rendezvous.location.longitude"/>:&nbsp;</b><jstl:out value="${rendezvous.location.longitude}" />
<b><spring:message code="rendezvous.location.latitude"/>:&nbsp;</b><jstl:out value="${rendezvous.location.latitude}" />

<br/>


<b><spring:message code="rendezvous.forAdults"/>:&nbsp;</b><jstl:out value="${rendezvous.forAdults}" />
<br/>




<input type="button" name="cancel" value="<spring:message code="rendezvous.cancel" />"
       onclick="javascript: relativeRedir('${cancelURI}');" />