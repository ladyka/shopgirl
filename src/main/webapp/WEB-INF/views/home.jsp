<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<title>DREAMHEAD.RU | Магазин женской одежды</title>
<style>
div {
	border: 1px solid black; /* Параметры рамки */
	padding: 5px; /* Поля вокруг текста */
	margin-bottom: 5px; /* Отступ снизу */
}

#left {
	text-align: left;
}

#right {
	text-align: right;
}

#center {
	text-align: center;
}
}
</style>
</head>
<body>
	<a href="/store/">
		<div id="center">
			<canvas id="myCanvas" width="800" height="100"></canvas>
		</div> <script>
      var canvas = document.getElementById('myCanvas');
      var context = canvas.getContext('2d');
      var x = canvas.width / 2;
      var y = canvas.height / 2;

      context.font = '30pt Calibri';
      context.textAlign = 'center';
      context.fillStyle = 'blue';
      context.fillText('DREAMHEAD.RU', x, y);
    </script>
	</a>
	<p>
		<c:if test="${guest}">
			<p>
				Привет, Гость.<br> <a href="/store/register">Зарегистрируйся
					пожалуйста</a> или <a href="/store/login">войди</a>.
		</c:if>
		<c:if test="${user}">

Привет. ${appUserName}.<br>
			<a href="/store/cabinet">Личный кабинет</a>. <a href="/store/cart">Корзина</a>
			<a href="/store/logout">Выйти</a>
		</c:if>
	</p>




	<table>
		<tr>
			<td>Имя</td>
			<td>Описание</td>
			<td>Ссылка на каталог</td>
			<c:if test="${canEditCategories}">
				<td>Ссылка на редактирование категории</td>
			</c:if>
		</tr>
		<c:forEach var="category" items="${categories}">
			<tr>
				<td><c:out value="${category.nameCategory}" /></td>
				<td><c:out value="${category.description}" /></td>
				<td><a href="category/<c:out value="${category.id}"/>">Перейти</a>
				<c:if test="${canEditCategories}">
					<td><a href="category/edit/<c:out value="${category.id}"/>">Изменить</a>
				</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
