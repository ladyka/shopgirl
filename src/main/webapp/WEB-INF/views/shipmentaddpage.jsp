<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить новый товар</title>
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
      context.fillText('Регистрация', x, y);
    </script>
    </a>


<form action="shipmentaddpage" method="post">

<table border="0" >

<tr>
<td>Уникальный идентификатор : </td>

<td><input type="text" name="id" value=""></td>
</tr>

<tr>
<td>Описание: </td>

<td><input type="text" name="description" value=""></td>
</tr>


<tr>
<td>Ссылка на картинку: </td>

<td><input type="text" name="imageURI" value=""></td>
</tr>


<tr>
<td>Имя товара: </td>

<td><input type="text" name="name" value=""></td>
</tr>



<tr>
<td>Идентификатор каталога</td>

<td><input type="text" name="catalogid" value=""></td>
</tr>

<tr>
<td></td>

<td><input type="submit" value="Go"></td>
</tr>

</table>

</form>

</body>
</html>