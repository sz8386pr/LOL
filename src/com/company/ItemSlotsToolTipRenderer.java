//Custom ListCellRenderer referenced from https://stackoverflow.com/questions/480261/java-swing-mouseover-text-on-jcombobox-items

package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemSlotsToolTipRenderer extends DefaultListCellRenderer {
    private List<String> tooltips;

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        JComponent comp = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        if (-1 < index && null != value && null != tooltips) {
            list.setToolTipText(tooltips.get(index));
        }
        return comp;
    }

    void setTooltips(List<String> tooltips) {
        this.tooltips = tooltips;
    }

}
