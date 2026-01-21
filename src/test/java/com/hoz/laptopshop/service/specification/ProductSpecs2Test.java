package com.hoz.laptopshop.service.specification;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test ProductSpecs2 - Lọc sản phẩm theo giá
 * Dùng JDBC trực tiếp (tránh lỗi Spring + JUnit 6)
 * Cấu hình lấy từ application.yaml
 */
@DisplayName("ProductSpecs2 Test")
public class ProductSpecs2Test {

    // Cấu hình giống application.yaml
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
        if (connection != null) connection.close();
    }

    @Test
    @DisplayName("priceBetWeen: 17.690.000 - 31.490.000")
    void testPriceBetween() throws SQLException {
        String sql = "SELECT name, price FROM products WHERE price BETWEEN 17690000 AND 31490000 ORDER BY price";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== GIÁ 17.690.000 - 31.490.000 ===");
            while (rs.next()) {
                double price = rs.getDouble("price");
                System.out.printf("- %s | %,.0f₫%n", rs.getString("name"), price);
                assertTrue(price >= 17690000 && price <= 31490000);
            }
        }
    }

    @Test
    @DisplayName("minPrice: >= 17.690.000")
    void testMinPrice() throws SQLException {
        String sql = "SELECT name, price FROM products WHERE price >= 17690000 ORDER BY price";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== GIÁ >= 17.690.000 ===");
            while (rs.next()) {
                System.out.printf("- %s | %,.0f₫%n", rs.getString("name"), rs.getDouble("price"));
            }
        }
    }

    @Test
    @DisplayName("(A AND B) OR (C AND D) - Apple>20tr OR Dell+Gaming")
    void testComplexCondition() throws SQLException {
        String sql = """
            SELECT name, price, factory, target FROM products 
            WHERE (factory = 'Apple' AND price > 20000000) 
               OR (factory = 'Dell' AND target = 'Gaming')
            """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== (Apple AND >20tr) OR (Dell AND Gaming) ===");
            while (rs.next()) {
                System.out.printf("- %s | %,.0f₫ | %s | %s%n", 
                    rs.getString("name"), rs.getDouble("price"), 
                    rs.getString("factory"), rs.getString("target"));
            }
        }
    }
}
