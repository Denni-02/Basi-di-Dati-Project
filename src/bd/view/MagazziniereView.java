package bd.view;
import java.io.IOException;
import java.util.Scanner;
public class MagazziniereView {
    public static int showMenu() throws IOException {
        System.out.println("*********************************");
        System.out.println("*    MAGAZZINIERE DASHBOARD    *");
        System.out.println("*********************************\n");
        System.out.println("*** Quale operazione desideri fare? ***\n");
        System.out.println("1) Gestione Prodotti");
        System.out.println("2) Registra Rifornimento");
        System.out.println("3) Stampa Ricevuta Rifornimento");
        System.out.println("4) Resoconto Giacenze");
        System.out.println("5) Report Scadenze");
        System.out.println("6) Quit");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 6) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }


}
