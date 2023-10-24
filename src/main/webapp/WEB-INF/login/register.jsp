<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chat - Registration Page</title>
  <script src="../../scripts/main/script.js"></script>
  <script src="../../scripts/main/ParticlesScript.js"></script>
  <link rel="stylesheet" type="text/css" href="../../styles/main/style.css">
</head>
<body>

<div class="particle-container"></div>

<div class="container">

  <h2>Registration Page</h2>

  <form action="" method="post">

    <div class="information">
      <h5><%= request.getAttribute("info").toString() %></h5>
    </div>

    <div class="text-inputs">
      <input type="text" name="username" placeholder="Username" required>
      <input type="text" name="email" placeholder="E-mail" required>
      <input type="password" name="password" placeholder="Password" required>
      <input type="password" name="repeat-password" placeholder="Repeat Password" required>
    </div>

    <div class="button-container">
      <input type="submit" value="Register" class="button-register">
      <p>Already have an account?</p>
      <a href="/login">Login now!</a>
    </div>
  </form>
</div>

<div class="background"></div>

<%@ include file="../main/footer.jsp" %>

</body>
</html>