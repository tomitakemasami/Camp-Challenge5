<%@page import="jums.JumsHelper"
        import="jums.UserDataDTO" %>
<%
    JumsHelper jh = JumsHelper.getInstance();
    
    //個人データをセッションで受け取る
    HttpSession hs = request.getSession();
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("resultData");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <h1>削除確認</h1>
    以下の内容を削除します。よろしいですか？<br>
    名前:<%=udd.getName()%><br>
    生年月日:<%=udd.getBirthday()%><br>
    種別:<%=jh.exTypeNum(udd.getType())%><br><!--表示を数値から職種名に変更-->
    電話番号:<%=udd.getTell()%><br>
    自己紹介:<%=udd.getComment()%><br>
    登録日時:<%=udd.getNewDate()%><br>
    
    <form action="DeleteResult" method="POST">
      <input type="hidden" name="ac"  value="<%= hs.getAttribute("ac")%>"><!--直リンク防止処理-->
      <input type="submit" name="YES" value="はい" style="width:100px"><!--半角スペース追記-->
    </form>
    <form action="ResultDetail" method="POST">
      <input type="hidden" name="ac"  value="<%= hs.getAttribute("ac")%>"><!--直リンク防止処理-->
      <input type="submit" name="NO" value="詳細画面に戻る" onClick="history.back()" style="width:100px"><!--「一つ前に戻る」を実装。 + 半角スペース追記-->
    </form>
    </body>
    <%=jh.home()%>
</html>
