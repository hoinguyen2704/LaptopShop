<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="utf-8">
                <title>Lịch Sử Mua Hàng - Laptopshop</title>
                <meta content="width=device-width, initial-scale=1.0" name="viewport">
                <meta content="" name="keywords">
                <meta content="" name="description">

                <!-- Google Web Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">

                <style>
                    body {
                        background-color: #f8f9fa;
                    }

                    .order-card {
                        transition: transform 0.2s;
                        border: none;
                        border-radius: 12px;
                        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
                        margin-bottom: 2rem;
                        overflow: hidden;
                    }

                    .order-card:hover {
                        transform: translateY(-3px);
                        box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
                    }

                    .product-icon {
                        width: 56px;
                        height: 56px;
                        background-color: #e9ecef;
                        color: #495057;
                        border-radius: 8px;
                        display: flex;
                        align-items: center;
                        justify-content-center;
                        font-size: 1.5rem;
                    }

                    .product-icon img {
                        width: 100%;
                        height: 100%;
                        object-fit: cover;
                        border-radius: 8px;
                    }

                    .order-total-label {
                        font-size: 0.9rem;
                        text-transform: uppercase;
                        letter-spacing: 0.5px;
                        font-weight: 600;
                    }

                    .page-header {
                        background: white;
                        padding: 2rem 0;
                        margin-bottom: 2rem;
                        border-bottom: 1px solid #dee2e6;
                    }
                </style>
            </head>

            <body>

                <!-- Spinner Start -->
                <div id="spinner"
                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                    <div class="spinner-grow text-primary" role="status"></div>
                </div>
                <!-- Spinner End -->

                <jsp:include page="../layout/header.jsp" />

                <!-- Page Header -->
                <div class="container-fluid py-5 mt-5">
                    <div class="container py-5 mt-5">
                        <!-- Breadcrumb -->
                        <nav aria-label="breadcrumb" class="mb-4">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="/">Trang chủ</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Lịch sử mua hàng</li>
                            </ol>
                        </nav>

                        <!-- Page Title -->
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <h2 class="fw-bold text-dark mb-0">
                                <i class="bi bi-clock-history me-2 text-primary"></i>Lịch Sử Mua Hàng
                            </h2>
                        </div>

                        <!-- Empty State -->
                        <c:if test="${empty orders}">
                            <div class="card border-0 shadow-sm text-center py-5">
                                <div class="card-body">
                                    <i class="bi bi-cart-x display-1 text-muted mb-3"></i>
                                    <h4 class="text-muted">Chưa có đơn hàng nào</h4>
                                    <p class="text-muted">Bạn chưa có đơn hàng nào được tạo</p>
                                    <a href="/" class="btn btn-primary mt-3">
                                        <i class="bi bi-shop me-2"></i>Tiếp tục mua sắm
                                    </a>
                                </div>
                            </div>
                        </c:if>

                        <!-- Orders List -->
                        <c:forEach var="order" items="${orders}">
                            <div class="card order-card">
                                <!-- Card Header: Order Info + Status -->
                    <div class="card-header bg-white py-3 border-bottom">
                        <div class="row align-items-center">
                            <div class="col-md-8">
                                <div class="d-flex flex-column">
                                    <div class="mb-2">
                                        <span class="fw-bold text-primary me-2">#DH-${order.id}</span>
                                    </div>
                                    <div class="small text-muted">
                                        <i class="bi bi-calendar-plus me-1"></i>
                                        <span class="fw-semibold">Ngày đặt:</span>
                                        ${order.createdAt.toString().replace('T', ' ').substring(0, 19)}
                                        <span class="mx-2">|</span>
                                        <i class="bi bi-clock-history me-1"></i>
                                        <span class="fw-semibold">Cập nhật:</span>
                                        ${order.updatedAt.toString().replace('T', ' ').substring(0, 19)}
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 text-md-end mt-2 mt-md-0">
                                            <!-- Status Badge -->
                                <c:choose>
                                    <c:when test="${order.status == 'COMPLETE'}">
                                        <span class="badge px-4 py-2 rounded-pill" style="background-color: #d4edda; color: #155724; border: 2px solid #c3e6cb; font-size: 0.875rem; font-weight: 600;">
                                            <i class="bi bi-check-circle-fill me-2"></i>Giao hàng thành công
                                        </span>
                                    </c:when>
                                    <c:when test="${order.status == 'SHIPPING'}">
                                        <span class="badge px-4 py-2 rounded-pill" style="background-color: #cfe2ff; color: #084298; border: 2px solid #b6d4fe; font-size: 0.875rem; font-weight: 600;">
                                            <i class="bi bi-truck me-2"></i>Đang vận chuyển
                                        </span>
                                    </c:when>
                                    <c:when test="${order.status == 'PENDING'}">
                                        <span class="badge px-4 py-2 rounded-pill" style="background-color: #fff3cd; color: #856404; border: 2px solid #ffeaa7; font-size: 0.875rem; font-weight: 600;">
                                            <i class="bi bi-clock-fill me-2"></i>Chờ xử lý
                                        </span>
                                    </c:when>
                                    <c:when test="${order.status == 'CANCELLED'}">
                                        <span class="badge px-4 py-2 rounded-pill" style="background-color: #f8d7da; color: #721c24; border: 2px solid #f5c6cb; font-size: 0.875rem; font-weight: 600;">
                                            <i class="bi bi-x-circle me-2"></i>Đã hủy
                                        </span>
                                    </c:when>
                                    <c:when test="${order.status == 'RETURNED'}">
                                        <span class="badge px-4 py-2 rounded-pill" style="background-color: #e2e3e5; color: #383d41; border: 2px solid #d6d8db; font-size: 0.875rem; font-weight: 600;">
                                            <i class="bi bi-arrow-return-left me-2"></i>Đã hoàn trả
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary px-4 py-2 rounded-pill" style="font-size: 0.875rem; font-weight: 600;">
                                            ${order.status}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <!-- Card Body: Product Table -->
                                <div class="card-body p-0">
                                    <div class="table-responsive">
                                        <table class="table table-hover mb-0 align-middle">
                                            <thead class="bg-light text-secondary small text-uppercase" style="color: black !important;">
                                                <tr>
                                                    <th class="ps-4 py-3" style="width: 45%">Sản phẩm</th>
                                                    <th class="text-center py-3">Đơn giá</th>
                                                    <th class="text-center py-3">Số lượng</th>
                                                    <th class="text-end pe-4 py-3">Thành tiền</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="orderDetail" items="${order.orderDetails}">
                                                    <tr>
                                                        <td class="ps-4">
                                                            <div class="d-flex align-items-center">
                                                                <div class="product-icon me-3">
                                                                    <c:choose>
                                                                        <c:when
                                                                            test="${not empty orderDetail.product.image}">
                                                                            <img src="/images/product/${orderDetail.product.image}"
                                                                                alt="${orderDetail.product.name}">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <i class="bi bi-laptop"></i>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
                                                                <div>
                                                                    <div class="fw-bold text-dark">
                                                                        <a href="/product/${orderDetail.product.id}"
                                                                            target="_blank"
                                                                            class="text-dark text-decoration-none">
                                                                            ${orderDetail.product.name}
                                                                        </a>
                                                                    </div>
                                                                    <div class="small text-muted">
                                                                        ${orderDetail.product.factory}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="text-center">
                                                            <fmt:formatNumber type="number"
                                                                value="${orderDetail.price}" /> ₫
                                                        </td>
                                                        <td class="text-center">${orderDetail.quantity}</td>
                                                        <td class="text-end pe-4 fw-medium">
                                                            <fmt:formatNumber type="number"
                                                                value="${orderDetail.price * orderDetail.quantity}" /> ₫
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <!-- Card Footer: Total -->
                                <div class="card-footer bg-white py-3 pe-4 text-end border-top">
                                    <span class="text-muted me-3 order-total-label">Tổng thanh toán:</span>
                                    <span class="fs-4 fw-bold text-danger">
                                        <fmt:formatNumber type="number" value="${order.totalPrice}" /> ₫
                                    </span>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>

                <jsp:include page="../layout/footer.jsp" />

                <!-- Back to Top -->
                <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top">
                    <i class="fa fa-arrow-up"></i>
                </a>

                <!-- JavaScript Libraries -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>
            </body>

            </html>