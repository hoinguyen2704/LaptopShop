<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>403 - TechZone</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body>
                <div class="container">
                    <div class="row ">
                        <div class="col-12 mt-5 justify-content-center">
                            <a href="<c:url value='/'/>">
                                <div class="flex flex-col items-center gap-4">
                                    <h1 class="text-3xl font-medium text-center">
                                        You are not authorized
                                    </h1>
                                    <p class="text-xl text-center ">
                                        You tried to access a page you did not have prior
                                        authorization for.
                                    </p>
                                    <p class="text-center text-primary">Go back to home page</p>
                                </div>
                                <img src="/images/deny.svg" alt="">
                            </a>
                        </div>
                    </div>
                </div>


                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>