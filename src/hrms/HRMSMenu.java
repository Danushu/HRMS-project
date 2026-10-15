
        package hrms;

import java.sql.SQLException;

public class HRMSMenu {

    public static void show(int orgId, String orgName) throws SQLException {
        while (true) {
            System.out.println("\n===== WELCOME TO " + orgName + " =====");
            System.out.println("1. Employee Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Notices / Holidays");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(Main.sc.nextLine().trim());

            switch (choice) {
                case 1:
                    EmployeeService.menu(orgId);
                    break;
                case 2:
                    AttendanceService.menu(orgId);
                    break;
                case 3:
                    SalaryService.menu(orgId);
                    break;
                case 4:
                    NoticeService.menu(orgId);
                    break;
                case 5:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
