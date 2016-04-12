package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.net.Tunnel;
import com.github.davetrencher.timone.net.TunnelManager;
import com.github.davetrencher.timone.settings.ConfigProvider;
import com.github.davetrencher.timone.settings.TunnelSetting;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.net.Tunnel;
import com.github.davetrencher.timone.net.TunnelManager;
import com.github.davetrencher.timone.settings.ConfigProvider;
import com.github.davetrencher.timone.settings.TunnelSetting;
import com.github.davetrencher.timone.ui.CallsPanel;
import com.github.davetrencher.timone.ui.ControlPanel;
import com.github.davetrencher.timone.ui.Icons;
import com.github.davetrencher.timone.ui.TunnelPanel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author boruvka
 * @since
 */
@Deprecated
public class StartAction extends AnAction {

    public StartAction() {
        super("Start timone", "Start timone", Icons.ICON_START);
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

        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
        CallsPanel list = tunnelPanel.getCallsPanelListener();

        ConfigProvider config = project.getComponent(ConfigProvider.class);
        List<TunnelSetting> settings = config.getState().getSettingsList();

        Tunnel tunnel = null;
        if (settings.size() > 0) {
            tunnel = new Tunnel(settings.get(0));
            tunnel.addTunnelListener(list);
        }

        return tunnel;
    }

    public void update(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData("project");
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
        Presentation p = event.getPresentation();
    //    p.setEnabled(!tunnelPanel.isRunning());
        p.setVisible(true);
    }
}
