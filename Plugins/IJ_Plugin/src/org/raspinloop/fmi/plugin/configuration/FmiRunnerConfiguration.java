package org.raspinloop.fmi.plugin.configuration;

import com.intellij.codeInsight.daemon.impl.analysis.JavaModuleGraphUtil;
import com.intellij.debugger.settings.DebuggerSettings;
import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.JavaRunConfigurationExtensionManager;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.BaseJavaApplicationCommandLineState;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.ArgumentFileFilter;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.KillableProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdkVersion;
import com.intellij.openapi.projectRoots.JdkUtil;
import com.intellij.openapi.projectRoots.ex.JavaSdkUtil;
import com.intellij.psi.PsiJavaModule;
import com.intellij.util.PathsList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.fmi.plugin.editor.selection.FmiApplicationConfigurable;

import java.util.Map;

public class FmiRunnerConfiguration extends ApplicationConfiguration {
    private final Project project;

    public FmiRunnerConfiguration(Project project, FmiRunnerConfigurationFactory factory, String name) {
        super(name, project, factory);
        this.project = project;
    }


    @Override
    @NotNull
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        SettingsEditorGroup<FmiRunnerConfiguration> group = new SettingsEditorGroup<>();
        group.addEditor("FMI Hardware configuration", new FmiApplicationConfigurable(getProject()));
        JavaRunConfigurationExtensionManager.getInstance().appendEditors(this, group);
        group.addEditor(ExecutionBundle.message("logs.tab.title"), new LogConfigurationPanel<>());
        return group;
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        final JavaCommandLineState state = new FmiApplicationCommandLineState<FmiRunnerConfiguration>(this, executionEnvironment);
        JavaRunConfigurationModule module = getConfigurationModule();
        state.setConsoleBuilder(TextConsoleBuilderFactory.getInstance().createBuilder(getProject(), module.getSearchScope()));
        return state;
    }

    public String getHardwareConfigFilename() {
        return "";
    }


    public static class FmiApplicationCommandLineState<T extends ApplicationConfiguration> extends BaseJavaApplicationCommandLineState<FmiRunnerConfiguration> {
        public FmiApplicationCommandLineState(@NotNull final FmiRunnerConfiguration configuration, final ExecutionEnvironment environment) {
            super(environment, configuration);
        }

        @Override
        protected JavaParameters createJavaParameters() throws ExecutionException {
            final JavaParameters params = new JavaParameters();
            FmiRunnerConfiguration configuration = getConfiguration();
            params.setShortenCommandLine(configuration.getShortenCommandLine(), configuration.getProject());

            String hardwareConfigFilename = configuration.getHardwareConfigFilename();

            final JavaRunConfigurationModule module = myConfiguration.getConfigurationModule();
            final String jreHome = myConfiguration.ALTERNATIVE_JRE_PATH_ENABLED ? myConfiguration.ALTERNATIVE_JRE_PATH : null;
            if (module.getModule() != null) {
                DumbService.getInstance(module.getProject()).runWithAlternativeResolveEnabled(() -> {
                    int classPathType = JavaParametersUtil.getClasspathType(module, myConfiguration.MAIN_CLASS_NAME, false);
                    JavaParametersUtil.configureModule(module, params, classPathType, jreHome);
                });
            }
            else {
                JavaParametersUtil.configureProject(module.getProject(), params, JavaParameters.JDK_AND_CLASSES_AND_TESTS, jreHome);
            }

            params.setMainClass(myConfiguration.MAIN_CLASS_NAME);

            setupJavaParameters(params);

            setupModulePath(params, module);

            return params;
        }

        @Override
        protected GeneralCommandLine createCommandLine() throws ExecutionException {
            GeneralCommandLine line = super.createCommandLine();
            Map<String, String> content = line.getUserData(JdkUtil.COMMAND_LINE_CONTENT);
            if (content != null) {
                content.forEach((key, value) -> addConsoleFilters(new ArgumentFileFilter(key, value)));
            }
            return line;
        }

        @NotNull
        @Override
        protected OSProcessHandler startProcess() throws ExecutionException {
            // should start FMI pxory and delegate start of process to it !
            OSProcessHandler processHandler = super.startProcess();
            if (processHandler instanceof KillableProcessHandler && DebuggerSettings.getInstance().KILL_PROCESS_IMMEDIATELY) {
                ((KillableProcessHandler)processHandler).setShouldKillProcessSoftly(false);
            }
            return processHandler;
        }

        private static void setupModulePath(JavaParameters params, JavaRunConfigurationModule module) {
            if (JavaSdkUtil.isJdkAtLeast(params.getJdk(), JavaSdkVersion.JDK_1_9)) {
                PsiJavaModule mainModule = DumbService.getInstance(module.getProject()).computeWithAlternativeResolveEnabled(
                        () -> JavaModuleGraphUtil.findDescriptorByElement(module.findClass(params.getMainClass())));
                if (mainModule != null) {
                    params.setModuleName(mainModule.getName());
                    PathsList classPath = params.getClassPath(), modulePath = params.getModulePath();
                    modulePath.addAll(classPath.getPathList());
                    classPath.clear();
                }
            }
        }
    }
}
