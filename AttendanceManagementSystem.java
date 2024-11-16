import java.sql.*;
import java.util.*;

// Student Class
class Student {
    private int id;
    private String name;
    private String email;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}

// StudentDAO Class
class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public void addStudent(Student student) {
        String query = "INSERT INTO students (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                student.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Failed to add student.");
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
                student.setId(resultSet.getInt("id"));
                return student;
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve student.");
            e.printStackTrace();
        }
        return null;
    }

    public void updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setInt(3, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update student.");
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
    
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
                student.setId(resultSet.getInt("id"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve students.");
            e.printStackTrace();
        }
        return students;
    }
    
    
}

// AttendanceDAO Class
class AttendanceDAO {
    private Connection connection;

    public AttendanceDAO(Connection connection) {
        this.connection = connection;
    }

    public void markAttendance(int studentId, String date, String status) {
        String query = "INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setString(2, date);
            statement.setString(3, status);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to mark attendance.");
            e.printStackTrace();
        }
    }

    public void viewAttendanceReport() {
        String query = "SELECT students.name, students.email, attendance.date, attendance.status " +
                       "FROM attendance " +
                       "JOIN students ON attendance.student_id = students.id " +
                       "ORDER BY attendance.date";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Attendance Report:");
            while (resultSet.next()) {
                System.out.println("Student: " + resultSet.getString("name") +
                                   ", Email: " + resultSet.getString("email") +
                                   ", Date: " + resultSet.getString("date") +
                                   ", Status: " + resultSet.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve attendance report.");
            e.printStackTrace();
        }
    }
}

// Main Class: AttendanceManagementSystem
public class AttendanceManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/attendance_system?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Change according to your MySQL username
    private static final String PASS = "ch.dhanush2006"; // Change according to your MySQL password

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Scanner scanner = new Scanner(System.in);

            StudentDAO studentDAO = new StudentDAO(connection);
            AttendanceDAO attendanceDAO = new AttendanceDAO(connection);

            while (true) {
                System.out.println("\nAttendance Management System:");
                System.out.println("1. Add Student");
                System.out.println("2. Mark Attendance");
                System.out.println("3. View Attendance Report");
                System.out.println("4. View All Students");
                System.out.println("5. Get Student by ID");
                System.out.println("6. Update Student Information");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Add Student
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student email: ");
                        String email = scanner.nextLine();
                        Student student = new Student(name, email);
                        studentDAO.addStudent(student);
                        System.out.println("Student added: " + student);
                        break;

                    case 2:
                        // Mark Attendance
                        System.out.print("Enter student ID: ");
                        int studentId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter date (yyyy-mm-dd): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter status (Present/Absent): ");
                        String status = scanner.nextLine();
                        attendanceDAO.markAttendance(studentId, date, status);
                        System.out.println("Attendance marked successfully.");
                        break;

                    case 3:
                        // View Attendance Report
                        attendanceDAO.viewAttendanceReport();
                        break;
                        
                    case 4:
                        // View All Students
                        System.out.println("All Students:");
                        studentDAO.getAllStudents().forEach(System.out::println);
                        break;
                        
                    case 5:
                        // Get Student by ID
                        System.out.print("Enter student ID: ");
                        int searchId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Student foundStudent = studentDAO.getStudentById(searchId);
                        if (foundStudent != null) {
                            System.out.println("Student found: " + foundStudent);
                        } else {
                            System.out.println("Student not found with ID: " + searchId);
                        }
                        break;
                        
                    case 6:
                        // Update Student Information
                        System.out.print("Enter student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Student studentToUpdate = studentDAO.getStudentById(updateId);
                        if (studentToUpdate != null) {
                            System.out.println("Current Info: " + studentToUpdate);
                            System.out.print("Enter new name (leave blank to keep current): ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter new email (leave blank to keep current): ");
                            String newEmail = scanner.nextLine();
                            
                            if (!newName.isEmpty()) {
                                studentToUpdate.setName(newName);
                            }
                            if (!newEmail.isEmpty()) {
                                studentToUpdate.setEmail(newEmail);
                            }
                            
                            studentDAO.updateStudent(studentToUpdate);
                            System.out.println("Student updated: " + studentToUpdate);
                        } else {
                            System.out.println("Student not found with ID: " + updateId);
                        }
                        break;
                        
                    case 7:
                            // Exit
                            System.out.println("Exiting...");
                             return;
                        
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}