
        package hrms;

import java.sql.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class OrganizationService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static byte[] derive(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(spec).getEncoded();
        spec.clearPassword();
        return result;
    }

    private static String hash(String password) throws Exception {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(derive(password, salt));
    }

    private static boolean verify(String password, String stored) throws Exception {
        String[] parts = stored.split(":");
        return MessageDigest.isEqual(
                Base64.getDecoder().decode(parts[1]),
                derive(password, Base64.getDecoder().decode(parts[0])));
    }

    public static void createOrganization() throws Exception {
        System.out.println("\n--- Create Organization ---");
        System.out.print("Organization name: ");
        String name = Main.sc.nextLine().trim();
        System.out.print("Address: ");
        String address = Main.sc.nextLine().trim();
        System.out.print("Email: ");
        String email = Main.sc.nextLine().trim();
        System.out.print("Password (minimum 8 characters): ");
        String password = Main.sc.nextLine();

        if (name.isEmpty() || address.isEmpty()
                || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
                || password.length() < 8) {
            System.out.println("Invalid details.");
            return;
        }

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Organizations "
                        + "(OrganizationName, Address, Email, PasswordHash) VALUES (?, ?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, email);
        ps.setString(4, hash(password));
        ps.executeUpdate();

        System.out.println("Organization registered successfully!");
        ps.close();
        con.close();
    }

    public static void loginMenu() throws Exception {
        while (true) {
            System.out.println("\n--- Organization Login ---");
            System.out.println("1. Login");
            System.out.println("2. Forgot Password");
            System.out.println("3. Back to Main Menu");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            switch (Integer.parseInt(Main.sc.nextLine().trim())) {
                case 1: login(); break;
                case 2: forgotPassword(); break;
                case 3: return;
                case 4:
                    System.out.println("Thank you for using HRMS!");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void login() throws Exception {
        System.out.print("Email: ");
        String email = Main.sc.nextLine().trim();
        System.out.print("Password: ");
        String password = Main.sc.nextLine();

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT OrganizationId, OrganizationName, PasswordHash "
                        + "FROM Organizations WHERE Email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next() && verify(password, rs.getString("PasswordHash"))) {
            int orgId = rs.getInt("OrganizationId");
            String name = rs.getString("OrganizationName");
            rs.close();
            ps.close();
            con.close();

            System.out.println("Welcome to " + name + "!");
            HRMSMenu.show(orgId, name);
        } else {
            System.out.println("Invalid email or password.");
            rs.close();
            ps.close();
            con.close();
        }
    }

    private static void forgotPassword() throws Exception {
        System.out.print("Registered email: ");
        String email = Main.sc.nextLine().trim();

        Connection con = DBConnection.getConnection();
        PreparedStatement check = con.prepareStatement(
                "SELECT OrganizationId FROM Organizations WHERE Email=?");
        check.setString(1, email);
        ResultSet rs = check.executeQuery();

        if (!rs.next()) {
            System.out.println("Email not found.");
            rs.close();
            check.close();
            con.close();
            return;
        }

        rs.close();
        check.close();

        System.out.print("New password (minimum 8 characters): ");
        String password = Main.sc.nextLine();

        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters.");
            con.close();
            return;
        }

        PreparedStatement update = con.prepareStatement(
                "UPDATE Organizations SET PasswordHash=? WHERE Email=?");
        update.setString(1, hash(password));
        update.setString(2, email);
        update.executeUpdate();

        System.out.println("Password updated successfully.");
        update.close();
        con.close();
    }
}