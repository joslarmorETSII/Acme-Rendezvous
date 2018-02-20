<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 17/02/2018
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table id="announcement" name="announcements" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="announcement.title" value="${announcement.title}" />
    <acme:column code="announcement.moment" value="${announcement.moment}" />
    <acme:column code="announcement.description" value="${announcement.description}"/>

    <security:authorize access="hasRole('USER')">
        <display:column>
            <acme:button code="announcement.create" url="announcement/user/create.do?announcementId=${announcement.id}" />
        </display:column>
    </security:authorize>

    <security:authorize access="hasRole('USER')">
        <display:column>
            <acme:button code="announcement.edit" url="announcement/user/edit.do?announcementId=${announcement.id}" />
        </display:column>
    </security:authorize>

</display:table>