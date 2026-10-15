package hrms;

public class NoticeService {

    public static void menu(int orgId) {

        while (true) {
            System.out.println("\n--- Notices / Holidays ---");
            System.out.println("1. Create");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            switch (Integer.parseInt(Main.sc.nextLine().trim())) {
                case 1:
                    System.out.println("Create Notice selected.");
                    break;

                case 2:
                    System.out.println("View Notices selected.");
                    break;

                case 3:
                    System.out.println("Update Notice selected.");
                    break;

                case 4:
                    System.out.println("Delete Notice selected.");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}