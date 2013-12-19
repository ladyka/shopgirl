<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Магазин женской одежды</title>
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
				Имя
			</td>
			<td>
				Описание
			</td>		
			<td>
				Ссылка на каталог
			</td>			
		</tr>
		 <c:forEach var="category" items="${categories}">
			<tr>
				<td><c:out value="${category.nameCategory}"/></td>
				<td><c:out value="${category.description}"/></td>
				<td>
					<a href="category/<c:out value="${category.id}"/>" >Перейти</a>
				</td>
			</tr> 
		 </c:forEach> 
	</table>
</body>
</html>
