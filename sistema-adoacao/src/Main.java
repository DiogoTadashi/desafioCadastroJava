import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FormReader reader = new FormReader();
        Menu menu = new Menu(reader, sc);

        menu.run();

        sc.close();
    }
}