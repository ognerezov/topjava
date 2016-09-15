<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
    <style>
    .red {
    	color: #ff0000;
    	font-style: italic;
    	font-weight: bold;
    }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>

<c:url var="addUrl" value="/topjava/addmeal" />
<table style="border: 1px solid; width: 620px; text-align:center">
	<thead style="background:#58D3F7">
		<tr>
			<th>ID</th>
			<th>Название</th>
			<th>Кол-во калорий</th>
			<th>Время приема</th>
			<th>Превышена ли дневная норма</th>
			<th colspan="5"></th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${meals}" var="meal">

				<c:url var="editUrl" value="/topjava/editmeal?id=${meal.id}" />
    			<c:url var="deleteUrl" value="/topjava/deletemeal?id=${meal.id}" />

	<c:if test="${meal.exceed}">
		<tr class="red">
            <td ><c:out value="${meal.id}"/></td>
			<td ><c:out value="${meal.description}"/></td>
			<td ><c:out value="${meal.calories}" /></td>
			<td ><c:out value="${meal.stringDateTime}" /></td>
			<td ><c:out value="${meal.exceed}" /></td>
						<td><a href="${editUrl}">Edit</a></td>
            			<td><a href="${deleteUrl}">Delete</a></td>
            			<td><a href="${addUrl}">Add</a></td>
		</tr>
			</c:if>

            <c:if test="${!meal.exceed}">
         <tr>
            <td ><c:out value="${meal.id}"/></td>
            <td ><c:out value="${meal.description}"/></td>
            <td ><c:out value="${meal.calories}" /></td>
            <td ><c:out value="${meal.stringDateTime}" /></td>
            <td ><c:out value="${meal.exceed}" /></td>
            			<td><a href="${editUrl}">Edit</a></td>
            			<td><a href="${deleteUrl}">Delete</a></td>
            			<td><a href="${addUrl}">Add</a></td>
          </tr>
            </c:if>


	</c:forEach>
	</tbody>
</table>

</body>
</html>
