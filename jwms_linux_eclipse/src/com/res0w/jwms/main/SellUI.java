package com.res0w.jwms.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import com.res0w.jwms.method.*;

/**
 * 
 * @author lqik2004
 */
public class SellUI {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		// TODO code application logic here
		sellFrame frame = new sellFrame();
		/**
		 * Toolkit tool = Toolkit.getDefaultToolkit(); Dimension screenSize =
		 * tool.getScreenSize(); int locateHeight = (screenSize.height -
		 * frame.getHeight()) / 2; int locateWidth = (screenSize.width -
		 * frame.getWidth()) / 2; frame.setLocation(locateWidth, locateHeight);
		 */
		frame.setLocationRelativeTo(null);// һ���ô��ھ���
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class sellFrame extends JFrame {

	private Object[] Objyear = { "2009", "2010", "2011", "2012" };
	private Object[] Objmonth = { "01", "02", "03", "04", "05", "06", "07",
			"09", "10", "11", "12" };
	private Object[] Objday = { "01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31" };
	private short sellORreturn;
	private int tagrow = 0;
	int sumvalues = 0;
	float sumprice = 0;
	Object[] items = null;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 647;
	private JComboBox NameCombo;
	private JTextField ID = new JTextField(12);// x20090330*** ��12λ
	private JComboBox year = new JComboBox(Objyear);// �������Զ�ѡ��ʱ�书��
	private JComboBox month = new JComboBox(Objmonth);
	private JComboBox day = new JComboBox(Objday);
	private JComboBox storeComboBox = new JComboBox();
	private TableModel model = new PlanetTableModel();
	private JTable table = new JTable(model);
	private JTextField sumPrice = new JTextField(6);// �ܼƽ�����6λ������С�����С�����һλ
	private JTextField sumValues = new JTextField(3);
	private static int exceptionTag = 0; // �쳣��ǣ��������û����ȷд����Ϣ�����ݿ�ͻ��дexceptionTag
	// ʹ��table�еġ��ܽ�һ�п����޸ģ������޸��������ߵ��ۻ����Զ��޸�
	private String[] tableOldAmount = new String[model.getRowCount()];
	private String[] tableOldPrice = new String[model.getRowCount()];
	private String IDString = null;

	public static void setExTag(int tag) {
		exceptionTag = tag;
	}

	public sellFrame() throws Exception {
		// ��ʼ�����ݿ⣬������Ϣ
		storeLoad();// ����ֿ���Ϣ
		items = infoLoad();// ����info��Ϣ
		setTitle("�����˻���");// ���ñ���������
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);// ���ô�С
		// ���õ�ѡ��ť
		// ͬʱ������sellORreturn��ֵ��sell->0,return->1
		ButtonGroup group = new ButtonGroup();// ���ð�ť�飬��ֻ֤�ܵ�ѡ
		JRadioButton sell = new JRadioButton("����", true);
		sell.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				sellORreturn = 0;
				// System.out.println(sellORreturn);
			}
		});
		JRadioButton Return = new JRadioButton("�˻�", false);
		Return.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				sellORreturn = 1;
				// System.out.println(sellORreturn);
			}
		});
		group.add(sell);
		group.add(Return);
		// ����ID
		JLabel labelID = new JLabel("��ţ�");// ��������
		ID.setEditable(false);// �����޸�
		InputIDMake id = new InputIDMake();
		IDString = id.showID("S", GetDate.getYear().toString(), GetDate
				.getMonth().toString(), GetDate.getDay().toString());
		ID.setText(IDString); // ���ñ�ţ����۵���S��ͷ���������������
		ID.setMaximumSize(ID.getPreferredSize()); // ʹ����ʽ�����²���Ĭ��ȡ�����ֵ������Ԥ�����С
		Box hbox0 = Box.createHorizontalBox();
		hbox0.add(sell);
		hbox0.add(Return);
		hbox0.add(Box.createHorizontalGlue());
		hbox0.add(labelID);
		hbox0.add(ID);
		hbox0.add(Box.createHorizontalStrut(5));
		// ����������
		JLabel label1 = new JLabel("���ڣ�");
		year.setSelectedIndex(GetDate.yearIndex());
		JLabel label2 = new JLabel("��");
		// JTextField month = new JTextField(2);
		month.setSelectedIndex(GetDate.monthIndex());
		JLabel label3 = new JLabel("��");
		// JTextField day = new JTextField(2);
		day.setSelectedIndex(GetDate.dayIndex());
		JLabel label4 = new JLabel("��");
		year.setMaximumSize(year.getPreferredSize());
		month.setMaximumSize(month.getPreferredSize());
		day.setMaximumSize(day.getPreferredSize());
		Box hbox1 = Box.createHorizontalBox();
		hbox1.add(Box.createHorizontalStrut(5));
		hbox1.add(label1);
		hbox1.add(year);
		hbox1.add(label2);
		hbox1.add(month);
		hbox1.add(label3);
		hbox1.add(day);
		hbox1.add(label4);
		hbox1.add(Box.createHorizontalGlue());
		// ���òֿ���
		JLabel labelStore = new JLabel("�ֿ⣺");
		// ��properties�ж�ȡ�ֿ�����
		storeComboBox.setSelectedIndex(Integer.parseInt(PropertiesRW
				.proIDMakeRead("storeSell")));
		storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
		storeComboBox.setEditable(false); // �ֿⲻ��ֱ���޸�

		Box hbox2 = Box.createHorizontalBox();
		hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(labelStore);
		hbox2.add(storeComboBox);

		hbox2.add(Box.createHorizontalGlue());
		// �����б���

		table.setRowSelectionAllowed(false); // ����ѡ����
		// table.setShowHorizontalLines(false);
		addEditEvent(table); // ��table�����˶Լ��������¼���Ӧ
		// set up renderers and editors
		// table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
		// table.setDefaultEditor(Color.class, new ColorTableCellEditor());
		// ��������
		// java.util.ArrayList list = new
		// java.util.ArrayList(Arrays.asList(items));
		// Collections.sort(list);
		// JComboBox cmb = new JAutoCompleteComboBox(list.toArray());
		// Arrays.sort(items);//��item��������
		AutoCompleter.setItems(items); // ��infoLoad()��������Ϣ���ݸ�
		// �ѵ�Ԫ������JAutoCompleteComboBox
		NameCombo = new JAutoCompleteComboBox(items);
		NameCombo.addActionListener(NameCombo);

		TableColumnModel columnModel = table.getColumnModel();
		TableColumn NameColumn = columnModel.getColumn(1);
		NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ�������ʽ��δ����
		table.getColumnModel().getColumn(0).setPreferredWidth(30);// ���õ�һ���п�
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		// table.getColumnModel().getColumn(3).setPreferredWidth(6);
		table.setDefaultRenderer(Object.class, new ColorRenderer()); // ����ÿһ�еı�����ɫ
		// Ϊ����š��и���ֵ
		for (int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt(i + 1, i, 0);
		}
		JScrollPane tablePane = new JScrollPane(table);
		Box hboxPane = Box.createHorizontalBox();
		hboxPane.add(Box.createHorizontalStrut(5));
		hboxPane.add(tablePane);
		hboxPane.add(Box.createHorizontalStrut(5));
		// ���úϼ���
		JLabel labelSumPrice = new JLabel("�ܼۣ�");
		sumPrice.setEditable(false);// �����޸�
		sumPrice.setMaximumSize(ID.getPreferredSize()); // ʹ����ʽ�����²���Ĭ��ȡ�����ֵ������Ԥ�����С
		JLabel labelSumValues = new JLabel("��������");
		sumValues.setEditable(false);// �����޸�
		sumValues.setMaximumSize(ID.getPreferredSize()); // ʹ����ʽ�����²���Ĭ��ȡ�����ֵ������Ԥ�����С
		Box hbox3 = Box.createHorizontalBox();
		hbox3.add(Box.createHorizontalGlue());
		hbox3.add(labelSumValues);
		hbox3.add(sumValues);
		hbox3.add(Box.createHorizontalStrut(30));
		hbox3.add(labelSumPrice);
		hbox3.add(sumPrice);
		hbox3.add(Box.createHorizontalStrut(5));
		// �����ύ��ť
		JButton referButton = new JButton("�ύ");
		JButton exit = new JButton("�ر�");
		Box hbox4 = Box.createHorizontalBox();
		hbox4.add(Box.createHorizontalGlue());
		hbox4.add(referButton);
		hbox4.add(Box.createHorizontalStrut(50));
		hbox4.add(exit);
		hbox4.add(Box.createHorizontalStrut(15));

		// ���ô�ֱ��ʽ����
		Box vbox = Box.createVerticalBox();
		vbox.add(Box.createVerticalStrut(8));
		vbox.add(hbox0);
		vbox.add(Box.createVerticalStrut(4));
		vbox.add(hbox1);
		vbox.add(Box.createVerticalStrut(6));
		vbox.add(hbox2);
		vbox.add(Box.createVerticalStrut(8));
		vbox.add(hboxPane);
		vbox.add(Box.createVerticalStrut(4));
		vbox.add(hbox3);
		vbox.add(Box.createVerticalStrut(15));
		vbox.add(hbox4);
		vbox.add(Box.createVerticalStrut(10));
		add(vbox, BorderLayout.CENTER);
		// add(new JScrollPane(table), BorderLayout.CENTER);
		// ����Ӳֿ⡱��ť���
		/**
		 * addStore.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent e) {
		 * storeComboBox.setEditable(true);//���òֿ�JComboBox�ɱ༭
		 * storeComboBox.setSelectedIndex(-1);//��������գ�����û����� } });
		 */

		// �˳���ť���
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		// �ύ��ť���
		referButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exceptionTag = 0;// ���쳣��ǩ���г�ʼ��
				// �����ύ��ť������һ���Ի�������ȷ���Ƿ�����ύ
				// shwoConfirmDialog,����һ������ֵ���ж�ѡ������Ǹ���ť
				int ifcontinue = JOptionPane.showConfirmDialog(null, "��ȷ�ϵ��ݹ���",
						"����ȷ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ifcontinue == JOptionPane.YES_OPTION) {
					Sell2main sellBt = new Sell2main();// ����һ���µĶ������Դ������ݣ�
					InputIDMake idmk = new InputIDMake();// ���ύ��ʱ���ID��¼��timeLine��������������������
					sellBt.setYear(year.getSelectedItem().toString());
					idmk.getYear(year.getSelectedItem().toString().trim());
					sellBt.setMonth(month.getSelectedItem().toString());
					idmk.getMonth(month.getSelectedItem().toString().trim());
					sellBt.setDay(day.getSelectedItem().toString());
					idmk.getDay(day.getSelectedItem().toString().trim());
					sellBt.setStore(storeComboBox.getSelectedItem().toString());
					sellBt.setDate(idmk.date);
					sellBt.setID(idmk.alterID("S"));

					for (int i = 0; i < model.getRowCount(); i++) { // ��ֹ�����м���ֶ��ж�ʧ���ݵ�����
						if (model.getValueAt(i, 1).toString() != "") { // ����ַ���û�У���ô����д�����ݿ⣬������һ��
							sellBt.setNum(model.getValueAt(i, 0).toString());
							sellBt.setInfo(model.getValueAt(i, 1).toString());
							sellBt.setAmount(model.getValueAt(i, 2).toString());
							sellBt.setOutPrice(model.getValueAt(i, 3)
									.toString());
							sellBt.setOthers(model.getValueAt(i, 5).toString());
							sellBt.setSellORreturn(sellORreturn);
							// sellBt.test();
							// ���ݵ�ѡ��ť����Ϣ��ѡ��ʹ���ĸ�����
							if (sellORreturn == 0) {
								sellBt.transmitSell();
							} else if (sellORreturn == 1) {
								sellBt.transmitReturn();
							}
						}
					}
					// ��ID��д���ļ���
					try {
						PropertiesRW.proIDMakeWrite("storeSell", storeComboBox
								.getSelectedIndex());
					} catch (IOException ex) {
						Logger.getLogger(sellFrame.class.getName()).log(
								Level.SEVERE, null, ex);
					}
					// ���û�з����쳣����ô�رմ��ڡ��쳣��Ϣ����Դ��sell2Main.java�����д�����ݿ��׳��쳣��tag==1;
					if (exceptionTag == 0) {
						dispose();
					}
				}
			}
		});
	}

	/**
	 * ������������������storeLoad��infoLoad ʵ�������ݴ����ݿ�Ķ�ȡ
	 */
	// ��storet�ж�ȡ�ֿ����Ϣ�������ж��ٸ��ֿ�
	private void storeLoad() {
		DBOperation storeLoad = new DBOperation();
		storeLoad.DBConnect();
		String sql = "select store from storet";
		ResultSet rs = null;
		try {
			try {
				rs = storeLoad.DBSqlQuery(sql);
			} catch (SQLException ex) {
				Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE,
						null, ex);
			}
			while (rs.next()) {
				storeComboBox.addItem(rs.getString(1).trim());
			}
			storeLoad.DBClosed();
		} catch (SQLException ex) {
			Logger.getLogger(sellFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	// ��maint�ж�ȡ��Ʒ��Ϣinfo��Ŀ�ģ��ṩ�����Զ���ɡ�ģ��ʹ�á�

	private Object[] infoLoad() {
		List v = new Vector();
		DBOperation infoLoad = new DBOperation();
		infoLoad.DBConnect();
		String sql = "select distinct info from maint";
		ResultSet rs = null;
		try {
			rs = infoLoad.DBSqlQuery(sql);
			while (rs.next()) {
				v.add(rs.getString("info").trim());
			}

		} catch (SQLException ex) {
			Logger.getLogger(AutoCompleter.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		infoLoad.DBClosed();
		return v.toArray();
	}

	private void addEditEvent(JTable tb) {

		// �ֱ����������¼��ͼ����¼�
		tb.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				JTable tb = (JTable) e.getSource();
				addKeyDowntoEditEvent(tb);
			}
		});
		// tb.addToolTipText("���¼���Tab������༭״̬��Escȡ���༭״̬");
		tb.addKeyListener(new java.awt.event.KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				JTable tb = (JTable) e.getSource();
				addKeyDowntoEditEvent(tb);
			}
		});
	}

	private void addKeyDowntoEditEvent(JTable tb) {
		try {
			int rows = tb.getRowCount();
			int cols = tb.getColumnCount();

			int selectingrow = tb.getSelectedRow();
			int selectingcol = tb.getSelectedColumn();

			if (selectingrow < 0 || selectingcol < 0) {
				return;
			}

			try {
				tb.getCellEditor(selectingrow, selectingcol).stopCellEditing();
			} catch (Exception ex) {
			}
			/*
			 * switch (key) { /* case KeyEvent.VK_ENTER: { break; } case
			 * KeyEvent.VK_ESCAPE: { //stopEditing(tb); return; } default: {
			 * return; } }
			 */
			try {
				if (selectingrow >= rows) {
					selectingrow = 0;
					selectingcol++;
				}
				if (selectingcol >= cols) {
					selectingcol = 0;
				}
				if (selectingcol >= cols) {
					selectingcol = 0;
					selectingrow++;
				}
				if (selectingrow >= rows) {
					selectingrow = 0;
				}

				if (!tb.isCellEditable(selectingrow, selectingcol)) {
					return;
				}

				// tb.setRowSelectionInterval(selectingrow,selectingrow);
				// tb.setColumnSelectionInterval(selectingcol,selectingcol);
				tb.editCellAt(selectingrow, selectingcol);// ʹ��ѡ�еĵ�Ԫ���ڱ༭״̬
				(((DefaultCellEditor) tb.getCellEditor(selectingrow,
						selectingcol)).getComponent()).requestFocus();// �����̻������ѡ�е�Ԫ���ʱ���Զ���ý��㣬����༭ģʽ
				((JTextField) ((DefaultCellEditor) tb.getCellEditor(
						selectingrow, selectingcol)).getComponent())
						.selectAll();// ����jtextfield��Ĭ�Ͻ���ȫѡ
				tb.scrollRectToVisible(new java.awt.Rectangle(
						(selectingcol - 1)
								* tb.getColumnModel().getColumn(0).getWidth(),
						(selectingrow - 1) * tb.getRowHeight(), 200, 200));
				ResultSet rs = null;
				if (selectingrow != tagrow) { // Ϊ�˴���Բ�ͬ�н��е��޸ģ��ر�����ж���䣬���Ҽ�¼��һ�α��������

					/**
					 * ��Tagrow�Ŀ��۽��в���
					 */
					if (model.getValueAt(tagrow, 3).toString() == "") {
						String in = null;
						String out = null;
						DBOperation findMain = new DBOperation();
						findMain.DBConnect();
						String sql = "select distinct outPrice from maint where info='"
								+ model.getValueAt(tagrow, 1).toString() + "'";
						rs = findMain.DBSqlQuery(sql);
						while (rs.next()) {
							out = rs.getString(1);
							break;
						}
						findMain.DBClosed();
						model.setValueAt(out, tagrow, 3);
						table.repaint();
					}
					/**
					 * ��tagrow ���ܼ۽��м���
					 */
					if (model.getValueAt(tagrow, 2).toString() != ""
							&& model.getValueAt(tagrow, 3).toString() != "") {
						if (tableOldAmount[tagrow] != model.getValueAt(tagrow,
								2).toString()
								|| tableOldPrice[tagrow] != model.getValueAt(
										tagrow, 3).toString()) {
							tableOldAmount[tagrow] = model
									.getValueAt(tagrow, 2).toString();
							tableOldPrice[tagrow] = model.getValueAt(tagrow, 3)
									.toString();
							float value = Float.parseFloat(model.getValueAt(
									tagrow, 2).toString().trim());
							float price = Float.parseFloat(model.getValueAt(
									tagrow, 3).toString().trim());
							float sp = value * price;
							model.setValueAt(String.valueOf(sp), tagrow, 4);
							table.repaint();// ˢ��table;
						}
					}
					tagrow = selectingrow;
				}
				/**
				 * ��Tagrow�Ŀ��۽��в���
				 */
				if (model.getValueAt(selectingrow, 1).toString() != ""
						&& model.getValueAt(selectingrow, 2).toString() == "") {
					String in = null;
					String out = null;
					DBOperation findMain = new DBOperation();
					findMain.DBConnect();
					String sql = "select distinct outPrice from maint where info='"
							+ model.getValueAt(selectingrow, 1).toString()
							+ "'";
					rs = findMain.DBSqlQuery(sql);
					while (rs.next()) {
						out = rs.getString(1);
						break;
					}
					findMain.DBClosed();
					model.setValueAt(out, selectingrow, 3);
					table.repaint();
				}
				/**
				 * ��tagrow ���ܼ۽��м���
				 */
				if (model.getValueAt(selectingrow, 2).toString() != ""
						&& model.getValueAt(selectingrow, 3).toString() != "") {
					if (tableOldAmount[selectingrow] != model.getValueAt(
							selectingrow, 2).toString()
							|| tableOldPrice[selectingrow] != model.getValueAt(
									selectingrow, 3).toString()) {
						float value = Float.parseFloat(model.getValueAt(
								selectingrow, 2).toString().trim());
						float price = Float.parseFloat(model.getValueAt(
								selectingrow, 3).toString().trim());
						float sp = value * price;
						model.setValueAt(String.valueOf(sp), selectingrow, 4);
						tableOldAmount[selectingrow] = model.getValueAt(
								selectingrow, 2).toString();
						tableOldPrice[selectingrow] = model.getValueAt(
								selectingrow, 3).toString();
						table.repaint();
					}
				}
				sumvalues = 0;// ���������ֵ
				for (int i = 0; i <= model.getRowCount(); i++) {
					String value = model.getValueAt(i, 2).toString().trim();
					if (value == "") {
						break;
					}
					sumvalues = sumvalues + Integer.parseInt(value);
					sumValues.setText(String.valueOf(sumvalues));
				}
				sumprice = 0;// ����ܼ�
				for (int i = 0; i <= model.getRowCount(); i++) {
					String value = model.getValueAt(i, 4).toString().trim();
					if (value == "") {
						break;
					}
					sumprice = sumprice + Float.parseFloat(value);
					sumPrice.setText(String.valueOf(sumprice));
				}
			} catch (Exception ex) {
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

@SuppressWarnings("serial")
class ColorRenderer extends DefaultTableCellRenderer {

	public ColorRenderer() {
		super();
		// setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	}

	Color customCol = new Color(215, 226, 247);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (isSelected) {
			comp.setForeground(table.getSelectionForeground());
			comp.setBackground(table.getSelectionBackground());
		} else if (row % 2 == 0) {
			comp.setForeground(table.getForeground());
			comp.setBackground(Color.white);
		} else {
			comp.setForeground(table.getForeground());
			comp.setBackground(customCol);
		}
//		setText(value.toString());	//what's this????
		return comp;
	}
}

class PlanetTableModel extends AbstractTableModel {

	public static final int NAME = 1;
	public static final int VALUES = 2;
	public static final int PRICE = 3;
	public static final int SUM = 4;
	public static final int OTHERS = 5;
	private static Object[][] cells = new Object[Integer.parseInt(PropertiesRW
			.proIDMakeRead("tablerow"))][6];
	private String[] columnNames = { "���", "��Ʒ����", "����", "����", "���", "��ע" };

	@SuppressWarnings("empty-statement")
	public PlanetTableModel() {
		for (int i = 0; i < Integer.parseInt(PropertiesRW
				.proIDMakeRead("tablerow")); i++) {
			for (int k = 0; k < 6; k++) {
				cells[i][k] = "";
			}
		}
	}

	@Override
	public String getColumnName(int c) {
		return columnNames[c];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return cells.length;
	}

	public Object getValueAt(int r, int c) {
		return cells[r][c];
	}

	@Override
	public void setValueAt(Object obj, int r, int c) {
		cells[r][c] = obj;
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return c == NAME || c == VALUES || c == PRICE || c == OTHERS
				|| c == SUM;
	}
}
