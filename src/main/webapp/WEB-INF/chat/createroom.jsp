<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PrivateChat</title>
    <link rel="stylesheet" type="text/css" href="../../styles/chat/homeapp-style.css">
</head>
<body>

<div class="container">
    <h3>Create ChatRoom</h3>
    <form action="" method="post">
        <div class="input-container">
            <input type="text" name="chatname" placeholder="Chat Name" required>
            <input type="submit" value="Create and Connect">
        </div>

    </form>
</div>

<a href="${pageContext.request.contextPath}/app/home" class="back">< Back</a>

</body>
</html>