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
<title>JTwitter - Tweets</title>
</head>
<body>
	<form action="/CSCI3381Project3/CSCI3381Project3Servlet" method="get">
	<% String electionText=(String)request.getAttribute("tweetlist");%>
	<%=electionText%><br>
	<input type="radio" name="predicttype" value="Single" checked>Single
	<input type="radio" name="predicttype" value="All">All<br>
	<input type="submit" name="predict" value="Predict"><br>
	<input type="radio" name="deletetype" value="Single" checked>Single
	<input type="radio" name="deletetype" value="All">All<br>
	<input type="submit" name="delete" value="Delete"><br>
	<input type="submit" name="tweetsmain" value="Main"><br>
	</form>
</body>
</html>