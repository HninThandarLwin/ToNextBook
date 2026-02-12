<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentYear" value="<%= java.time.Year.now().getValue() %>" />

<footer>

  <div class="sns-icons">
    <a href="#"><img src="./img/twitter.png" alt="twitter" width="32"></a>
    <a href="#"><img src="./img/facebook.png" alt="facebook" width="32"></a>
    <a href="#"><img src="./img/instagram.png" alt="instagram" width="32"></a>
  </div>
  <p><small>Copyright &copy; ${currentYear} ECC SE1A　ニンタンダールイン。 All Rights Reserved.</small></p>
</footer>