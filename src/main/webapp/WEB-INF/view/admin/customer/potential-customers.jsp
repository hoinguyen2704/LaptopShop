<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Top 10 Khách Hàng Tiềm Năng</title>
</head>
<body>
    <div class="container mt-5">
        <h2>Top 10 Khách Hàng Tiềm Năng</h2>
        <p>Danh sách 10 khách hàng có tổng số tiền mua hàng lớn nhất</p>
        
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Họ và Tên</th>
                    <th>Tổng Số Đơn Hàng</th>
                    <th>Tổng Số Lượng Sản Phẩm</th>
                    <th>Tổng Số Tiền Đã Chi</th>
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
                                             type="currency" 
                                             currencySymbol="₫" 
                                             maxFractionDigits="0"/>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty topCustomers}">
                    <tr>
                        <td colspan="5" class="text-center">Chưa có dữ liệu khách hàng</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
