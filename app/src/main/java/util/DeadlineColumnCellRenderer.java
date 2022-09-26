/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import model.Tasks;

/**
 *
 * @author valte
 */
public class DeadlineColumnCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        //Cells are by default rendered as a JLabel.
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        label.setHorizontalAlignment( JLabel.CENTER );

        //Get the status for the current row.
        TaskTableModel taskModel = (TaskTableModel) table.getModel();
        Tasks task = taskModel.getTasks().get(row);

        if (task.getDeadline().equals(new Date())) {
            label.setBackground(Color.YELLOW);
        } else {
            if (task.getDeadline().after(new Date())) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(Color.RED);
            }
        }
        //Return the JLabel which renders the cell.
        return label;
    }
    
}
