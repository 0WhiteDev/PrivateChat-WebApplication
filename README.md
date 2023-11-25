
# PrivateChat WebApp 🌐


Web application for chatting with other people, with a database and authorization (login/register) system ⌨️

## Basic information

The entire project is based on JakartaEE with Java 21 and Tomcat 10.1.13. The build system I chose is Maven 📚

I don't use any external libraries, all necessary libraries are defined in pom.xml

#### How to Run Application
- Open project in your IDE (I using **IntelliJ Ultimate**)
- Run your project as **maven project**
- Go to **PrivateChatWebApp\src\main\webapp\META-INF**
- Open **context.xml** and configure the connection to your database
- Go to your MySQL (I use MySQL Workbench), log in to the database you defined in **context.xml** and create a new schema, the name depends on what you entered in context.xml

SQL Script:
```
CREATE SCHEMA `privatechat` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci ;

CREATE TABLE `privatechat`.`chatroom` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `message` VARCHAR(1000) NOT NULL,
  `userid` INT NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `privatechat`.`chatroomid` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `privatechat`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `token` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE INDEX `iduser_UNIQUE` (`iduser` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `token_UNIQUE` (`token` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
```
- After correctly connecting the database to the application and creating all the necessary tables, you can run your application using the standard tomcat configuration, the only thing you should change in the tomcat configuration is ```Deployment > Application context``` the value of this field should be set to "/"

- Once you have done this, run your application and go to the address "localhost:8080" (if you changed the port, instead of 8080 enter the port you provided in the configuration, this port is the standard port for tomcat).


#### Overview of Project
This project was created so that I could test myself in the field of writing web applications, if you need to use my application or any part of it, feel free to do so, if you find any error that I should fix, write to me or report the problem in an issue on github.

The entire project has basic functions such as:
- Logout/Login/Register
- Encrypted data
- DataBase
- User Panel (Changing name and password)
- Creating Room System
- Chat Rooms

Data encryption is quite basic, I used the same system as in the desktop version of PrivateChat, but I think it serves its purpose well.

Unfortunately, I'm just learning css and js, so the application has **NOT** been adapted to the mobile version, although it is possible to use it, it is not yet ready in the mobile version, but in the PC version it is 100% functional.

When creating this project, I used ChatGPT and StackOverflow, because this is my first project since finishing the web technology course, I needed something that I could use to help me.

#### Project Suppot
If you need help, text to me:
- Discord: 0whitedev / 0WhiteDev#0001
- Email: 0whitedev@gmail.com
## Authors

- [@DevsMarket](https://github.com/DEVS-MARKET)
- [@0WhiteDev](https://github.com/0WhiteDev)

