import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

interface IEmployee {
    void showInfo();
}

class Employee implements IEmployee, Serializable {
    private static final long serialVersionUID = 1L;
    private static int employeeCount = 0;
    protected int ID;
    protected String fullName;
    protected String birthDay;
    protected String phone;
    protected String email;
    protected String employeeType;

    public Employee(String fullName, String birthDay, String phone, String email, String employeeType) {
        this.ID = ++employeeCount;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.phone = phone;
        this.email = email;
        this.employeeType = employeeType;
    }

    public static int getEmployeeCount() {
        return employeeCount;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void showInfo() {
        System.out.println("ID: " + ID);
        System.out.println("Full Name: " + fullName);
        System.out.println("Birth Day: " + birthDay);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Employee Type: " + employeeType);
    }
}

class Experience extends Employee {
    private int expInYear;
    private String proSkill;

    public Experience(String fullName, String birthDay, String phone, String email, int expInYear, String proSkill) {
        super(fullName, birthDay, phone, email, "Experience");
        this.expInYear = expInYear;
        this.proSkill = proSkill;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Experience in Years: " + expInYear);
        System.out.println("Professional Skill: " + proSkill);
    }
}

class Fresher extends Employee {
    private String graduationDate;
    private String graduationRank;
    private String education;

    public Fresher(String fullName, String birthDay, String phone, String email, String graduationDate,
            String graduationRank, String education) {
        super(fullName, birthDay, phone, email, "Fresher");
        this.graduationDate = graduationDate;
        this.graduationRank = graduationRank;
        this.education = education;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Graduation Date: " + graduationDate);
        System.out.println("Graduation Rank: " + graduationRank);
        System.out.println("Education: " + education);
    }
}

class Intern extends Employee {
    private String majors;
    private String semester;
    private String universityName;

    public Intern(String fullName, String birthDay, String phone, String email, String majors, String semester,
            String universityName) {
        super(fullName, birthDay, phone, email, "Intern");
        this.majors = majors;
        this.semester = semester;
        this.universityName = universityName;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Majors: " + majors);
        System.out.println("Semester: " + semester);
        System.out.println("University Name: " + universityName);
    }
}

class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void addEmployee() {
        System.out.println("Enter employee type (Experience/Fresher/Intern):");
        String employeeType = scanner.nextLine();
        System.out.println("Enter full name:");
        String fullName = scanner.nextLine();
        System.out.println("Enter birth day:");
        String birthDay = scanner.nextLine();
        System.out.println("Enter phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();

        if (employeeType.equalsIgnoreCase("Experience")) {
            System.out.println("Enter years of experience:");
            int expInYear = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter professional skill:");
            String proSkill = scanner.nextLine();
            employees.add(new Experience(fullName, birthDay, phone, email, expInYear, proSkill));
        } else if (employeeType.equalsIgnoreCase("Fresher")) {
            System.out.println("Enter graduation date:");
            String graduationDate = scanner.nextLine();
            System.out.println("Enter graduation rank:");
            String graduationRank = scanner.nextLine();
            System.out.println("Enter education:");
            String education = scanner.nextLine();
            employees.add(new Fresher(fullName, birthDay, phone, email, graduationDate, graduationRank, education));
        } else if (employeeType.equalsIgnoreCase("Intern")) {
            System.out.println("Enter majors:");
            String majors = scanner.nextLine();
            System.out.println("Enter semester:");
            String semester = scanner.nextLine();
            System.out.println("Enter university name:");
            String universityName = scanner.nextLine();
            employees.add(new Intern(fullName, birthDay, phone, email, majors, semester, universityName));
        } else {
            System.out.println("Invalid employee type!");
        }
    }

    public void displayAllEmployees() {
        for (Employee employee : employees) {
            employee.showInfo();
            System.out.println("-----------------------------");
        }
    }

    public void updateEmployee() {
        System.out.println("Enter the ID of the employee to update:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Employee employeeToUpdate = null;
        for (Employee employee : employees) {
            if (employee.getID() == id) {
                employeeToUpdate = employee;
                break;
            }
        }
        if (employeeToUpdate != null) {
            System.out.println("Enter new full name:");
            employeeToUpdate.fullName = scanner.nextLine();
            System.out.println("Enter new birth day:");
            employeeToUpdate.birthDay = scanner.nextLine();
            System.out.println("Enter new phone:");
            employeeToUpdate.phone = scanner.nextLine();
            System.out.println("Enter new email:");
            employeeToUpdate.email = scanner.nextLine();
            System.out.println("Employee information updated successfully.");
        } else {
            System.out.println("Employee not found!");
        }
    }

    public void deleteEmployee() {
        System.out.println("Enter the ID of the employee to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getID() == id) {
                iterator.remove();
                System.out.println("Employee deleted successfully.");
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    public void writeToFile(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(employees);
            out.close();
            fileOut.close();
            System.out.println("Employees saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String filename) {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            employees = (List<Employee>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Employees loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add an employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Update employee");
            System.out.println("4. Delete employee");
            System.out.println("5. Write employees to file");
            System.out.println("6. Read employees from file");
            System.out.println("7. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    manager.addEmployee();
                    break;
                case 2:
                    manager.displayAllEmployees();
                    break;
                case 3:
                    manager.updateEmployee();
                    break;
                case 4:
                    manager.deleteEmployee();
                    break;
                case 5:
                    System.out.println("Enter file name to save:");
                    String saveFilename = scanner.nextLine();
                    manager.writeToFile(saveFilename);
                    break;
                case 6:
                    System.out.println("Enter file name to load:");
                    String loadFilename = scanner.nextLine();
                    manager.readFromFile(loadFilename);
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
