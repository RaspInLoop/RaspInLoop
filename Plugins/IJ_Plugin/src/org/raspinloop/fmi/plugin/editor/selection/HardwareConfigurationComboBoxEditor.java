package org.raspinloop.fmi.plugin.editor.selection;

import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.ui.SortedComboBoxModel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.StatusText;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HardwareConfigurationComboBoxEditor extends BasicComboBoxEditor {
    public static final TextComponentAccessor<JComboBox> TEXT_COMPONENT_ACCESSOR = new HardwareComboBoxTextComponentAccessor();
    private final SortedComboBoxModel<HardwareConfigurationItem> myComboBoxModel;


    public HardwareConfigurationComboBoxEditor(SortedComboBoxModel<HardwareConfigurationItem> comboBoxModel){
        myComboBoxModel = comboBoxModel;
    }

    @Override
    public void setItem(Object anObject) {
        editor.setText(anObject == null ? "" : ((HardwareConfigurationItem)anObject).getPresentableText());
    }

    @Override
    public Object getItem() {
        String text = editor.getText().trim();
        for (HardwareConfigurationItem item : myComboBoxModel.getItems()) {
            if (item.getPresentableText().equals(text)) {
                return item;
            }
        }
        return new HardwareConfigurationItem(text);
    }

    @Override
    protected JTextField createEditorComponent() {
        JBTextField field = new JBTextField();
        field.setBorder(null);
        field.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                update(e);
            }
            @Override public void focusLost(FocusEvent e) {
                update(e);
            }

            private void update(FocusEvent e) {
                Component c = e.getComponent().getParent();
                if (c != null) {
                    c.revalidate();
                    c.repaint();
                }
            }
        });

        return field;
    }

    public StatusText getEmptyText() {
        return getEditorComponent().getEmptyText();
    }

    public JBTextField getEditorComponent() {
        return (JBTextField)super.getEditorComponent();
    }

    private static class HardwareComboBoxTextComponentAccessor implements TextComponentAccessor<JComboBox> {
        @Override
        public String getText(JComboBox component) {
            Object item = component.getEditor().getItem();
            return item != null ? ((HardwareConfigurationItem)item).getPresentableText() : "";
        }

        @Override
        public void setText(JComboBox component, @NotNull String text) {
            component.getEditor().setItem(new HardwareConfigurationItem(text));
        }
    }
}
