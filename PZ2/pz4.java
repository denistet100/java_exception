/*Разработайте программу, которая выбросит Exception, когда пользователь вводит пустую строку. 
Пользователю должно показаться сообщение, что пустые строки вводить нельзя. */


import java.util.Scanner;

public class pz4 {
    public static void main(String[] args) {
        prompt();
    }

    public static void prompt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку:");
        String line = scanner.nextLine();
        if(!line.isEmpty()) {
            System.out.println(line);
            prompt();
        }
        else {
            throw new RuntimeException("Пустые строки вводить нельзя.");
        }
    }
}
