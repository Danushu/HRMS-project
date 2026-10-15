package hrms;
import java.sql.*;
public class AttendanceService {
    private static int readNumber(String msg) {
        System.out.print(msg);
        return Integer.parseInt(Main.sc.nextLine().trim());
    }
    public static void menu(int orgId) throws SQLException {
        while (true) {
            System.out.println("\n--- Attendance ---");
            System.out.println("1. Create Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Update Attendance");
            System.out.println("4. Delete Attendance");
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
    private static boolean employeeExists(Connection con, int orgId, int eid)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT EmployeeId FROM Employees WHERE EmployeeId=? AND OrganizationId=?");
        ps.setInt(1, eid);
        ps.setInt(2, orgId);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        return exists;
    }
    private static boolean validStatus(String s) {
        return s.equalsIgnoreCase("Present")
                || s.equalsIgnoreCase("Absent")
                || s.equalsIgnoreCase("Leave");
    }
    private static String normalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    private static void create(int orgId) throws SQLException {
        int eid = readNumber("Employee ID: ");
        System.out.print("Status (Present/Absent/Leave): ");
        String status = Main.sc.nextLine().trim();
        if (eid < 1 || !validStatus(status)) {
            System.out.println("Invalid employee ID or status.");
            return;
        }
        Connection con = DBConnection.getConnection();
        if (!employeeExists(con, orgId, eid)) {
            System.out.println("Employee not found.");
            con.close();
            return;
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO Attendance (EmployeeId, Status) VALUES (?, ?)");
        ps.setInt(1, eid);
        ps.setString(2, normalize(status));
        ps.executeUpdate();
        System.out.println("Attendance created successfully.");
        ps.close();
        con.close();
    }
    private static void view(int orgId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT a.AttendanceId, a.EmployeeId, e.EmployeeName, a.Status " + "FROM Attendance a JOIN Employees e ON e.EmployeeId=a.EmployeeId " + "WHERE e.OrganizationId=?");
        ps.setInt(1, orgId);
        ResultSet rs = ps.executeQuery();
        System.out.println("ID | Employee ID | Employee Name | Status");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " | " + rs.getInt(2) + " | "
                    + rs.getString(3) + " | " + rs.getString(4));
        }
        rs.close();
        ps.close();
        con.close();
    }
    private static void update(int orgId) throws SQLException {
        int aid = readNumber("Attendance ID: ");
        System.out.print("New status (Present/Absent/Leave): ");
        String status = Main.sc.nextLine().trim();
        if (aid < 1 || !validStatus(status)) {
            System.out.println("Invalid attendance ID or status.");
            return;
        }
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE Attendance a JOIN Employees e ON e.EmployeeId=a.EmployeeId " + "SET a.Status=? WHERE a.AttendanceId=? AND e.OrganizationId=?");
        ps.setString(1, normalize(status));
        ps.setInt(2, aid);
        ps.setInt(3, orgId);
        System.out.println(ps.executeUpdate() == 1 ? "Attendance updated successfully." : "Attendance record not found.");
        ps.close();
        con.close();
    }
    private static void delete(int orgId) throws SQLException {
        int aid = readNumber("Attendance ID: ");
        if (aid < 1) {
            System.out.println("Invalid attendance ID.");
            return;
        }
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE a FROM Attendance a JOIN Employees e " + "ON e.EmployeeId=a.EmployeeId " + "WHERE a.AttendanceId=? AND e.OrganizationId=?");
        ps.setInt(1, aid);
        ps.setInt(2, orgId);
        System.out.println(ps.executeUpdate() == 1 ? "Attendance deleted successfully." : "Attendance record not found.");
        ps.close();
        con.close();
    }
}
