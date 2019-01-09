package org.raspinloop.fmi.plugin.editor.selection;

import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FmiRunnerConfigurationPanel<T extends RunConfigurationBase> extends SettingsEditor<T> {

        @Override
        protected void resetEditorFrom(@NotNull T s) {

        }

        @Override
        protected void applyEditorTo(@NotNull T s) throws ConfigurationException {

        }

        @NotNull
    @Override
    protected JComponent createEditor() {
        return null;
    }
}
