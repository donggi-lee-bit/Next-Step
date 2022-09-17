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
                <li><a href="../user/login.jsp" role="button">로그인</a></li>
                <li><a href="../user/form.html" role="button">회원가입</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container" id="main">
   <div class="col-md-6 col-md-offset-3">
      <div class="panel panel-default content-main">
          <form name="question" method="post" action="/user/login">
              <div class="form-group">
                  <label for="userId">사용자 아이디</label>
                  <input class="form-control" id="userId" name="userId" placeholder="User ID">
              </div>
              <div class="form-group">
                  <label for="password">비밀번호</label>
                  <input type="password" class="form-control" id="password" name="password" placeholder="Password">
              </div>
              <button type="submit" class="btn btn-success clearfix pull-right">로그인</button>
              <div class="clearfix" />
          </form>
        </div>
    </div>
</div>

<!-- script references -->
<script src="../js/jquery-2.2.0.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
	</body>
</html>
