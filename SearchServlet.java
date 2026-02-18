package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientId = "mQK9z4hEO3NrOZEGlrKQ"; // 네이버 개발자센터에서 받은 클라이언트 ID
        String clientSecret = "w6ySLSvvp6"; // 네이버 개발자센터에서 받은 클라이언트 시크릿
        String keyword = request.getParameter("keyword"); // 검색어 가져오기
        int itemsPerPage = 10;  // 한 페이지에 표시할 데이터 수
        int totalPages = 10;  // 총 10페이지로 제한
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        try {
            // 전체 데이터를 저장할 JsonArray
            JsonArray allItems = new JsonArray();

            // 모든 페이지의 데이터를 가져옵니다.
            for (int page = 1; page <= totalPages; page++) {
                // 검색어를 인코딩합니다.
                String text = URLEncoder.encode(keyword, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/search/doc.json?query=" + text + "&start=" 
                + ((page - 1) * itemsPerPage + 1) + "&display=" + itemsPerPage;

                // URL 연결 설정
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 호출 성공
                    br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                } else {  // 오류 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                }

                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                br.close();

                // JSON 파싱하여 결과에 추가
                JsonObject jsonObject = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
                JsonArray items = jsonObject.getAsJsonArray("items");
                allItems.addAll(items);
            }

            // 현재 페이지에 해당하는 데이터만 추출
            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, allItems.size());
            JsonArray pageItems = new JsonArray();
            for (int i = startIndex; i < endIndex; i++) {
                pageItems.add(allItems.get(i));
            }

            // 결과 HTML로 변환
            StringBuilder resultHtml = new StringBuilder();
            resultHtml.append("<table border='1'><tr><th>순번</th><th>제목</th><th>링크</th></tr>");
            for (int i = 0; i < pageItems.size(); i++) {
                JsonObject item = (JsonObject) pageItems.get(i);
                String title = item.get("title").getAsString().replaceAll("<.*?>", ""); // HTML 태그 제거
                String link = item.get("link").getAsString();
                resultHtml.append("<tr><td>").append(startIndex + i + 1).append("</td><td>").append(title).append("</td><td><a href='")
                .append(link).append("' target='_blank'>링크</a></td></tr>");
            }
            resultHtml.append("</table>");

            // 페이지 네비게이션 추가
            resultHtml.append("<div style='margin-top: 20px;'>페이지: ");
            int totalResults = allItems.size();
            int totalPageCount = (int) Math.ceil((double) totalResults / itemsPerPage);
            for (int i = 1; i <= totalPageCount; i++) {
                if (i == currentPage) {
                    resultHtml.append("<strong>").append(i).append("</strong> ");
                } else {
                    resultHtml.append("<a href='?keyword=").append(URLEncoder.encode(keyword, "UTF-8")).append("&page=").append(i)
                    .append("'>").append(i).append("</a> ");
                }
            }
            resultHtml.append("</div>");

            // 전체 데이터를 저장하기 위해 JSON 형태로 전달
            request.getSession().setAttribute("allItems", allItems);

            // JSP에 데이터 전달
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("result", resultHtml.toString());
            request.getRequestDispatcher("/Search.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result", "오류 발생: " + e.getMessage());
            request.getRequestDispatcher("/Search.jsp").forward(request, response);
        }
    }
}
