package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.settings.TunnelSetting;
import com.github.davetrencher.timone.ui.Icons;
import com.github.davetrencher.timone.ui.SettingsPanel;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;

/**
 * Created by dave on 13/04/16.
 */
public class AddTunnelAction extends BaseAction {

    public AddTunnelAction() {
        super("Add tunnel from list", "Add tunnel from list",
                Icons.ICON_ADD);
    }


    @Override
    public void actionPerformed(AnActionEvent event) {

        TunnelPanel panel = getTunnelPanel(event);
        JTable settingsTable = (JTable)panel.getComponentWithName(SettingsPanel.SETTINGS_TABLE);
        TunnelSetting.SettingsTableModel model = (TunnelSetting.SettingsTableModel) settingsTable.getModel();
        model.addRow(new Object[]{ new Integer(80), "localhost", new Integer(8080)});
        panel.revalidate();
        settingsTable.repaint();
    }

}
