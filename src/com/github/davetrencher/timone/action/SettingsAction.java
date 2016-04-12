package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.net.Tunnel;
import com.github.davetrencher.timone.net.TunnelManager;
import com.github.davetrencher.timone.settings.ConfigProvider;
import com.github.davetrencher.timone.settings.TunnelSetting;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.net.Tunnel;
import com.github.davetrencher.timone.net.TunnelManager;
import com.github.davetrencher.timone.settings.ConfigProvider;
import com.github.davetrencher.timone.settings.TunnelSetting;
import com.github.davetrencher.timone.ui.Icons;
import com.github.davetrencher.timone.ui.TunnelPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.github.davetrencher.timone.ui.TunnelPanel.SETTINGS_PANEL;

/**
 * Created by dave on 08/04/16.
 */
public class SettingsAction extends AnAction {

    public SettingsAction()  {
        super("timone Settings", "timone Settings", Icons.ICON_SETTINGS);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = (Project) event.getDataContext().getData("project");
        ConfigProvider config = project.getComponent(ConfigProvider.class);
        java.util.List<TunnelSetting> settings = config.getState().getSettingsList();


        TunnelPanel panel = TunnelPlugin.getTunnelPanel();
        Component settingsPanel = panel.getComponentWithName(TunnelPanel.SETTINGS_PANEL);
        if (settingsPanel!= null) {
            panel.remove(settingsPanel);
        } else {
            JBTable table = createTunnelSettingsTable(settings);
            JScrollPane scrollPane = new JBScrollPane(table);
            scrollPane.setName(TunnelPanel.SETTINGS_PANEL);
            panel.add(scrollPane, BorderLayout.EAST);
        }
        panel.revalidate();

    }

    @NotNull
    private JBTable createTunnelSettingsTable(List<TunnelSetting> settings) {
        TunnelSetting.SettingsTableModel tableModel = new TunnelSetting.SettingsTableModel(settings);

        JBTable table = new JBTable(tableModel);
        table.setName(TunnelSetting.SettingsTableModel.NAME);
        table.setStriped(true);
        table.sizeColumnsToFit(2);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(getTableClickedListener());
        table.getModel().addTableModelListener(e -> table.repaint());
        return table;
    }

    @NotNull
    private MouseAdapter getTableClickedListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {

                JTable target = (JTable)event.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();

                if (target.getColumnName(column).equals(TunnelSetting.SettingsTableModel.RUNNING)) {
                    Integer srcPort = (Integer)target.getValueAt(row,0);
                    String destHost = String.valueOf(target.getValueAt(row,1));
                    Integer destPort =  (Integer)target.getValueAt(row,2);

                    Tunnel tunnel = new Tunnel(srcPort,destHost,destPort);
                    if (TunnelManager.isTunnelRunning(tunnel)) {
                        try {
                            TunnelManager.stop(tunnel.getSrcPort());
                            target.setValueAt(Boolean.FALSE,row,column);
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("unable to stop tunnel running on port: " + srcPort);
                        }
                    } else if (!TunnelManager.isTunnelRunningOnPort(srcPort)) {
                        TunnelManager.start(tunnel);
                        target.setValueAt(Boolean.TRUE,row,column);
                    }
                }
            }
        };
    }
}
