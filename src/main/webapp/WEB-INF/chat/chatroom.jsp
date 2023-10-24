<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PrivateChat</title>
    <script src="../../scripts/chat/RefreshMessage.js"></script>
    <link rel="stylesheet" type="text/css" href="../../styles/chat/chatroom-style.css">
</head>
<body>

<div class="container" id="refresh">
    <div class="messages-container">
        <c:forEach var="current" items="${messages}">
            <c:choose>
                <c:when test="${not empty current}">
                    <h5>${current.user}</h5>
                    <p class="message">${current.message}</p>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</div>


<form action="" method="post">
    <div class="input-container">
        <input type="text" name="message" placeholder="Message" required>
        <input type="submit" value="Send">
    </div>
</form>

<div class="channel-info">
    <h2>PrivateChat</h2>
    <h4><%= request.getAttribute("channel-name").toString() %></h4>
    <h5>Channel id: <%= request.getAttribute("channel-id").toString() %></h5>
    <h5>Logged as: <%= request.getAttribute("username").toString() %></h5>
    <a href="${pageContext.request.contextPath}/app/home">Leave the Chat!</a>
</div>

</body>
</html>