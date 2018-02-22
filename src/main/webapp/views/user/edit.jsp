<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="user/edit.do" modelAttribute="user" onsubmit="return validateForm()">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount" />
	<form:hidden path="comments" />
	<form:hidden path="participates" />
	<form:hidden path="rendezvouses" />

	<jstl:if test="${user.id == 0}">
		<form:hidden path="userAccount.authorities" />

		<b><form:label path="userAccount.username"><spring:message code="user.userAccount.username" /></form:label>:&nbsp;</b>
		<form:input path="userAccount.username" />
		<form:errors path="userAccount.username" cssClass="error"  />
		<br />

		<b><form:label path="userAccount.password"><spring:message code="user.userAccount.password" /></form:label>:&nbsp;</b>
		<form:password path="userAccount.password" />
		<form:errors path="userAccount.password" cssClass="error"  />
		<br />
	</jstl:if>
	
	<b><form:label path="name"><spring:message code="user.name"/></form:label>:&nbsp;</b>
	<form:input path="name"/>
	<form:errors path="name" cssClass="error"/>
	<br/>
	
	<b><form:label path="surname"><spring:message code="user.surname"/></form:label>:&nbsp;</b>
	<form:input path="surname"/>
	<form:errors path="surname" cssClass="error"/>
	<br/>
	
	<b><form:label path="email"><spring:message code="user.email"/></form:label>:&nbsp;</b>
	<form:input path="email"/>
	<form:errors path="email" cssClass="error"/>
	<br/>
	
	<b><form:label path="postalAdresses"><spring:message code="user.postalAdresses"/></form:label>:&nbsp;</b>
	<form:input path="postalAdresses"/>
	<form:errors path="postalAdresses" cssClass="error"/>
	<br/>
	
	<b><form:label path="phone"><spring:message code="user.phone"/></form:label>:&nbsp;</b>
	<form:input path="phone" placeholder="+CC (AC) PN"/>
	<form:errors path="phone" cssClass="error"/>
	<br/>

    <b><form:label path="birthday"><spring:message code="user.birthday"/></form:label>:&nbsp;</b>
    <form:input path="birthday"/>
    <form:errors path="birthday" cssClass="error"/>
    <br/>


	<input type="submit" name="save" value="<spring:message code="user.save"/>" />
	
	<input type="button" name="cancel" value="<spring:message code="user.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<script>

function validateForm() {
 <spring:message code="user.phone.ask" var="ask"/>
    var x = document.getElementById("phoneId").value;
    var patt = new RegExp("^(\\+[1-9][0-9]{2}|\\+[1-9][0-9]|\\+[1-9])(\\s\\([1-9][0-9]{2}\\)|\\ \\([1-9][0-9]\\)|\\ \\([1-9]\\))?(\\ \\d{4,})|(\\d{4,})$");
    if(x != "" && !patt.test(x)){
        return confirm('<jstl:out value="${ask}"/>');
    } 
}

</script>

