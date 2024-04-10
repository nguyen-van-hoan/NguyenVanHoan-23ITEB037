import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private final String url = "jdbc:mysql://localhost:3306/employee_management";
    private final String user = "root";
    private final String password = "Nvh840511@";

    public void writeEmployeesToDatabase(List<Employee> employees) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO employee (full_name, birth_day, phone, email, employee_type, " +
                    "exp_in_year, pro_skill, graduation_date, graduation_rank, education, majors, semester, university_name) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            for (Employee employee : employees) {
                statement.setString(1, employee.fullName);
                statement.setString(2, employee.birthDay);
                statement.setString(3, employee.phone);
                statement.setString(4, employee.email);
                statement.setString(5, employee.employeeType);

                if (employee instanceof Experience) {
                    Experience experience = (Experience) employee;
                    statement.setInt(6, experience.expInYear);
                    statement.setString(7, experience.proSkill);
                    statement.setNull(8, Types.DATE);
                    statement.setNull(9, Types.VARCHAR);
                    statement.setNull(10, Types.VARCHAR);
                    statement.setNull(11, Types.VARCHAR);
                    statement.setNull(12, Types.VARCHAR);
                    statement.setNull(13, Types.VARCHAR);
                } else if (employee instanceof Fresher) {
                    Fresher fresher = (Fresher) employee;
                    statement.setNull(6, Types.INTEGER);
                    statement.setNull(7, Types.VARCHAR);
                    statement.setString(8, fresher.graduationDate);
                    statement.setString(9, fresher.graduationRank);
                    statement.setString(10, fresher.education);
                    statement.setNull(11, Types.VARCHAR);
                    statement.setNull(12, Types.VARCHAR);
                    statement.setNull(13, Types.VARCHAR);
                } else if (employee instanceof Intern) {
                    Intern intern = (Intern) employee;
                    statement.setNull(6, Types.INTEGER);
                    statement.setNull(7, Types.VARCHAR);
                    statement.setNull(8, Types.DATE);
                    statement.setNull(9, Types.VARCHAR);
                    statement.setNull(10, Types.VARCHAR);
                    statement.setString(11, intern.majors);
                    statement.setString(12, intern.semester);
                    statement.setString(13, intern.universityName);
                }

                statement.addBatch();
            }

            statement.executeBatch();
            System.out.println("Employees saved to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> readEmployeesFromDatabase() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM employee";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String employeeType = resultSet.getString("employee_type");
                if (employeeType.equalsIgnoreCase("Experience")) {
                    Experience experience = new Experience(
                            resultSet.getString("full_name"),
                            resultSet.getString("birth_day"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getInt("exp_in_year"),
                            resultSet.getString("pro_skill"));
                    employees.add(experience);
                } else if (employeeType.equalsIgnoreCase("Fresher")) {
                    Fresher fresher = new Fresher(
                            resultSet.getString("full_name"),
                            resultSet.getString("birth_day"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("graduation_date"),
                            resultSet.getString("graduation_rank"),
                            resultSet.getString("education"));
                    employees.add(fresher);
                } else if (employeeType.equalsIgnoreCase("Intern")) {
                    Intern intern = new Intern(
                            resultSet.getString("full_name"),
                            resultSet.getString("birth_day"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("majors"),
                            resultSet.getString("semester"),
                            resultSet.getString("university_name"));
                    employees.add(intern);
                }
            }
            System.out.println("Employees loaded from database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
