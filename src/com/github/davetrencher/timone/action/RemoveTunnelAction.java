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
public class RemoveTunnelAction extends BaseAction {

    public RemoveTunnelAction() {
        super("Remove tunnel from list", "Remove tunnel from list",
                Icons.ICON_REMOVE);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        TunnelPanel panel = getTunnelPanel(event);
        JTable settingsTable = (JTable)panel.getComponentWithName(SettingsPanel.SETTINGS_TABLE);
        TunnelSetting.SettingsTableModel model = (TunnelSetting.SettingsTableModel) settingsTable.getModel();
        if (settingsTable.getSelectedRow() > -1) {
            model.removeRow(settingsTable.getSelectedRow());
            panel.revalidate();
            settingsTable.repaint();
        }
    }
}
