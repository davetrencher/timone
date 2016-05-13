package com.github.davetrencher.timone.ui;

import com.github.davetrencher.timone.net.Call;
import com.github.davetrencher.timone.net.TunnelListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;

/**
 * @author boruvka
 */
public class CallsPanel extends JPanel implements TunnelListener {

    private JList list;

    private DefaultListModel<Call> model;

    private ViewersPanel viewers;

    public static final int DIVIDER_SIZE = 2;

    public CallsPanel() {

        setBackground(UIManager.getColor("Tree.textBackground"));
        model = new DefaultListModel<>();
        list = new JBList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        viewers = new ViewersPanel();
        list.addListSelectionListener(new CallsListSelectionListener(viewers));

        list.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    clearSelected();
                }
            }
        });

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        list.setBackground(UIManager.getColor("Tree.textBackground"));
        list.setVisibleRowCount(3);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JBScrollPane(list), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(viewers, BorderLayout.CENTER);

        createSplitPane(topPanel, bottomPanel);
    }

    private void createSplitPane(JPanel topPanel, JPanel bottomPanel) {
        JSplitPane splitPaneTopBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneTopBottom.setDividerLocation(0.20d);
        splitPaneTopBottom.setResizeWeight(0.20d);
        splitPaneTopBottom.setDividerSize(DIVIDER_SIZE);

        splitPaneTopBottom.add(topPanel, JSplitPane.TOP);
        splitPaneTopBottom.add(bottomPanel, JSplitPane.BOTTOM);

        add(splitPaneTopBottom, BorderLayout.CENTER);
    }

    public void tunnelStarted() {
        // nothing yet
    }

    public void tunnelStopped() {
        // nothing yet
    }

    public synchronized void newCall(Call call) {
        ApplicationManager.getApplication().invokeLater(() -> model.addElement(call));
    }

    public synchronized void endCall(Call call) {
        if (list.isVisible()) {
            list.repaint();
            viewers.repaint();
        }

    }

    public void wrap() {
        viewers.wrap();
    }

    public void unwrap() {
        viewers.unwrap();
    }

    public synchronized void clear() {
        model.clear();
    }

    public synchronized void clearSelected() {
        int index = list.getSelectedIndex();
        if (index != -1) {
            model.removeElementAt(index);
        }
    }

}

class CallsListSelectionListener implements ListSelectionListener {

    private ViewersPanel v;

    public CallsListSelectionListener(ViewersPanel v) {
        this.v = v;
    }

    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        Call call = (Call) list.getSelectedValue();

        if (call != null)
            v.view(call);
        else
            v.clear();

    }
}

class ViewersPanel extends JPanel {

    private JTextArea left;

    private JTextArea right;

    ViewersPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        setBackground(UIManager.getColor("Tree.textBackground"));

        left = new JTextArea();
        right = new JTextArea();

        left.setEditable(true);
        right.setEditable(true);

        left.setBackground(UIManager.getColor("Tree.textBackground"));
        right.setBackground(UIManager.getColor("Tree.textBackground"));

        JScrollPane leftScroll = new JBScrollPane(left);
        JScrollPane rightScroll = new JBScrollPane(right);

        JSplitPane splitPaneLeftRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneLeftRight.setDividerSize(CallsPanel.DIVIDER_SIZE);
        splitPaneLeftRight.setDividerLocation(0.50d);
        splitPaneLeftRight.setResizeWeight(0.50d);

        splitPaneLeftRight.add(leftScroll, JSplitPane.LEFT);
        splitPaneLeftRight.add(rightScroll, JSplitPane.RIGHT);

        add(splitPaneLeftRight, BorderLayout.CENTER);
    }

    public void view(Call call) {

        boolean asBytes = false;
        if (call == null)
            return;

        ByteArrayOutputStream leftBaos = (ByteArrayOutputStream) call
                .getOutputLogger();
        if (leftBaos == null)
            return;

        left.setText("");

        if (!asBytes) {
            left.setText(leftBaos.toString());

        } else {
            // TODO

            byte[] bytes = leftBaos.toByteArray();
            for (byte b : bytes) {
                String s = Integer.toHexString(b).toUpperCase();
                if (s.length() == 1)
                    s = "0" + s;
                left.append(s);
            }
        }
        left.setCaretPosition(0);

        ByteArrayOutputStream rightBaos = ((ByteArrayOutputStream) call
                .getInputLogger());
        if (rightBaos == null)
            return;
        right.setText(rightBaos.toString());
        right.setCaretPosition(0);
    }

    public void wrap() {
        left.setLineWrap(true);
        left.setWrapStyleWord(true);
        right.setLineWrap(true);
        right.setWrapStyleWord(true);
    }

    public void unwrap() {
        left.setLineWrap(false);
        right.setLineWrap(false);
    }

    public void clear() {
        left.setText("");
        right.setText("");
    }

}
