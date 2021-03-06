package com.github.davetrencher.timone;

import com.github.davetrencher.timone.action.*;
import com.github.davetrencher.timone.net.TunnelManager;
import com.github.davetrencher.timone.ui.Icons;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.NamedComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * @author boruvka
 */
public class TunnelPlugin implements ProjectComponent {

    private TunnelPanel tunnelPanel;

    public static Properties PROPERTIES;

    private static final String PROPERTIES_FILE_NAME = "timone.properties";

    private static File PROPERTIES_FILE;

    public static final String COMPONENT_NAME = "com.github.davetrencher.timone.TunnelWindow";

    private static final String TOOL_WINDOW_ID = TunnelBundle.getBundle()
            .getString("timone.version");

    static {
        PROPERTIES_FILE = new File(System.getProperty("user.home"),
                PROPERTIES_FILE_NAME);
        PROPERTIES = new Properties();
    }

    private static Project project;

    public TunnelPlugin(Project project) {
        this.project = project;
    }

    public void projectOpened() {
        initToolWindow();
    }

    public void projectClosed() {
        unregisterToolWindow();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public synchronized void initComponent() {
        if (PROPERTIES_FILE.exists()) {
            try {
                InputStream is = new FileInputStream(PROPERTIES_FILE);
                PROPERTIES.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static StatusBar getStatusBar() {
        return WindowManager.getInstance().getStatusBar(project);
    }

    public synchronized void disposeComponent() {
        try {
            OutputStream os = new FileOutputStream(PROPERTIES_FILE);
            PROPERTIES.store(os, "TunnelliJ plugin");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            TunnelManager.shutdownAll();
        }
    }

    private void initToolWindow() {

        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        tunnelPanel = createTunnelPanel();
        ToolWindow tunnelWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID,
                true, ToolWindowAnchor.BOTTOM);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        tunnelWindow.getContentManager().addContent(contentFactory.createContent(tunnelPanel,"TunnelPanel",true));
        tunnelWindow.setIcon(Icons.ICON_WATCH);

        DefaultActionGroup actionGroup = initToolbarActionGroup();
        ActionToolbar toolBar = ActionManager.getInstance()
                .createActionToolbar("timone.Toolbar", actionGroup, false);

        tunnelPanel.add(toolBar.getComponent(), BorderLayout.WEST);
    }

    private void unregisterToolWindow() {
        ToolWindowManager toolWindowManager = ToolWindowManager
                .getInstance(project);
        toolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
    }

    private static TunnelPanel createTunnelPanel() {
        TunnelPanel panel = new TunnelPanel();
        panel.setBackground(UIManager.getColor("Tree.textBackground"));
        return panel;
    }

    private DefaultActionGroup initToolbarActionGroup() {
        DefaultActionGroup actionGroup = new DefaultActionGroup();

        AnAction clearAction = new ClearAction();
        AnAction clearSelectedAction = new ClearSelectedAction();
        AnAction aboutAction = new AboutAction();
        AnAction settingsAction = new SettingsAction();
        ToggleAction wrapAction = new WrapAction();

        actionGroup.add(settingsAction);
        actionGroup.add(clearSelectedAction);
        actionGroup.add(clearAction);
        actionGroup.add(wrapAction);
        actionGroup.add(aboutAction);

        return actionGroup;
    }

    public TunnelPanel getTunnelPanel() {
        return tunnelPanel;
    }

    public static class TunnelConfig {

        public static final int BUFFER_LENGTH = 4096;

        public static final String DST_HOST = "timone.dst.hostname";

        public static final String DST_PORT = "timone.dst.port";

        public static final String SRC_PORT = "timone.src.port";

        public static String getDestinationString() {
            return PROPERTIES.getProperty(DST_HOST, "localhost");
        }

        public static String getDestinationPort() {
            return PROPERTIES.getProperty(DST_PORT, "6060");
        }

        public static String getSourcePort() {
            return PROPERTIES.getProperty(SRC_PORT, "4444");
        }
    }

}
