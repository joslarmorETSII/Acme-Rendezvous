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

<display:table name="comment" id="row" pagesize="5" class="displaytag" requestURI="comment/user/list.do">

    <acme:column code="comment.user" value="${row.user}" />
    <acme:column code="comment.text" value="${row.text}"/>
    <acme:column code="comment.moment" value="${row.moment}"/>
    <acme:column code="comment.picture" value="${row.picture}"/>

</display:table>

<acme:button code="comment.create" url="rondezvous/findAll.do"/>

