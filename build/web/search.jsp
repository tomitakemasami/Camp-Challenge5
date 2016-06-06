<!--import文の無駄な改行を修正-->
<%@page import="javax.servlet.http.HttpSession"
        import="jums.JumsHelper" %>
<%
    JumsHelper jh = JumsHelper.getInstance();
    HttpSession hs = request.getSession();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMSユーザー情報検索フォーム</title>
    </head>
    <body>
        <h1>◆ユーザー情報検索画面◆</h1><br>
        
         <!--値渡しをPOSTからGETへ変換-->
         <form action="SearchResult" method="GET">
             
        <!--フォームに記入してから検索可能-->
        <!--検索条件は、名前・生年・種別の複合検索-->
        名前:
        <input type="text" name="name">
        <br><br>

        生年:　
        <select name="year">
            <option value="">----</option>
            <% for(int i=1950; i<=2010; i++){ %>
            <option value="<%=i%>"><%=i%></option>
            <% } %>
        </select>年生まれ
        <br><br>

        種別:
        <br>
            <% for(int i = 1; i<=3; i++){ %>
            <input type="radio" name="type" value="<%=i%>"><%=jh.exTypeNum(i)%><br>
            <% } %>
        <br>
        <!--直リンク防止処理-->
        <input type="hidden" name="ac"  value="<%= hs.getAttribute("ac")%>">
        <input type="submit" name="btnSubmit" value="検索">
    </form>
        <br>
        <%=jh.home()%>
    </body>
</html>
