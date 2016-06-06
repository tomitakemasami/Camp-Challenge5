package jums;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ユーザー情報を持ちまわるJavaBeans
 * データベースのカラムと型に対応させている(DTO)。データの挿入、取り出しどちらにも便利
 * @version 1.00
 * @author hayashi-s
 */
public class UserDataDTO {
    private int userID;
    private String name;
    private Date birthday;
    private String tell;
    private int type;
    private String comment;
    private Timestamp newDate;
    private int year;
    private int month;
    private int day;
    
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public Date getBirthday(){
        return birthday;
    }
    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
    
    public String getTell(){
        return tell;
    }
    public void setTell(String tell){
        this.tell = tell;
    }
    
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    
    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    
    public Timestamp getNewDate() {
        return newDate;
    }
    public void setNewDate(Timestamp newDate) {
        this.newDate = newDate;
    }

    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }
    public int getMonth(){
        return month;
    }
    public void setMonth(int month){
        this.month = month;
    }
    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day = day;
    }

        //フォーム用変換
    public void UD2BeansMapping(UserDataBeans udb){
        udb.setName(this.name);
        udb.setYear(new SimpleDateFormat("yyyy").format(this.birthday));
        udb.setMonth(new SimpleDateFormat("MM").format(this.birthday));
        udb.setDay(new SimpleDateFormat("dd").format(this.birthday));
        udb.setTell(this.tell);
        udb.setType(String.valueOf(this.type));
        udb.setComment(this.comment);
    }
}
