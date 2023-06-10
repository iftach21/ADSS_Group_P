package Interface.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UITaskBox {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;

    public JPanel createPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel for task input and add button
        JPanel inputPanel = new JPanel(new BorderLayout());
        taskInput = new JTextField();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Create the task list and set its model
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);

        // Create a panel for the task list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Create a panel for delete button
        JPanel deletePanel = new JPanel();
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteButtonListener());
        deletePanel.add(deleteButton);

        // Add the components to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(deletePanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String task = taskInput.getText().trim();
            if (!task.isEmpty()) {
                taskListModel.addElement(task);
                taskInput.setText("");
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskListModel.remove(selectedIndex);
            }
        }
    }
}
