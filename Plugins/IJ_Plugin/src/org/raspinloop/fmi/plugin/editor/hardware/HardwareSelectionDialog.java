package org.raspinloop.fmi.plugin.editor.hardware;

import com.intellij.ide.util.gotoByName.*;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.NameUtil;
import com.intellij.util.Processor;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.config.HardwareProperties;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class HardwareSelectionDialog extends DialogWrapper {

    protected final Project project;

    private ChooseByNameModel model;
    private ChooseByNameItemProvider provider;
    private ChooseByNamePopupComponent.Callback callback;
    private HardwareSelectionPanel hardwareSelectionPanel;
    private JPanel dummyPanel;

    protected HardwareSelectionDialog(Project project) {
        super(project);
        this.project = project;
        model = getModel();
        provider = getProvider();
        callback = getCallback();
        init();
    }

    protected abstract ChooseByNameModel getModel();

    protected ChooseByNameItemProvider getProvider() {
        return new Provider();
    }

    @NotNull
    protected Callback getCallback() {
        return new Callback();
    }

    private HardwareProperties theChosenOne;

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        setTitle("RaspInLoop Hardware Selection");
        dummyPanel = new JPanel(new BorderLayout());
        hardwareSelectionPanel = new HardwareSelectionPanel(project, model, provider, getInitialSerach()){
            @Override
            protected void close(final boolean isOk) {
                super.close(isOk);

                if (isOk) {
                    doOKAction();
                }
                else {
                    doCancelAction();
                }
            }

            @Override
            protected void initUI(final ChooseByNamePopupComponent.Callback callback,
                                  final ModalityState modalityState,
                                  boolean allowMultipleSelection) {
                super.initUI(callback, modalityState, allowMultipleSelection);
                dummyPanel.add(hardwareSelectionPanel.getPanel(), BorderLayout.CENTER);
            }

            @Override
            protected void showTextFieldPanel() {
            }

            @Override
            protected void chosenElementMightChange() {
                handleSelectionChanged();
            }
        };
        SwingUtilities.invokeLater(() -> hardwareSelectionPanel.invoke(callback, ModalityState.stateForComponent(getRootPane()), false));
        dummyPanel.setPreferredSize(new Dimension(500,150));  // TODO: must be dynamic
        return dummyPanel;
    }


    public HardwareProperties getSelected() {
        Object chosen = hardwareSelectionPanel.getChosenElement();
        if (chosen instanceof HardwareSelectionItem)
            return ((HardwareSelectionItem)chosen).getProperty();
        return theChosenOne;
    }

    private void handleSelectionChanged(){
        final HardwareProperties theChosenOne = getSelected();
        setOKActionEnabled(theChosenOne != null);
    }


    private class Callback extends ChooseByNamePopupComponent.Callback {

        @Override
        public void onClose() {
            super.onClose();
        }

         @Override
        public void elementChosen(Object element) {
            if (element instanceof HardwareSelectionItem)
                theChosenOne = ((HardwareSelectionItem) element).getProperty();
            close(OK_EXIT_CODE);
        }
    }

    protected class Provider implements ChooseByNameItemProvider {

        private String myPattern;
        private Pattern myCompiledPattern;

        @NotNull
        @Override
        public List<String> filterNames(@NotNull ChooseByNameBase base, @NotNull String[] names, @NotNull String pattern) {
            return null;
        }

        @Override
        public boolean filterElements(@NotNull ChooseByNameBase base, @NotNull String pattern, boolean everywhere, @NotNull ProgressIndicator cancelled, @NotNull Processor<Object> consumer) {
            Arrays.stream(model.getNames(true))
                   .filter(n -> matches(n, pattern))
                  .map(n -> model.getElementsByName(n,true, ""))
                  .map(a -> a[0]) // should always containt 1 element due to previous filter
                  .forEach(consumer::process);
            return true;
        }

        private boolean matches(@NotNull final String name, @NotNull final String pattern) {
            final Pattern compiledPattern = getTaskPattern(pattern);
            if (compiledPattern == null) {
                return false;
            }

            return new Perl5Matcher().matches(name, compiledPattern);
        }

        @Nullable
        private Pattern getTaskPattern(String pattern) {
            if (!Comparing.strEqual(pattern, myPattern)) {
                myCompiledPattern = null;
                myPattern = pattern;
            }
            if (myCompiledPattern == null) {
                final String regex = "^.*" + NameUtil.buildRegexp(pattern, 0, true, true);

                final Perl5Compiler compiler = new Perl5Compiler();
                try {
                    myCompiledPattern = compiler.compile(regex);
                }
                catch (MalformedPatternException ignored) {
                }
            }
            return myCompiledPattern;
        }
    }

    protected class Model extends ListChooseByNameModel<HardwareSelectionItem> {
        public Model(String prompt, String notInMessage, List<HardwareSelectionItem> items) {
            super(project, prompt, notInMessage, items);
        }
    }

    protected class HardwareSelectionItem implements ChooseByNameItem {
        public HardwareProperties getProperty() {
            return property;
        }

        final private HardwareProperties property;

        public HardwareSelectionItem(HardwareProperties property) {
            this.property = property;
        }

        @Override
        public String getName() {
            return property.getComponentName();
        }

        @Override
        public String getDescription() {
            return property.getImplementationClassName();
        }
    }

    protected String getInitialSerach() {
        return "*";
    }


    protected List<HardwareSelectionItem> wrap(Collection<HardwareProperties> allHardware) {
        return allHardware.stream()
                .map(HardwareSelectionItem::new)
                .collect(Collectors.toList());
    }
    /**
     * Some classes implemting HardwareProperties are only delegates, we check here if implementation is not a delegate
     * @param hardwareProperties
     * @return if class is not a delegate
     */

    protected boolean isRealImplementation(HardwareProperties hardwareProperties) {
        try{
            hardwareProperties.getType();
            return true;
        }
        catch (Exception e) { // not able to get typse -> should be a delegate
            return false;
        }
    }
}

