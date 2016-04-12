package com.github.davetrencher.timone.ui;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author boruvka
 */
public class TunnelPanel extends JPanel {

    public static final String SETTINGS_PANEL = "settingsPanel";

    private CallsPanel list;

    public TunnelPanel() {

        setLayout(new BorderLayout());

        list = new CallsPanel();
        add(list, BorderLayout.CENTER);

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

    public CallsPanel getCallsPanelListener() {
        return list;
    }

    public Component getComponentWithName(String name) {

        java.util.Map<String,Component> components = getAllNamedComponents(this);
        return components.get(name);
    }

    private Map<String,Component> getAllNamedComponents(final Container c) {

        Component[] comps = c.getComponents();
        Map<String,Component> compMap = new HashMap<>();
        for (Component comp : comps) {
            if (StringUtils.isNotEmpty(comp.getName())) {
                compMap.put(comp.getName(), comp);
            }

            if (comp instanceof Container) {
                compMap.putAll(getAllNamedComponents((Container) comp));
            }
        }
        return compMap;
    }

}

