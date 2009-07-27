/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.res0w.jwms.method;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author Administrator
 */
@SuppressWarnings("serial")
public class ProgressBarDialog extends JFrame{
    JProgressBar progressBar=new JProgressBar();
    public ProgressBarDialog() {
        progressBar.setForeground(new Color(255, 0, 0));
        progressBar.setStringPainted(true);
        add(progressBar);
        //setLocationRelativeTo(null);//һ���ô��ھ���
        setUndecorated(true);
        pack();
        setVisible(true);
    }
    public void adoptDeterminate(int maxValue){
        progressBar.setMaximum(maxValue);
        progressBar.setVisible(true);
    }
    public void setValue(int value){
        progressBar.setValue(value);
        if(value>=progressBar.getMaximum()){
            progressBar.setString("���ڴ������ݣ����Ժ�...");
        }
    }
    public void finishDeterminate(){
        
        this.dispose();
    }
}
