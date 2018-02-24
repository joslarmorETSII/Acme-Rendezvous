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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="user/register.do" modelAttribute="userForm">


        <acme:textbox code="user.username" path="username" />
        <br/>

        <acme:password code="user.password" path="password" />
        <br/>

        <acme:password code="user.repeatPassword" path="repeatPassword" />
        <br/>  <br/>


        <acme:textbox code="user.name" path="name" />
        <br />

        <acme:textbox code="user.surname" path="surname" />
        <br />

        <acme:textbox code="user.phone" path="phone"/>
        <br />

        <acme:textbox code="user.email" path="email"/>
        <br />

        <acme:textbox code="user.postalAdresses" path="postalAdresses"/>
        <br/>

        <acme:textbox code="user.birthday" path="birthday"/>
        <br/>


        <div>
                <form:checkbox id="myCheck" onclick="comprobar();" path="check"/>
                <form:label path="check">
                        <spring:message code="user.accept" />
                        <a href="termAndCondition/termAndCondition.do"><spring:message code="user.lopd"/></a>
                </form:label>
        </div>


	<input type="submit" name="save" id="submitButton" value="<spring:message code="user.save"/>" />
	
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

<script>

    document.getElementById("submitButton").disabled = true;
    document.getElementById("myCheck").checked = false;

    function comprobar() {

        var x = document.getElementById("myCheck").checked;

        if(x == true){
            document.getElementById("submitButton").disabled = false;
        }
        else{
            document.getElementById("submitButton").disabled = true;
        }
    }
</script>

