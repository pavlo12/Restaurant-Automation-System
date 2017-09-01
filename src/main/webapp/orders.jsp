<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <title>Orders - Restaurant Automation System</title>
    <script
            src="https://code.jquery.com/jquery-2.1.3.js"
            integrity="sha256-goy7ystDD5xbXSf+kwL4eV6zOPJCEBD1FBiCElIm+U8="
            crossorigin="anonymous"></script>
    <%--<script--%>
    <%--src="https://code.jquery.com/jquery-3.2.1.js"--%>
    <%--integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="--%>
    <%--crossorigin="anonymous"></script>--%>

    <%@ include file="header.jsp" %>

</head>
<body>
<%@ include file="navbar.jsp"%>

<div class="wrapper">

    <div class="container">
        <div class="row">
            <div class="col-lg-9 col-lg-push-3">

                <ul class="nav nav-tabs nav-justified">
                    <li role="presentation" id="new-order" class="active"><a href="#">Нові замовлення</a></li>
                    <li role="presentation" id="all-orders"><a href="#">Зробленні замовлення</a></li>
                </ul>
                <div id="mycontext">

                </div>


            </div>
            <div class="col-lg-3 col-lg-pull-9">

                <div class="panel panel-info">

                    <div class="panel-heading">
                        <div class="sidebar-header">
                            <a href="/">Головна</a>
                        </div>
                        <div class="sidebar-header">
                            <a href="/menu">Меню</a>
                        </div>
                        <div class="sidebar-header">
                            <a id="table-br" href="/tables">Бронювання столиків</a>
                        </div>
                        <div class="sidebar-header">
                            <a href="/orders">Мої замовлення</a>
                        </div>
                        <div class="sidebar-header">
                            <a href="#">Контакти</a>
                        </div>
                    </div>

                </div>

                <div class="panel panel-info hidden-xs">
                    <div class="panel-heading"> <div class="sidebar-header">Пошук</div> </div>
                    <div class="panel-body">
                        <form role="search">
                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">@</span>
                                    <input type="search" class="form-control input-lg" placeholder="ваш запит">

                                    <div class="input-group-btn">
                                        <button class="btn btn-default btn-lg" type="submit"><i class="glyphicon glyphicon-search"></i></button>

                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>


                <div class="panel panel-info">
                    <div class="panel-heading"> <div class="sidebar-header">Вхід</div> </div>
                    <div class="panel-body">

                        <form role="form">
                            <div class="form-group">
                                <input type="text" class="form-control input-lg" placeholder="Логін">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control input-lg" placeholder="Пароль">
                            </div>

                            <button type="submit" class="btn btn-warning pull-right">Вхід</button>

                        </form>

                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>

<script>


    $('.add-new-order').click(function () {

        $.ajax({
            type: "POST",
            cache: false,
            url: '/api/add-new-order',
            data: '',
            success: function () {
                alert("Вашу дію успішно виконано!!!");
                document.location.replace("/orders");
            }
        });

    });

    $( window ).load(function() {
        $('#new-order').click();
    });

    $('#new-order').click(function () {
        $('#all-orders').attr('class', '');
        $(this).attr('class', 'active');

//        document.location.replace("/orders");

        $('#mycontext').load("new-order.jsp");

        $.ajax({
            type: "GET",
            cache: false,
            url: '/api/new-order',
            data: "",
            success: function (response) {

                if (response.result == "success") {
                    $('#no-new-order').hide();
                } else {
                    $('#is-new-order').hide();
                }

            }
        });

    });


    $('#all-orders').click(function() {
        $('#new-order').attr('class', '');
        $(this).attr('class', 'active');

        $('#mycontext').load('all-orders.jsp');
        $.ajax({
            type: "GET",
            cache: false,
            url: '/api/orders',
            data: "",
            success: function (response) {
                var html = '';
                //html = response.result;
                $.each(response.data, function (i) {
                    html =  html + "<tr>";
                    html = html + "<th>" + response.data[i].id + "</th>";
                    html = html + "<th></th>";
                    html = html + "<th>" + response.data[i].status + "</th>";
                    html = html + "<th>" + response.data[i].orderSum + "</th>";
                    html = html + "<th></th>";
//                    html = html + "<th><input type=\"text\" id='" + response.data[i].id +  "'class=\"form-control\"></th>";
//                    html = html + "<th><button type=\"button\" id=\"dishAdd\" name=\"dishAdd\" value='" + response.data[i].id + "'class=\"btn btn-success btn-dish\">Додати</button></th>";
                    html =  html + "</tr>";
                });
                $('#table-my-orders').html(html);

//                  $('.btn-dish').click(function () {
//                    var str = $(this).val();
//                    if (!$("#" + str).val()) {
//                        alert("Введіть кількість порцій");
//                    } else {
////                    alert($("#" + str).val());
//                        $.ajax({
//                            type: "POST",
//                            cache: false,
//                            url: '/menu/add-dish-to-order',
//                            data: {
//                                'dishId': $(this).val(),
//                                'countDish': $("#" + str).val()
//                            },
//                            success: function () {
//                                alert("Вашу дію успішно виконано!!!")
//                            }
//                        });
//                    }
//                });
            }
        });
    });

</script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
