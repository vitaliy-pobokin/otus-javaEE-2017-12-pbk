<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download Browser</title>
</head>
<body>
    <%@include file="views/header.jsp"%>
    <%@include file="views/nav.jsp"%>
    <div style="width: 1170px; display:flex; flex-wrap: wrap; justify-content: center; margin-left: auto; margin-right: auto;">
        <%--<div style="width: 100%; max-width: 100%;" >
            <div class="frame" style="padding: 20px">

            </div>
        </div>--%>
        <div class="image">
            <img src="${ctx}/images/chrome_download.jpg">
        </div>
        <div class="image">
            <img src="${ctx}/images/firefox_download.jpg">
        </div>
        <div class="image">
            <img src="${ctx}/images/opera_download.png">
        </div>
        <div class="image">
            <img src="${ctx}/images/ie_download.png">
        </div>
    </div>
    <%@include file="views/footer.jsp"%>
</body>
</html>
