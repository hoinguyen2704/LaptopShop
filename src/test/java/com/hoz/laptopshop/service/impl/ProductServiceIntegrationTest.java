package com.hoz.laptopshop.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Integration Test - Kết nối trực tiếp MySQL
 * Không dùng Spring để tránh lỗi tương thích JUnit 6
 * 
 * Chạy: ./mvnw test -Dtest=ProductServiceIntegrationTest
 */
@DisplayName("Integration Test - MySQL Direct Connection")
class ProductServiceIntegrationTest {

    // Cấu hình database (giống application.yaml)
    private static final String URL = "jdbc:mysql://localhost:3306/laptop_shop?serverTimezone=Asia/Ho_Chi_Minh";
    private static final String USER = "dev";
    private static final String PASSWORD = "9999";

    private static Connection connection;

    @BeforeAll
    static void setup() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("✅ Kết nối MySQL thành công!");
    }

    @AfterAll
    static void cleanup() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("✅ Đã đóng kết nối MySQL");
        }
    }

    // ==================== Đọc dữ liệu ====================
    @Nested
    @DisplayName("📖 Đọc dữ liệu từ MySQL")
    class ReadTests {

        @Test
        @DisplayName("Đếm tổng số sản phẩm")
        void testCountProducts() throws SQLException {
            String sql = "SELECT COUNT(*) FROM products";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("\n=== TỔNG SỐ SẢN PHẨM: " + count + " ===");
                    assertTrue(count > 0, "Database phải có sản phẩm");
                }
            }
        }

        @Test
        @DisplayName("Lấy 10 sản phẩm đầu tiên")
        void testFetchFirst10() throws SQLException {
            String sql = "SELECT id, name, price, factory, quantity, sold FROM products LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== 10 SẢN PHẨM ĐẦU TIÊN ===");
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.printf("%d. %s | %s | Kho: %d | Bán: %d%n",
                        rs.getLong("id"),
                        rs.getString("name"),
                        formatVND(rs.getDouble("price")),
                        rs.getLong("quantity"),
                        rs.getLong("sold"));
                }
                assertTrue(count > 0);
            }
        }

        @Test
        @DisplayName("Tìm sản phẩm theo ID = 1")
        void testFindById1() throws SQLException {
            String sql = "SELECT * FROM products WHERE id = 1";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                if (rs.next()) {
                    System.out.println("\n=== SẢN PHẨM ID = 1 ===");
                    System.out.println("Tên: " + rs.getString("name"));
                    System.out.println("Giá: " + formatVND(rs.getDouble("price")));
                    System.out.println("Hãng: " + rs.getString("factory"));
                    System.out.println("Đối tượng: " + rs.getString("target"));
                    System.out.println("Tồn kho: " + rs.getLong("quantity"));
                    System.out.println("Đã bán: " + rs.getLong("sold"));
                } else {
                    System.out.println("Không tìm thấy sản phẩm ID = 1");
                }
            }
        }

        @Test
        @DisplayName("Tìm sản phẩm tên 'iPhone'")
        void testFindByNameIPhone() throws SQLException {
            String sql = "SELECT * FROM products WHERE name = 'iPhone'";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                if (rs.next()) {
                    System.out.println("\n=== TÌM THẤY iPHONE ===");
                    System.out.println("ID: " + rs.getLong("id"));
                    System.out.println("Giá: " + formatVND(rs.getDouble("price")));
                    assertEquals("iPhone", rs.getString("name"));
                } else {
                    System.out.println("Không tìm thấy 'iPhone' trong DB");
                }
            }
        }
    }

    // ==================== Lọc theo tiêu chí ====================
    @Nested
    @DisplayName("🔍 Lọc sản phẩm")
    class FilterTests {

        @Test
        @DisplayName("Lọc theo hãng Apple")
        void testFilterApple() throws SQLException {
            String sql = "SELECT name, price FROM products WHERE factory = 'Apple' LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== SẢN PHẨM APPLE ===");
                while (rs.next()) {
                    System.out.printf("- %s | %s%n", rs.getString("name"), formatVND(rs.getDouble("price")));
                }
            }
        }

        @Test
        @DisplayName("Lọc theo hãng Dell")
        void testFilterDell() throws SQLException {
            String sql = "SELECT name, price FROM products WHERE factory = 'Dell' LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== SẢN PHẨM DELL ===");
                while (rs.next()) {
                    System.out.printf("- %s | %s%n", rs.getString("name"), formatVND(rs.getDouble("price")));
                }
            }
        }

        @Test
        @DisplayName("Giá dưới 10 triệu")
        void testPriceUnder10M() throws SQLException {
            String sql = "SELECT name, price FROM products WHERE price < 10000000 LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== DƯỚI 10 TRIỆU ===");
                while (rs.next()) {
                    double price = rs.getDouble("price");
                    System.out.printf("- %s | %s%n", rs.getString("name"), formatVND(price));
                    assertTrue(price < 10000000);
                }
            }
        }

        @Test
        @DisplayName("Giá 15-20 triệu")
        void testPrice15To20M() throws SQLException {
            String sql = "SELECT name, price FROM products WHERE price BETWEEN 15000000 AND 20000000 LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== 15-20 TRIỆU ===");
                while (rs.next()) {
                    System.out.printf("- %s | %s%n", rs.getString("name"), formatVND(rs.getDouble("price")));
                }
            }
        }

        @Test
        @DisplayName("Giá trên 20 triệu")
        void testPriceOver20M() throws SQLException {
            String sql = "SELECT name, price FROM products WHERE price >= 20000000 LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== TRÊN 20 TRIỆU ===");
                while (rs.next()) {
                    double price = rs.getDouble("price");
                    System.out.printf("- %s | %s%n", rs.getString("name"), formatVND(price));
                    assertTrue(price >= 20000000);
                }
            }
        }

        @Test
        @DisplayName("Tìm kiếm chứa 'Laptop'")
        void testNameLike() throws SQLException {
            String sql = "SELECT name, price FROM products WHERE name LIKE '%Laptop%' LIMIT 10";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== CHỨA 'Laptop' ===");
                while (rs.next()) {
                    String name = rs.getString("name");
                    System.out.printf("- %s | %s%n", name, formatVND(rs.getDouble("price")));
                    assertTrue(name.toLowerCase().contains("laptop"));
                }
            }
        }
    }

    // ==================== Thống kê ====================
    @Nested
    @DisplayName("📊 Thống kê")
    class StatsTests {

        @Test
        @DisplayName("Thống kê tổng quan")
        void testOverview() throws SQLException {
            String sql = """
                SELECT 
                    COUNT(*) as total,
                    SUM(quantity) as total_stock,
                    SUM(sold) as total_sold,
                    MAX(price) as max_price,
                    MIN(price) as min_price,
                    AVG(price) as avg_price
                FROM products
                """;
            
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                if (rs.next()) {
                    System.out.println("\n╔═══════════════════════════════════════╗");
                    System.out.println("║       THỐNG KÊ DATABASE               ║");
                    System.out.println("╠═══════════════════════════════════════╣");
                    System.out.printf("║ Tổng sản phẩm:    %,18d ║%n", rs.getInt("total"));
                    System.out.printf("║ Tổng tồn kho:     %,18d ║%n", rs.getLong("total_stock"));
                    System.out.printf("║ Tổng đã bán:      %,18d ║%n", rs.getLong("total_sold"));
                    System.out.printf("║ Giá cao nhất:     %18s ║%n", formatVND(rs.getDouble("max_price")));
                    System.out.printf("║ Giá thấp nhất:    %18s ║%n", formatVND(rs.getDouble("min_price")));
                    System.out.printf("║ Giá trung bình:   %18s ║%n", formatVND(rs.getDouble("avg_price")));
                    System.out.println("╚═══════════════════════════════════════╝");
                }
            }
        }

        @Test
        @DisplayName("Top 5 bán chạy nhất")
        void testTop5BestSellers() throws SQLException {
            String sql = "SELECT name, sold FROM products ORDER BY sold DESC LIMIT 5";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== TOP 5 BÁN CHẠY ===");
                int rank = 1;
                while (rs.next()) {
                    System.out.printf("%d. %s (Đã bán: %,d)%n", 
                        rank++, rs.getString("name"), rs.getLong("sold"));
                }
            }
        }

        @Test
        @DisplayName("Top 5 giá cao nhất")
        void testTop5MostExpensive() throws SQLException {
            String sql = "SELECT name, price FROM products ORDER BY price DESC LIMIT 5";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== TOP 5 GIÁ CAO NHẤT ===");
                int rank = 1;
                while (rs.next()) {
                    System.out.printf("%d. %s | %s%n", 
                        rank++, rs.getString("name"), formatVND(rs.getDouble("price")));
                }
            }
        }

        @Test
        @DisplayName("Thống kê theo hãng")
        void testByFactory() throws SQLException {
            String sql = "SELECT factory, COUNT(*) as count FROM products GROUP BY factory ORDER BY count DESC";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== SỐ LƯỢNG THEO HÃNG ===");
                while (rs.next()) {
                    System.out.printf("- %s: %d sản phẩm%n", 
                        rs.getString("factory"), rs.getInt("count"));
                }
            }
        }

        @Test
        @DisplayName("Thống kê theo đối tượng (target)")
        void testByTarget() throws SQLException {
            String sql = "SELECT target, COUNT(*) as count FROM products GROUP BY target ORDER BY count DESC";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("\n=== SỐ LƯỢNG THEO ĐỐI TƯỢNG ===");
                while (rs.next()) {
                    System.out.printf("- %s: %d sản phẩm%n", 
                        rs.getString("target"), rs.getInt("count"));
                }
            }
        }
    }

    private String formatVND(double price) {
        return String.format("%,.0f₫", price);
    }
}
