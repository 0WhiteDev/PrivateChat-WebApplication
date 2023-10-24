package uk.whitedev.chat.logic.chatlogic;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.db.LoginRequest;
import uk.whitedev.chat.db.UserRequest;
import uk.whitedev.chat.object.UserObject;
import uk.whitedev.chat.utils.TokenActions;
import uk.whitedev.chat.utils.TokenStatus;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class LoginSystem {

    private final LoginRequest loginRequest;

    public LoginSystem() throws NamingException {
        loginRequest = new LoginRequest();
    }

    private Optional<UserObject> tryLogin(String username, String password) {
        return loginRequest.getUserByName(username, password);
    }

    public boolean forwardAndTokenCheck(HttpServletRequest request, HttpServletResponse response, boolean loginPage) throws ServletException, IOException {
        try {
            UserRequest userRequest = new UserRequest();
            TokenStatus status = userRequest.findMatchingToken(TokenActions.getTokenFromCookie(request));
            if (status == TokenStatus.CORECT) {
                return true;
            } else if (status == TokenStatus.INVALID) {
                return false;
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("info", "");
        request.getRequestDispatcher(loginPage ? "/WEB-INF/login/login.jsp" : "/WEB-INF/login/register.jsp").forward(request, response);
        return false;
    }

    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");
        String rpassword = request.getParameter("repeat-password");
        if (password.equals(rpassword)) {
            switch (loginRequest.findIfUserExist(username, email, true)) {
                case EXIST -> registerAction(true, null, null, null, request, response);
                case NOT_EXIST -> registerAction(false, username, password, email, request, response);
            }
        } else {
            request.setAttribute("info", "The password must be the same in both fields");
            request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
        }
    }

    private void registerAction(boolean exist, String username, String password, String email, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (exist) {
            request.setAttribute("info", "Someone with this name/email already exists as our user, change this data or log in to this account");
            request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
        } else {
            String token = loginRequest.registerUser(username, email, password);
            if (token.equals("no-token")) {
                request.setAttribute("info", "SQL ERROR - Can't Register!");
                request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
            } else {
                Cookie cookie = generateTokenCookie(token);
                response.addCookie(cookie);
                response.sendRedirect(request.getContextPath() + "/app/home");
            }
        }
    }

    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Optional<UserObject> userObject = tryLogin(username, password);
        if (userObject.isPresent()) {
            Cookie cookie = generateTokenCookie(userObject.get().getToken());
            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/app/home");
        } else {
            request.setAttribute("info", "Wrong password or username!");
            request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
        }
    }

    public Optional<Cookie> removeInvalidTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token-cookie")) {
                    cookie.setMaxAge(0);
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }


    private Cookie generateTokenCookie(String token) {
        Cookie cookie = new Cookie("token-cookie", token);
        Date now = new Date();
        long expires = now.getTime() + 10L * 365 * 24 * 60 * 60 * 1000;
        cookie.setMaxAge((int) (expires / 1000));
        return cookie;
    }

}
