package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.ui.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author boruvka
 */
public class ClearSelectedAction extends AnAction {

    public ClearSelectedAction() {
        super("Remove selected call", "Remove selected call", Icons.ICON_REMOVE);
    }

    public void actionPerformed(AnActionEvent event) {
//        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
//        tunnelPanel.clearSelected();
    }
}
