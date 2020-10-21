import java.awt.*;
import java.sql.*;
import java.util.*;
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

            System.out.print("Что вы хотите добавить?\n1 - Транспорт;\n2 - Рейс;\n3 - Назначить транспорт на рейс.");
            int inputTable = scan.nextInt();

            switch (inputTable){
                case 1:
                    System.out.println("Для добавления транспорта введите следующие данные:\nНомер транспорта: ");
                    int num_trans = scan.nextInt();
                    System.out.println("Введите тип транспорта (автобус, троллейбус, трамвай и т.д.)");
                    String type_trans = scan.next();

                    try {
                        st = con.createStatement();
                        st.executeUpdate("INSERT INTO transport (trans_num, type) VALUES (".concat(num_trans + ", " + type_trans + ")"));

                        System.out.println("\nГотово!\n");
                        st.cancel();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    table_err = false;
                    break;
                case 2:
                    System.out.println("Для добавления маршрута введите следующие данные:\nНомер маршрута: ");
                    int num_route = scan.nextInt();
                    System.out.println("Введите длину маршрута:");
                    String route_len = scan.next();

                    try {
                        st = con.createStatement();
                        st.executeUpdate("INSERT INTO route (num_route, len) VALUES (".concat(num_route + ", " + route_len + ")"));

                        System.out.println("\nГотово!\n");
                        st.cancel();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    table_err = false;
                    break;
                case 3:
                    // Ввод соответствия маршрута и транспорта.
                    // Нужна проверка на наличие такого маршрута и транспорта в БД

                    table_err = false;
                    break;
                default:
                    System.out.println("Введено неверное значение. Попробуйте еще раз");
            }
        }
    }

    // Редактирование строки в таблице
    public static void edit_row(Connection con) {
        System.out.println("Edit");
    }

    // Удаление строки из таблицы
    public static void delete_row(Connection con) {
        Statement st = null;
        ResultSet rs = null;
        ArrayList tableName = tableNames(con);
        Scanner scan = new Scanner(System.in);

        boolean table_err = true;
        while (table_err){
            System.out.println("Список доступных таблиц");
            System.out.println(tableName);

            System.out.println("\nВведите имя таблицы, из которой хотите удалить запись: ");
            String input = scan.next();

            if (tableName.contains(input)) {
                try {
                    st = con.createStatement();
                    rs = st.executeQuery("SELECT * FROM ".concat(input));
                    DatabaseMetaData dmd = con.getMetaData();
                    ResultSet rsPrimaryKey = dmd.getPrimaryKeys(null, null, input);

                    switch (input){
                        case "route":
                            System.out.print("Укажите номер маршрута, который хотите удалить: ");
                            break;
                        case "transport":
                            System.out.print("Укажите номер транспорта, который хотите удалить: ");
                            break;
                        case "route_trans":
                            System.out.print("Укажите номер маршрута, с которого хотите снять транспорт: ");
                            break;
                    }

                    int delete_input = scan.nextInt();
                    while (rs.next()) {
                        if (rs.getInt(1) == delete_input) {
                            st.executeUpdate("DELETE FROM ".concat(input + " WHERE "));
                        }
                    }

                    System.out.println("Удалено");

                    st.cancel();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
            else{
                System.out.println("Таблицы с таким именем не существует. Попробуйте еще раз.");
            }
        }


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
