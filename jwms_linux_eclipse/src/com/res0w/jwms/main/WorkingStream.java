package com.res0w.jwms.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.imageio.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.res0w.jwms.method.*;

/**
 *
 * @author lqik2004
 */
public class WorkingStream {

    static workingFrame frame = new workingFrame();

    public WorkingStream() {

        frame.setLocationRelativeTo(null);//һ���ô��ھ���
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static Point frameLocateOnScr() {
        return frame.getLocationOnScreen();
    }
}

@SuppressWarnings("serial")
class workingFrame extends JFrame {

    private Object[] Objyear = {
        "2009", "2010", "2011", "2012"
    };
    private Object[] Objmonth = {
        "01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12"
    };
    private Object[] Objday = {
        "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    private Object[] type = {"ȫ������", "���۵�", "������", "ͬ�۵�����", "����", "���浥", "�����˻���", "�����˻���", "�������"};
    private int WIDTH = 750;
    private int HEIGHT = 600;
    private JComboBox bYear = new JComboBox(Objyear);
    private JComboBox bMonth = new JComboBox(Objmonth);
    private JComboBox bDay = new JComboBox(Objday);
    private JComboBox eYear = new JComboBox(Objyear);
    private JComboBox eMonth = new JComboBox(Objmonth);
    private JComboBox eDay = new JComboBox(Objday);
    private JComboBox storeComboBox = new JComboBox();
    private JComboBox typeComboBox = new JComboBox(type);
    private choicePopFrame storePop = new choicePopFrame();//���òֿⵯ������
    private choicePopFrameType typePop = new choicePopFrameType();//���ò鿴���൯������
    private static List liststore = new Vector();
    private static List listtypeDB = new Vector();
    private static List listtype = new Vector();
    private static JLabel storeSelect = new JLabel();
    private static JLabel typeSelect = new JLabel();
    DefaultTableModel model1 = new model();
    JTable table1 = new JTable(model1);
    DefaultTableModel model2 = new model();
    JTable table2 = new JTable(model2);
    JLabel storeX = new JLabel();
    JLabel sumpriceX = new JLabel();
    JLabel sumvaluesX = new JLabel();

    @SuppressWarnings("empty-statement")
    public workingFrame() {
        try {
            storeLoad(); //��ȡ�ֿ���Ϣ
        } catch (SQLException ex) {
            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTitle("��Ӫ����");
        setSize(WIDTH, HEIGHT);
        imagePanel panel = new imagePanel();
        JLabel label1 = new JLabel("�ӣ�");
        bYear.setSelectedIndex(GetDate.yearIndex());
        JLabel label2 = new JLabel("��");
        bMonth.setSelectedIndex(GetDate.monthIndex());
        JLabel label3 = new JLabel("��");
        bDay.setSelectedIndex(GetDate.dayIndex());
        JLabel label4 = new JLabel("��");
        bYear.setMaximumSize(bYear.getPreferredSize());
        bMonth.setMaximumSize(bMonth.getPreferredSize());
        bDay.setMaximumSize(bDay.getPreferredSize());
        JLabel label45 = new JLabel("����");
        eYear.setSelectedIndex(GetDate.yearIndex());
        JLabel label5 = new JLabel("��");
        eMonth.setSelectedIndex(GetDate.monthIndex());
        JLabel label6 = new JLabel("��");
        eDay.setSelectedIndex(GetDate.dayIndex());
        JLabel label7 = new JLabel("��");
        eYear.setMaximumSize(eYear.getPreferredSize());
        eMonth.setMaximumSize(eMonth.getPreferredSize());
        eDay.setMaximumSize(eDay.getPreferredSize());
        JButton confirm = new JButton("��ѯ");

        Box hbox1 = Box.createHorizontalBox();
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(panel);
        hbox1.add(Box.createHorizontalStrut(50));
        hbox1.add(label1);
        hbox1.add(bYear);
        hbox1.add(label2);
        hbox1.add(bMonth);
        hbox1.add(label3);
        hbox1.add(bDay);
        hbox1.add(label4);
        hbox1.add(Box.createHorizontalStrut(20));
        hbox1.add(label45);
        hbox1.add(eYear);
        hbox1.add(label5);
        hbox1.add(eMonth);
        hbox1.add(label6);
        hbox1.add(eDay);
        hbox1.add(label7);
        hbox1.add(Box.createHorizontalStrut(30));
        hbox1.add(confirm);
        hbox1.add(Box.createHorizontalGlue());
        //����ֿ�ѡ��
        JLabel labelStore = new JLabel("�ֿ⣺");
        //��properties�ж�ȡ�ֿ�����
        storeComboBox.setSelectedIndex(Integer.parseInt(PropertiesRW.proIDMakeRead("storeWorkingStream")));
        storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
        storeComboBox.setEditable(false);   //�ֿⲻ��ֱ���޸�
        //״̬��ǩ
        JLabel states = new JLabel("��ѡ�ֿ⣺");
        Box hbox2 = Box.createHorizontalBox();
        hbox2.add(Box.createHorizontalStrut(5));
        hbox2.add(labelStore);
        hbox2.add(storeComboBox);
        hbox2.add(Box.createHorizontalStrut(30));
        hbox2.add(states);
        hbox2.add(storeSelect);
        hbox2.add(Box.createHorizontalGlue());

        JLabel labelType = new JLabel("�������ͣ�");
        JLabel stateType = new JLabel("��ѡ�������ͣ�");
        typeComboBox.setMaximumSize(typeComboBox.getPreferredSize());
        //typeComboBox.setMinimumSize(getPreferredSize());
        Box hbox3 = Box.createHorizontalBox();
        hbox3.add(Box.createHorizontalStrut(5));
        hbox3.add(labelType);
        hbox3.add(typeComboBox);
        hbox3.add(Box.createHorizontalStrut(30));
        hbox3.add(stateType);
        hbox3.add(typeSelect);
        hbox3.add(Box.createHorizontalGlue());
        //�����
        Object[] colName1 = new Object[3];
        colName1[0] = "����";
        colName1[1] = "ID";
        colName1[2] = "��������";
        model1.setColumnCount(3);
        model1.setRowCount(0);
        model1.setColumnIdentifiers(colName1);//��������
        table1.getColumnModel().getColumn(0).setPreferredWidth(70);
        table1.getColumnModel().getColumn(1).setPreferredWidth(100);
        table1.getColumnModel().getColumn(2).setPreferredWidth(90);
        JScrollPane panel1 = new JScrollPane(table1);
        panel1.setPreferredSize(new Dimension(300, 400));
        table1.setDefaultRenderer(Object.class, new ColorRenderer());
        //table1.setSelectionBackground(Color.RED);
        table1.setShowHorizontalLines(false);
        table1.setShowVerticalLines(false);
        Object[] colName2 = new Object[3];
        colName2[0] = "����";
        colName2[1] = "ID";
        colName2[2] = "��������";
        model2.setColumnCount(3);
        model2.setRowCount(0);
        TableColumnModel tc2 = table2.getColumnModel();
        tc2.getColumn(0).setPreferredWidth(20);
        tc2.getColumn(0).setPreferredWidth(25);
        tc2.getColumn(0).setPreferredWidth(15);
        model2.setColumnIdentifiers(colName1);//��������
        table2.setDefaultRenderer(Object.class, new ColorRenderer());
        table2.setShowHorizontalLines(false);
        JScrollPane panel2 = new JScrollPane(table2);
        panel2.setPreferredSize(new Dimension(450, 400));
        Box hbox4 = Box.createHorizontalBox();
        hbox4.add(Box.createHorizontalStrut(5));
        hbox4.add(panel1);
        hbox4.add(Box.createHorizontalStrut(30));
        Box vhbox4 = Box.createVerticalBox();
        hbox4.add(vhbox4);
        JLabel store = new JLabel("�ֿ⣺");

        JLabel sumprice = new JLabel("�ϼƽ�");

        JLabel sumvalues = new JLabel("�ϼ�������");

        Box h4 = Box.createHorizontalBox();
        h4.add(store);
        h4.add(storeX);
        h4.add(Box.createHorizontalGlue());
        h4.add(sumvalues);
        h4.add(sumvaluesX);
        h4.add(Box.createHorizontalStrut(20));
        h4.add(sumprice);
        h4.add(sumpriceX);
        vhbox4.add(h4);
        vhbox4.add(panel2);

        //hbox4.add(Box.createHorizontalStrut(400));
        hbox4.add(Box.createHorizontalGlue());
        //��ֱ����
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox4);
        vbox.add(Box.createVerticalGlue());
        //��ʾ��ʽ����
        add(vbox, BorderLayout.NORTH);

        storeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (storeComboBox.getSelectedItem() == "�������...") {
                    Point point = WorkingStream.frameLocateOnScr();
                    storePop.setLocation(point.x - 100, point.y + 30);//���ô���ͣ�����Զ����������������
                    storePop.setSize(100, 300);
                    storePop.setUndecorated(true);//���ر�����
                    storePop.setVisible(true);
                } else {//ʵ�ֵ������ڵ��Զ��رպʹ�
                    storePop.dispose();
                    setStoreSelected(storeComboBox.getSelectedItem().toString().trim());
                }
            }
        });
        typeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (typeComboBox.getSelectedItem() == "�������") {
                    Point point = WorkingStream.frameLocateOnScr();
                    typePop.setLocation(point.x - 100, point.y + 60);//���ô���ͣ�����Զ����������������
                    typePop.setSize(100, 300);
                    typePop.setUndecorated(true);//���ر�����
                    typePop.setVisible(true);
                } else {//ʵ�ֵ������ڵ��Զ��رպʹ�
                    typePop.dispose();
                    setTypeSelected(typeComboBox.getSelectedItem().toString().trim());
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String keyWord;
                String keyStore;
                String sql = null;
                ResultSet rs = null;
                if (e.getButton() == MouseEvent.BUTTON1) {// ����������
                    if (e.getClickCount() == 2) {
                        /*for (int j = table2.getRowCount() - 1; j >= 0; j--) {
                        for (int i = table2.getColumnCount() - 1; i >= 0; i--) {
                        table2.setValueAt("", j, i);
                        }
                        }*/
                        model2.setRowCount(0);
                        table2.repaint();
                        keyWord = table1.getModel().getValueAt(table1.getSelectedRow(), 1).toString().trim();
                        if (keyWord.startsWith("S")) {
                            Object[] colName = new Object[6];
                            colName[0] = "���";
                            colName[1] = "��Ʒ����";
                            colName[2] = "����";
                            colName[3] = "����";
                            colName[4] = "���";
                            colName[5] = "��ע";
                            model2.setColumnCount(6);
                            model2.setColumnIdentifiers(colName);
                            keyStore = "sellt";
                            sql = "select num,info,amount,outprice,others,store from " + keyStore + " where id='" + keyWord + "'order by num";
                            System.out.print(sql);
                            DBOperation st = new DBOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//�ϼ�����
                                float sum2 = 0;//�ϼƽ��
                                while (rs.next()) {
                                    Object[] data = new Object[6];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    float sum = Float.parseFloat(rs.getString(3).trim()) * Float.parseFloat(rs.getString(4).trim());
                                    data[3] = rs.getString(4).trim();
                                    data[4] = sum;
                                    data[5] = rs.getString(5).trim();
                                    model2.addRow(data);
                                    storeX.setText(rs.getString(6).trim());
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                    sum2 = sum2 + sum;

                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText(String.valueOf(sum2));
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();
                        } else if (keyWord.startsWith("E")) {
                            Object[] colName = new Object[4];
                            colName[0] = "���";
                            colName[1] = "��Ʒ����";
                            colName[2] = "����";
                            colName[3] = "��ע";
                            model2.setColumnCount(4);
                            model2.setColumnIdentifiers(colName);
                            keyStore = "equalt";
                            sql = "select num,info,amount,others,inStore,outStore from " + keyStore + " where id='" + keyWord + "' order by num";
                            System.out.print(sql);
                            DBOperation st = new DBOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//�ϼ�����
                                float sum2 = 0;//�ϼƽ��
                                while (rs.next()) {
                                    Object[] data = new Object[4];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    data[3] = rs.getString(4).trim();
                                    model2.addRow(data);
                                    storeX.setText("'" + rs.getString(5) + "' ��  '" + rs.getString(6) + "' ��");
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText("NULL");
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();


                        } else if (keyWord.startsWith("L")) {
                            keyStore = "loset";
                            Object[] colName = new Object[6];
                            colName[0] = "���";
                            colName[1] = "��Ʒ����";
                            colName[2] = "����";
                            colName[3] = "����";
                            colName[4] = "���";
                            colName[5] = "��ע";
                            model2.setColumnCount(6);
                            model2.setColumnIdentifiers(colName);

                            sql = "select num,info,amount,inprice,others,store from " + keyStore + " where id='" + keyWord + "'order by num";
                            System.out.print(sql);
                            DBOperation st = new DBOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//�ϼ�����
                                float sum2 = 0;//�ϼƽ��
                                while (rs.next()) {
                                    Object[] data = new Object[6];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    float sum = Float.parseFloat(rs.getString(3).trim()) * Float.parseFloat(rs.getString(4).trim());
                                    data[3] = rs.getString(4).trim();
                                    data[4] = sum;
                                    data[5] = rs.getString(5).trim();
                                    model2.addRow(data);
                                    storeX.setText(rs.getString(6).trim());
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                    sum2 = sum2 + sum;
                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText(String.valueOf(sum2));
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();
                        } else if (keyWord.startsWith("I")) {
                            keyStore = "inputt";
                            Object[] colName = new Object[6];
                            colName[0] = "���";
                            colName[1] = "��Ʒ����";
                            colName[2] = "����";
                            colName[3] = "��ⵥ��";
                            colName[4] = "���ۼ�";
                            colName[5] = "�ϼƽ��";
                            model2.setColumnCount(6);
                            model2.setColumnIdentifiers(colName);

                            sql = "select num,info,amount,inprice,outPrice,sumprice,store from " + keyStore + " where id='" + keyWord + "'order by num";
                            System.out.print(sql);
                            DBOperation st = new DBOperation();
                            st.DBConnect();
                            try {
                                int i = 0;
                                rs = st.DBSqlQuery(sql);
                                int sum1 = 0;//�ϼ�����
                                float sum2 = 0;//�ϼƽ��
                                while (rs.next()) {
                                    Object[] data = new Object[6];
                                    data[0] = rs.getString(1).trim();
                                    data[1] = rs.getString(2).trim();
                                    data[2] = rs.getString(3).trim();
                                    float sum = Float.parseFloat(rs.getString(3).trim()) * Float.parseFloat(rs.getString(4).trim());
                                    data[3] = rs.getString(4).trim();
                                    data[4] = rs.getString(5).trim();
                                    data[5] = sum;
                                    model2.addRow(data);
                                    storeX.setText(rs.getString(7).trim());
                                    sum1 = sum1 + Integer.parseInt(data[2].toString().trim());
                                    sum2 = sum2 + sum;

                                }
                                sumvaluesX.setText(String.valueOf(sum1));
                                sumpriceX.setText(String.valueOf(sum2));
                            } catch (SQLException ex) {
                                Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            st.DBClosed();
                        }

                    /*
                    int colummCount = table1.getModel().getColumnCount();// ����
                    for (int i = 0; i < colummCount; i++) {
                    System.out.print(table1.getModel().getValueAt(table1.getSelectedRow(), i).toString() + " ");
                    }
                    System.out.println();
                     * */
                    }
                }
            }
        });
        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                /*for (int j = table1.getRowCount() - 1; j >= 0; j--) {
                for (int i = table1.getColumnCount() - 1; i >= 0; i--) {
                table1.setValueAt("", j, i);
                }
                }*/
                model1.setRowCount(0);
                table1.repaint();
                DBOperation init = new DBOperation();
                init.DBConnect();
                String sqlinit;
                sqlinit = "delete from StreamCache";
                try {
                    init.DBSqlExe(sqlinit);
                } catch (SQLException ex) {
                    Logger.getLogger(DBOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
                init.DBClosed();
                String byear = bYear.getSelectedItem().toString().trim();
                String bmonth = bMonth.getSelectedItem().toString().trim();
                String bday = bDay.getSelectedItem().toString().trim();
                String eyear = eYear.getSelectedItem().toString().trim();
                String emonth = eMonth.getSelectedItem().toString().trim();
                String eday = eDay.getSelectedItem().toString().trim();
                int dayValue = (Integer.parseInt(eyear) - Integer.parseInt(byear)) * 371 + (Integer.parseInt(emonth) - Integer.parseInt(bmonth)) * 31 + (Integer.parseInt(eday) - Integer.parseInt(bday));
                final ProgressBarDialog proBar = new ProgressBarDialog();
                Point point = WorkingStream.frameLocateOnScr();
                proBar.setLocation(point.x + 300, point.y + 320);//���ô���ͣ�����Զ����������������
                proBar.adoptDeterminate(dayValue);

                new Thread() {

                    @Override
                    public void run() {
                        ResultSet rs = null;
                        String sql;
                        String store = null;
                        String byear = bYear.getSelectedItem().toString().trim();
                        String bmonth = bMonth.getSelectedItem().toString().trim();
                        String bday = bDay.getSelectedItem().toString().trim();
                        String eyear = eYear.getSelectedItem().toString().trim();
                        String emonth = eMonth.getSelectedItem().toString().trim();
                        String eday = eDay.getSelectedItem().toString().trim();
                        DBOperation stable = new DBOperation();
                        stable.DBConnect();
                        String bDate=byear+bmonth+bday;
                        String eDate=eyear+emonth+eday;

                        if (typeComboBox.getSelectedItem().toString().trim() == "�������") {
                            int proBarValues = 0;
                            for (int i = 0; i < listtypeDB.size(); i++) {
                                if (storeComboBox.getSelectedItem().toString().trim() == "�������...") {
                                    store = liststore.get(0).toString().trim();
                                    for (int k = 1; k < liststore.size(); k++) {
                                        store = store + "' or store= '" + liststore.get(k).toString().trim();
                                    }

                                    sql = "select distinct date,id from " + listtypeDB.get(i) + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"' ";
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            DBOperation tem = new DBOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + listtype.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else if (storeComboBox.getSelectedItem().toString().trim() == "ȫ���ֿ�") {
                                    sql = "select distinct date,id from " + listtypeDB.get(i) + "  date<='"+eDate+"' and date>='"+bDate+"' ";
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);

                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + listtype.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            stable.DBSqlExe(s);

                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else {
                                    store = storeComboBox.getSelectedItem().toString().trim();
                                    sql = "select distinct date,id from " + listtypeDB.get(i) + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);

                                        while (rs.next()) {
                                            DBOperation tem = new DBOperation();
                                            tem.DBConnect();
                                            String date = rs.getString(1).substring(0, 8);
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + listtype.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }


                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                }
                            }
                        } else if (typeComboBox.getSelectedItem().toString().trim() == "ȫ������") {
                            int proBarValues = 0;
                            List type = new Vector();
                            type.add("���۵�");
                            type.add("sellt where sellORreturn=0 and");
                            type.add("�����˻���");
                            type.add("sellt where sellORreturn=1 and");
                            type.add("�����˻���");
                            type.add("inputt where inputORreturn=1 and");
                            type.add("������");
                            type.add("inputt where inputORreturn=0 and");
                            type.add("����");
                            type.add("loset where loseORgain=0 and");
                            type.add("���浥");
                            type.add("loset where loseORgain=1 and");
                            type.add("ͬ�۵�����");
                            type.add("equalt where");
                            for (int i = 0; i < type.size(); i = i + 2) {
                                if (storeComboBox.getSelectedItem().toString().trim() == "�������...") {
                                    //��������֡�ͬ�۵���������ʱ��sql���ᷢ���仯�����Բ����˷�֧
                                    if (type.get(i) == "ͬ�۵�����") {
                                        store = "instore=  '" + liststore.get(0).toString().trim() + "' or outstore= '" + liststore.get(0).toString().trim() + "'";
                                        for (int k = 1; k < liststore.size(); k++) {
                                            store += "or instore= '" + liststore.get(k).toString().trim() + "' or outstore= '" + liststore.get(k).toString().trim() + "'";
                                        }
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (" + store + ") and date<'"+eDate+"' and date>'"+bDate+"'  ";
                                    } else {
                                        store = liststore.get(0).toString().trim();
                                        for (int k = 1; k < liststore.size(); k++) {
                                            store = store + "' or store= '" + liststore.get(k).toString().trim();
                                        }
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    }
                                    System.out.println(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            DBOperation tem = new DBOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + type.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else if (storeComboBox.getSelectedItem().toString().trim() == "ȫ���ֿ�") {
                                    sql = "select distinct date,id from " + type.get(i + 1) + "  date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    //System.out.println(sql);
                                    //System.out.println(syear + smonth + sday);
                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                        String date = rs.getString(1);
                                            DBOperation tem = new DBOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + type.get(i).toString().trim() + "')";
                                            //System.out.println(s);

                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                } else {
                                    store = storeComboBox.getSelectedItem().toString().trim();
                                    if (type.get(i) == "ͬ�۵�����") {
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (instore='" + store + "' or outstore='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    } else {
                                        sql = "select distinct date,id from " + type.get(i + 1) + " (store='" + store + "') and date=<'"+eDate+"' and date>='"+bDate+"'  ";
                                    }
                                    System.out.print(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            DBOperation tem = new DBOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + type.get(i).toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                }

                            }
                        } else {
                            int proBarValues = 0;
                            String type = null;
                            if (typeComboBox.getSelectedItem().toString().trim() == "���۵�") {
                                type = "sellt where sellORreturn=0 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "�����˻���") {
                                type = "sellt where sellORreturn=1 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "�����˻���") {
                                type = "inputt where inputORreturn=1 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "������") {
                                type = "inputt where inputORreturn=0 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "����") {
                                type = "loset where loseORgain=0 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "���浥") {
                                type = "loset where loseORgain=1 and";
                            } else if (typeComboBox.getSelectedItem().toString().trim() == "ͬ�۵�����") {
                                type = "equalt where ";
                            }
                            if (storeComboBox.getSelectedItem().toString().trim() == "�������...") {
                                if (type == "equalt where ") {
                                    store = "instore=  '" + liststore.get(0).toString().trim() + "' or outstore= '" + liststore.get(0).toString().trim() + "'";
                                    for (int k = 1; k < liststore.size(); k++) {
                                        store += "or instore= '" + liststore.get(k).toString().trim() + "' or outstore= '" + liststore.get(k).toString().trim() + "'";
                                    }
                                    sql = "select distinct date,id from eqult where (" + store + ") and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                } else {
                                    store = liststore.get(0).toString().trim();
                                    for (int k = 1; k < liststore.size(); k++) {
                                        store = store + "' or store= '" + liststore.get(k).toString().trim();
                                    }
                                    sql = "select distinct date,id from " + type + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                    System.out.println(sql);

                                    try {
                                        rs = stable.DBSqlQuery(sql);
                                        while (rs.next()) {
                                            String date = rs.getString(1);
                                            DBOperation tem = new DBOperation();
                                            tem.DBConnect();
                                            String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + typeComboBox.getSelectedItem().toString().trim() + "')";
                                            System.out.print(s);
                                            tem.DBSqlExe(s);
                                            tem.DBClosed();
                                        }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    proBar.setValue(proBarValues++);
                                }

                            } else if (storeComboBox.getSelectedItem().toString().trim() == "ȫ���ֿ�") {
                                sql = "select distinct date,id from " + type + "  date<='"+eDate+"' and date>='"+bDate+"'  ";
                                System.out.print(sql);

                                try {
                                    rs = stable.DBSqlQuery(sql);
                                    while (rs.next()) {
                                        String date = rs.getString(1);
                                        DBOperation tem = new DBOperation();
                                        tem.DBConnect();
                                        String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + typeComboBox.getSelectedItem().toString().trim() + "')";
                                        System.out.print(s);
                                        tem.DBSqlExe(s);
                                        tem.DBClosed();
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                proBar.setValue(proBarValues++);

                            } else {
                                store = storeComboBox.getSelectedItem().toString().trim();

                                if (type == "equalt where ") {
                                    sql = "select distinct date,id from " + type + " (instore='" + store + "' or outstore='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                } else {
                                    sql = "select distinct date,id from " + type + " (store='" + store + "') and date<='"+eDate+"' and date>='"+bDate+"'  ";
                                }
                                System.out.print(sql);

                                try {
                                    rs = stable.DBSqlQuery(sql);
                                    while (rs.next()) {
                                        String date = rs.getString(1);
                                        DBOperation tem = new DBOperation();
                                        tem.DBConnect();
                                        String s = "insert into StreamCache values('" + rs.getString(1) + "','" + rs.getString(2) + "','" + date + "','" + typeComboBox.getSelectedItem().toString().trim() + "')";
                                        System.out.print(s);
                                        tem.DBSqlExe(s);
                                        tem.DBClosed();
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                proBar.setValue(proBarValues++);
                            }
                        }
                        stable.DBClosed();
                        ResultSet RS = null;
                        DBOperation c = new DBOperation();
                        c.DBConnect();
                        sql = "select date,id,type from StreamCache order by date ASC";
                        try {
                            RS = c.DBSqlQuery(sql);
                            while (RS.next()) {
                                Object[] data = new Object[3];
                                data[0] = RS.getString(1).substring(0, 8).trim();
                                data[1] = RS.getString("id").trim();
                                data[2] = RS.getString("type").trim();
                                model1.addRow(data);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(workingFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c.DBClosed();
                        proBar.finishDeterminate();
                    }
                }.start();
                proBar.setVisible(true);
            }
        });

    }

    private void storeLoad() throws SQLException {
        storeComboBox.addItem("ȫ���ֿ�");
        DBOperation storeLoad = new DBOperation();
        storeLoad.DBConnect();
        String sql = "select store from storet";
        ResultSet rs = null;
        try {
            try {
                rs = storeLoad.DBSqlQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (rs.next()) {
                storeComboBox.addItem(rs.getString(1).trim());
                storePop.cb.addItem(rs.getString(1).trim());
            }
            storePop.cb.addItem(null);
            storeLoad.DBClosed();
        } catch (SQLException ex) {
            Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        storeComboBox.addItem("�������...");
    }
//���Ӵ��ڰѲֿ�ѡ����Ϣ���ݻ���

    public static void setListStore(String x) {
        if (x == "clear") {
            liststore.clear();
        } else {
            liststore.add(x);
        }

    }

    public static void setStoreSelected(String x) {
        storeSelect.setText(x);
    }

    public static void setTypeSelected(String x) {
        typeSelect.setText(x);
    }

    public static void setListTypeDB(String x) {
        if (x == "clear") {
            listtypeDB.clear();
        } else {
            listtypeDB.add(x);
        }

    }

    public static void setListType(String x) {
        if (x == "clear") {
            listtype.clear();
        } else {
            listtype.add(x);
        }






    }
}

class imagePanel extends JPanel {

    private Image image;

    public imagePanel() {
        setPreferredSize(new Dimension(153, 54));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        try {
            image = ImageIO.read(new File("image"+PropertiesRW.proIDMakeRead("osname")+"logo.gif"));
        } catch (IOException ex) {
            Logger.getLogger(imagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            return;
        }
        g.drawImage(image, 0, 0, null);
    }
}

class choicePopFrame extends JFrame {

    public JComboBox cb;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public choicePopFrame() {
        Object[] colName = new Object[1];
        colName[0] = "����ֿ����...";
        model.setColumnCount(1);
        model.setRowCount(7);
        model.setColumnIdentifiers(colName);//��������
        table.setRowHeight(30);
        cb = new JComboBox();
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn popColumn = columnModel.getColumn(0);
        popColumn.setCellEditor(new DefaultCellEditor(cb));
        JScrollPane panel = new JScrollPane(table);
        panel.setPreferredSize(new Dimension(100, 200));
        JButton confirmBt = new JButton("ȷ��");

        Box vbox = Box.createVerticalBox();
        vbox.add(panel);
        vbox.add(confirmBt);
        add(vbox, BorderLayout.CENTER);

        confirmBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                workingFrame.setListStore("clear");
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        workingFrame.setListStore(model.getValueAt(i, 0).toString());
                        temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                    }
                }
                System.out.print(temStoreSelect);
                workingFrame.setStoreSelected(temStoreSelect);
                dispose();
            }
        });
    }
}

class model extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

class choicePopFrameType extends JFrame {

    public JComboBox cb;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public choicePopFrameType() {
        Object[] colName = new Object[1];
        colName[0] = "���൥�����...";
        Object[] type = {"���۵�", "������", "ͬ�۵�����", "����", "���浥", "�����˻���", "�����˻���", ""}; //�����������
        model.setColumnCount(1);
        model.setRowCount(8);
        model.setColumnIdentifiers(colName);//��������
        table.setRowHeight(30);
        cb = new JComboBox(type);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn popColumn = columnModel.getColumn(0);
        popColumn.setCellEditor(new DefaultCellEditor(cb));
        JScrollPane panel = new JScrollPane(table);
        panel.setPreferredSize(new Dimension(100, 200));
        JButton confirmBt = new JButton("ȷ��");

        Box vbox = Box.createVerticalBox();
        vbox.add(panel);
        vbox.add(confirmBt);
        add(vbox, BorderLayout.CENTER);

        confirmBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                workingFrame.setListTypeDB("clear");
                workingFrame.setListType("clear");
                String temStoreSelect = "";
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (model.getValueAt(i, 0) != null) {
                        if (model.getValueAt(i, 0).toString() != "") {      //�жϵ�ѡ�Ϊ�յ�ʱ��Ž����жϲ��������Ϊ���򲻲���
                            if (model.getValueAt(i, 0).toString() == "���۵�") {
                                workingFrame.setListTypeDB("sellt where sellORreturn=0 and");
                            } else if (model.getValueAt(i, 0).toString() == "�����˻���") {
                                workingFrame.setListTypeDB("sellt where sellORreturn=1 and");
                            } else if (model.getValueAt(i, 0).toString() == "�����˻���") {
                                workingFrame.setListTypeDB("inputt where inputORreturn=1 and");
                            } else if (model.getValueAt(i, 0).toString() == "������") {
                                workingFrame.setListTypeDB("inputt where inputORreturn=0 and");
                            } else if (model.getValueAt(i, 0).toString() == "����") {
                                workingFrame.setListTypeDB("loset where loseORgain=0 and");
                            } else if (model.getValueAt(i, 0).toString() == "���浥") {
                                workingFrame.setListTypeDB("loset where loseORgain=1 and");
                            } else if (model.getValueAt(i, 0).toString() == "ͬ�۵�����") {
                                workingFrame.setListTypeDB("equalt where ");
                            }
                            workingFrame.setListType(model.getValueAt(i, 0).toString().trim());
                            temStoreSelect = temStoreSelect + model.getValueAt(i, 0).toString().trim() + "  ";
                        }
                    }
                }
                System.out.print(temStoreSelect);
                workingFrame.setTypeSelected(temStoreSelect);
                dispose();
            }
        });
    }
}