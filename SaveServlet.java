package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/save")
public class SaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 전체 데이터를 가져옵니다.
        JsonArray allItems = (JsonArray) request.getSession().getAttribute("allItems");

        if (allItems == null || allItems.size() == 0) {
            response.getWriter().write("저장할 데이터가 없습니다.");
            return;
        }

        try {
            // MySQL 연결 설정
            String dbUrl = "jdbc:mysql://localhost:3306/naver1_data?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
            String dbUser = "root";
            String dbPassword = "1234";

            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO search_results (title, link) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    // 데이터베이스에 검색 결과 저장
                    for (int i = 0; i < allItems.size(); i++) {
                        JsonObject record = allItems.get(i).getAsJsonObject();
                        String title = record.get("title").getAsString();
                        String link = record.get("link").getAsString();

                        pstmt.setString(1, title);
                        pstmt.setString(2, link);
                        pstmt.addBatch();
                    }

                    pstmt.executeBatch();
                }
            }
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("저장 중 오류 발생: " + e.getMessage());
        }
    }
}

