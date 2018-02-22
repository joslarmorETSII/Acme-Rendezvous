<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/19/18
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="rendezvous" id="row" pagesize="10" class="displaytag" requestURI="${requestUri}">
    <jstl:set var="testuser" value="${user}"/>
    <jstl:set var="now" value="${now}"/>


    <display:column>
        <security:authorize access="hasRole('USER')">
        <jstl:if test="${row.finalMode == false && row.creator eq testuser && row.deleted ne true}">
            <acme:button url="rendezvous/user/edit.do?rendezvousId=${row.id}" code="rendezvous.edit"/>
        </jstl:if>
    </security:authorize>
    </display:column>
    <acme:column code="rendezvous.creator" value="${row.creator.name}"/>
    <acme:column code="rendezvous.name" value="${row.name}"/>
    <acme:column code="rendezvous.description" value="${row.description}"/>
    <acme:column code="rendezvous.moment" value="${row.moment}" sortable="true"/>

    <display:column>
        <jstl:set var="contains" value="false" />
        <jstl:forEach var="item" items="${row.participated}">
            <jstl:if test="${item.attendant == testuser}">
                <jstl:set var="contains" value="true"/>
            </jstl:if>
        </jstl:forEach>
        <jstl:if test="${contains eq false && row.deleted ne true && row.moment ge now }">
            <acme:button url="participate/user/create.do?rendezvousId=${row.id}" code="rendezvous.participate"/>
        </jstl:if>
        <jstl:if test="${row.deleted eq true && row.moment ge now}">
            <spring:message code="rendezvous.deleted" var="delet"/><jstl:out value="${delet}"/>
        </jstl:if>
        <jstl:if test="${contains eq true  && row.deleted ne true && row.creator ne testuser && row.moment ge now }">
            <acme:button url="participate/user/${cancelUri}.do?rendezvousId=${row.id}" code="rendezvous.cancelparticipate"/>
        </jstl:if>
        <jstl:if test="${row.moment lt now }">
            <spring:message code="rendezvous.passed" var="passed"/><jstl:out value="${passed}"/>
        </jstl:if>
    </display:column>

    <display:column >
        <jstl:if test="${contains eq true && row.deleted ne true}">
             <acme:button url="comment/user/create.do?rendezvousId=${row.id}" code="comment.rendezvous.create"/>
        </jstl:if>
    </display:column>


</display:table>

<acme:button code="rendezvous.create" url="rendezvous/user/create.do"/>



