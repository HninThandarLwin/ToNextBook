<%
String lang = (String) request.getAttribute("lang");
Book book = (Book) request.getAttribute("book");
%>

<h2><%= book.getLocalizedName(lang) %></h2>
<p><%= book.getLocalizedDescription(lang) %></p>
<p>Status: <%= book.getLocalizedStatus(lang) %></p>
