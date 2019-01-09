package org.raspinloop.fmi.plugin.editor.component;

import com.intellij.ui.IdeBorderFactory;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GPIOHardware;
import org.raspinloop.config.Pin;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GpioAssignation extends JPanel{

    private String labelText;
    private Map<Integer, Pin> pins;

    public GpioAssignation(LayoutManager layout) {
        super(layout);
    }

    public GpioAssignation(String labelText) {
        super(new GridBagLayout());
        this.labelText = labelText;
        setBorder(IdeBorderFactory.createTitledBorder(labelText, false));

    }

    public void setBoard(BoardHardware board){
        this.pins = board.getSupportedPin().stream().collect(
                Collectors.toMap(Pin::getAddress, Function.identity() )  );
        removeAll();
        for (int i = 0;  i <pins.size(); i++) {
            Pin pin = pins.get(i);
            JLabel label = new JLabel(pin.getName());
            Insets insets = new Insets(0, 0, 4, 0);
            final int weighty = i == pins.size() - 1 ? 1 : 0;
            add(label, new GridBagConstraints(1, 1 + i, 1, 1, 0, weighty, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            ButtonGroup group = new ButtonGroup();
            JRadioButton input = new JRadioButton("Input");
            add(input, new GridBagConstraints(2, 1 + i, 1, 1, 0, weighty, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            group.add(input);
            JRadioButton output = new JRadioButton("Output");
            group.add(output);
            add(output, new GridBagConstraints(3, 1 + i, 1, 1, 0, weighty, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
            JRadioButton unused = new JRadioButton("Unused");
            group.add(unused);
            add(unused, new GridBagConstraints(4, 1 + i, 1, 1, 0, weighty, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        }
    }
}

