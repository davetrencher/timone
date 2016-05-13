package com.github.davetrencher.timone.ui;

import com.intellij.openapi.components.NamedComponent;
import com.intellij.ui.components.JBPanel;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author boruvka
 */
public class TunnelPanel extends JBPanel implements NamedComponent {

    public static final String NAME = "TunnelPanel";

    private CallsPanel list;

    public TunnelPanel() {

        setLayout(new BorderLayout());

        list = new CallsPanel();
        add(list, BorderLayout.CENTER);

    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    @Override
    public String getName() {
        return NAME;
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

