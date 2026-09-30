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
                    System.out.println(
                            "\nThank you for using Student Management System!"
                    );
                    return;

                default:
                    System.out.println(
                            "\nInvalid choice. Please select 1-6."
                    );
            }
        }
    }

    // ==============================
    // Main Menu
    // ==============================
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

    // ==============================
    // Add Student
    // ==============================
    private static void addStudent() {

        System.out.println("\n================================================");
        System.out.println("                 ADD STUDENT");
        System.out.println("================================================");

        String name = readNonEmpty("Enter name: ");
        String email = readEmail();
        String phone = readPhone();
        String department = readNonEmpty("Enter department: ");
        int year = readYear();

        Student student = new Student(
                name,
                email,
                phone,
                department,
                year
        );

        try {

            if (studentDAO.addStudent(student)) {
                System.out.println("\n✓ Student added successfully!");
            } else {
                System.out.println("\n✗ Failed to add student.");
            }

        } catch (Exception e) {

            System.out.println(
                    "\n✗ Unable to add student due to a database error."
            );
            System.out.println("Please check your database connection.");
        }
    }

    // ==============================
    // View All Students
    // ==============================
    private static void viewStudents() {

        System.out.println("\n================================================");
        System.out.println("                ALL STUDENTS");
        System.out.println("================================================");

        try {

            List<Student> students = studentDAO.getAllStudents();

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            System.out.printf(
                    "%-5s %-22s %-30s %-15s %-15s %-5s%n",
                    "ID",
                    "NAME",
                    "EMAIL",
                    "PHONE",
                    "DEPARTMENT",
                    "YEAR"
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

        } catch (Exception e) {

            System.out.println(
                    "\n✗ Unable to retrieve students."
            );
            System.out.println(
                    "Please check your database connection."
            );
        }
    }

    // ==============================
    // Search Student
    // ==============================
    private static void searchStudent() {

        System.out.println("\n================================================");
        System.out.println("               SEARCH STUDENT");
        System.out.println("================================================");

        System.out.print("Enter student ID: ");
        int id = readPositiveInt();

        try {

            Student student = studentDAO.getStudentById(id);

            if (student != null) {

                System.out.println("\n✓ Student found:");
                System.out.println(
                        "-----------------------------------------------"
                );
                System.out.println(student);
                System.out.println(
                        "-----------------------------------------------"
                );

            } else {

                System.out.println("\n✗ Student not found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "\n✗ Unable to search student."
            );
            System.out.println(
                    "Please check your database connection."
            );
        }
    }

    // ==============================
    // Update Student
    // ==============================
    private static void updateStudent() {

        System.out.println("\n================================================");
        System.out.println("               UPDATE STUDENT");
        System.out.println("================================================");

        System.out.print("Enter student ID: ");
        int id = readPositiveInt();

        try {

            Student existingStudent = studentDAO.getStudentById(id);

            if (existingStudent == null) {

                System.out.println("\n✗ Student not found.");
                return;
            }

            System.out.println("\nCurrent student details:");
            System.out.println(
                    "-----------------------------------------------"
            );
            System.out.println(existingStudent);
            System.out.println(
                    "-----------------------------------------------"
            );

            String name = readNonEmpty("\nEnter new name: ");
            String email = readEmail();
            String phone = readPhone();
            String department =
                    readNonEmpty("Enter new department: ");
            int year = readYear();

            Student updatedStudent = new Student(
                    id,
                    name,
                    email,
                    phone,
                    department,
                    year
            );

            System.out.println("\nNew student details:");
            System.out.println(
                    "-----------------------------------------------"
            );
            System.out.println(updatedStudent);
            System.out.println(
                    "-----------------------------------------------"
            );

            String confirmation = readYesNo(
                    "Are you sure you want to update this student? (yes/no): "
            );

            if (confirmation.equalsIgnoreCase("yes")) {

                if (studentDAO.updateStudent(updatedStudent)) {

                    System.out.println(
                            "\n✓ Student updated successfully!"
                    );

                } else {

                    System.out.println(
                            "\n✗ Failed to update student."
                    );
                }

            } else {

                System.out.println(
                        "\nUpdate operation cancelled."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "\n✗ Unable to update student."
            );
            System.out.println(
                    "Please check your database connection."
            );
        }
    }

    // ==============================
    // Delete Student
    // ==============================
    private static void deleteStudent() {

        System.out.println("\n================================================");
        System.out.println("               DELETE STUDENT");
        System.out.println("================================================");

        System.out.print("Enter student ID: ");
        int id = readPositiveInt();

        try {

            Student student = studentDAO.getStudentById(id);

            if (student == null) {

                System.out.println("\n✗ Student not found.");
                return;
            }

            System.out.println("\nStudent selected:");
            System.out.println(
                    "-----------------------------------------------"
            );
            System.out.println(student);
            System.out.println(
                    "-----------------------------------------------"
            );

            String confirmation = readYesNo(
                    "Are you sure you want to delete this student? (yes/no): "
            );

            if (confirmation.equalsIgnoreCase("yes")) {

                if (studentDAO.deleteStudent(id)) {

                    System.out.println(
                            "\n✓ Student deleted successfully!"
                    );

                } else {

                    System.out.println(
                            "\n✗ Failed to delete student."
                    );
                }

            } else {

                System.out.println(
                        "\nDelete operation cancelled."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "\n✗ Unable to delete student."
            );
            System.out.println(
                    "Please check your database connection."
            );
        }
    }

    // ==============================
    // Read Non-Empty Text
    // ==============================
    private static String readNonEmpty(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty. Please try again."
            );
        }
    }

    // ==============================
    // Read Email
    // ==============================
    private static String readEmail() {

        while (true) {

            System.out.print("Enter email: ");

            String email = scanner.nextLine().trim();

            if (email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            )) {

                return email;
            }

            System.out.println(
                    "Invalid email. Please enter a valid email address."
            );
        }
    }

    // ==============================
    // Read Phone
    // ==============================
    private static String readPhone() {

        while (true) {

            System.out.print("Enter phone: ");

            String phone = scanner.nextLine().trim();

            if (phone.matches("\\d{10}")) {
                return phone;
            }

            System.out.println(
                    "Invalid phone. Enter exactly 10 digits."
            );
        }
    }

    // ==============================
    // Read Year
    // ==============================
    private static int readYear() {

        while (true) {

            System.out.print("Enter year: ");

            try {

                int year =
                        Integer.parseInt(scanner.nextLine().trim());

                if (year >= 1 && year <= 4) {
                    return year;
                }

                System.out.println(
                        "Invalid year. Please enter a number from 1 to 4."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number from 1 to 4."
                );
            }
        }
    }

    // ==============================
    // Read Integer
    // ==============================
    private static int readInt() {

        while (true) {

            try {

                String input = scanner.nextLine().trim();

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }

    // ==============================
    // Read Positive Integer
    // ==============================
    private static int readPositiveInt() {

        while (true) {

            int number = readInt();

            if (number > 0) {
                return number;
            }

            System.out.print(
                    "Please enter a positive number: "
            );
        }
    }

    // ==============================
    // Read Yes / No
    // ==============================
    private static String readYesNo(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("yes") ||
                    input.equalsIgnoreCase("no")) {

                return input;
            }

            System.out.println(
                    "Please enter only yes or no."
            );
        }
    }
}