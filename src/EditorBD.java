import java.awt.*;
import java.sql.*;
import java.util.*
import java.util.Scanner;

public class EditorBD {

    public static void openConn(Connection con) {
        System.out.println("Open");
    }

    // Список имен таблиц
    public static ArrayList tableNames(Connection con){
        ResultSet rs = null;

        ArrayList table_name = new ArrayList();

        try {
            rs = con.createStatement().executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES");

            while (rs.next()) {
                table_name.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return table_name;
    }

    // Добавление строки в таблицу
    public static void add_row(Connection con) {
        Statement st = null;
        ArrayList tableName = tableNames(con);
        Scanner scan = new Scanner(System.in);

        boolean table_err = true;
        while (table_err) {
            System.out.println("Список доступных таблиц");
            System.out.println(tableName);

            System.out.print("Введите имя таблицы: ");
            String inputTable = scan.next();

            if (tableName.contains(inputTable)) {

                

                try {
                    st = con.createStatement();
                    st.executeUpdate("INSERT INTO ".concat(table + " VALUES ").concat("(" + num_route + ", " + len + ")"));

                    System.out.println("\nГотово!\n");
                    st.cancel();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                table_err = false;
            }
            else {
                table_err = true;
                System.out.println("Таблицы с таким именем не существует. Попробуйте еще раз.\n");
            }
        }



    }

    // Редактирование строки в таблице
    public static void edit_row(Connection con) {
        System.out.println("Edit");
    }

    // Удаление строки из таблицы
    public static void delete_row(Connection con) {
        System.out.println("Delete");
    }

    //Вывод всех записей таблицы
    public static void show_row(Connection con) {
        Statement st = null;
        ResultSet rs = null;
        ArrayList tableName = tableNames(con);
        Scanner scan = new Scanner(System.in);

        boolean table_err = true;
        while (table_err) {
            System.out.println("Список доступных таблиц");
            System.out.println(tableName);

            System.out.print("Введите имя таблицы: ");
            String inputTable = scan.next();

            if (tableName.contains(inputTable)) {
                try {
                    st = con.createStatement();
                    rs = st.executeQuery("SELECT * FROM ".concat(inputTable));

                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + " " + rs.getString(2));
                    }
                    System.out.println("\nГотово!\n");
                    st.cancel();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                table_err = false;
            }
            else {
                table_err = true;
                System.out.println("Таблицы с таким именем не существует. Попробуйте еще раз.\n");
            }
        }
    }
}
