package hrms;

import java.sql.*;
import java.math.BigDecimal;

public class SalaryService {

    private static int readNumber(String msg) {
        System.out.print(msg);
        return Integer.parseInt(Main.sc.nextLine().trim());
    }

    private static BigDecimal readSalary(String msg) {
        System.out.print(msg);
        BigDecimal amount = new BigDecimal(Main.sc.nextLine().trim());

        if (amount.signum() < 0) {
            System.out.println("Salary cannot be negative.");
            return null;
        }
        return amount;
    }

    public static void menu(int orgId) throws SQLException {
        while (true) {
            System.out.println("\n--- Salary Management ---");
            System.out.println("1. Create Salary");
            System.out.println("2. View Salaries");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Salary");
            System.out.println("5. Back");
            System.out.print("Choice: ");

            switch (readNumber("")) {
                case 1: create(orgId); break;
                case 2: view(orgId); break;
                case 3: update(orgId); break;
                case 4: delete(orgId); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void create(int orgId) throws SQLException {
        int eid = readNumber("Employee ID: ");
        BigDecimal amount = readSalary("Salary: ");
        if (eid < 1 || amount == null) return;

        Connection con = DBConnection.getConnection();

        PreparedStatement check = con.prepareStatement(
                "SELECT EmployeeId FROM Employees WHERE EmployeeId=? AND OrganizationId=?");
        check.setInt(1, eid);
        check.setInt(2, orgId);
        ResultSet rs = check.executeQuery();

        if (!rs.next()) {
            System.out.println("Employee not found in this organization.");
            rs.close();
            check.close();
            con.close();
            return;
        }

        rs.close();
        check.close();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Salaries (EmployeeId, Salary) VALUES (?, ?)");
        ps.setInt(1, eid);
        ps.setBigDecimal(2, amount);
        ps.executeUpdate();

        System.out.println("Salary created successfully.");
        ps.close();
        con.close();
    }

    private static void view(int orgId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT s.SalaryId, e.EmployeeId, e.EmployeeName, s.Salary "
                        + "FROM Salaries s JOIN Employees e ON e.EmployeeId=s.EmployeeId "
                        + "WHERE e.OrganizationId=?");
        ps.setInt(1, orgId);
        ResultSet rs = ps.executeQuery();

        System.out.println("Salary ID | Employee ID | Employee Name | Salary");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " | " + rs.getInt(2) + " | "
                    + rs.getString(3) + " | " + rs.getBigDecimal(4));
        }

        rs.close();
        ps.close();
        con.close();
    }

    private static void update(int orgId) throws SQLException {
        int sid = readNumber("Salary ID: ");
        BigDecimal amount = readSalary("New salary: ");
        if (sid < 1 || amount == null) return;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE Salaries s JOIN Employees e ON e.EmployeeId=s.EmployeeId "
                        + "SET s.Salary=? WHERE s.SalaryId=? AND e.OrganizationId=?");
        ps.setBigDecimal(1, amount);
        ps.setInt(2, sid);
        ps.setInt(3, orgId);

        System.out.println(ps.executeUpdate() == 1
                ? "Salary updated successfully."
                : "Salary record not found for this organization.");

        ps.close();
        con.close();
    }

    private static void delete(int orgId) throws SQLException {
        int sid = readNumber("Salary ID: ");

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "DELETE s FROM Salaries s JOIN Employees e "
                        + "ON e.EmployeeId=s.EmployeeId "
                        + "WHERE s.SalaryId=? AND e.OrganizationId=?");
        ps.setInt(1, sid);
        ps.setInt(2, orgId);

        System.out.println(ps.executeUpdate() == 1
                ? "Salary deleted successfully."
                : "Salary record not found for this organization.");

        ps.close();
        con.close();
    }
}
