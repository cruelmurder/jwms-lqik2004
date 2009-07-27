package com.res0w.jwms.main;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.res0w.jwms.method.*;

/**
 *
 * @author lqik2004
 * ����/���� ��
 */
public class Lost2main {

    private String year;
    private String month;
    private String day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String store;
    private String inPrice;
    private String amount;
    private short loseORgain;//�����ж��Ǳ����Ǳ���
    private String others = "";
    private String num;
    private String date;
    private String outPrice;
//TEST ������

    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(store);
        System.out.println(info);

        System.out.println(amount);

        System.out.println(others);

    }

    /**
     * �����Ϣ����
     * 
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
        num = text;
    }

    public void setLoseORgain(short text) {
        loseORgain = text;
    }
    public void setDate(String text){
        date=text;
    }
    //���𷽷�

    public void transmit2Lose() {
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
                DBOperation t2Lose = new DBOperation();
                t2Lose.DBConnect();
                String sql;
                sql = "insert into loset values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + loseORgain + "','" + others + "','" + num + "','" + date + "')";
                t2Lose.DBSqlExe(sql);
                t2Lose.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "������Ʒ��'" + info + "'��������ڱ���������������");
                loseFrame.setExTag(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lost2main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "����δ��д�룬�����Ƿ���ȷ�������ݿ⣬����������������������ϵ");
            loseFrame.setExTag(1);
        }
    }

    //���淽��
    public void transmit2Gain() {
        ResultSet rs = null;
        DBOperation findMain = new DBOperation();
        findMain.DBConnect();
        String sqll = "select distinct outPrice from maint where info='" + info + "'";
        try {
            rs = findMain.DBSqlQuery(sqll);
        } catch (SQLException ex) {
            Logger.getLogger(Lost2main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rs.next()) {
                outPrice = rs.getString(1);
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lost2main.class.getName()).log(Level.SEVERE, null, ex);
        }
        findMain.DBClosed();
        if (outPrice == null) {
            outPrice = inPrice;
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
            DBOperation t2Gain = new DBOperation();
            t2Gain.DBConnect();
            String sql;
            sql = "insert into loset values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + store + "','" + inPrice + "','" + loseORgain + "','" + others + "','" + num + "','" + date + "')";
            System.out.println(sql);
            t2Gain.DBSqlExe(sql);
            t2Gain.DBClosed();

        } catch (SQLException ex) {
            Logger.getLogger(Lost2main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "����δ��д�룬�����Ƿ���ȷ�������ݿ⣬����������������������ϵ");
            loseFrame.setExTag(1);
        }
    }
}
