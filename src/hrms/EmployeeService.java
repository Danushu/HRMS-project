package hrms;
import java.sql.*;
public class EmployeeService {
    private static int readNumber(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(Main.sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
    public static void menu(int orgId) {
        while (true) {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            int choice;
            try {
                choice = Integer.parseInt(Main.sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    add(orgId);
                    break;
                case 2:
                    view(orgId);
                    break;
                case 3:
                    update(orgId);
                    break;
                case 4:
                    delete(orgId);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static void add(int orgId) {
        System.out.print("Employee name: ");
        String name = Main.sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Employee name is required.");
            return;
        }
        double salary;
        try {
            System.out.print("Salary: ");
            salary = Double.parseDouble(Main.sc.nextLine().trim());
            if (salary < 0) {
                System.out.println("Salary cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid salary.");
            return;
        }
        System.out.print("Department: ");
        String dept = Main.sc.nextLine().trim();
        String sql = "INSERT INTO Employees " + "(OrganizationId, EmployeeName, Salary, Department) " + "VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orgId);
            ps.setString(2, name);
            ps.setDouble(3, salary);
            ps.setString(4, dept);
            ps.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void view(int orgId) {
        String sql = "SELECT * FROM Employees WHERE OrganizationId = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\nID | Name | Salary | Department");

                while (rs.next()) {
                    System.out.println(
                            rs.getInt("EmployeeId") + " | "
                                    + rs.getString("EmployeeName") + " | "
                                    + rs.getBigDecimal("Salary") + " | "
                                    + rs.getString("Department"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void update(int orgId) {
        int id = readNumber("Employee ID: ");

        if (id < 1) {
            System.out.println("Invalid employee ID.");
            return;
        }

        System.out.print("New name: ");
        String name = Main.sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Employee name is required.");
            return;
        }

        double salary;

        try {
            System.out.print("New salary: ");
            salary = Double.parseDouble(Main.sc.nextLine().trim());

            if (salary < 0) {
                System.out.println("Salary cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary.");
            return;
        }

        System.out.print("New department: ");
        String dept = Main.sc.nextLine().trim();

        String sql = "UPDATE Employees SET EmployeeName=?, Salary=?, "
                + "Department=? WHERE EmployeeId=? AND OrganizationId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, salary);
            ps.setString(3, dept);
            ps.setInt(4, id);
            ps.setInt(5, orgId);

            System.out.println(ps.executeUpdate() == 1
                    ? "Employee updated successfully."
                    : "Employee not found.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete(int orgId) {
        int id = readNumber("Employee ID: ");

        if (id < 1) {
            System.out.println("Invalid employee ID.");
            return;
        }

        String sql = "DELETE FROM Employees "
                + "WHERE EmployeeId=? AND OrganizationId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, orgId);

            System.out.println(ps.executeUpdate() == 1
                    ? "Employee deleted successfully."
                    : "Employee not found.");

        } catch (SQLException e) {
            System.out.println(
                    "Delete failed. Remove related attendance and "
                            + "salary records first, if present.");
        }
    }
}
