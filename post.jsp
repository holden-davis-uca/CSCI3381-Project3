<!---
//Holden Davis
//CSCI 3381 - CRN 18741
//Project 3 - Dr. Doderer - Fall 2021 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta charset="ISO-8859-1">
<title>JTwitter - Post</title>
</head>
<body>
	<form action="/CSCI3381Project3/CSCI3381Project3Servlet" method="get">
	<label for="polarities">Polarity</label><br>
	<input type="radio" name="polarities" value="4">4
	<input type="radio" name="polarities" value="2" checked>2
	<input type="radio" name="polarities" value="0">0<br>
	<label for="ID">ID</label><br>
	<input type="number" name="ID"><br>
	<label for="User">User</label><br>
	<input type="text" name="User"><br>
	<label for="Content">Content</label><br>
	<input type="text" name="Content"><br>
	<input type="checkbox" name="autogencheck">Auto Generate ID<br>
	<input type="submit" name="postpost" value="Post"><br>
	<input type="submit" name="postmain" value="Main"><br>
	</form>
</body>
</html>