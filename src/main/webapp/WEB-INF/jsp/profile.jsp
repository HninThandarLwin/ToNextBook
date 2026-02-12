<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="user" value="${sessionScope.loginUser}" />

<!-- Cover Photo -->
<c:choose>
    <c:when test="${empty user.coverPhoto}">
        <c:set var="coverPhoto" value="./img/profileCover.png" />
    </c:when>
    <c:otherwise>
        <c:set var="coverPhoto" value="${user.coverPhoto}" />
    </c:otherwise>
</c:choose>

<!-- Profile Photo -->
<c:choose>
    <c:when test="${empty user.profilePhoto}">
        <c:set var="profilePhoto" value="./img/admin_profile.jpg" />
    </c:when>
    <c:otherwise>
        <c:set var="profilePhoto" value="${user.profilePhoto}" />
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>プロフィール</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Tailwind (or your CSS build) -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/profile.css">
</head>

<body class="bg-gray-100">

<div class="max-w-4xl mx-auto mt-8 bg-white rounded-xl shadow">

    <!-- ================= Cover ================= -->
    <div class="relative">
        <img src="${coverPhoto}"
             alt="Profile cover"
             class="w-full h-56 object-cover rounded-t-xl">

        <img src="${profilePhoto}"
             alt="${user.userName} profile photo"
             class="w-32 h-32 object-cover rounded-full border-4 border-white
                    absolute -bottom-16 left-1/2 -translate-x-1/2 bg-white">
    </div>

    <!-- ================= User Info ================= -->
    <div class="pt-20 pb-6 text-center px-6">
        <h2 class="text-2xl font-bold text-[#3a007a]">
            ${user.userName}
        </h2>

        <p class="mt-4 text-gray-700">
            <c:choose>
                <c:when test="${empty user.profileMessage}">
                    <span class="text-gray-400">
                        まだ自己紹介がありません。
                    </span>
                </c:when>
                <c:otherwise>
                    ${user.profileMessage}
                </c:otherwise>
            </c:choose>
        </p>

        <p class="mt-4 text-sm text-gray-400">
            Joined on ${user.registeredDate}
        </p>
    </div>

    <!-- ================= Optional Stats / Future ================= -->
    <div class="border-t px-6 py-4 text-center text-sm text-gray-500">
        📚 読書リスト・お気に入りはここに表示されます
    </div>

</div>

</body>
</html>
