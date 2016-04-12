package com.github.davetrencher.timone.settings;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 22/03/16.
 */
@State(
        name = "ConfigProviderState",
        storages = {
                @Storage(id = "other", file = "$APP_CONFIG$/configProvider.xml")
        }
)
public class ConfigProvider implements ApplicationComponent, PersistentStateComponent<ConfigProvider.State> {


    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return getClass().getSimpleName();
    }

    public State myState = new State();

    public static class State {

        public List<TunnelSetting> settingsList = new ArrayList<>();

        public List<TunnelSetting> getSettingsList() {
            return settingsList;
        }

        public void setSettingsList(List<TunnelSetting> settingsList) {
            this.settingsList = settingsList;
        }

        public void addSetting(TunnelSetting setting) {
            settingsList.add(setting);
        }
    }

    @Nullable
    @Override
    public State getState() {
        return myState;
    }

    public void loadState(State state) {
        myState = state;
    }


}

