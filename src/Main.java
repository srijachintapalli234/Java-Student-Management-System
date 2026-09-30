import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {

        while (true) {

            displayMenu();

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
                    System.out.println("\nThank you for using Student Management System!");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice. Please select 1-6.");
            }
        }
    }

    // Display Main Menu
    private static void displayMenu() {

        System.out.println();
        System.out.println("================================================");
        System.out.println("          STUDENT MANAGEMENT SYSTEM");
        System.out.println("================================================");
        System.out.println("  1. Add Student");
        System.out.println("  2. View All Students");
        System.out.println("  3. Search Student");
        System.out.println("  4. Update Student");
        System.out.println("  5. Delete Student");
        System.out.println("  6. Exit");
        System.out.println("================================================");
        System.out.print("Enter your choice: ");
    }

    // Add Student
    private static void addStudent() {

        System.out.println("\n================================================");
        System.out.println("                 ADD STUDENT");
        System.out.println("================================================");

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
            System.out.println("\n✓ Student added successfully!");
        } else {
            System.out.println("\n✗ Failed to add student.");
        }
    }

    // View All Students
    private static void viewStudents() {

        System.out.println("\n================================================");
        System.out.println("                ALL STUDENTS");
        System.out.println("================================================");

        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf(
                "%-5s %-22s %-30s %-15s %-15s %-5s%n",
                "ID", "NAME", "EMAIL", "PHONE", "DEPARTMENT", "YEAR"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );

        for (Student student : students) {
            System.out.printf(
                    "%-5d %-22s %-30s %-15s %-15s %-5d%n",
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getDepartment(),
                    student.getYear()
            );
        }

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );
    }

    // Search Student
    private static void searchStudent() {

        System.out.println("\n================================================");
        System.out.println("               SEARCH STUDENT");
        System.out.println("================================================");

        System.out.print("Enter student ID: ");
        int id = readInt();

        Student student = studentDAO.getStudentById(id);

        if (student != null) {
            System.out.println("\n✓ Student found:");
            System.out.println("-----------------------------------------------");
            System.out.println(student);
            System.out.println("-----------------------------------------------");
        } else {
            System.out.println("\n✗ Student not found.");
        }
    }

    // Update Student
    private static void updateStudent() {

        System.out.println("\n================================================");
        System.out.println("               UPDATE STUDENT");
        System.out.println("================================================");

        System.out.print("Enter student ID: ");
        int id = readInt();

        Student existingStudent = studentDAO.getStudentById(id);

        if (existingStudent == null) {
            System.out.println("\n✗ Student not found.");
            return;
        }

        System.out.println("\nCurrent student details:");
        System.out.println(existingStudent);

        System.out.print("\nEnter new name: ");
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
            System.out.println("\n✓ Student updated successfully!");
        } else {
            System.out.println("\n✗ Failed to update student.");
        }
    }

    // Delete Student
    private static void deleteStudent() {

        System.out.println("\n================================================");
        System.out.println("               DELETE STUDENT");
        System.out.println("================================================");

        System.out.print("Enter student ID: ");
        int id = readInt();

        Student student = studentDAO.getStudentById(id);

        if (student == null) {
            System.out.println("\n✗ Student not found.");
            return;
        }

        System.out.println("\nStudent selected:");
        System.out.println(student);

        System.out.print("\nAre you sure you want to delete? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {

            if (studentDAO.deleteStudent(id)) {
                System.out.println("\n✓ Student deleted successfully!");
            } else {
                System.out.println("\n✗ Failed to delete student.");
            }

        } else {
            System.out.println("\nDelete operation cancelled.");
        }
    }

    // Read integer safely
    private static int readInt() {

        while (true) {

            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}