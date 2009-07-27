package com.res0w.jwms.method;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *   �Զ���������Զ��ҵ���ƥ�����Ŀ���������б����ǰ�档
 *   @author   lqik2004
 */
public class AutoCompleter
        implements KeyListener, ItemListener {

    private JComboBox owner = null;
    private JTextField editor = null;
    private ComboBoxModel model = null;

    public AutoCompleter(JComboBox comboBox) {
        owner = comboBox;
        editor = (JTextField) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(this);
        model = comboBox.getModel();
        owner.addItemListener(this);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        char ch = e.getKeyChar();
        /*if (ch == KeyEvent.CHAR_UNDEFINED || Character.isISOControl(ch)) {
        return;}
        else*/ if (ch == KeyEvent.VK_DELETE || ch == KeyEvent.VK_BACK_SPACE) {
            int caretPosition = editor.getCaretPosition();
            String str = editor.getText();
            /**
             * �Ľ������������û�г����ַ���ʱ�򣬹ر��Զ���ȫ��
             */
            if (str.length() == 0) {
                owner.setPopupVisible(false);
            } else {

                autoCompleteDel(str, caretPosition);

            }
        } else if (ch == KeyEvent.CHAR_UNDEFINED || Character.isISOControl(ch)) {
            return;
        } else {
            int caretPosition = editor.getCaretPosition();
            String str = editor.getText();
            if (str.length() == 0) {
                return;
            } else if (str.length() == 1) {
                /**
                 * �����ǰ���ַ�������Ϊ1����ô��ȡautoCompleteDel����
                 * Ҳ���Ǵ�����items��������
                 * Ŀ����Ϊ�˽���ڶ�һ���ı�����в�ȫ֮�󣬲��ܶ������ı���ȫ�������
                 */
                autoCompleteDel(str, caretPosition);
            } else {
                autoComplete(str, caretPosition);
            }
        }
    }

    /**
     *   �Զ���ɡ�������������ݣ����б����ҵ����Ƶ���Ŀ.
     */
    protected void autoComplete(String strf, int caretPosition) {
        Object[] opts;
        opts = getMatchingOptions(strf.substring(0, caretPosition));
        if (owner != null) {
            model = new DefaultComboBoxModel(opts);
            owner.setModel(model);
        }
        if (opts.length > 0) {
            String str = opts[0].toString();

            editor.setCaretPosition(caretPosition);
            //�����ѡֻ��һ����ʱ��ʵ���Զ��ϴ�
            if(opts.length==1){
                editor.setText(opts[0].toString().trim());
                editor.setCaretPosition(opts[0].toString().length());
            }else{
                //����ά��ԭ������
            editor.setText(strf.substring(0, caretPosition));
            }
            if (owner != null) {
                try {
                    owner.showPopup();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            editor.setText(strf);
        }
    }

    protected void autoCompleteDel(String strf, int caretPosition) {
        Object[] opts;
        opts = getMatchingOptionsDel(strf.substring(0, caretPosition));
        if (owner != null) {
            model = new DefaultComboBoxModel(opts);
            owner.setModel(model);
        }
        if (opts.length > 0) {
            String str = opts[0].toString();
            if (caretPosition > editor.getText().length()) {
                return;
            }
            editor.setCaretPosition(caretPosition);
            if(opts.length==1){
                editor.setText(opts[0].toString().trim());
                editor.setCaretPosition(opts[0].toString().length());
            }else{
            editor.setText(strf.substring(0, caretPosition));
            }
            if (owner != null) {
                try {
                    owner.showPopup();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {          //��û�ж�Ӧ��Ŀ��ʱ��֮����ʵ�����ַ���
            editor.setText(strf);
        }
    }

    /**
     *
     *   �ҵ����Ƶ���Ŀ,   ���ҽ�֮���е��������ǰ�档
     *   @param   str
     *   @return   ����������Ŀ���б�
     */
    protected Object[] getMatchingOptions(String str) {
        List v = new Vector();
        // List v1 = new Vector();

        for (int k = 0; k < model.getSize(); k++) {
            Object itemObj = model.getElementAt(k);

            if (itemObj != null) {
                String item = itemObj.toString().toLowerCase();
                if (KMPAlgorith.kmp(item, str)) {
                    v.add(model.getElementAt(k));
                }
            /* else {
            v1.add(model.getElementAt(k));
            }
            }
            else {
            v1.add(model.getElementAt(k));
            }
            }
            for (int i = 0; i < v1.size(); i++) {
            v.add(v1.get(i));
            }
            if (v.isEmpty()) {
            v.add(str);
            }*/

            }
        }
        return v.toArray();
    }

    protected Object[] getMatchingOptionsDel(String str) {
        List v = new Vector();
        for (int k = 0; k < items.length; k++) {
            Object itemObj = items[k];
            if (itemObj != null) {
                String item = itemObj.toString().toLowerCase();
                if (KMPAlgorith.kmp(item, str)) {
                    v.add(items[k]);
                }
            /* else {
            v1.add(model.getElementAt(k));
            }
            }
            else {
            v1.add(model.getElementAt(k));
            }
            }
            for (int i = 0; i < v1.size(); i++) {
            v.add(v1.get(i));
            }
            if (v.isEmpty()) {
            v.add(str);
            }*/

            }
        }
        return v.toArray();
    }

    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            int caretPosition = editor.getCaretPosition();
            if (caretPosition != -1) {
                try {
                    editor.moveCaretPosition(caretPosition);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void setItems(Object[] x) {
        items = x;
    }
    private static Object[] items;
}
