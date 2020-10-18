import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EditorBD editBD = new EditorBD();
        UserInterface userInter = new UserInterface();
        Scanner scan = new Scanner(System.in);


        // Подключение JDBC
        conJDBC();

        // Подключение к БД
        Connection con = null;
        conBD(con);


        boolean exit = true;
        boolean que = true;
        while (exit) {
            int choice = userInter.welcomeInterface();

            switch (choice) {
                case 1:
                    editBD.show_row(con);
                    break;
                case 2:
                    editBD.add_row(con);
                    break;
                case 3:
                    editBD.edit_row(con);
                    break;
                case 4:
                    editBD.delete_row(con);
                    break;
                case 0:
                    break;
            }

            System.out.println("Хотите сделать что-нибудь еще?\n1 - Вернуться в главное меню);\n2 - Выйти из программы)");

            while (que) {
                int input = scan.nextInt();

                if (input == 1) {
                    System.out.println("Возврат в главное меню ...");
                    que = false;
                    exit = true;
                } else if (input == 2) {
                    System.out.println("Выход из программы ...");
                    que = false;
                    exit = false;
                } else {
                    System.out.println("Введено неверное значение. Попробуйте еще раз.\nХотите сделать что-нибудь еще?\n1 - Вернуться в главное меню);\n2 - Выйти из программы)");
                    que = true;
                }
            }
        }


        // Закрытие подключения к БД
        disconBD(con);
    }

    public static void conJDBC() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void conBD(Connection con) {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:D:\\untitled\\db\\testautoBD.db");
            System.out.println("Connection success!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void disconBD(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
