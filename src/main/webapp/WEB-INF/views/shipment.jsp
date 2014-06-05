<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${shipmentTitle}"/></title>
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

<div id="center">
<h1><c:out value="${shipmentName}"/></h1>
<img src="<c:out value="${shipment.imageURI}"/>" />
</div>

<div id="justify">
<p><c:out value="${shipment.description}"/></p>

</div>
	<table>
		<tr> 
			<td>
				Имя магазина
			</td>
			<td>
				Цена
			</td>
			<td>
				Описание
			</td>		
			<td>
				Добавить в корзину
			</td>			
		</tr>
		 <c:forEach var="entity" items="${prices}">
			<tr>
				<td><c:out value="${entity.nameshop}"/></td>
				<td><c:out value="${entity.priceInt}"/></td>
				<td><c:out value="${entity.description}"/></td>
				<td><a href="../cart/add/<c:out value="${entity.id}"/>" >Добавить</a></td>
			</tr> 
		 </c:forEach> 
	</table>
</body>
</html>