<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<a href="http://localhost:48080/shop-0.0.1/" >
<div id="center"><canvas id="myCanvas" width="800" height="100" ></canvas></div>
    <script>
      var canvas = document.getElementById('myCanvas');
      var context = canvas.getContext('2d');
      var x = canvas.width / 2;
      var y = canvas.height / 2;

      context.font = '30pt Calibri';
      context.textAlign = 'center';
      context.fillStyle = 'blue';
      context.fillText('Каталог магазина женской одежды', x, y);
    </script>
    </a>
    
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
		</tr>
		 <c:forEach var="element" items="${elements}">
			<tr>
				<td><c:out value="${element.name}"/></td>
				<td><c:out value="${element.status}"/></td>
				<td><c:out value="${element.timestamp}"/></td>
				<td>
					<a href="shipment/<c:out value="${element.id}"/>" >Перейти</a>
				</td>
			</tr> 
		 </c:forEach> 
	</table>
</body>
</html>