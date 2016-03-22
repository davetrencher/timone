package net.boruvka.idea.tunnellij.ui;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.net.*;

import com.intellij.openapi.ui.Messages;

/**
 * @author boruvka
 * @since
 */
public class TunnelPanel extends JPanel {

    private CallsPanel list;

    private ControlPanel control;

    public TunnelPanel() {

        setLayout(new BorderLayout());

        list = new CallsPanel();
        control = new ControlPanel();
        add(list, BorderLayout.CENTER);
        add(control, BorderLayout.SOUTH);

    }

    public void clear() {
        list.clear();
    }

    public void clearSelected() {
        list.clearSelected();
    }

    public void wrap() {
        list.wrap();
    }

    public void unwrap() {
        list.unwrap();
    }

    public boolean isRunning() {
        return control.isRunning();
    }

    public int getSrcPort() { return control.getSrcPort(); }


    public CallsPanel getCallsPanelListener() {
        return list;
    }

    public ControlPanel getControlPanelListener() {
        return control;
    }
}

