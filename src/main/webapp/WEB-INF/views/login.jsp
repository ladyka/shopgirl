<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login Page</title><style>
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

<form method="post" action="j_spring_security_check">
<input size="30" maxlength="128" id="accountname" type="text" name="j_username"/>
<br>
<input size="30" maxlength="32" id="accountpassword" type="password" name="j_password"/>
<input alt="Sign In" type="submit"/>
</form>
<a href="register" >Или зарегестрируйтесь </a>
</body>
</html>