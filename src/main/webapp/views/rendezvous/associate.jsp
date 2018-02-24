<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 24/02/2018
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="rendezvous/user/associate.do" modelAttribute="rendezvous">


    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="comments"/>
    <form:hidden path="announcements"/>
    <form:hidden path="questions"/>
    <form:hidden path="participated"/>
    <form:hidden path="creator"/>
    <form:hidden path="parentRendezvous"/>

    <security:authorize access="hasRole('USER')">
        <form:label path="associated"><spring:message code="rendezvous.associated"/></form:label>
        <form:select path="associated">
            <form:option label="----" value="0"/>
            <form:options items="${allRendezvous}" itemLabel="name" itemValue="id"/>
        </form:select>
        <form:errors path="associated" cssClass="error"/>
        <br/>
    </security:authorize>


    <input type="submit" name="save" value="<spring:message code="rendezvous.save"/>" />

    <input type="button" name="cancel" value="<spring:message code="rendezvous.cancel" />"
           onclick="javascript: relativeRedir('rendezvous/user/list.do');" />

</form:form>




