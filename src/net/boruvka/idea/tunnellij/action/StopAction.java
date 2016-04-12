package net.boruvka.idea.tunnellij.action;

import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.net.TunnelManager;
import net.boruvka.idea.tunnellij.settings.ConfigProvider;
import net.boruvka.idea.tunnellij.settings.TunnelSetting;
import net.boruvka.idea.tunnellij.ui.Icons;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.util.List;

/**
 * @author boruvka
 * @since
 */
@Deprecated
public class StopAction extends AnAction {

    public StopAction() {
        super("Stop tunnellij", "Stop tunnellij", Icons.ICON_STOP);
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData("project");
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
        try {
            ConfigProvider config = project.getComponent(ConfigProvider.class);
            List<TunnelSetting> settings = config.getState().getSettingsList();

            if (settings.size() > 0) {
                TunnelManager.stop(settings.get(0).getSrcPort());
            }
            tunnelPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            Messages.showMessageDialog("Error when starting server: "
                    + e.getMessage(), "Error", Messages.getErrorIcon());
        }
    }

    public void update(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData("project");
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
        Presentation p = event.getPresentation();
     //   p.setEnabled(tunnelPanel.isRunning());
        p.setVisible(true);
    }

}
