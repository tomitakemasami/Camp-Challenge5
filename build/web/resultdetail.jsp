<%@page import="jums.JumsHelper"
        import="jums.UserDataDTO" %>
<%
    JumsHelper jh = JumsHelper.getInstance();
    
    //このページで表示されたユーザー情報はセッションで受け取る
    HttpSession hs = request.getSession();
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("resultData");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMSユーザー情報詳細画面</title>
    </head>
    <body>
        <h1>詳細情報</h1>
<!-- クリックされた個人の詳細情報を表示-->
<!--表示項目はDBカラムのすべての項目-->

        名前:<%= udd.getName()%><br>
        生年月日:<%= udd.getBirthday()%><br>
        種別:<%= jh.exTypeNum(udd.getType())%><br><!--表示は数値から職種名に変更-->
        電話番号:<%= udd.getTell()%><br>
        自己紹介:<%= udd.getComment()%><br>
        登録日時:<%= udd.getNewDate()%><br>
        
<!--「変更」「削除」ボタン表示し、それぞれUpdate.java、Delete.javaに遷移する。-->
        <form action="Update" method="POST">
        <input type="hidden" name="ac"  value="<%= hs.getAttribute("ac")%>"><!--直リンク防止処理-->
        <input type="submit" name="update" value="変更" style="width:100px"><!--半角スペース追加-->
        </form>
        <form action="Delete" method="POST">
        <input type="hidden" name="ac"  value="<%= hs.getAttribute("ac")%>"><!--直リンク防止処理-->
        <input type="submit" name="delete" value="削除" style="width:100px"><!--半角スペース追加-->
        </form>
    </body>
    <%=jh.home()%>
</html>
