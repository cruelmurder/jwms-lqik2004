package com.res0w.jwms.main;

import java.sql.ResultSet;
import com.res0w.jwms.method.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lqik2004
 * ���۵�/�˻���
 */
public class Sell2main {

    private String year;
    private String month;
    private String day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String inPrice;
    private String outPrice;
    private String store;
    private String amount;
    private short sellORreturn; //�ж��ǽ��������˻���
    private String others = "";
    private int num;
    private String date;
    //TEST

    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(store);
        System.out.println(info);
        System.out.println(num);
        System.out.println(amount);
        System.out.println(outPrice);
        System.out.println(others);

    }

    /**
     * �����Ϣ����
     * 12��
     */
    public void setYear(String text) {
        year = text;
    }

    public void setMonth(String text) {
        month = text;
    }

    public void setDay(String text) {
        day = text;
    }

    public void setID(String text) {
        id = text;
    }

    public void setInfo(String text) {
        info = text;
    }

    public void setColor(String text) {
        color = text;
    }

    public void setSize(String text) {
        size = text;
    }

    public void setInPrice(String text) {
        inPrice = text;
    }

    public void setOutPrice(String text) {
        outPrice = text;
    }

    public void setStore(String text) {
        store = text;
    }

    public void setAmount(String text) {
        amount = text;
    }

    public void setOthers(String text) {
        others = text;
    }

    public void setNum(String text) {
        num = Integer.parseInt(text);
    }

    public void setSellORreturn(short text) {
        sellORreturn = text;
    }
    public void setDate(String text){
        date=text;
    }

    public void transmitSell() {
        boolean result;
        try {
            AddDel mainT = new AddDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            result = mainT.decreaseMethod();
            if (result) {
                DBOperation t2Sell = new DBOperation();
                t2Sell.DBConnect();
                String sql;
                sql = "insert into sellt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + outPrice + "','" + sellORreturn + "','" + others + "','" + num + "','" + date + "')";
                System.out.println(sql);
                t2Sell.DBSqlExe(sql);
                t2Sell.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "��Ʒ:" + info + "�����ڻ���ֵΪ����");
                sellFrame.setExTag(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sell2main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "����δ��д�룬�����Ƿ���ȷ�������ݿ⣬����������������������ϵ");
            sellFrame.setExTag(1);
        }
    }

    public void transmitReturn() {
        //ȡ�ý����۸�
        ResultSet rs = null;
        DBOperation findMain = new DBOperation();
        findMain.DBConnect();
        String sqll = "select distinct inPrice from maint where info='" + info + "'";
        try {
            rs = findMain.DBSqlQuery(sqll);
        } catch (SQLException ex) {
            Logger.getLogger(Sell2main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rs.next()) {
                inPrice = rs.getString(1);
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sell2main.class.getName()).log(Level.SEVERE, null, ex);
        }
        findMain.DBClosed();
        if (inPrice == null) {
            inPrice = outPrice;
        }

        try {

            AddDel mainT = new AddDel();
            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(store);
            mainT.setInPrice(inPrice);
            mainT.setOutPrice(outPrice);
            mainT.increaseMethod();
            DBOperation t2Rturn = new DBOperation();
            t2Rturn.DBConnect();
            String sql;
            sql = "insert into sellt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + outPrice + "','" + sellORreturn + "','" + others + "','" + num + "','" + date + "')";
            t2Rturn.DBSqlExe(sql);
            t2Rturn.DBClosed();

        } catch (SQLException ex) {
            Logger.getLogger(Sell2main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "����δ��д�룬�����Ƿ���ȷ�������ݿ⣬����������������������ϵ");
            //д���쳣��Ǳ�����ʹ�ô��ڲ���ر�
            sellFrame.setExTag(1);
        }
    }
}

