package hrms;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        Connection con = DBConnection.getConnection();
        System.out.println("MySQL connected successfully!");
        con.close();
        while (true) {
            System.out.println("\n========== HRMS ==========");
            System.out.println("1. Create New Organization");
            System.out.println("2. Login to Organization");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine().trim());
            switch (choice) {
                case 1:
                    OrganizationService.createOrganization();
                    break;
                case 2:
                    OrganizationService.loginMenu();
                    break;
                case 3:
                    System.out.println("Thank you for using HRMS!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
