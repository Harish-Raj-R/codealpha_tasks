import java.util.*;
import java.io.*;

public class GradeTracker {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String CSV_FILE = "student_grades.csv";

    public static void main(String[] args) {
        loadFromCSV();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Student Grade Tracker ---");
            System.out.println("1. Add Student");
            System.out.println("2. Enter Grades");
            System.out.println("3. View Student Report");
            System.out.println("4. View All Reports");
            System.out.println("5. Export to CSV");
            System.out.println("6. Sort by Average");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1: addStudent(); break;
                case 2: enterGrades(); break;
                case 3: viewStudentReport(); break;
                case 4: viewAllReports(); break;
                case 5: saveToCSV(); System.out.println("Exported to CSV."); break;
                case 6: sortByAverage(); break;
                case 7: saveToCSV(); running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new Student(name));
        saveToCSV();
        System.out.println("Student added.");
    }

    private static void enterGrades() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student student = findStudent(name);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("How many grades? ");
        int count = scanner.nextInt();
        for (int i = 0; i < count; i++) {
            System.out.print("Grade " + (i + 1) + ": ");
            student.addScore(scanner.nextInt());
        }
        saveToCSV();
    }

    private static void viewStudentReport() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student s = findStudent(name);
        if (s != null) System.out.println(s);
        else System.out.println("Student not found.");
    }

    private static void viewAllReports() {
        if (students.isEmpty()) {
            System.out.println("No students.");
            return;
        }
        for (Student s : students) System.out.println(s);
    }

    private static void sortByAverage() {
        students.sort((a, b) -> Double.compare(b.getAverage(), a.getAverage()));
        saveToCSV();
        System.out.println("Sorted by average.");
    }

    private static void saveToCSV() {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("Name,Scores,Average,Highest,Lowest,Grade\n");
            for (Student s : students) {
                writer.write(s.getName() + "," + s.getScores() + "," +
                        String.format("%.2f", s.getAverage()) + "," +
                        s.getHighest() + "," +
                        s.getLowest() + "," +
                        s.getLetterGrade() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to save to CSV: " + e.getMessage());
        }
    }

    private static void loadFromCSV() {
        try (Scanner fileScanner = new Scanner(new File(CSV_FILE))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // skip header
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",", -1);
                if (data.length < 3) continue;

                Student student = new Student(data[0]);
                String scoresStr = data[1].replace("[", "").replace("]", "").trim();
                if (!scoresStr.isEmpty()) {
                    String[] scoreArray = scoresStr.split("\\s*,\\s*");
                    for (String s : scoreArray) {
                        student.addScore(Integer.parseInt(s));
                    }
                }
                students.add(student);
            }
            System.out.println("Loaded data from CSV.");
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
        }
    }

    private static Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }
}
