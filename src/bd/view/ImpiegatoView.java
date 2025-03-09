package bd.view;
import java.io.IOException;
import java.util.Scanner;

public class ImpiegatoView {
    public static int showMenu() throws IOException {
        System.out.println("*********************************");
        System.out.println("*    IMPIEGATO DASHBOARD    *");
        System.out.println("*********************************\n");
        System.out.println("*** Quale operazione desideri fare? ***\n");
        System.out.println("1) Registra Ordine");
        System.out.println("2) Stampa Ricevuta Ordine");
        System.out.println("3) Report Ordini Giornalieri");
        System.out.println("4) Quit");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 4) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }
}
