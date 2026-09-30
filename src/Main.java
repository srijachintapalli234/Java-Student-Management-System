import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n=================================");
            System.out.println("     STUDENT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    System.out.println("Thank you for using Student Management System!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add Student
    private static void addStudent() {

        System.out.println("\n--- Add Student ---");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter department: ");
        String department = scanner.nextLine();

        System.out.print("Enter year: ");
        int year = readInt();

        Student student = new Student(
                name,
                email,
                phone,
                department,
                year
        );

        if (studentDAO.addStudent(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    // View All Students
    private static void viewStudents() {

        System.out.println("\n--- All Students ---");

        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    // Search Student
    private static void searchStudent() {

        System.out.println("\n--- Search Student ---");

        System.out.print("Enter student ID: ");
        int id = readInt();

        Student student = studentDAO.getStudentById(id);

        if (student != null) {
            System.out.println("\nStudent found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Update Student
    private static void updateStudent() {

        System.out.println("\n--- Update Student ---");

        System.out.print("Enter student ID: ");
        int id = readInt();

        Student existingStudent = studentDAO.getStudentById(id);

        if (existingStudent == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        System.out.print("Enter new phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter new department: ");
        String department = scanner.nextLine();

        System.out.print("Enter new year: ");
        int year = readInt();

        Student updatedStudent = new Student(
                id,
                name,
                email,
                phone,
                department,
                year
        );

        if (studentDAO.updateStudent(updatedStudent)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Failed to update student.");
        }
    }

    // Delete Student
    private static void deleteStudent() {

        System.out.println("\n--- Delete Student ---");

        System.out.print("Enter student ID: ");
        int id = readInt();

        Student student = studentDAO.getStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student: " + student);

        System.out.print("Are you sure you want to delete? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {

            if (studentDAO.deleteStudent(id)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }

        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    // Read integer safely
    private static int readInt() {

        while (true) {

            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}