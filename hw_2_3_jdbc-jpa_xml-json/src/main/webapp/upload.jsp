<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload File</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <form action="<%=request.getAttribute("path")%>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file">Выберите подходящий файл</label>
            <input type="file" class="form-control-file" id="file" name="file"/>
            <input type="submit" class="btn btn-default"/>
        </div>
    </form>
</body>
</html>
