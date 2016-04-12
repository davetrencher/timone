package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.ui.Icons;
import com.github.davetrencher.timone.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author boruvka
 * @since
 */
public class ClearAction extends AnAction {

    public ClearAction() {
        super("Remove all calls from list", "Remove all calls from list",
                Icons.ICON_CLEAR);
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData("project");
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
        tunnelPanel.clear();
    }
}