package app;

import java.util.Scanner;

public class ConsoleAppUtils {

    private ConsoleAppUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static int askDesiredCollectionSize(Scanner scanner) {
        while (true) {
            System.out.print("Введите желаемый размер коллекции: ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through: не число И число <= 0 получают одно общее сообщение ниже
            }
            System.out.println("Нужно ввести целое положительное число. Попробуйте снова.");
        }
    }
}
