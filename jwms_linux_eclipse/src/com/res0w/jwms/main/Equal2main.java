/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * ͬ�۵�����
 * 
 */
public class Equal2main {

    private String year;
    private String month;
    private String day;
    private String id;
    private String info;
    private String color = "";
    private String size = "";
    private String inStore;//����ֿ� getin store
    private String outStore;//�����ֿ� getout store
    private String amount;
    private String others = "";
    private String num;
    private String inPrice;
    private String outPrice;
    private String date;
//¼����Ϣ

    public void test() {
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(id);
        System.out.println(inStore);
        System.out.println(info);
        System.out.println(outStore);
        System.out.println(amount);
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

    public void setINStore(String text) {
        inStore = text;
    }

    public void setOUTStore(String text) {
        outStore = text;
    }

    public void setAmount(String text) {
        amount = text;
    }

    public void setNum(String text) {
        num = text;
    }

    public void setOthers(String text) {
        others = text;
    }
    public void setDate(String text){
        date=text;
    }

    public void transmit() {
        try {
            boolean result;
            com.res0w.jwms.method.AddDel mainT = new AddDel();
            //�Կ�������

            mainT.setAmount(amount);
            mainT.setColor(color);
            mainT.setInfo(info);
            mainT.setSize(size);
            mainT.setStore(outStore);
            result = mainT.decreaseMethod();
            if (result == true) {
                mainT.setStore(inStore);
                boolean result1 = mainT.isInfoExist(info, inStore);
                if (result1 == false) {
                    ResultSet rs = null;
                    DBOperation findMain = new DBOperation();
                    findMain.DBConnect();
                    String sqll = "select distinct inPrice,outPrice from maint where info='" + info + "'";
                    try {
                        rs = findMain.DBSqlQuery(sqll);
                    } catch (SQLException ex) {
                        Logger.getLogger(Equal2main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        while (rs.next()) {
                            inPrice = rs.getString(1);
                            outPrice = rs.getString(2);
                            break;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Equal2main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    findMain.DBClosed();
                    mainT.setInPrice(inPrice);
                    mainT.setOutPrice(outPrice);
                }
                mainT.increaseMethod();
              DBOperation t2Equal = new DBOperation();
                t2Equal.DBConnect();
                String sql;
                sql = "insert into equalt values('" + id + "','" + year + "','" + month + "','" + day + "','" + info + "','" + amount + "','" + color + "','" + size + "','" + inStore + "','" + outStore + "','" + others + "','" + num + "','" + date + "')";
                t2Equal.DBSqlExe(sql);
                t2Equal.DBClosed();
            } else {
                JOptionPane.showMessageDialog(null, "����������Ʒ��'" + info + "'���Ϊ��򲻴��ڣ�");
                equalFrame.setExTag(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Equal2main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "����δ��д�룬�����Ƿ���ȷ�������ݿ⣬����������������������ϵ");
            equalFrame.setExTag(1);
        }
    }
}
