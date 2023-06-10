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

        // Create a label for the task list
        JLabel label = new JLabel("Todo List");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(51, 102, 204)); // Set a custom background color

        // Create a panel for the label
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(new Color(240, 240, 240)); // Set a custom background color
        labelPanel.add(label, BorderLayout.CENTER);

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
        JButton deleteButton = new JButton("Complete mission");
        deleteButton.addActionListener(new DeleteButtonListener());
        deletePanel.add(deleteButton);

        // Create a panel for the label and inputPanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        // Add the components to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
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
