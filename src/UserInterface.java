import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    public int welcomeInterface() {

        Map<Integer, String> choice = new HashMap<Integer, String>();
        choice.put(1, "показать все записи таблицы");
        choice.put(2, "добавить запись в таблицу");
        choice.put(3, "редактировать запись в таблице");
        choice.put(4, "удалить запись из таблицы");

        System.out.print("Главное меню:\n1-показать все записи таблицы;\n2-добавить запись в таблицу;" +
                "\n3-редактировать запись в таблице;\n4-удалить запись из таблицы.\n\nВыберите действие: ");

        boolean err = true;
        Scanner scanner = new Scanner(System.in);

        while (err) {
            int input = scanner.nextInt();

            if (input > 4 || input < 1) {
                System.out.print("\nВведено неверное значение. Попробуйте еще раз.\nВыберите действие: ");
            } else {
                System.out.println("Выбрано действие: " + choice.get(input));
                err = false;

                return input;
            }
        }

        return 0;
    }
}
