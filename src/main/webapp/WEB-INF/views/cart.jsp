<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Корзина пользователя</title>
<style>
   div {
    border: 1px solid black; /* Параметры рамки */
    padding: 5px; /* Поля вокруг текста */
    margin-bottom: 5px; /* Отступ снизу */
   }
   #left { text-align: left; }
   #right { text-align: right; }
   #center { text-align: center; }
   }
  </style>
</head>
<body>
<a href="/store/" >
<div id="center"><canvas id="myCanvas" width="800" height="100" ></canvas></div>
    <script>
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
Привет, Гость.<br>
<a href="/store/register"  >Зарегистрируйся пожалуйста</a> или <a href="/store/login">войди</a>.

</c:if>
<c:if test="${user}">

Привет. ${appUserName}.<br>
<a href="/store/cabinet" >Личный кабинет</a>. <a href="/store/cart">Корзина</a> <a href="/store/logout" >Выйти</a>
</c:if>
</p>

	<table>
		<tr> 
			<td>
				Имя товара
			</td>
			<td>
				Cтатус
			</td>	
			<td>
				Время изменения
			</td>		
			<td>
				Ссылка на товар
			</td>
			<td>
				Удалить из корзины
			</td>				
		</tr>
		 <c:forEach var="element" items="${elements}">
			<tr>
				<td><c:out value="${element.name}"/></td>
				<td><c:out value="${element.status}"/></td>
				<td><c:out value="${element.timestamp}"/></td>
				<td>
					<a href="shipment/<c:out value="${element.id}"/>" >Перейти</a>
				</td>
				<td>
					<a href="cart/del/<c:out value="${element.oderid}"/>" >Удалить</a>
				</td>
			</tr> 
		 </c:forEach> 
	</table>
</body>
</html>