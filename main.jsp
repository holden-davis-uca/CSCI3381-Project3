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
<title>JTwitter - Main</title>
</head>
<body>
	<form action="/CSCI3381Project3/CSCI3381Project3Servlet" method="get">
	<label for="userlist">Users</label><br>
	<% String selectionText=(String)request.getAttribute("userlist");%>
	<%=selectionText%><br>
	<input type="submit" name="mainpost" value="Post"><br>
	<select name="mainsearchtype">
	<option value="id">Search by ID</option>
	<option value="user">Search by User</option>
	</select><br>
	<input type="text" name="searchfield"><br>
	<input type="submit" name="mainsearch" value="Search"><br>
	<input type="submit" name="mainview" value="View Tweets by User"><br>
	<input type="submit" name="mainindex" value="Login"><br>
	</form>
</body>
</html>