<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fabflix LikePredicate</title>
</head>
<body>
<h2>Hi I am the Like Predicate</h2>

<p> The Like-predicate was used in MovieList.java</p>

<p>It is used in lines 200 & 202 for retrieving movie Id from the database. When the user uses Advanced Search feature for browsing movies, this query is used to retrieve the result.</p> 
<p>It is used in line 284 for retrieving results from the database when the user uses Browse by title  feature for browsing movie list.</p>
<p>Line numbers:</p>
<p>200-" select movie_id from stars_in_movies where star_id in (select id from stars where upper(concat(first_name,' ',last_name)) like '%"+name.toUpperCase()+"%'))" </p>
<p>202-" select movie_id from stars_in_movies where star_id in (select id from stars where upper(first_name) like '%"+name.toUpperCase()+"%' or upper(last_name) like '%"+name+"%'))"</p>
<p>284-"select * from movies where title like '"+title+"%'";</p>

 


</body>
</html>