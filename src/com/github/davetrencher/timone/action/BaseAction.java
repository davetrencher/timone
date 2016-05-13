package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * Created by dave on 25/04/16.
 */
public abstract class BaseAction extends AnAction {

    public BaseAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }


    TunnelPanel getTunnelPanel(AnActionEvent event) {
        final Project project = getProject(event);
        TunnelPlugin tunnelPlugin = (TunnelPlugin)project.getComponent(TunnelPlugin.COMPONENT_NAME);
        return tunnelPlugin.getTunnelPanel();
    }

    Project getProject(AnActionEvent event) {
        return event.getData(PlatformDataKeys.PROJECT);
    }
}
