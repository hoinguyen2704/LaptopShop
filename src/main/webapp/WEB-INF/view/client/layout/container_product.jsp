<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <!-- Laptop Shop Start-->
            <div class="container-fluid fruite py-5">
                <div class="container py-5">
                    <div class="tab-class text-center">
                        <div class="row g-4">
                            <div class="col-lg-4 text-start">
                                <h1>Sản Phẩm nổi bật</h1>
                            </div>
                            <div class="col-lg-8 text-end">
                                <ul class="nav nav-pills d-inline-flex text-center mb-5">
                                    <li class="nav-item">
                                        <a class="d-flex m-2 py-2 bg-light rounded-pill active" data-bs-toggle="pill"
                                            href="#tab-1">
                                            <span class="text-dark" style="width: 130px;">All Products</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane fade show p-0 active">
                                <div class="row g-4">
                                    <div class="col-lg-12">
                                        <div class="row g-4">
                                            <c:forEach var="product" items="${products}">
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div
                                                        class="rounded position-relative fruite-item h-100 d-flex flex-column">
                                                        <div class="fruite-img">
                                                            <a href="<c:url value='/product/${product.id}'/>">
                                                                <img src="/images/product/${product.image}"
                                                                    class="img-fluid w-100 rounded-top"
                                                                    style="height: 300px; object-fit: cover;" alt="">
                                                            </a>
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Laptop</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom flex-fill d-flex flex-column">
                                                            <h4 style="font-size: 18px;">
                                                                <a href="<c:url value='/product/${product.id}'/>">
                                                                    ${product.name}
                                                                </a>
                                                            </h4>
                                                            <p class="flex-grow-1"
                                                                style="display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">
                                                                ${product.shortDesc}
                                                            </p>
                                                            <div class="d-flex justify-content-center flex-lg-wrap">
                                                                <p class="text-dark fw-bold mb-3"
                                                                    style="font-size: 1.25rem; text-align: center; width: 100%;">
                                                                    <fmt:formatNumber value="${product.price}" /> đ
                                                                </p>
                                                                <form action="/add-product-to-cart/${product.id}" method="post">
                                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                                    <button type="submit"
                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                        <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                                                        Add to
                                                                        cart</button>
                                                                </form>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Laptop Shop End-->