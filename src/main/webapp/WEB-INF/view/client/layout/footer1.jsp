<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <footer class="bg-white border-t border-gray-200 mt-auto">
            <div class="max-w-7xl mx-auto px-6 py-12 md:py-16">
                <div class="grid grid-cols-1 md:grid-cols-4 gap-12">
                    <!-- Brand & About -->
                    <div class="space-y-6">
                        <div class="flex items-center space-x-2">
                            <div class="bg-indigo-600 p-2 rounded-lg">
                                <a href="<c:url value='/'/>">
                                    <i data-lucide="laptop" class="h-6 w-6 text-white"></i>
                            </div>
                            <span class="text-xl font-bold text-gray-900 tracking-tight">TechZone</span>
                            </a>
                        </div>
                        <p class="text-gray-500 text-sm leading-relaxed font-medium">
                            Hệ thống bán lẻ laptop và thiết bị công nghệ chính hãng hàng đầu Việt Nam. Cam kết chất
                            lượng, giá cả cạnh tranh.
                        </p>
                        <div class="flex space-x-4">
                            <a href="<c:url value='/'/>"
                                class="p-3 bg-gray-50 rounded-2xl text-gray-400 hover:text-indigo-600 hover:bg-indigo-50 transition border border-gray-100 hover:border-indigo-100 shadow-sm">
                                <i data-lucide="facebook" class="h-5 w-5"></i>
                            </a>
                            <a href="<c:url value='/'/>"
                                class="p-3 bg-gray-50 rounded-2xl text-gray-400 hover:text-pink-600 hover:bg-pink-50 transition border border-gray-100 hover:border-pink-100 shadow-sm">
                                <i data-lucide="instagram" class="h-5 w-5"></i>
                            </a>
                            <a href="<c:url value='/'/>"
                                class="p-3 bg-gray-50 rounded-2xl text-gray-400 hover:text-blue-400 hover:bg-blue-50 transition border border-gray-100 hover:border-blue-100 shadow-sm">
                                <i data-lucide="twitter" class="h-5 w-5"></i>
                            </a>
                        </div>
                    </div>

                    <!-- Customer Service -->
                    <div>
                        <h3 class="font-black text-gray-900 mb-6 uppercase text-sm tracking-wider">Chăm sóc khách hàng
                        </h3>
                        <ul class="space-y-4 text-sm font-medium text-gray-500">
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Trung tâm trợ giúp</a>
                            </li>
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Hướng dẫn mua hàng</a>
                            </li>
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Chính sách bảo hành</a>
                            </li>
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Trả góp 0% lãi suất</a>
                            </li>
                        </ul>
                    </div>

                    <!-- Quick Links -->
                    <div>
                        <h3 class="font-black text-gray-900 mb-6 uppercase text-sm tracking-wider">Về TechZone</h3>
                        <ul class="space-y-4 text-sm font-medium text-gray-500">
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Giới thiệu công ty</a>
                            </li>
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Tuyển dụng nhân tài</a>
                            </li>
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Tin tức công nghệ</a>
                            </li>
                            <li><a href="<c:url value='/'/>" class="hover:text-indigo-600 transition flex items-center"><span
                                        class="w-1.5 h-1.5 rounded-full bg-gray-300 mr-2"></span>Hệ thống cửa hàng</a>
                            </li>
                        </ul>
                    </div>

                    <!-- Contact Info -->
                    <div>
                        <h3 class="font-black text-gray-900 mb-6 uppercase text-sm tracking-wider">Liên hệ</h3>
                        <ul class="space-y-4 text-sm font-medium text-gray-500">
                            <li class="flex items-start space-x-3">
                                <i data-lucide="map-pin" class="h-5 w-5 text-indigo-600 shrink-0 mt-0.5"></i>
                                <span>132 Đường Cầu Diễn, Bắc Từ Liêm, Hà Nội</span>
                            </li>
                            <li class="flex items-center space-x-3">
                                <i data-lucide="phone" class="h-5 w-5 text-indigo-600 shrink-0"></i>
                                <span class="font-bold text-gray-900">1900 1000</span>
                            </li>
                            <li class="flex items-center space-x-3">
                                <i data-lucide="mail" class="h-5 w-5 text-indigo-600 shrink-0"></i>
                                <span>hozinium@gmail.com</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div
                    class="border-t border-gray-100 mt-12 pt-8 flex flex-col md:flex-row justify-between items-center gap-4">
                    <p class="text-sm text-gray-400 font-medium">© 2025 TechZone Store. All rights reserved.</p>
                    <div class="flex space-x-6 text-sm text-gray-400 font-bold">
                        <a href="<c:url value='/'/>" class="hover:text-indigo-600 transition">Privacy Policy</a>
                        <a href="<c:url value='/'/>" class="hover:text-indigo-600 transition">Terms of Service</a>
                        <a href="<c:url value='/'/>" class="hover:text-indigo-600 transition">Cookies Settings</a>
                    </div>
                </div>
            </div>
        </footer>
        <script>
            lucide.createIcons();
        </script>