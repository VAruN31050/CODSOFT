import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private double marks;

    public Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-15d %.2f", name, rollNumber, marks);
    }
}

class StudentManagementSystem {
    private ArrayList<Student> students;
    private Scanner scanner;
    private final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadStudentsFromFile();
    }

    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }

    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data.");
        }
    }

    public void addStudent() {
        String name = getValidInput("Enter student name: ");
        int rollNumber = getValidIntegerInput("Enter roll number: ");
        double marks = getValidDoubleInput("Enter marks: ");

        students.add(new Student(name, rollNumber, marks));
        saveStudentsToFile();
        System.out.println("Student added successfully.");
    }

    public void removeStudent() {
        int rollNumber = getValidIntegerInput("Enter roll number of student to remove: ");
        boolean found = false;
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                students.remove(student);
                saveStudentsToFile();
                found = true;
                System.out.println("Student removed successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("No student found with that roll number.");
        }
    }

    public void searchStudent() {
        int rollNumber = getValidIntegerInput("Enter roll number of student to search: ");
        boolean found = false;
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                System.out.println("Student found: " + student);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No student found with that roll number.");
        }
    }

    public void editStudent() {
        int rollNumber = getValidIntegerInput("Enter roll number of student to edit: ");
        boolean found = false;
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                System.out.println("Editing student: " + student);
                String newName = getValidInput("Enter new name (press Enter to keep unchanged): ");
                if (!newName.isEmpty()) student.setName(newName);
                double newMarks = getValidDoubleInput("Enter new marks (or 0 to keep unchanged): ");
                if (newMarks > 0) student.setMarks(newMarks);
                saveStudentsToFile();
                System.out.println("Student updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No student found with that roll number.");
        }
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("List of students:");
            System.out.println(String.format("%-20s %-15s %s", "Name", "Roll Number", "Marks"));
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private String getValidInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getValidIntegerInput(String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double getValidDoubleInput(String prompt) {
        double value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public void start() {
        while (true) {
            System.out.println("\nStudent Management System:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");

            int choice = getValidIntegerInput("Choose an option: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: removeStudent(); break;
                case 3: searchStudent(); break;
                case 4: editStudent(); break;
                case 5: displayAllStudents(); break;
                case 6: 
                    saveStudentsToFile();
                    System.out.println("Exiting. Goodbye!");
                    return;
                default: System.out.println("Invalid option. Try again."); break;
            }
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.start();
    }
}
