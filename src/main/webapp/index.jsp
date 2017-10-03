<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <title>Main - Restaurant Automation System</title>

    <%@ include file="header.jsp" %>

</head>
<body>
<%@ include file="navbar.jsp"%>

<div class="wrapper">

    <div class="container">
        <div class="row">
            <div class="col-lg-9 col-lg-push-3">

                <form role="search"  class="visible-xs">
                    <div class="form-group">
                        <div class="input-group">
                            <input type="search" class="form-control input-lg" placeholder="ваш запит">

                            <div class="input-group-btn">
                                <button class="btn btn-default btn-lg" type="submit"><i class="glyphicon glyphicon-search"></i></button>

                            </div>
                        </div>
                    </div>
                </form>

                <h1>Події і новини</h1>
                <hr>

                <div class="jumbotron" style="margin-top: 20px;">
                    <h1>Головна</h1>
                    <sec:authorize access="!isAuthenticated()">
                        <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Увійти</a></p>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
                        <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Вийти</a></p>
                    </sec:authorize>
                </div>

            </div>
            <div class="col-lg-3 col-lg-pull-9">

                <%@ include file="navigation.jsp"%>

            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
