package com.res0w.jwms.main;

import java.awt.BorderLayout;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import com.res0w.jwms.method.*;

/**
 * 
 * @author lqik2004
 */
public class Setup {

	/**
	 * mysql
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		setupDialog frame = new setupDialog();
		frame.setTitle("��������");
		frame.setLocationRelativeTo(null);
		frame.setSize(250, 100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}

class setupDialog extends JFrame {

	private JButton initInfo = new JButton("��ʼ���ִ���Ϣ");
	private JButton initProgram = new JButton("��ʼ����������");

	public setupDialog() {
		Box vbox = Box.createVerticalBox();
		vbox.add(Box.createHorizontalStrut(10));
		vbox.add(initInfo);
		vbox.add(Box.createVerticalStrut(5));
		vbox.add(initProgram);
		vbox.add(Box.createVerticalGlue());
		add(vbox);
		initInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				initInfoFrame frame = null;
				try {
					frame = new initInfoFrame();
				} catch (Exception ex) {
					Logger.getLogger(setupDialog.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.setLocationRelativeTo(null);// һ���ô��ھ���
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		initProgram.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				initProgFrame frame = null;
				try {
					frame = new initProgFrame();
				} catch (Exception ex) {
					Logger.getLogger(setupDialog.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.setLocationRelativeTo(null);// һ���ô��ھ���
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

@SuppressWarnings("serial")
class initProgFrame extends JFrame {
	private int WIDTH = 300;
	private int HEIGHT = 200;
	private JLabel sqlSelectLabel;
	private JRadioButton sqlSelectButton1;
	private JRadioButton sqlSelectButton2;
	private JButton commitButton = new JButton("�ύ");
	private ButtonGroup sqlSelectButGrp = new ButtonGroup();

	public initProgFrame() {
		setTitle("�����ʼ������");
		setSize(WIDTH, HEIGHT);
		Box hbox0 = Box.createHorizontalBox();
		sqlSelectLabel = new JLabel("��ѡ��ʹ�õ����ݿ����ͣ�");
		sqlSelectButton1 = new JRadioButton("SqlServer", true);
		sqlSelectButton2 = new JRadioButton("MySql", false);
		sqlSelectButGrp.add(sqlSelectButton1);
		sqlSelectButGrp.add(sqlSelectButton2);
		hbox0.add(sqlSelectLabel);
		hbox0.add(Box.createHorizontalGlue());
		Box hbox1 = Box.createHorizontalBox();
		hbox1.add(sqlSelectButton1);
		hbox1.add(sqlSelectButton2);

		Box hbox2 = Box.createHorizontalBox();
		hbox2.add(commitButton);

		Box vbox = Box.createVerticalBox();
		vbox.add(hbox0);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(hbox1);
		vbox.add(Box.createVerticalGlue());
		vbox.add(hbox2);
		vbox.add(Box.createVerticalStrut(10));

		add(vbox, BorderLayout.CENTER);
		
		sqlSelectButton1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PropertiesRW.proIDMakeWrite("sqltype", sqlSelectButton1.getText().toLowerCase());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		sqlSelectButton2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PropertiesRW.proIDMakeWrite("sqltype", sqlSelectButton2.getText().toLowerCase());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		commitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
	}
}

@SuppressWarnings("serial")
class initInfoFrame extends JFrame {

	private float sumprice = 0; // ���е��ܼ�
	private int sumvalues = 0; // ���е�������
	private int tagrow = 0;// ����һ�������
	Object[] items = null;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 647;
	private JComboBox NameCombo;
	private JComboBox storeComboBox = new JComboBox();
	private TableModel model = new initInfoPlanetTableModel();
	private JTable table = new JTable(model);
	private JTextField sumPrice = new JTextField(6);// �ܼƽ�����6λ������С�����С�����һλ
	private JTextField sumValues = new JTextField(3);
	private static int exceptionTag = 0; // �쳣���

	public static void setExTag(int tag) {
		exceptionTag = tag;
	}

	public initInfoFrame() throws Exception {
		// ��ʼ�����ݿ⣬������Ϣ
		storeLoad();// ����ֿ���Ϣ
		items = infoLoad();
		setTitle("��ʼ����Ʒ��Ϣ");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// ���òֿ���
		JLabel labelStore = new JLabel("�ֿ⣺");
		storeComboBox.setSelectedIndex(1);
		storeComboBox.setMaximumSize(storeComboBox.getPreferredSize());
		storeComboBox.setEditable(false); // �ֿⲻ��ֱ���޸�
		JButton addStore = new JButton("��Ӳֿ�");
		Box hbox2 = Box.createHorizontalBox();
		hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(labelStore);
		hbox2.add(storeComboBox);
		hbox2.add(Box.createHorizontalStrut(20));
		hbox2.add(addStore);
		hbox2.add(Box.createHorizontalGlue());
		// �����б���

		table.setRowSelectionAllowed(false);
		addEditEvent(table);
		// set up renderers and editors
		// table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
		// table.setDefaultEditor(Color.class, new ColorTableCellEditor());
		// ��������
		// java.util.ArrayList list = new
		// java.util.ArrayList(Arrays.asList(items));
		// Collections.sort(list);
		// JComboBox cmb = new JAutoCompleteComboBox(list.toArray());
		// Arrays.sort(items);//��item��������
		AutoCompleter.setItems(items);
		table.setDefaultRenderer(Object.class, new ColorRenderer()); // ����ÿһ�еı�����ɫ
		// �ѵ�Ԫ������JAutoCompleteComboBox
		NameCombo = new JAutoCompleteComboBox(items);
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn NameColumn = columnModel.getColumn(1);
		NameColumn.setCellEditor(new DefaultCellEditor(NameCombo));

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ�������ʽ��δ����
		table.getColumnModel().getColumn(0).setPreferredWidth(30);// ���õ�һ���п�
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
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
		sumPrice.setMaximumSize(addStore.getPreferredSize()); // ʹ����ʽ�����²���Ĭ��ȡ�����ֵ������Ԥ�����С
		JLabel labelSumValues = new JLabel("��������");
		sumValues.setEditable(false);// �����޸�
		sumValues.setMaximumSize(addStore.getPreferredSize()); // ʹ����ʽ�����²���Ĭ��ȡ�����ֵ������Ԥ�����С
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
		addStore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				storeComboBox.setEditable(true);// ���òֿ�JComboBox�ɱ༭
				storeComboBox.setSelectedIndex(-1);// ��������գ�����û�����
			}
		});

		// �˳���ť���
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		// �ύ��ť���
		referButton.addActionListener(new ActionListener() {

			/**
			 * ����ť������Ӧ�����ԡ��־û���tag��judge�����ļ�����������
			 */
			public void actionPerformed(ActionEvent e) {
				int ifcontinue = JOptionPane.showConfirmDialog(null, "��ȷ�ϵ��ݹ���",
						"����ȷ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (ifcontinue == JOptionPane.YES_OPTION) {
					try {
						if (!new AddDel().isStoreExist(storeComboBox
								.getSelectedItem().toString())) {
							DBOperation inputStore = new DBOperation();
							inputStore.DBConnect();
							String sql = "insert into storet (store) values ('"
									+ storeComboBox.getSelectedItem()
											.toString() + "')";
							System.out.println(sql);
							inputStore.DBSqlExe(sql);
							try {
								PropertiesRW.proIDMakeWrite("storeInput",
										storeComboBox.getItemCount());
							} catch (IOException ex) {
								Logger.getLogger(inputFrame.class.getName())
										.log(Level.SEVERE, null, ex);
							}
						}
					} catch (SQLException ex) {
						Logger.getLogger(inputFrame.class.getName()).log(
								Level.SEVERE, null, ex);
					}
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.getValueAt(i, 1).toString() != "") { // ����ַ���û�У���ô�����м���д�����ݿ�
							transmitMain(i);
						}
					}
					if (exceptionTag == 0) {
						dispose();
					}
				}
			}
		});
	}

	private void storeLoad() {
		DBOperation storeLoad = new DBOperation();
		storeLoad.DBConnect();
		String sql = "select store from storet";
		ResultSet rs = null;
		try {
			rs = storeLoad.DBSqlQuery(sql);
			while (rs.next()) {
				storeComboBox.addItem(rs.getString(1).trim());
			}
			storeLoad.DBClosed();
		} catch (SQLException ex) {
			Logger.getLogger(inputFrame.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

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

	public void addEditEvent(JTable tb) {
		// tb.addToolTipText("���¼���Tab������༭״̬��Escȡ���༭״̬");
		tb.addKeyListener(new java.awt.event.KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				JTable tb = (JTable) e.getSource();
				addKeyDowntoEditEvent(tb);
			}
		});
		tb.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
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
				/**
				 * if (selectingrow >= rows) { selectingrow = 0; selectingcol++;
				 * } if (selectingcol >= cols) { selectingcol = 0; } if
				 * (selectingcol >= cols) { selectingcol = 0; selectingrow++; }
				 * if (selectingrow >= rows) { selectingrow = 0; }
				 */
				if (!tb.isCellEditable(selectingrow, selectingcol)) {
					return;
				}

				// tb.setRowSelectionInterval(selectingrow,selectingrow);
				// tb.setColumnSelectionInterval(selectingcol,selectingcol);
				tb.editCellAt(selectingrow, selectingcol);
				(((DefaultCellEditor) tb.getCellEditor(selectingrow,
						selectingcol)).getComponent()).requestFocus();
				((JTextField) ((DefaultCellEditor) tb.getCellEditor(
						selectingrow, selectingcol)).getComponent())
						.selectAll();
				tb.scrollRectToVisible(new java.awt.Rectangle(
						(selectingcol - 1)
								* tb.getColumnModel().getColumn(0).getWidth(),
						(selectingrow - 1) * tb.getRowHeight(), 200, 200));
				ResultSet rs = null;
				if (selectingrow != tagrow) { // Ϊ�˴���Բ�ͬ�н��е��޸ģ��ر�����ж���䣬���Ҽ�¼��һ�α��������
					/**
					 * ��Tagrow�Ŀ��۽��в���
					 */
					if (model.getValueAt(tagrow, 3).toString() == ""
							&& model.getValueAt(tagrow, 4).toString() == "") {
						String in = null;
						String out = null;
						DBOperation findMain = new DBOperation();
						findMain.DBConnect();
						String sql = "select distinct inPrice,outPrice from maint where info='"
								+ model.getValueAt(tagrow, 1).toString() + "'";
						rs = findMain.DBSqlQuery(sql);
						while (rs.next()) {
							in = rs.getString(1);
							out = rs.getString(2);
							break;
						}
						findMain.DBClosed();
						model.setValueAt(in, tagrow, 3);
						model.setValueAt(out, tagrow, 4);
						table.repaint();
					}
					/**
					 * ��tagrow ���ܼ۽��м���
					 */
					if (model.getValueAt(tagrow, 2).toString() != ""
							&& model.getValueAt(tagrow, 3).toString() != "") {
						float value = Float.parseFloat(model.getValueAt(tagrow,
								2).toString().trim());
						float price = Float.parseFloat(model.getValueAt(tagrow,
								3).toString().trim());
						float sp = value * price;
						model.setValueAt(String.valueOf(sp), tagrow, 5);
						table.repaint();// ˢ��table;
					}
					tagrow = selectingrow;
				}
				/**
				 * ��Tagrow�Ŀ��۽��в���
				 */
				if (model.getValueAt(selectingrow, 1).toString() != ""
						&& model.getValueAt(selectingrow, 3).toString() == ""
						&& model.getValueAt(selectingrow, 4).toString() == "") {
					String in = null;
					String out = null;
					DBOperation findMain = new DBOperation();
					findMain.DBConnect();
					String sql = "select distinct inPrice,outPrice from maint where info='"
							+ model.getValueAt(selectingrow, 1).toString()
							+ "'";
					rs = findMain.DBSqlQuery(sql);
					while (rs.next()) {
						in = rs.getString(1);
						out = rs.getString(2);
						break;
					}
					findMain.DBClosed();
					model.setValueAt(in, selectingrow, 3);
					model.setValueAt(out, selectingrow, 4);
					table.repaint();
				}
				/**
				 * ��tagrow ���ܼ۽��м���
				 */
				if (model.getValueAt(selectingrow, 2).toString() != ""
						&& model.getValueAt(selectingrow, 3).toString() != "") {
					float value = Float.parseFloat(model.getValueAt(
							selectingrow, 2).toString().trim());
					float price = Float.parseFloat(model.getValueAt(
							selectingrow, 3).toString().trim());
					float sp = value * price;
					model.setValueAt(String.valueOf(sp), selectingrow, 5);
					table.repaint();
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
					String value = model.getValueAt(i, 5).toString().trim();
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

	public void transmitMain(int selectedrow) {
		try {
			AddDel mainT = new AddDel();
			mainT.setAmount(model.getValueAt(selectedrow, 2).toString().trim());
			mainT.setInfo(model.getValueAt(selectedrow, 1).toString().trim());
			mainT.setStore(storeComboBox.getSelectedItem().toString().trim());
			mainT
					.setInPrice(model.getValueAt(selectedrow, 3).toString()
							.trim());
			mainT.setOutPrice(model.getValueAt(selectedrow, 4).toString()
					.trim());
			mainT.increaseMethod();
		} catch (SQLException ex) {
			Logger.getLogger(initInfoFrame.class.getName()).log(Level.SEVERE,
					null, ex);
			JOptionPane.showMessageDialog(null,
					"����δ��д�룬�����Ƿ���ȷ�������ݿ⣬����������������������ϵ");
			initInfoFrame.setExTag(1);
		}
	}
}

class initInfoPlanetTableModel extends AbstractTableModel {

	public initInfoPlanetTableModel() {
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
		return c == NAME || c == VALUES || c == INPRICE || c == OUTPRICE
				|| c == SUMPRICE;
	}

	public static final int NAME = 1;
	public static final int VALUES = 2;
	public static final int INPRICE = 3;
	public static final int OUTPRICE = 4;
	public static final int SUMPRICE = 5;
	// public static final int OTHERS = 6;
	private Object[][] cells = new Object[Integer.parseInt(PropertiesRW
			.proIDMakeRead("tablerow"))][6];
	private String[] columnNames = { "���", "��Ʒ����", "����", "��ⵥ��", "���ۼ�", "�ϼƽ��" };
}

class InitDateBase {
	public InitDateBase() {
		int IFCONFIRM = JOptionPane.showConfirmDialog(null,
				"��������ɾ�����е����������Ҳ��ɻָ���������ѡ��", "����", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (IFCONFIRM == JOptionPane.OK_OPTION) {

		}
	}
}