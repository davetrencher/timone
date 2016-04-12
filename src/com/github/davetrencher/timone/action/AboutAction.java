package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.TunnelBundle;
import com.github.davetrencher.timone.ui.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.*;

/**
 * @author boruvka
 */
public class AboutAction extends AnAction {

    private static final String MESSAGE = TunnelBundle.getBundle().getString("timone.message");

    public AboutAction() {
        super("Show About dialog", "Show About dialog", Icons.ICON_HELP);
    }

    public void actionPerformed(AnActionEvent event) {

        if (MESSAGE != null) {
            TextArea area = new TextArea(20, 80);
            area.setEditable(false);
            area.append(MESSAGE);
            JOptionPane.showMessageDialog(null, area);
        } else {
            System.out.println("stream is null!");
        }
    }
}