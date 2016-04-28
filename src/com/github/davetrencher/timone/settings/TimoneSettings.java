package com.github.davetrencher.timone.settings;

import com.github.davetrencher.timone.net.Tunnel;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.components.*;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 22/03/16.
 */
@State(
        name = "TimoneSettings",
        storages = {
                @Storage(
                        value =  "/timone_settings.xml"
                )}
)
public class TimoneSettings implements PersistentStateComponent<Element>, ApplicationComponent {

    private static final String ELEMENT_TIMONE_SETTINGS = "timoneSettings";
    private static final String ELEMENT_TUNNEL = "tunnel";
    private static final String ELEMENT_SOURCE = "source";
    private static final String ELEMENT_DESTINATION = "destination";
    private static final String ATTRIBUTE_PORT = "port";
    private static final String ATTRIBUTE_HOST = "host";

    private List<TunnelSetting> settingsList = new ArrayList<>();

    @Override
    public void loadState(Element timoneSettings) {

        List<Element> tunnels = timoneSettings.getChildren();

        tunnels.forEach(tunnel -> {

            Element source = tunnel.getChild(ELEMENT_SOURCE);
            int srcPort = Integer.parseInt(source.getAttributeValue(ATTRIBUTE_PORT));

            Element destination = tunnel.getChild(ELEMENT_DESTINATION);
            String destHost = destination.getAttributeValue(ATTRIBUTE_HOST);
            int destPort = Integer.parseInt(destination.getAttributeValue(ATTRIBUTE_PORT));
            TunnelSetting tunnelSetting = new TunnelSetting(srcPort,destHost,destPort);

            settingsList.add(tunnelSetting);
        });

    }


    @Nullable
    @Override
    public Element getState() {

        Element timoneSettings = new Element(ELEMENT_TIMONE_SETTINGS);
        settingsList.forEach(tunnelSetting -> {
            Element tunnel = new Element(ELEMENT_TUNNEL);
            Element source = new Element(ELEMENT_SOURCE);
            source.setAttribute(ATTRIBUTE_PORT,String.valueOf(tunnelSetting.getSrcPort()));
            tunnel.addContent(source);

            Element target = new Element(ELEMENT_DESTINATION);
            target.setAttribute(ATTRIBUTE_HOST,tunnelSetting.getDestHost());
            target.setAttribute(ATTRIBUTE_PORT,String.valueOf(tunnelSetting.getDestPort()));
            tunnel.addContent(target);

            timoneSettings.addContent(tunnel);
        });

        return timoneSettings;
    }

    public List<TunnelSetting> getSettingsList() {
        return settingsList;
    }

    public void setSettingsList(List<TunnelSetting> settingsList) {
        this.settingsList = settingsList;
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return this.getClass().getName();
    }
}

