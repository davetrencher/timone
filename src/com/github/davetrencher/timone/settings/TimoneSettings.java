package com.github.davetrencher.timone.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
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
                        id = "settings", value =  "timone_settings.xml"
                )}
)
public class TimoneSettings implements PersistentStateComponent<TimoneSettings> {

    private List<TunnelSetting> settingsList = new ArrayList<>();

    @Nullable
    public static TimoneSettings getInstance(Project project) {
        return ServiceManager.getService(project,TimoneSettings.class);
    }

    @Override
    @org.jetbrains.annotations.Nullable
    public TimoneSettings getState() {
        System.out.println("Get State Called" + settingsList.size());
        return this; //Saves all public variables to disk.
    }

    @Override
    public void loadState(TimoneSettings settings) {
        System.out.println("Load State Called" + settings.settingsList.size());
        XmlSerializerUtil.copyBean(settings, this); //restores state from disk
    }

    public List<TunnelSetting> getSettingsList() {
        return this.settingsList;
    }

    public void setSettingsList(List<TunnelSetting> settingsList) {
        this.settingsList = settingsList;
    }


}

