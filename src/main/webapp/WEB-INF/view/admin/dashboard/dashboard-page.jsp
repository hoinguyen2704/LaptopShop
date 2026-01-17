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

                                <!-- Hero Cards - Prominent Stats -->
                                <div class="row mb-5">
                                    <!-- Tổng Doanh Thu 7 Ngày -->
                                    <div class="col-xl-4 col-md-6 mb-4">
                                        <div class="card border-0" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 20px; overflow: hidden; box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3); position: relative;">
                                            <!-- Dark overlay for text area -->
                                            <div class="position-absolute w-100 h-100" style="background: linear-gradient(to right, rgba(0,0,0,0.3) 0%, transparent 60%); z-index: 0;"></div>
                                            <div class="card-body text-white p-4 position-relative" style="z-index: 1;">
                                                <h6 class="text-white mb-2" style="font-size: 0.9rem; font-weight: 600; letter-spacing: 0.5px; text-shadow: 0 2px 4px rgba(0,0,0,0.3);">TỔNG DOANH THU</h6>
                                                <h2 class="mb-2" style="font-weight: 700; font-size: 2.2rem; text-shadow: 0 2px 8px rgba(0,0,0,0.4);">
                                                    <fmt:formatNumber type="number" value="${stats.totalRevenue7Days}" /> đ
                                                </h2>
                                                <div style="font-size: 0.85rem; opacity: 1; text-shadow: 0 1px 3px rgba(0,0,0,0.3);">
                                                    <c:choose>
                                                        <c:when test="${stats.revenueGrowthPercent >= 0}">
                                                            <i class="fas fa-arrow-up"></i> +<fmt:formatNumber value="${stats.revenueGrowthPercent}" maxFractionDigits="1"/>% so với tháng trước
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="fas fa-arrow-down"></i> <fmt:formatNumber value="${stats.revenueGrowthPercent}" maxFractionDigits="1"/>% so với tháng trước
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <!-- Icon background glow -->
                                                <div class="position-absolute" style="right: -10px; top: 50%; transform: translateY(-50%); width: 120px; height: 120px; background: radial-gradient(circle, rgba(255,255,255,0.15) 0%, transparent 70%); border-radius: 50%;"></div>
                                                <i class="fas fa-dollar-sign position-absolute" style="right: 25px; top: 50%; transform: translateY(-50%); font-size: 5.5rem; color: rgba(255,255,255,0.5); filter: drop-shadow(0 0 20px rgba(255,255,255,0.6)) drop-shadow(0 0 40px rgba(255,255,255,0.4));"></i>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- Đơn Hàng Thành Công -->
                                    <div class="col-xl-4 col-md-6 mb-4">
                                        <div class="card border-0" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); border-radius: 20px; overflow: hidden; box-shadow: 0 10px 30px rgba(240, 147, 251, 0.3); position: relative;">
                                            <!-- Dark overlay for text area -->
                                            <div class="position-absolute w-100 h-100" style="background: linear-gradient(to right, rgba(0,0,0,0.3) 0%, transparent 60%); z-index: 0;"></div>
                                            <div class="card-body text-white p-4 position-relative" style="z-index: 1;">
                                                <h6 class="text-white mb-2" style="font-size: 0.9rem; font-weight: 600; letter-spacing: 0.5px; text-shadow: 0 2px 4px rgba(0,0,0,0.3);">ĐƠN HÀNG THÀNH CÔNG</h6>
                                                <h2 class="mb-2" style="font-weight: 700; font-size: 2.2rem; text-shadow: 0 2px 8px rgba(0,0,0,0.4);">
                                                    <fmt:formatNumber type="number" value="${stats.completedOrders}" />
                                                </h2>
                                                <div style="font-size: 0.85rem; opacity: 1; text-shadow: 0 1px 3px rgba(0,0,0,0.3);">
                                                    <i class="fas fa-check-circle"></i> <fmt:formatNumber value="${stats.deliverySuccessRate}" maxFractionDigits="1"/>% tỷ lệ giao hàng thành công
                                                </div>
                                                <!-- Icon background glow -->
                                                <div class="position-absolute" style="right: -10px; top: 50%; transform: translateY(-50%); width: 120px; height: 120px; background: radial-gradient(circle, rgba(255,255,255,0.15) 0%, transparent 70%); border-radius: 50%;"></div>
                                                <i class="fas fa-shopping-cart position-absolute" style="right: 25px; top: 50%; transform: translateY(-50%); font-size: 5.5rem; color: rgba(255,255,255,0.5); filter: drop-shadow(0 0 20px rgba(255,255,255,0.6)) drop-shadow(0 0 40px rgba(255,255,255,0.4));"></i>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- Sản Phẩm Đang Hoạt Động -->
                                    <div class="col-xl-4 col-md-6 mb-4">
                                        <div class="card border-0" style="background: linear-gradient(135deg, #ffa726 0%, #fb8c00 100%); border-radius: 20px; overflow: hidden; box-shadow: 0 10px 30px rgba(255, 167, 38, 0.3); position: relative;">
                                            <!-- Dark overlay for text area -->
                                            <div class="position-absolute w-100 h-100" style="background: linear-gradient(to right, rgba(0,0,0,0.3) 0%, transparent 60%); z-index: 0;"></div>
                                            <div class="card-body text-white p-4 position-relative" style="z-index: 1;">
                                                <h6 class="text-white mb-2" style="font-size: 0.9rem; font-weight: 600; letter-spacing: 0.5px; text-shadow: 0 2px 4px rgba(0,0,0,0.3);">SẢN PHẨM ĐANG BÁN</h6>
                                                 <h2 class="mb-2" style="font-weight: 700; font-size: 2.2rem; text-shadow: 0 2px 8px rgba(0,0,0,0.4);">
                                                    <fmt:formatNumber type="number" value="${stats.activeProducts}" />
                                                </h2>
                                                <div style="font-size: 0.85rem; opacity: 1; text-shadow: 0 1px 3px rgba(0,0,0,0.3);">
                                                    <i class="fas fa-box-open"></i> Kho hàng: Ổn định
                                                </div>
                                                <!-- Icon background glow -->
                                                <div class="position-absolute" style="right: -10px; top: 50%; transform: translateY(-50%); width: 120px; height: 120px; background: radial-gradient(circle, rgba(255,255,255,0.15) 0%, transparent 70%); border-radius: 50%;"></div>
                                                <i class="fas fa-tags position-absolute" style="right: 25px; top: 50%; transform: translateY(-50%); font-size: 5.5rem; color: rgba(255,255,255,0.5); filter: drop-shadow(0 0 20px rgba(255,255,255,0.6)) drop-shadow(0 0 40px rgba(255,255,255,0.4));"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>

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
                                        <div class="card text-white mb-4"
                                            style="background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%); box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small"
                                                            style="font-weight: 600; text-shadow: 1px 1px 2px rgba(0,0,0,0.3);">
                                                            Đơn hoàn thành</div>
                                                        <div class="h5 mb-0"
                                                            style="font-weight: 700; text-shadow: 2px 2px 4px rgba(0,0,0,0.3);">
                                                            ${stats.completedOrders} đơn</div>
                                                    </div>
                                                    <i class="fas fa-check-circle fa-2x" style="opacity: 0.9;"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Đơn đang xử lý -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card text-white mb-4"
                                            style="background: linear-gradient(135deg, #fd7e14 0%, #ca6510 100%); box-shadow: 0 4px 6px rgba(0,0,0,0.1); cursor: pointer;"
                                            onclick="showPieChart('processing')">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small"
                                                            style="font-weight: 600; text-shadow: 1px 1px 2px rgba(0,0,0,0.3);">
                                                            Đơn đang xử lý</div>
                                                        <div class="h5 mb-0"
                                                            style="font-weight: 700; text-shadow: 2px 2px 4px rgba(0,0,0,0.3);">
                                                            ${stats.processingOrders} đơn</div>
                                                    </div>
                                                    <i class="fas fa-clock fa-2x" style="opacity: 0.9;"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Row 2: Business Metrics -->
                                <div class="row mb-4">
                                    <!-- Đơn hoàn/hủy -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card bg-danger text-white mb-4" style="cursor: pointer;"
                                            onclick="showPieChart('cancelled')">
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
                                        <div class="card bg-success text-white mb-4" style="cursor: pointer;"
                                            onclick="showPieChart('products')">
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
                                        <div class="card text-white mb-4"
                                            style="background: linear-gradient(135deg, #0dcaf0 0%, #0aa2c0 100%); box-shadow: 0 4px 6px rgba(0,0,0,0.1); cursor: pointer;"
                                            onclick="showPieChart('users')">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small"
                                                            style="font-weight: 600; text-shadow: 1px 1px 2px rgba(0,0,0,0.3);">
                                                            Tổng người dùng</div>
                                                        <div class="h5 mb-0"
                                                            style="font-weight: 700; text-shadow: 2px 2px 4px rgba(0,0,0,0.3);">
                                                            ${stats.totalUsers} người</div>
                                                    </div>
                                                    <i class="fas fa-users fa-2x" style="opacity: 0.9;"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Top khách hàng -->
                                    <div class="col-xl-3 col-md-6">
                                        <div class="card text-white mb-4"
                                            style="background: linear-gradient(135deg, #6c757d 0%, #545b62 100%); box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <div class="small"
                                                            style="font-weight: 600; text-shadow: 1px 1px 2px rgba(0,0,0,0.3);">
                                                            Top khách hàng</div>
                                                        <div class="h5 mb-0"
                                                            style="font-weight: 700; text-shadow: 2px 2px 4px rgba(0,0,0,0.3);">
                                                            ${topCustomers.size()} người</div>
                                                    </div>
                                                    <i class="fas fa-star fa-2x" style="opacity: 0.9;"></i>
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
                                                <canvas id="myAreaChart" width="100%" height="60"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="card mb-4">
                                            <div class="card-header">
                                                <i class="fas fa-chart-bar me-1"></i>
                                                Đơn Hàng 7 Ngày Gần Nhất
                                            </div>
                                            <div class="card-body">
                                                <canvas id="myBarChart" width="100%" height="60"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Top 10 S\u1ea3n Ph\u1ea9m B\u00e1n Ch\u1ea1y Nh\u1ea5t - 1 Row, 2 Columns -->
                                <div class="row">
                                    <div class="col-xl-6">
                                        <div class="card mb-4 h-100">
                                            <div class="card-header">
                                                <i class="fas fa-chart-bar me-1"></i>
                                                Top 10 Sản Phẩm Bán Chạy Nhất
                                            </div>
                                            <div class="card-body">
                                                <canvas id="topProductsChart" width="100%" height="50"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <div class="card mb-4 h-100">
                                            <div class="card-header">
                                                <i class="fas fa-table me-1"></i>
                                                Chi Tiết Top 10 Sản Phẩm
                                            </div>
                                            <div class="card-body">
                                                <table class="table table-striped table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>Tên Sản Phẩm</th>
                                                            <th class="text-end">Đã Bán</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="product" items="${stats.topSellingProducts}" varStatus="status">
                                                            <tr>
                                                                <td>${status.index + 1}</td>
                                                                <td>${product.productName}</td>
                                                                <td class="text-end"><strong>${product.soldCount}</strong></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
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

                            <div class="modal fade" id="pieChartModal" tabindex="-1"
                                aria-labelledby="pieChartModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-xl">
                                    <div class="modal-content" style="overflow: visible;">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="pieChartModalLabel"></h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body" style="overflow: visible; min-height: 400px;">
                                            <!-- Wrapper để center chart với padding -->
                                            <div style="width: 60%; margin: 0 auto; padding: 40px; overflow: visible;">
                                                <canvas id="pieChart" width="180" height="180"></canvas>
                                            </div>
                                        </div>
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
                <!-- Chart.js Datalabels Plugin for external labels -->
                <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
                <script>
                    // Disable datalabels globally - chỉ enable cho pie chart trong modal
                    Chart.defaults.global.plugins.datalabels.display = false;
                </script>

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

                <!-- Prepare top selling products data for bar chart -->
                <script>
                    var orderLabels = [];
                    var completedData = [];
                    var failedData = [];
                    <c:forEach var="stat" items="${stats.dailyOrderStats}">
                        orderLabels.push('${stat.date}');
                        completedData.push(${stat.completedOrders});
                        failedData.push(${stat.failedOrders});
                    </c:forEach>
                </script>

                <!-- Prepare products data for horizontal bar chart -->
                <script>
                    var productNames = [];
                    var productSoldCounts = [];
                    <c:forEach var="product" items="${stats.topSellingProducts}">
                        productNames.push('${product.productName}');
                        productSoldCounts.push(${product.soldCount});
                    </c:forEach>
                </script>

                <script src="/js/chart-bar-demo.js"></script>
                <script src="/js/chart-products.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/datatables-simple-demo.js"></script>
                <!-- Revenue Area Chart -->
                <script src="/js/chart-area.js"></script>

                <!-- Pie Chart for Cards -->
                <script>
                    var pieChartInstance = null;

                    function showPieChart(type) {
                        let chartData = {};
                        let chartTitle = '';

                        switch (type) {
                            case 'processing':
                                chartData = {
                                    labels: ['Pending', 'Shipping'],
                                    data: [${ stats.pendingOrders }, ${ stats.shippingOrders }],
                                    colors: ['#ffc107', '#fd7e14']
                                };
                                chartTitle = 'Đơn đang xử lý - Chi tiết';
                                break;
                            case 'cancelled':
                                chartData = {
                                    labels: ['Cancelled', 'Returned'],
                                    data: [${ stats.cancelledOrdersOnly }, ${ stats.returnedOrders }],
                                    colors: ['#dc3545', '#6c757d']
                                };
                                chartTitle = 'Đơn hoàn/hủy - Chi tiết';
                                break;
                            case 'products':
                                chartData = {
                                    labels: ['Active', 'Inactive'],
                                    data: [${stats.activeProducts}, ${stats.inactiveProducts}],
                                    colors: ['#198754', '#6c757d']
                                };
                                chartTitle = 'Sản phẩm - Chi tiết';
                                break;
                            case 'users':
                                chartData = {
                                    labels: ['Active', 'Inactive'],
                                    data: [${ stats.activeUsers }, ${ stats.inactiveUsers }],
                                    colors: ['#0dcaf0', '#6c757d']
                                };
                                chartTitle = 'Người dùng - Chi tiết';
                                break;
                        }

                        renderPieChart(chartData, chartTitle);
                        new bootstrap.Modal(document.getElementById('pieChartModal')).show();
                    }

                    function renderPieChart(chartData, title) {
                        // Destroy existing chart if any
                        if (pieChartInstance) {
                            pieChartInstance.destroy();
                        }

                        document.getElementById('pieChartModalLabel').innerText = title;

                        const ctx = document.getElementById('pieChart');
                        pieChartInstance = new Chart(ctx, {
                            type: 'pie',
                            data: {
                                labels: chartData.labels,
                                datasets: [{
                                    data: chartData.data,
                                    backgroundColor: chartData.colors,
                                    borderWidth: 3,
                                    borderColor: '#fff'
                                }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: true,
                                layout: {
                                    padding: {
                                        left: 80,
                                        right: 80,
                                        top: 20,
                                        bottom: 20
                                    }
                                },
                                plugins: {
                                    // Data labels plugin - chỉ hiển thị ở bên ngoài
                                    datalabels: {
                                        display: true,  // Override global disable
                                        color: '#000',
                                        font: {
                                            size: 16,
                                            weight: 'bold'
                                        },
                                        formatter: function (value, context) {
                                            const total = context.dataset.data.reduce((a, b) => a + b, 0);
                                            const percentage = ((value / total) * 100).toFixed(1);
                                            return value + '\n(' + percentage + '%)';
                                        },
                                        anchor: 'end',
                                        align: 'end',
                                        offset: 15,
                                        textAlign: 'center',
                                        clip: false
                                    },
                                    legend: {
                                        position: 'bottom',
                                        labels: {
                                            font: {
                                                size: 16 // Giảm từ 19px xuống 16px
                                            },
                                            padding: 20
                                        }
                                    },
                                    tooltip: {
                                        enabled: false  // Tắt tooltip để không hiển thị số ở giữa
                                    }
                                }
                            }
                        });
                    }
                </script>
            </body>

            </html>