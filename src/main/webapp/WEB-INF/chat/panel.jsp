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
    <h3>User Panel</h3>

    <div class="information">
        <h5><%= request.getAttribute("info").toString() %>
        </h5>
    </div>

    <form action="" method="post">
        <div class="input-container">
            <input type="text" name="new-username" placeholder="New Name" required>
            <input type="submit" value="Change name">
        </div>

    </form>
    <form action="" method="post">
        <div class="input-container">
            <input type="password" name="old-password" placeholder="Old Password" required>
            <input type="password" name="new-password" placeholder="New Password" required>
            <input type="submit" value="Change password">
        </div>
    </form>
</div>

<a href="${pageContext.request.contextPath}/app/home" class="back">< Back</a>

</body>
</html>