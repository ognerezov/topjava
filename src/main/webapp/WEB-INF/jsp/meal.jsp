<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><a href="${pageContext.request.contextPath}/"><fmt:message key="app.home"/></a></h2>
    <h3>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="POST" action="meals?action=update">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit">Save</button>
        <button type="reset">Reset</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>