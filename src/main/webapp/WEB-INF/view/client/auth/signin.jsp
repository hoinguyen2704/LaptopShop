<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Signin</title>
                <link rel="stylesheet" type="text/css" href="/client/css/as-alert-message.min.css">
                <link rel="stylesheet" type="text/css"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">
                <link rel="stylesheet" type="text/css" href="/client/css/style-starter.css">
                <link rel="stylesheet" type="text/css" href="/client/css/sign-in.css">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                <!-- Customized Bootstrap Stylesheet -->
                <!-- <link href="/client/css/bootstrap.min.css" rel="stylesheet"> -->

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">
            </head>

            <body>

                <div class="container_signup_signin" id="container_signup_signin">
                    <!-- <c:if test="${checkOverLay}">
                        <script>
                            document.getElementById('container_signup_signin').classList.add("right-panel-active");
                        </script>
                    </c:if> -->
                    <div class="form-container sign-up-container">
                        <form:form method="post" action="/register" name="sign-up-form" modelAttribute="registerUser"
                            onsubmit="return signUpValidateForm()">
                            <c:set var="errorPassword">
                                <form:errors path="confirmPassword" cssClass="invalid-feedback" />
                            </c:set>
                            <c:set var="errorEmail">
                                <form:errors path="email" cssClass="invalid-feedback" />
                            </c:set>
                            <c:set var="errorPhone">
                                <form:errors path="phone" cssClass="invalid-feedback" />
                            </c:set>
                            <c:set var="errorFirstName">
                                <form:errors path="firstName" cssClass="invalid-feedback" />
                            </c:set>
                            <c:set var="errorLastName">
                                <form:errors path="lastName" cssClass="invalid-feedback" />
                            </c:set>
                            <h1 style="color: black;">Create Account</h1>
                            <div class="social-container">
                                <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                                <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                                <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                            </div>
                            <span>or use your email for registration</span>
                            <div class="form-floating mb-3">
                                <form:input class="form-control ${not empty errorFirstName ? 'is-invalid' : ''}"
                                    name="sign-up-first-name" type="text" placeholder="First Name" path="firstName" />
                                <label for="inputFirstName">First name</label>
                                ${errorFirstName}
                            </div>
                            <div class="form-floating mb-3">
                                <form:input class="form-control ${not empty errorFirstName ? 'is-invalid' : ''}"
                                    name="sign-up-last-name" type="text" placeholder="Last Name" path="lastName" />
                                <label for="inputLastName">Last name</label>
                                ${errorLastName}
                            </div>
                            <div class="form-floating mb-3">
                                <form:input class="form-control ${not empty errorPhone ? 'is-invalid' : ''}"
                                    name="sign-up-phone" type="phone" placeholder="Phone Number" path="phone" />
                                <label for="inputPhone">Phone Number</label>
                                ${errorPhone}
                            </div>
                            <div class="form-floating mb-3">
                                <form:input class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                                    name="sign-up-email" type="email" placeholder="Email" path="email" />
                                <label for="inputEmail">Email</label>
                                ${errorEmail}
                            </div>
                            <div class="form-floating mb-3">
                                <form:input class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                    name="sign-up-passwd" type="password" placeholder="Password" path="password" />
                                <label for="inputPassword">Password</label>
                                ${errorPassword}
                            </div>
                            <div class="form-floating mb-3">
                                <form:input class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                    name="sign-up-confirm-passwd" type="password" placeholder="Confirm Password"
                                    path="confirmPassword" />
                                <label for="inputConfirmPassword">Confirm Password</label>
                                ${errorPassword}
                            </div>
                            <button type="submit" name="action" value="register">Sign Up</button>
                        </form:form>
                    </div>
                    <div class="form-container sign-in-container">
                        <form method="post" action="/login" name="sign-in-form" style="color: var(--theme-title);"
                            onsubmit="return signInValidateForm()">
                            <c:if test="${param.error != null}">
                                <div class="my-2" style="color: red; font-weight: bold; font-size: 1.5rem;">Invalid email or password.
                                </div>
                            </c:if>
                            <c:if test="${param.logout != null}">
                                <div class="my-2" style="color: green; font-weight: bold; font-size: 1.5rem;">Logout success.
                                </div>
                            </c:if>
                            <c:if test="${param.success != null}">
                                <div class="my-2" style="color: green; font-weight: bold; font-size: 1.5rem;">Register success Please login.
                                </div>
                            </c:if>
                            <h1>Sign in</h1>
                            <div class="social-container">
                                <a href="#" class="social" style="color: var(--theme-title);"><i
                                        class="fab fa-facebook-f"></i></a>
                                <a href="#" class="social" style="color: var(--theme-title);"><i
                                        class="fab fa-google-plus-g"></i></a>
                                <a href="#" class="social" style="color: var(--theme-title);"><i
                                        class="fab fa-linkedin-in"></i></a>
                            </div>
                            <span>or use your account</span>
                            <input type="email" placeholder="Email: name@example.com" name="username" />
                            <input type="password" placeholder="Password" name="password" />

                            <div class="form-check mb-3">
                                <input class="form-check-input" id="inputRememberPassword" type="checkbox"
                                    name="remember-me" />
                                <label class="form-check-label" for="inputRememberPassword">Remember Password</label>
                            </div>
                            <div class="form-check mb-3">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </div>
                            <button type="submit" name="action" value="login">Sign In</button>
                        </form>
                    </div>
                    <div class="overlay-container">
                        <div class="overlay">
                            <div class="overlay-panel overlay-left">
                                <h1>Welcome Back!</h1>
                                <p>To keep connected with us please login with your login details</p>
                                <button class="ghost" id="signIn">Sign In</button>
                            </div>
                            <div class="overlay-panel overlay-right">
                                <h1>Hello, Friend!</h1>
                                <p>Register and book your tickets now!!!</p>
                                <!-- <a href="<c:url value='/register'/>"> -->
                                <button class="ghost" id="signUp"> Sign Up</button>
                                <c:if
                                    test="${not empty errorFirstName || not empty errorLastName || not empty errorPhone || not empty errorEmail || not empty errorPassword}">
                                    <script>
                                        document.getElementById('container_signup_signin').classList.add("right-panel-active");
                                    </script>
                                </c:if>
                                <!-- </a> -->
                            </div>
                        </div>
                    </div>
                </div>

                <script type="text/javascript" src="/client/js/as-alert-message.min.js"></script>
                <script src="/client/js/jquery-3.3.1.min.js"></script>
                <!--/theme-change-->
                <script src="/client/js/theme-change.js"></script>
                <!-- disable body scroll which navbar is in active -->
                <script>
                    $(function () {
                        $('.navbar-toggler').click(function () {
                            $('body').toggleClass('noscroll');
                        })
                    });
                </script>
                <!-- disable body scroll which navbar is in active -->
                <!--/MENU-JS-->
                <script>
                    $(window).on("scroll", function () {
                        var scroll = $(window).scrollTop();

                        if (scroll >= 80) {
                            $("#site-header").addClass("nav-fixed");
                        } else {
                            $("#site-header").removeClass("nav-fixed");
                        }
                    });

                    //Main navigation Active Class Add Remove
                    $(".navbar-toggler").on("click", function () {
                        $("header").toggleClass("active");
                    });
                    $(document).on("ready", function () {
                        if ($(window).width() > 991) {
                            $("header").removeClass("active");
                        }
                        $(window).on("resize", function () {
                            if ($(window).width() > 991) {
                                $("header").removeClass("active");
                            }
                        });
                    });
                </script>
                <script src="/client/js/bootstrap.min.js"></script>

                <script type="text/javascript" src="/client/js/sign-in.js"></script>

            </body>

            </html>