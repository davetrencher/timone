package com.github.davetrencher.timone.ui;

import com.github.davetrencher.timone.action.*;
import com.github.davetrencher.timone.net.Tunnel;
import com.github.davetrencher.timone.net.TunnelManager;
import com.github.davetrencher.timone.settings.TunnelSetting;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.NamedComponent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by dave on 13/04/16.
 */
public class SettingsPanel extends JBPanel implements NamedComponent {

    public static final String SETTINGS_PANEL = "SettingsPanel";

    public static final String SETTINGS_TABLE = "SettingsTable";

    public SettingsPanel(List<TunnelSetting> settings) {
        this.setLayout(new BorderLayout());
        addControlToolbar();
        addSettingsTable(settings);
        repaint();
    }

    private void addControlToolbar() {

        DefaultActionGroup actionGroup = initToolbarActionGroup();
        ActionToolbar toolBar = ActionManager.getInstance()
                .createActionToolbar("timone.Controlbar", actionGroup, true);

        JPanel controlActions = new JBPanel<>(new BorderLayout());
        controlActions.add(toolBar.getComponent(), BorderLayout.CENTER);

        add(controlActions, BorderLayout.NORTH);

    }

    private DefaultActionGroup initToolbarActionGroup() {
        DefaultActionGroup actionGroup = new DefaultActionGroup();

        AnAction addTunnelAction = new AddTunnelAction();
        AnAction removeTunnelAction = new RemoveTunnelAction();

        actionGroup.add(addTunnelAction);
        actionGroup.add(removeTunnelAction);

        return actionGroup;
    }

    private void addSettingsTable(List<TunnelSetting> settings) {
        JBTable table = createTunnelSettingsTable(settings);
        JScrollPane scrollPane = new JBScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public String getName() {
        return SETTINGS_PANEL;
    }

    @NotNull
    private JBTable createTunnelSettingsTable(List<TunnelSetting> settings) {
        TunnelSetting.SettingsTableModel tableModel = new TunnelSetting.SettingsTableModel(settings);

        JBTable table = new JBTable(tableModel);
        table.setName(SETTINGS_TABLE);
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

                if (target.getColumnName(column) != null && target.getColumnName(column).equals(TunnelSetting.SettingsTableModel.RUNNING)) {
                    Integer srcPort = (Integer)target.getValueAt(row,0);
                    String destHost = String.valueOf(target.getValueAt(row,1));
                    Integer destPort =  (Integer)target.getValueAt(row,2);

                    Tunnel tunnel = new Tunnel(srcPort,destHost,destPort);
                    if (TunnelManager.isTunnelRunning(tunnel)) {
                        TunnelManager.stop(tunnel.getSrcPort());
                        target.setValueAt(Boolean.FALSE,row,column);
                    } else if (!TunnelManager.isTunnelRunningOnPort(srcPort)) {
                        TunnelManager.start(tunnel);
                        target.setValueAt(Boolean.TRUE,row,column);
                    }
                }
            }
        };
    }


    @NotNull
    @Override
    public String getComponentName() {
        return SETTINGS_PANEL;
    }
}
