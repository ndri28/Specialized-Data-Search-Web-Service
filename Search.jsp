<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>네이버 전문자료 검색</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: auto;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #000;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        button, input[type="submit"] {
            width: 150px;
            height: 40px;
            margin-left: 5px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover, input[type="submit"]:hover {
            background-color: #45a049;
        }
        input[type="text"] {
            border: 2px solid #4CAF50;
            padding: 8px;
            width: 300px;
        }
        .header {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }
        .naver-icon {
            width: 40px;
            height: 40px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <img src="naver-icon-style.png" alt="네이버 아이콘" class="naver-icon">
            <h2>네이버 전문자료 검색</h2>
        </div>
        <form action="search" method="get">
            <label for="keyword">검색어: </label>
            <input type="text" id="keyword" name="keyword" required>
            <input type="submit" value="검색">
            <button type="button" onclick="saveResults()">저장</button>
        </form>
        <hr>

        <div id="searchResult">
            ${result}
        </div>
    </div>

    <script>
    function saveResults() {
        // 검색된 데이터를 가져온다.
        const rows = document.querySelectorAll("#searchResult table tr");
        let data = [];

        // 첫 번째 행은 테이블 허더이므로 제외하고 데이터 추출
        for (let i = 1; i < rows.length; i++) {
            const columns = rows[i].querySelectorAll("td");
            if (columns.length >= 3) {
                const title = columns[1].textContent.trim();
                const link = columns[2].querySelector("a").getAttribute("href").trim();
                data.push({ title, link });
            }
        }

        if (data.length === 0) {
            alert("저장할 데이터가 없습니다.");
            return;
        }

        // 데이터 전송
        fetch('save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
        })
        .catch(error => alert('저장 중 오류 발생: ' + error));
    }
    </script>
</body>
</html>
