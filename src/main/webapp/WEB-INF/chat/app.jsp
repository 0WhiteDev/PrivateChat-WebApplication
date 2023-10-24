<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PrivateChat</title>
    <link rel="stylesheet" type="text/css" href="../../styles/chat/homeapp-style.css">
    <script type="module" src="../../scripts/main/AnimationHomePage.js"></script>
    <script src="../../scripts/lib/gsap.min.js"></script>
    <script src="../../scripts/lib/ScrollMagic.min.js"></script>
    <script src="../../scripts/lib/animation.gsap.min.js"></script>
</head>
<body>

<div class="panel">
    <a href="${pageContext.request.contextPath}/app/panel" class="panel-link">
        <img src="../../images/chat/settings-icon.png" alt="Settings icon" width="30" height="30">
    </a>
    <p>Settings</p>
    <a href="${pageContext.request.contextPath}/app/create" class="panel-link">
        <img src="../../images/chat/plus-icon.png" alt="Create room icon" width="30" height="30">
    </a>
    <p>Create New ChatRoom</p>
</div>


<div class="container">
    <h3>Connect to ChatRoom</h3>
    <div class="information">
        <h5><%= request.getAttribute("info").toString() %>
        </h5>
    </div>
    <form action="${pageContext.request.contextPath}/app/chat" method="get">
        <input type="text" name="chatroom-id" placeholder="ChatRoom id" required>
        <input type="submit" value="Connect">
    </form>
</div>

<a href="${pageContext.request.contextPath}/app/logout" class="logout">Logout</a>


</body>
</html>