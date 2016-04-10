package net.boruvka.idea.tunnellij.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import net.boruvka.idea.tunnellij.net.Tunnel;
import net.boruvka.idea.tunnellij.net.TunnelManager;
import net.boruvka.idea.tunnellij.settings.ConfigProvider;
import net.boruvka.idea.tunnellij.settings.TunnelSetting;
import net.boruvka.idea.tunnellij.ui.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dave on 08/04/16.
 */
public class SettingsAction extends AnAction {

    public SettingsAction()  {
        super("tunnellij Settings", "tunnellij Settings", Icons.ICON_SETTINGS);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = (Project) event.getDataContext().getData("project");
        ConfigProvider config = project.getComponent(ConfigProvider.class);
        java.util.List<TunnelSetting> settings = config.getState().getSettingsList();
        JBTable table = getTunnelSettingsTable(settings);

        JOptionPane.showMessageDialog(null, new JBScrollPane(table));

    }

    @NotNull
    private JBTable getTunnelSettingsTable(List<TunnelSetting> settings) {
        TunnelSetting.SettingsTableModel tableModel = new TunnelSetting.SettingsTableModel(settings);

        JBTable table = new JBTable(tableModel);
        table.setStriped(true);
        table.sizeColumnsToFit(2);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(getTableClickedListener());
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
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("unable to stop tunnel running on port: " + srcPort);
                        }
                    } else if (!TunnelManager.isTunnelRunningOnPort(srcPort)) {
                        TunnelManager.start(tunnel);
                    }
                }
            }
        };
    }
}
