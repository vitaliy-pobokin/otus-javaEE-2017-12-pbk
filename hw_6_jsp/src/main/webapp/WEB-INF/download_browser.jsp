<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Download Browser</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="${ctx}/static/styles/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/styles/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/static/styles/styles.css" rel="stylesheet">
    <base href="${ctx}/">
</head>
<body>
    <%@include file="views/header.jsp"%>
    <div style="width: 1170px; display:flex; flex-wrap: wrap; justify-content: space-between; margin-left: auto; margin-right: auto;">
        <div style="width: 100%; max-width: 100%;" >
            <div class="frame" style="padding: 20px; margin: 10 0 5 0;">
              <h3>It seems you are using an outdated browser. You can download latest version by the following links.</h3>
            </div>
        </div>
        <div class="image">
          <a href="https://www.google.com/chrome/">
            <img src="${ctx}/static/images/chrome_download.jpg">
          </a>
        </div>
        <div class="image">
          <a href="https://www.mozilla.org/en-US/firefox/">
            <img src="${ctx}/static/images/firefox_download.jpg">
          </a>
        </div>
        <div class="image">
          <a href="https://www.opera.com/ru">
            <img src="${ctx}/static/images/opera_download.png">
          </a>
        </div>
        <div class="image">
          <a href="https://support.microsoft.com/en-us/help/17621/internet-explorer-downloads">
            <img src="${ctx}/static/images/ie_download.png">
          </a>
        </div>
    </div>
    <%@include file="views/footer.jsp"%>
</body>
</html>
