<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat - Main Page</title>
    <script src="../../scripts/main/ButtonScript.js"></script>
    <script src="../../scripts/main/ParticlesScript.js"></script>
    <script src="../../scripts/main/script.js"></script>
    <script type="module" src="../../scripts/main/MainPageAnimationScript.js"></script>
    <script src="../../scripts/lib/gsap.min.js"></script>
    <script src="../../scripts/lib/ScrollMagic.min.js"></script>
    <script src="../../scripts/lib/animation.gsap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../../styles/main/mainpage-style.css">
</head>
<body>

<div class="particle-container"></div>

<div class="first-view-section">
    <header class="top-bar">
        <div class="top-bar-content">
            <span class="top-bar-text">PrivateChat</span>
            <button class="top-bar-login-button" onclick="redirectToLoginPage()">Login</button>
        </div>
    </header>

    <div class="main-text-container">
        <h1>Welcome on PrivateChat v1.0</h1>
        <h2>Designed and Coded by 0WhiteDev</h2>
        <div class="main-button-container">
            <button onclick="redirectToLoginPage()">Login</button>
            <button onclick="redirectToRegisterPage()">Register</button>
        </div>
    </div>

    <div class="main-images">
        <img src="../../images/main/main-page-pic.png" alt="Image with screenshots from app" width="740" height="460">
    </div>
</div>

<div class="features-section">

    <div class="features-text">
        <h3>
            PrivateChat Web Application
        </h3>
        <h4>
            PrivateChat is an application created to communicate safely
            <br>and without restrictions with anyone you want, it is based
            <br>on a chat room system, anyone who has a link can join your
            <br>chat room and write with you, we offer security and privacy
            <br>of your data and messages!
        </h4>
    </div>

    <div class="features-container">
        <img src="../../images/main/lock-icon.png" alt="Lock icon" width="70" height="70">
        <h2>Encrypted Data</h2>
        <p>We use encryption of messages and user data
            so that no one can get your messages or passwords.</p>
    </div>
    <div class="features-container">
        <img src="../../images/main/easyuse-icon.png" alt="Lock icon" width="70" height="70">
        <h2>Easy to Use</h2>
        <p>Our application has a basic look and is designed
            so that anyone without special knowledge can use it.</p>
    </div>
    <div class="features-container">
        <img src="../../images/main/opensource-icon.png" alt="Lock icon" width="70" height="70">
        <h2>Open-Source Project</h2>
        <p>It is an open-source application, so you can check
            everything you need, both backend and frontend,
            the application is ready to use!</p>
    </div>
</div>

<%@ include file="footer.jsp" %>

<div class="background"></div>

</body>
</html>