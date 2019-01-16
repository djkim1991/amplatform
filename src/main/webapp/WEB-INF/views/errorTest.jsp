<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <script src="/js/websock/sockjs.min.js"></script>
    <script src="/js/websock/stomp.min.js"></script>
    <script src="/js/websock/websock_handler.js"></script>

</head>
<body>
<ul id="authControl">
    <li><button id="exceptionOnHtmlRequst"><span>Exception on HTML request</span></button></li>
    <li><button id="sqlExceptionOnHtmlRequest" ><span>SqlException on HTML reuqest</span></button></li>
    <li><button id="exceptionOnJsonRequest"><span>Exception on JSON request</span></button></li>
    <li><button id="sqlExceptionOnJsonRequest"><span>SqlException on JSON request</span></button></li>
    <li><button id="forbidden"><span>HTTP 403 Exception</span></button></li>
    <li><button id="notFound"><span>HTTP 404 Exception</span></button></li>
</ul>
<script>
    $(function () {
        $.ajaxSetup({
            contentType: "application/json"
        });
        $(document).ajaxError(function(event, request, settings) {
            alert(request.responseText);
        });
    });

    $("#exceptionOnHtmlRequst").click(function() {
        location.href="/exceptionOnHtmlRequst";
    });

    $("#sqlExceptionOnHtmlRequest").click(function() {
        location.href="/sqlExceptionOnHtmlRequest";
    });

    $("#exceptionOnJsonRequest").click(function() {
        $.getJSON("/exceptionOnJsonRequest");
    });

    $("#sqlExceptionOnJsonRequest").click(function() {
        $.getJSON("/sqlExceptionOnJsonRequest");
    });

    $("#forbidden").click(function() {
        location.href = "/forbidden";
    });

    $("#notFound").click(function() {
        location.href = "/notFound";
    });
</script>
</body>
</html>