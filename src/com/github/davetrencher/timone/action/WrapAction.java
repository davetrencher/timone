package com.github.davetrencher.timone.action;

import com.github.davetrencher.timone.ui.Icons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;

/**
 * @author boruvka
 */
public class WrapAction extends ToggleAction {

    public WrapAction() {
        super("Wrap lines", "Wrap lines", Icons.ICON_WRAP);
    }

    private boolean selected = false;

    public boolean isSelected(AnActionEvent event) {
        return selected;
    }

    public void setSelected(AnActionEvent event, boolean b) {
//        selected = b;
//
//        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
//
//        if (selected)
//            tunnelPanel.wrap();
//        else
//            tunnelPanel.unwrap();
//
    }
}
