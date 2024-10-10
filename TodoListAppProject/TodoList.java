import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // Import ArrayList
import java.util.Scanner;   // Import Scanner
import javax.swing.SwingUtilities; // Import for SwingUtilities

public class TodoList {
    private static ArrayList<String> tasks = new ArrayList<>();
    
    // GUI components
    private static DefaultListModel<String> listModel; // Make it static
    private JList<String> todoList;
    private JTextField taskField;

    public TodoList() {
        // Initialize the GUI components
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // List model and GUI components
        listModel = new DefaultListModel<>(); // Initialize once
        todoList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(todoList);
        frame.add(scrollPane, BorderLayout.CENTER);

        taskField = new JTextField();
        frame.add(taskField, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                if (!task.isEmpty()) {
                    tasks.add(task);
                    listModel.addElement(task); // Add to list model
                    System.out.println("Added Task (GUI): " + task); // Print to console
                    taskField.setText(""); // Clear the text field
                    taskField.requestFocus(); // Request focus back to the text field
                }
            }
        });
        frame.add(addButton, BorderLayout.NORTH);

        JButton removeButton = new JButton("Remove Task");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = todoList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String removedTask = tasks.remove(selectedIndex);
                    listModel.remove(selectedIndex); // Remove from list model
                    System.out.println("Removed Task (GUI): " + removedTask); // Print to console
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a task to remove.");
                }
            }
        });
        frame.add(removeButton, BorderLayout.EAST);

        frame.setVisible(true);
        taskField.requestFocus(); // Set initial focus to the text field
    }

    public static void main(String[] args) {
        // Create the GUI in the event dispatch thread
        SwingUtilities.invokeLater(() -> new TodoList());

        // Console-based functionality
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Give user some time to launch GUI
        try {
            Thread.sleep(1000); // Sleep for 1 second to allow GUI to open
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        do {
            System.out.println("To-Do List Options (Console):");
            System.out.println("1. Add Task (Console)");
            System.out.println("2. View Tasks (Console)");
            System.out.println("3. Remove Task (Console)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter task: ");
                    String task = scanner.nextLine();
                    if (!task.isEmpty()) {
                        tasks.add(task);
                        listModel.addElement(task); // Add to list model directly
                        System.out.println("Added Task (Console): " + task); // Print to console
                    }
                    break;
                case 2:
                    System.out.println("Your Tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Enter task number to remove: ");
                    int taskNumber = scanner.nextInt();
                    if (taskNumber > 0 && taskNumber <= tasks.size()) {
                        String removedTask = tasks.remove(taskNumber - 1);
                        System.out.println("Removed Task (Console): " + removedTask);
                        listModel.remove(taskNumber - 1); // Remove from list model
                    } else {
                        System.out.println("Invalid task number.\n");
                    }
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        } while (choice != 4);

        scanner.close();
    }
}
