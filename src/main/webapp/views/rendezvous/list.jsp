<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/19/18
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="rendezvous" id="row" pagesize="10" class="displaytag" requestURI="${requestUri}">


    <acme:column code="rendezvous.name" value="${row.name}"/>
    <acme:column code="rendezvous.description" value="${row.description}"/>
    <acme:column code="rendezvous.moment" value="${row.moment}" sortable="true"/>

    <display:column>
        <jstl:set var="testuser" value="${user}"/>
        <jstl:set var="contains" value="false" />
        <jstl:forEach var="item" items="${row.participated}">
            <jstl:if test="${item.attendant == testuser}">
                <jstl:set var="contains" value="true"/>
            </jstl:if>
        </jstl:forEach>
        <jstl:if test="${contains eq false}">
            <acme:columnButton url="participate/user/create.do?rendezvousId=${row.id}" codeButton="rendezvous.participate"/>
        </jstl:if>
        <jstl:if test="${contains eq true}">
            <acme:columnButton url="participate/user/cancel.do?rendezvousId=${row.id}" codeButton="rendezvous.cancelparticipate"/>
        </jstl:if>
    </display:column>

    <security:authorize access="hasRole('USER')">
        <jstl:if test="${row.finalMode == false && row.creator eq testuser}">
            <acme:columnButton url="rendezvous/user/edit.do?rendezvousId=${row.id}" codeButton="rendezvous.edit"/>

        </jstl:if>
    </security:authorize>

    <acme:columnButton url="comment/user/create.do?rendezvousId=${row.id}" codeButton="comment.rendezvous.create"/>

</display:table>

<acme:button code="rendezvous.create" url="rendezvous/user/create.do"/>

