<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header >
    <div class="header-container">
        <nav>
            <a href="${pageContext.request.contextPath}/Home" class="logo"><i class="fa-solid fa-book-atlas fa-2x"></i>次の一冊へ</a>
            <ul>                    
                <li><a href="${pageContext.request.contextPath}/Home"><i class="fa-solid fa-house-chimney fa-1x"></i>Home</a></li>
                <li id="search"><a href="${pageContext.request.contextPath}/Search"><i class="fa-solid fa-magnifying-glass-arrow-right fa-1x"></i>Search</a></li>
                <li id="user">                
				<c:choose>
				    <c:when test="${not empty sessionScope.loginUser}">
				         <c:choose>
							<c:when test="${isProfilePage}">
				              <a href="${pageContext.request.contextPath}/Logout">
				                    <i class="fa-solid fa-right-from-bracket"></i> Logout
				                </a>
				            </c:when>
				            <c:otherwise>
				                <a href="${pageContext.request.contextPath}/Profile">
				                    <i class="fa-solid fa-user"></i> Profile
				                </a>
				            </c:otherwise>
				        </c:choose>
				    </c:when>
				    <c:otherwise>
				        <a href="javascript:void(0)" id="open-login">
				            <i class="fa-solid fa-right-to-bracket"></i> Login
				        </a>
				    </c:otherwise>
				</c:choose>
               </li>
            </ul>
        </nav>
    </div>
</header>