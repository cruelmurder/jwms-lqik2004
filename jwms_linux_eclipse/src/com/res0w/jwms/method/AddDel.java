/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.res0w.jwms.method;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lqik2004
 */
public class AddDel {

    private String info;
    private String color = "";
    private String size = "";
    private String inPrice;
    private String outPrice;
    private String store;
    private String amount;
    //get some necessary information

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
    //�ж����ӻ��߼��������ݿ���Ϣ�Ƿ����
    //Ϊ�˸������Ӳֿ⡱�ṩ�жϷ�������ǿ���жϷ�������

    public boolean isStoreExist(String store) throws SQLException {
        boolean result = false;
        String x = store;
        DBOperation isInfoExistDb = new DBOperation();
        isInfoExistDb.DBConnect();
        String sql = "select * from storet where store='" + x + "'";
        ResultSet rs = isInfoExistDb.DBSqlQuery(sql);
        if (rs.next()) {
            result = true;
        }
        isInfoExistDb.DBClosed();
        return result;
    }

    public boolean isInfoExist(String info, String store) throws SQLException {
        boolean result = false;
        String x = info;
        String y = store;
        DBOperation isInfoExistDb = new DBOperation();
        isInfoExistDb.DBConnect();
        String sql = "select * from maint where store='" + y + "' and info='" + x + "'";
        ResultSet rs = isInfoExistDb.DBSqlQuery(sql);
        if (rs.next()) {
            result = true;
        }
        isInfoExistDb.DBClosed();
        return result;
    }
    //core method

    /**
     * ���ӿ��
     * @throws java.sql.SQLException
     */
    public void increaseMethod() throws SQLException {
        boolean result = isInfoExist(info, store);
        DBOperation increaseDb = new DBOperation();
        if (result) {
            increaseDb.DBConnect();
            //update maint set amount=amount+"amount" where info="info" and store="store"
            String sql = "update maint set amount=amount+'" + amount + "' where info='" + info + "' and store='" + store + "'";
            increaseDb.DBSqlExe(sql);
            increaseDb.DBClosed();
        } else {
            increaseDb.DBConnect();
            //update maint set amount=amount+"amount" where info="info" and store="store"
            String sql = "insert into maint(info,amount,store,inPrice,outPrice) values('" + info + "','" + amount + "','" + store + "','" + inPrice + "','" + outPrice + "')";
            increaseDb.DBSqlExe(sql);
            increaseDb.DBClosed();
        }
    }
    //Ӧ���ŵ�ģ�����֮ǰ�����ж�

    /**
     * ���ٿ��
     * @return
     * @throws java.sql.SQLException
     */
    public boolean decreaseMethod() throws SQLException {
        boolean result = isInfoExist(info, store);
        ResultSet rs = null;
        DBOperation decreaseDb = new DBOperation();
        decreaseDb.DBConnect();
        String sql = "select amount from maint where info='" + info + "' and store='" + store + "'";
        rs = decreaseDb.DBSqlQuery(sql);
        if (rs.next()) {
            if (Integer.parseInt(rs.getString(1)) - Integer.parseInt(amount) < 0) {
                result = false;
            }
        } else {
            result = false;
        }
        decreaseDb.DBClosed();
        if (result) {
            decreaseDb.DBConnect();
            //update maint set amount=amount+"amount" where info="info" and store="store"
            sql = "update maint set amount=amount-'" + amount + "' where info='" + info + "' and store='" + store + "'";
            decreaseDb.DBSqlExe(sql);
            decreaseDb.DBClosed();
        }

        return result;
    }
}
