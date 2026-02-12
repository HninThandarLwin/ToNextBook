<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>次のー冊へ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookCard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">


	<script src="https://cdn.tailwindcss.com"></script>
	<script src="${pageContext.request.contextPath}/js/login.js" defer></script>
	<script src="${pageContext.request.contextPath}/js/profile.js" defer></script>
	
	<script src="${pageContext.request.contextPath}/js/ramdomBook.js" defer></script>
</head>
<body data-ctx="${pageContext.request.contextPath}">
	<jsp:include page="header.jsp"/>
	    <main>
	        <jsp:include page="${page}.jsp"/>
	    </main>
	<jsp:include page="footer.jsp"/>
	<jsp:include page="../login.jsp" />
<c:if test="${page == 'search'}">
    <script src="${pageContext.request.contextPath}/js/search.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/resultBooks.js" defer></script>
</c:if>
</body>
</html>