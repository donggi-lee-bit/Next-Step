<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="kr">

<%@ include file="header.jsp" %>

<div class="navbar navbar-default" id="subnav">
    <div class="col-md-12">

        <%@ include file="navbar_header.jsp" %>

        <div class="collapse navbar-collapse" id="navbar-collapse2">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="../index.jsp">Posts</a></li>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li><a href="../user/logout" role="button">로그아웃</a></li>
                        <li><a href="../user/update.jsp" role="button">개인정보수정</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="../user/login.jsp" role="button">로그인</a></li>
                        <li><a href="../user/form.html" role="button">회원가입</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>

<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="status">
                    <tr>
                        <th scope="row">${status.count}</th>
                        <td>${user.userId}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td><a href="../user/update?userId=${user.userId}" class="btn btn-success" role="button">수정</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- script references -->
<script src="../js/jquery-2.2.0.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
