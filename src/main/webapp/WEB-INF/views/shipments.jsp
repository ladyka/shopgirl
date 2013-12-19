<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${categoryName}"/></title>
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
<h1><c:out value="${categoryName}"/></h1>

	<table>
		<tr> 
			<td>
				Картинка
			</td>
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
		 <c:forEach var="shipment" items="${shipments}">
			<tr>
				<td><img src="<c:out value="${shipment.imageURI}"/>" width="189" /></td>
				<td><c:out value="${shipment.name}"/></td>
				<td><c:out value="${shipment.description}"/></td>
				<td><a href="../shipment/<c:out value="${shipment.id}"/>" >Перейти</a></td>
			</tr> 
		 </c:forEach> 
	</table>
</body>
</html>