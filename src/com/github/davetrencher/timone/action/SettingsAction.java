package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.settings.TimoneSettings;
import com.github.davetrencher.timone.settings.TunnelSetting;
import com.github.davetrencher.timone.ui.Icons;
import com.github.davetrencher.timone.ui.SettingsPanel;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.github.davetrencher.timone.util.Logger;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

/**
 * Created by dave on 08/04/16.
 */
public class SettingsAction extends BaseAction {

    public SettingsAction()  {
        super("timone Settings", "timone Settings", Icons.ICON_SETTINGS);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        TunnelPanel tunnelPanel = getTunnelPanel(event);
        JBPanel settingsPanel = (JBPanel)tunnelPanel.getComponentWithName(SettingsPanel.SETTINGS_PANEL);


        TimoneSettings config = getConfigProvider(event);
        if (config == null) return;
        Logger.info("Pressed Settings Button");
        updateSettingsPanel(config, tunnelPanel, settingsPanel);

    }

    @Nullable
    protected TimoneSettings getConfigProvider(AnActionEvent event) {

        Project project = getProject(event);
        if (project == null) {
            System.out.println("Could not get project.");
            return null;
        }

        TimoneSettings config = TimoneSettings.getInstance(project);
        config.getState();

        if (config == null) {
            System.out.println("Could not get config.");
            return null;
        }
        return config;
    }

    private void updateSettingsPanel(TimoneSettings config, JBPanel tunnelPanel, JBPanel settingsPanel) {

        if (settingsPanel!= null) {
            tunnelPanel.remove(settingsPanel);
        } else {
            List<TunnelSetting> settings = config.getSettingsList();
            settingsPanel = new SettingsPanel(settings);
            tunnelPanel.add(settingsPanel, BorderLayout.EAST);
        }
        tunnelPanel.revalidate();
    }


}
