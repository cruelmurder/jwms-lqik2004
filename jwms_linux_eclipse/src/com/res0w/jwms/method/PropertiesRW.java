package com.res0w.jwms.method;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lqik2004
 * Ӧ��
 * text
 */
public class PropertiesRW {
	
    public static String proIDMakeRead(String x) {
    	String propertiesURL;
        String returnFiled = null;
        if(System.getProperty("os.name").toUpperCase().equals("LINUX")){
			propertiesURL="data/properties.txt";
		}else{
			propertiesURL="data\\properties.txt";
		}
        Properties fieldName = new Properties();
        try {
            fieldName.load(new FileInputStream(propertiesURL));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO��������,������ȷ��ȡ�ļ�");
        }
        returnFiled = fieldName.getProperty(x);
        return returnFiled;
    }

    /**
     *
     * @param x     NAMEkey
     * @param y     NAMEvalue
     * @throws java.io.IOException
     *
     * �ڶ�properties����д��ʱ����Ҫ��LOAD,Ȼ������(setProperties),���д�뵽�ļ���store��
     */
    public static void proIDMakeWrite(String x, int y) throws IOException {
    	String propertiesURL;
        String transY = String.valueOf(y);
        if(System.getProperty("os.name").toUpperCase().equals("LINUX")){
			propertiesURL="data/properties.txt";
		}else{
			propertiesURL="data\\properties.txt";
		}
        try {
            Properties fieldName = new Properties();
            fieldName.load(new FileInputStream(propertiesURL));
            fieldName.setProperty(x, transY);
            fieldName.store(new FileOutputStream(propertiesURL), "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesRW.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "IO��������,������ȷд���ļ�");
        }
    }
    /**
    *	ʹ��string��ʽ����д��
    * @param x     NAMEkey
    * @param y     NAMEvalue
    * @throws java.io.IOException
    *
    * 
    */
    public static void proIDMakeWrite(String x, String y) throws IOException {
    	String propertiesURL;
    	if(System.getProperty("os.name").toUpperCase().equals("LINUX")){
			propertiesURL="data/properties.txt";
		}else{
			propertiesURL="data\\properties.txt";
		}
        try {
            Properties fieldName = new Properties();
            fieldName.load(new FileInputStream(propertiesURL));
            fieldName.setProperty(x, y);
            fieldName.store(new FileOutputStream(propertiesURL), "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesRW.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "IO��������,������ȷд���ļ�");
        }
    }
}
