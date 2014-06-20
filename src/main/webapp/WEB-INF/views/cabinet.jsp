<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Личный кабинет</title>
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
		<br><a href="shipmentaddpage" >Добавить товар</a>
		<br><a href="clientmain" >Операции над пользователями</a>
		<br><a href="search" >Поискать</a>
		<br><a href="https://oauth.vk.com/authorize?client_id=<c:out value="${client_id}" />&scope=notify,friends,photos,audio,video,docs,notes,pages,status,wall,groups,notifications,stats,offline&redirect_uri=<c:out value="${redirect_uri}" />&response_type=code&v=5.21" >Подключить Вконтакте</a>

			


		</c:if>
	</p>





</body>
</html>