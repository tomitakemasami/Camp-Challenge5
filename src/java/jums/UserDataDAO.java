package jums;

import base.DBManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * ユーザー情報を格納するテーブルに対しての操作処理を包括する
 * DB接続系はDBManagerクラスに一任
 * 基本的にはやりたい1種類の動作に対して1メソッド
 * @author hayashi-s
 */
public class UserDataDAO {
    
    //インスタンスオブジェクトを返却させてコードの簡略化
    public static UserDataDAO getInstance(){
        return new UserDataDAO();
    }
    
    /**
     * データの挿入処理を行う。現在時刻は挿入直前に生成
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     */
    public void insert(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("INSERT INTO user_t(name,birthday,tell,type,comment,newDate) VALUES(?,?,?,?,?,?)");
            st.setString(1, ud.getName());
            st.setDate(2, new java.sql.Date(ud.getBirthday().getTime()));//指定のタイムスタンプ値からSQL格納用のDATE型に変更
            st.setString(3, ud.getTell());
            st.setInt(4, ud.getType());
            st.setString(5, ud.getComment());
            st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            st.executeUpdate();
            System.out.println("insert completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
    
    /**
     * データの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 検索結果
     */
    
    //検索結果(ユーザー情報)が複数の時のために、返り値の型をUserDataDTOからArrayListへ変更
    //その結果をsearchListに入れて返却
    public ArrayList<UserDataDTO> search(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        
        ArrayList<UserDataDTO> searchList = new ArrayList<UserDataDTO>();
        try{
            con = DBManager.getConnection();
            
            //フォーム未入力の場合、全件表示
            String sql = "SELECT * FROM user_t";
            
            boolean flag = false;
            int name = 0;
            int birth = 0;
            int type = 0;
            int i = 1;
            
            //フォーム入力されている場合の流れ
            //名前
            if (!ud.getName().equals("")) {
                sql += " WHERE name like ?";
                flag = true;
                name = 1;
            }
            
            //生年
            if (ud.getBirthday()!=null) {
                if(!flag){
                    sql += " WHERE birthday like ?";
                    flag = true;
                    birth = 1;
                }else{
                    sql += " AND birthday like ?";
                    birth = 1;
                }
            }
            
            //職種
            if (ud.getType()!=0) {
                if(!flag){
                    sql += " WHERE type like ?";
                    type = 1;
                }else{
                    sql += " AND type like ?";
                    type = 1;
                }
            }
            
            st =  con.prepareStatement(sql);
            
            //フォーム未入力の際に？に入れる値
            if(name == 1){
            st.setString(i, "%"+ud.getName()+"%");
            i++;
            }
            if(birth == 1){
            st.setString(i, "%"+new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");
            i++;
            }
            if(type == 1){
                st.setInt(i, ud.getType());
            }
            
            ResultSet rs = st.executeQuery();
            
            
            ArrayList<UserDataDTO> al = new ArrayList<UserDataDTO>();
            while(rs.next()){
            UserDataDTO resultUd = new UserDataDTO();
            resultUd.setUserID(rs.getInt(1));
            resultUd.setName(rs.getString(2));
            resultUd.setBirthday(rs.getDate(3));
            resultUd.setTell(rs.getString(4));
            resultUd.setType(rs.getInt(5));
            resultUd.setComment(rs.getString(6));
            resultUd.setNewDate(rs.getTimestamp(7));
            al.add(resultUd);
            }
            System.out.println("search completed");

            return al;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
    
    /**
     * ユーザーIDによる1件のデータの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 検索結果
     */
    public UserDataDTO searchByID(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            
            String sql = "SELECT * FROM user_t WHERE userID = ?";
            
            st =  con.prepareStatement(sql);
            st.setInt(1, ud.getUserID());
            
            ResultSet rs = st.executeQuery();
            rs.next();
            UserDataDTO resultUd = new UserDataDTO();
            resultUd.setUserID(rs.getInt(1));
            resultUd.setName(rs.getString(2));
            resultUd.setBirthday(rs.getDate(3));
            resultUd.setTell(rs.getString(4));
            resultUd.setType(rs.getInt(5));
            resultUd.setComment(rs.getString(6));
            resultUd.setNewDate(rs.getTimestamp(7));
            
            System.out.println("searchByID completed");

            return resultUd;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
    /**
     * ユーザーIDによる1件のデータの更新処理を行う。update用のメソッド。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 更新結果
     */
    public UserDataDTO update(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            
            String sql = "UPDATE user_t SET name=?, birthday=?, tell=?, type=?, comment=?, newDate=? WHERE userID=?";
            
            st =  con.prepareStatement(sql);
            st.setString(1, ud.getName());
            st.setDate(2, new java.sql.Date(ud.getBirthday().getTime()));
            st.setString(3, ud.getTell());
            st.setInt(4, ud.getType());
            st.setString(5, ud.getComment());
            st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            st.setInt(7, ud.getUserID());

            st.executeUpdate();
            
            //searchByIDメソッドで変更後の情報を表示
            UserDataDTO resultUd = new UserDataDTO();
            resultUd = this.searchByID(ud);
            
            System.out.println("update completed");

            return resultUd;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
            
        }finally{
            if(con != null){
                con.close();
                
            }
        }
    }
    
    /**
     * ユーザーIDによる1件のデータの物理削除処理を行う。delete用メソッド。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 無し
     */
    public void delete(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            
            String sql = "DELETE FROM user_t WHERE userID=?";
            
            st =  con.prepareStatement(sql);
            st.setInt(1, ud.getUserID());
            
            st.executeUpdate();
            
            System.out.println("delete completed");
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
            
        }finally{
            if(con != null){
                con.close();
                
            }
        }
    }
    
}