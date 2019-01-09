package org.raspinloop.fmi.plugin.editor.selection;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.*;
import com.intellij.util.ui.StatusText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.fmi.plugin.editor.hardware.HarwareListDialog;
import org.raspinloop.fmi.plugin.utils.HardwareAccessor;
import org.raspinloop.fmi.plugin.utils.PreferenceHardwareAcesseor;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class HardwareConfigurationSelector extends ComboboxWithBrowseButton{

        private static final String DEFAULT_HW_TEXT = "Default";
        private final HardwareConfigurationComboBoxEditor myComboboxEditor;
        private final Project project;
        private JComponent myAnchor;
        private final SortedComboBoxModel<HardwareConfigurationItem> myComboBoxModel;
        private DefaultHardwareSelector mydefaultHwSelector;
        private HardwareAccessor hardwareAccessor = new PreferenceHardwareAcesseor();

    private HardwareConfigurationItem hardwareconfig;

    public HardwareConfigurationSelector(Project project, DefaultHardwareSelector defaultHwSelector) {

            this(project);
            fillWithKnownHardware();
            setDefaultHardwareSelector(defaultHwSelector);
        }

    private void listHardwares(ActionEvent e) {
        HarwareListDialog diag = new HarwareListDialog(project);
        diag.show();
        if (diag.isOK()){
            fillWithKnownHardware();
        }

    }

    private void fillWithKnownHardware() {
        myComboBoxModel.clear();;
        hardwareAccessor.getList().forEach(h ->
                myComboBoxModel.add(new HardwareConfigurationItem(h.getComponentName())));
    }


    /**
         * This constructor can be used in UI forms. <strong>Don't forget to call {@link #setDefaultHardwareSelector(DefaultHardwareSelector)}!</strong>
         */
        HardwareConfigurationSelector(Project project) {
            super();
            this.project = project;
            myComboBoxModel = new SortedComboBoxModel<>((o1, o2) -> {
                int result = Comparing.compare(o1.getName(), o2.getName());
                if (result != 0) {
                    return result;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            });

            getComboBox().setEditable(true);
            getComboBox().setRenderer(new ColoredListCellRenderer<HardwareConfigurationItem>() {
                @Override
                protected void customizeCellRenderer(@NotNull JList<? extends HardwareConfigurationItem> list,
                                                     HardwareConfigurationItem value,
                                                     int index,
                                                     boolean selected,
                                                     boolean hasFocus) {
                    if (value != null) {
                        value.render(this, selected);
                    }
                }
            });
            myComboboxEditor = new HardwareConfigurationComboBoxEditor(myComboBoxModel);
            myComboboxEditor.getEditorComponent().setTextToTriggerEmptyTextStatus(DEFAULT_HW_TEXT);
            getComboBox().setEditor(myComboboxEditor);

            addActionListener(this::listHardwares);
        }

        @Nullable
        public String getHardwareConfigName() {
            return hardwareconfig.getName();
        }


        private void setDefaultHardwareSelector(DefaultHardwareSelector defaultHwSelector) {
            mydefaultHwSelector = defaultHwSelector;
            mydefaultHwSelector.addChangeListener(this::updateDefaultHardwarePresentation);
        }

        public void setName(@Nullable String name) {
            if (!StringUtil.isEmpty(name)) {
                HardwareConfigurationItem alternative = findOrAddCustomHardware(name);
                updateDefaultHardwarePresentation();
            }
        }

        private void updateDefaultHardwarePresentation() {
            StatusText text = myComboboxEditor.getEmptyText();
            text.clear();
            text.appendText(DEFAULT_HW_TEXT, SimpleTextAttributes.REGULAR_ATTRIBUTES);
            text.appendText(mydefaultHwSelector.getDescriptionString(), SimpleTextAttributes.GRAY_ATTRIBUTES);
        }

        private HardwareConfigurationItem findOrAddCustomHardware(@NotNull String name) {
            for (HardwareConfigurationItem item : myComboBoxModel.getItems()) {
                if (name.equals(item.getName())) {
                    return item;
                }
            }
            HardwareConfigurationItem item = new HardwareConfigurationItem(name);
            myComboBoxModel.add(item);
            return item;
        }
    }


