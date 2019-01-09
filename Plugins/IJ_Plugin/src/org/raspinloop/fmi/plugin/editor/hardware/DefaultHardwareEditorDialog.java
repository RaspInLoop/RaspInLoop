package org.raspinloop.fmi.plugin.editor.hardware;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.config.HardwareProperties;

import javax.swing.*;
import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultHardwareEditorDialog extends HardwareEditorDialog<HardwareProperties> {

    private HardwareProperties properties;
    private List<Property> reflectedProperties;

    public DefaultHardwareEditorDialog(Project project) {
        super(project);


    }

    @Override
    public void init(HardwareProperties property) {
        this.properties = property;
        reflectedProperties = findProperties(properties);
        init();
    }

    private List<Property> findProperties(@NotNull HardwareProperties properties) {
        try {
            return Arrays.stream(
                    Introspector.getBeanInfo(properties.getClass(), Object.class)
                            .getPropertyDescriptors()
            )
                    // filter out properties with setters only
                    .filter(pd -> Objects.nonNull(pd.getReadMethod()))
                    .map(pd -> {
                        try {
                            return new Property(pd.getName(), pd.getReadMethod().invoke(properties) );
                        } catch (IllegalAccessException | InvocationTargetException e) {
                           return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IntrospectionException e) {
            // and this, too
            return Collections.emptyList();
        }
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel topLevelPanel = new JPanel();
        GridLayout propLayout = new GridLayout(0,2);
        topLevelPanel.setLayout(propLayout);

        for (Property prop : reflectedProperties){
            addPropertiy(topLevelPanel, prop);
        }
        return topLevelPanel;
    }

    private void addPropertiy(JPanel topLevelPanel, Property prop) {
        JLabel nameLabel = new JLabel(prop.getName());
        JTextField valueField = new JTextField(prop.getValue().toString());
        topLevelPanel.add(nameLabel);
        topLevelPanel.add(valueField);
    }

    @Override
    public Class<HardwareProperties> getSupportedPropertiesForEdition() {
        return HardwareProperties.class;
    }

    @Override
    public HardwareProperties getEdited() {
        return properties;
    }

    private class Property {
        private final String name;
        private final Object value;

        Property(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        String getName() {
            return name;
        }

        Object getValue() {
            return value;
        }
    }
}
