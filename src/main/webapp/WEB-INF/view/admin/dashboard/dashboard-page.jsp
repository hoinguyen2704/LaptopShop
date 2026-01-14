<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Dự án laptopshop" />
                <meta name="author" content="Hozinium" />
                <title>Dashboard</title>
                <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Dashboard</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="<c:url value='/admin'/>">Dashboard</a></li>
                                </ol>

                                <!-- Revenue Statistics Cards -->
                                <!-- Row 1: Revenue & Orders -->
                                <div class="row mb-4">
                                    <!-- Doanh thu 7 ngày -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-primary text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Doanh thu 7 ngày</div>
                                                        <div class="h5 mb-0">
                                                            <fmt:formatNumber type="number"
                                                                value="${stats.totalRevenue7Days}" /> đ
                                                        </div>
                                                    </div>
                                                    <i class="fas fa-dollar-sign fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Trung bình/ngày -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-success text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Trung bình/ngày</div>
                                                        <div class="h5 mb-0">
                                                            <fmt:formatNumber type="number"
                                                                value="${stats.avgRevenuePerDay}" /> đ
                                                        </div>
                                                    </div>
                                                    <i class="fas fa-chart-line fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Đơn hoàn thành -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-info text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Đơn hoàn thành</div>
                                                        <div class="h5 mb-0">${stats.completedOrders} đơn</div>
                                                    </div>
                                                    <i class="fas fa-check-circle fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Đơn đang xử lý -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-warning text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Đơn đang xử lý</div>
                                                        <div class="h5 mb-0">${stats.processingOrders} đơn</div>
                                                    </div>
                                                    <i class="fas fa-clock fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Row 2: Business Metrics -->
                                <div class="row mb-4">
                                    <!-- Đơn hoàn/hủy -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-danger text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Đơn hoàn/hủy</div>
                                                        <div class="h5 mb-0">${stats.cancelledOrders} đơn</div>
                                                    </div>
                                                    <i class="fas fa-times-circle fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Sản phẩm active -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-success text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Sản phẩm active</div>
                                                        <div class="h5 mb-0">${stats.activeProducts} SP</div>
                                                    </div>
                                                    <i class="fas fa-box fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Tổng người dùng -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-info text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Tổng người dùng</div>
                                                        <div class="h5 mb-0">${stats.totalUsers} người</div>
                                                    </div>
                                                    <i class="fas fa-users fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Top khách hàng -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-secondary text-white mb-4">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small">Top khách hàng</div>
                                                        <div class="h5 mb-0">${topCustomers.size()} người</div>
                                                    </div>
                                                    <i class="fas fa-star fa-2x"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-xl-6">
                                        <div class="card mb-4">
                                            <div class="card-header">
                                                <i class="fas fa-chart-area me-1"></i>
                                                Doanh thu 7 ngày gần nhất
                                            </div>
                                            <div class="card-body">
                                                <canvas id="myAreaChart" width="100%" height="40"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="card mb-4">
                                            <div class="card-header">
                                                <i class="fas fa-chart-bar me-1"></i>
                                                Bar Chart Example
                                            </div>
                                            <div class="card-body">
                                                <canvas id="myBarChart" width="100%" height="40"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-table me-1"></i>
                                        Top 10 Khách Hàng Tiềm Năng
                                    </div>
                                    <div class="card-body">
                                        <table id="datatablesSimple" width="100%">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Họ và Tên</th>
                                                    <th>Tổng Đơn Hàng</th>
                                                    <th>Tổng Sản Phẩm</th>
                                                    <th>Tổng Tiền Đã Chi</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="customer" items="${topCustomers}">
                                                    <tr>
                                                        <td>${customer.id}</td>
                                                        <td>${customer.fullname}</td>
                                                        <td>${customer.totalOrders}</td>
                                                        <td>${customer.totalProducts}</td>
                                                        <td>
                                                            <fmt:formatNumber value="${customer.totalSpent}"
                                                                type="currency" currencySymbol="₫"
                                                                maxFractionDigits="0" />
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty topCustomers}">
                                                    <tr>
                                                        <td colspan="5" class="text-center">Chưa có dữ liệu khách hàng
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </main>

                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
                    crossorigin="anonymous"></script>

                <!-- Prepare revenue data from backend (MUST be in JSP for JSTL) -->
                <script>
                    var revenueLabels = [];
                    var revenueData = [];

                    <c:forEach var="revenue" items="${revenueData}">
                        // Format date as DD/MM
                        var date = new Date('${revenue.date}');
                        var label = ('0' + date.getDate()).slice(-2) + '/' + ('0' + (date.getMonth() + 1)).slice(-2);
                        revenueLabels.push(label);
                        revenueData.push(${revenue.revenue});
                    </c:forEach>
                </script>

                <script src="/js/chart-bar-demo.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/datatables-simple-demo.js"></script>
                <!-- Revenue Area Chart -->
                <script src="/js/chart-area.js"></script>
            </body>

            </html>