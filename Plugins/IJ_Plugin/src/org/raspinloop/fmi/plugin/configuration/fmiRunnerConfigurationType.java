package org.raspinloop.fmi.plugin.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.raspinloop.fmi.plugin.utils.Icons;

import javax.swing.*;

public class fmiRunnerConfigurationType implements ConfigurationType {
    @Nls
    @Override
    public String getDisplayName() {
        return "RaspInLoop";
    }

    @Nls
    @Override
    public String getConfigurationTypeDescription() {
        return "Run your project with Simulated Hardware";
    }

    @Override
    public Icon getIcon() {
        return Icons.getInstance().getIcon();
    }

    @NotNull
    @Override
    public String getId() {
        return "RaspInLoop.fmi.runner";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        FmiRunnerConfigurationFactory factory = new FmiRunnerConfigurationFactory(this);
        return new ConfigurationFactory[]{factory};
    }
}
