import java.sql.*;
import java.util.Scanner;
public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String args[]) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment3", "root",
                            "varunkarwa");
            Statement stmt = con.createStatement();
            System.out.println("Connected Database Successfully!\n");
            int choice = 0;
            do {
                System.out.println("1.Create table\n2.Insert New Rating\n3.Update Rating\n4.Delete Rating\n5.Show Table\n0.Exit\n");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("\nCREATING TABLE");
                        String query = "create table movie(\n" +
                                " movie_id int not null primary key, \n" +
                                "genre varchar(10),\n "+
                                " rating float not null\n" +
                                ")";
                        System.out.println(query);
                        stmt.executeUpdate(query);
                        break;
                    case 2:
                        System.out.println("\nNew rating insertion ");
                        System.out.print("Movie Id: ");
                        int movie_id = sc.nextInt();
                        System.out.println("Enter genre");
                        String genre = sc.next();
                        System.out.println("Enter rating");
                        float rating = sc.nextFloat();
                        query = String.format(
                                "insert into movie (movie_id,genre,rating)\n" +
                                        "values ('%s','%s',%s)", movie_id,genre,rating
                        );
                        System.out.println(query);
                        stmt.executeUpdate(query);
                        break;
                    case 3:
                        System.out.println("\nUpdating rating");
                        System.out.println("Enter the movie_id you want to update: ");

                        int id = sc.nextInt();
                        System.out.println("Enter rating");
                        float rating1 = sc.nextFloat();
                        query = String.format(
                                "update movie set rating='%s'\n" +
                                        "where movie_id='%s'", rating1, id);
                        System.out.println(query);
                        int row = stmt.executeUpdate(query);
                        if (row == 0)
                            System.out.println("movie with : " + id + " Not Found");
                        else
                            System.out.println("Rating Updated");
                        break;
                    case 4:
                        System.out.println("\nDELETE Rating");
                        System.out.print("Movie Id: ");
                        int mi = sc.nextInt();
                        query = String.format("delete from movie where movie_id ='%s'", mi);
                        System.out.println(query);
                        row = stmt.executeUpdate(query);
                        if (row == 0)
                            System.out.println("Movie Id: " + mi + " Not Found");
                        else
                            System.out.println("Movie Deleted");
                        break;
                    case 5:
                        ResultSet r = stmt.executeQuery("select * from movie");
                        int columns = r.getMetaData().getColumnCount();
                        String headers = "";
                        for (int i = 1; i <= columns; i++) {
                            String a = "";
                            headers += String.format("%-20s", r.getMetaData().getColumnName(i)) + " ";
                        }
                        System.out.println(headers);
                        for (int i = 0; i < columns * 20; i++)
                            System.out.print("-");
                        System.out.println();
                        while (r.next()) {
                            String rowx = "";
                            for (int i = 1; i <= columns; i++) {
                                rowx += String.format("%-20s", r.getString(i)) + " ";
                            }
                            System.out.println(rowx);
                        }
                        break;
                }
            } while (choice != 0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}