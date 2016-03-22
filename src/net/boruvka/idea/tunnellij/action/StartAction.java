package net.boruvka.idea.tunnellij.action;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.net.Tunnel;
import net.boruvka.idea.tunnellij.net.TunnelManager;
import net.boruvka.idea.tunnellij.settings.ConfigProvider;
import net.boruvka.idea.tunnellij.settings.ConfigProvider;
import net.boruvka.idea.tunnellij.settings.TunnelSetting;
import net.boruvka.idea.tunnellij.ui.CallsPanel;
import net.boruvka.idea.tunnellij.ui.ControlPanel;
import net.boruvka.idea.tunnellij.ui.Icons;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author boruvka
 * @since
 */
public class StartAction extends AnAction {

    public StartAction() {
        super("Start tunnellij", "Start tunnellij", Icons.ICON_START);
    }

    public void actionPerformed(AnActionEvent event) {

        Project project = (Project) event.getDataContext().getData("project");
        try {
            Tunnel tunnel = getTunnel(project);
            TunnelManager.start(tunnel);
        } catch (Exception e) {
            Messages.showMessageDialog("Error when starting server: "
                    + e.getMessage(), "Error", Messages.getErrorIcon());
        }

    }

    @NotNull
    private Tunnel getTunnel(Project project) {

        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel(project);
        ControlPanel control = tunnelPanel.getControlPanelListener();
        CallsPanel list = tunnelPanel.getCallsPanelListener();

        ConfigProvider config = project.getComponent(ConfigProvider.class);
        List<TunnelSetting> settings = config.getState().getSettingsList();

        Tunnel tunnel;
        if (settings.size() > 0) {
            tunnel = new Tunnel(settings.get(0));
        } else {
            tunnel = new Tunnel(control.getSrcPort(), control.getDestPort(), control.getDestHost());
        }
        tunnel.addTunnelListener(list);
        tunnel.addTunnelListener(control);
        return tunnel;
    }

    public void update(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData("project");
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel(project);
        Presentation p = event.getPresentation();
        p.setEnabled(!tunnelPanel.isRunning());
        p.setVisible(true);
    }
}
