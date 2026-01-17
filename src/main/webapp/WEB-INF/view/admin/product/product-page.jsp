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
                <title>MANAGER PRODUCT</title>
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
                                <h1 class="mt-4">Manage Products</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="<c:url value='/admin'/>">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Product</li>
                                </ol>
                                
                                <!-- Flash Message Alert -->
                                <c:if test="${not empty message}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <i class="fas fa-check-circle"></i> ${message}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                </c:if>
                                
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Table products</h3>
                                                <a href="<c:url value='/admin/product/create' />"
                                                    class="btn btn-primary">Create a
                                                    product</a>
                                            </div>

                                            <hr />
                                            <table class=" table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Name</th>
                                                        <th>Price</th>
                                                        <th>Quantity</th>
                                                        <th>Sold</th>
                                                        <th>Factory</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="product" items="${products}">
                                                        <tr>
                                                            <th>${product.id}</th>
                                                            <td>${product.name}</td>
                                                            <td>
                                                                <fmt:formatNumber type="number"
                                                                    value="${product.price}" /> đ
                                                            </td>
                                                            <td>${product.quantity}</td>
                                                            <td>${product.sold}</td>
                                                            <td>${product.factory}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${product.active}">
                                                                        <span class="badge bg-success">Active</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-danger">Inactive</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <a href="/admin/product/${product.id}"
                                                                    class="btn btn-success btn-sm">View</a>
                                                                <a href="/admin/product/update/${product.id}?page=${currentPage}"
                                                                    class="btn btn-warning btn-sm mx-2">Update</a>
                                                                <a href="/admin/product/delete/${product.id}?page=${currentPage}"
                                                                    class="btn ${product.active ? 'btn-secondary' : 'btn-primary'} btn-sm">
                                                                    ${product.active ? 'Deactivate' : 'Activate'}
                                                                </a>
                                                            </td>
                                                        </tr>

                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                            <nav aria-label="Page navigation example">
                                                <ul class="pagination justify-content-center">
                                                    <!-- Previous Button -->
                                                    <li class="page-item">
                                                        <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                            href="/admin/product?page=${currentPage - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">&laquo;</span>
                                                        </a>
                                                    </li>
                                                    
                                                    <!-- First Page (if not in visible range) -->
                                                    <c:if test="${currentPage > 3}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="/admin/product?page=1">1</a>
                                                        </li>
                                                        <li class="page-item disabled">
                                                            <span class="page-link">...</span>
                                                        </li>
                                                    </c:if>
                                                    
                                                    <!-- Page Numbers (2 before, current, 2 after) -->
                                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                                        <c:if test="${pageNum >= currentPage - 2 && pageNum <= currentPage + 2}">
                                                            <li class="page-item">
                                                                <a class="${pageNum eq currentPage ? 'active page-link' : 'page-link'}"
                                                                    href="/admin/product?page=${pageNum}">
                                                                    ${pageNum}
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                    </c:forEach>
                                                    
                                                    <!-- Last Page (if not in visible range) -->
                                                    <c:if test="${currentPage < totalPages - 2}">
                                                        <li class="page-item disabled">
                                                            <span class="page-link">...</span>
                                                        </li>
                                                        <li class="page-item">
                                                            <a class="page-link" href="/admin/product?page=${totalPages}">${totalPages}</a>
                                                        </li>
                                                    </c:if>
                                                    
                                                    <!-- Next Button -->
                                                    <li class="page-item">
                                                        <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                            href="/admin/product?page=${currentPage + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">&raquo;</span>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </nav>
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

            </body>

            </html>