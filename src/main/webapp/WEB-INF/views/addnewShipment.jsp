<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавление товара</title>
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
<br><br>
			<c:if test="${allowToAddNewShipment}">
				<form method="POST" action="/store/shipmentEdit">
					<input type="hidden" name="id" value="<c:out value="${shipmentId}"/>" /> 
					<br>Имя товара<br>
					<input size="100" type="text" name="name" /> 
					<br>Ссылка на картинку<br>
					<input size="100"  type="text" name="imageURI" /> 
					<br>Описание товара <br>
					<textarea accesskey="m" rows="4" cols="48" id="mes-inp" name="description"></textarea> 
					<br>Категория<br>
					<select multiple name="catalogid">
						<c:forEach var="category" items="${categories}">
							<option value="<c:out value="${category.id}"/>"><c:out
									value="${category.nameCategory}" /></option>
						</c:forEach>
					</select>
					<br>Цена<br>
					<input size="100"  type="text" name="price" /> 
					
					
					<br>Кол-во товара на складе<br>
					<input size="100"  type="text" name="count" />
					
					<input alt="Add" type="submit" />

				</form>

			</c:if>


		</c:if>
	</p>





</body>
</html>