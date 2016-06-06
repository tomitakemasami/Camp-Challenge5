<%@page import="java.util.ArrayList"
        import="jums.JumsHelper"
        import="jums.UserDataDTO" %>

<%
    HttpSession hs = request.getSession();
    JumsHelper jh = JumsHelper.getInstance();
    ArrayList<UserDataDTO> udd = (ArrayList<UserDataDTO>)request.getAttribute("resultData");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS検索結果画面</title>
    </head>
    <body>
        <h1>検索結果</h1>
        該当件数:<%= udd.size() %>件<br>
        
        <% if(!udd.isEmpty()){ %><!-- 該当データがあれば表示-->
        
        <!--検索結果がID昇順にテーブル型で表示される-->
        <table border=1>
            <tr>
                <!--検索結果として表示されるのは名前、生年、種別、登録日のみ-->
                <th>名前</th>
                <th>生年</th>
                <th>種別</th>
                <th>登録日時</th>
            </tr>
            <%for(int i = 0;i<udd.size();i++){ %>
            <tr>
                <!--名前がクリック可能、resultdetail.javaへ遷移する-->
                <!--直リンク防止処理-->
                <td><a href="ResultDetail?id=<%= udd.get(i).getUserID()%>&ac=<%= hs.getAttribute("ac")%>"><%= udd.get(i).getName()%></a></td>
                <td><%= udd.get(i).getBirthday()%></td>
                <td><%= jh.exTypeNum(udd.get(i).getType())%></td><!--数字ではなく種別名で表示-->
                <td><%= udd.get(i).getNewDate()%></td>
            </tr>
            <% } %>
        </table>
        <% } else { %><!-- 該当データが無い場合に以下を表示-->
            条件に該当するデータはありませんでした.
        <% } %>
        <form action="Search" method="POST">
        <input type="hidden" name="ac"  value="<%= hs.getAttribute("ac")%>">    
        <input type="submit" name="return" value="検索に戻る" style="width:100px">
        </form>
        
        
        <%=jh.home()%>
    </body>
</html>