package org.raspinloop.fmi.plugin.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class FmiRunnerConfigurationFactory extends ConfigurationFactory {
    public FmiRunnerConfigurationFactory(@NotNull fmiRunnerConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new FmiRunnerConfiguration(project, this, "Fmi Runner");
    }


    @Override
    public boolean isConfigurationSingletonByDefault() {
        return true;
    }
}
