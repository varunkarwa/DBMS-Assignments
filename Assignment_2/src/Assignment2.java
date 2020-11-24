import java.sql.*;
import java.util.Scanner;

public class Assignment2
{
    public static void main(String []args)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment3?autoReconnect=true&useSSL=false", "root", "varunkarwa");
            Statement stmt = conn.createStatement();

            Scanner sc = new Scanner(System.in);
            int choice = 0;
            do {
                System.out.println("1.View\n2.Index\n3.Sequence or Auto-Increment\n4.Exit");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        int choice1;
                        System.out.println("1.Simple View\n2.Complex View\n3.Drop View");
                        choice1 = sc.nextInt();
                        switch (choice1) {
                            case 1:
                                try {
                                    stmt.executeUpdate("Create or Replace view prof_view as select prof_id,prof_fname,prof_lname from professor");
                                    System.out.println("Query OK");
                                    ResultSet rs = stmt.executeQuery("select * from prof_view");
                                    while (rs.next())
                                        System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                                break;
                            case 2:
                                try {
                                    stmt.executeUpdate("create or replace view prof_dept_view as select prof_id,prof_fname,prof_lname from professor,department where professor.dept_id=department.dept_id");
                                    System.out.println("Query OK");
                                    ResultSet rs = stmt.executeQuery("select * from prof_dept_view");
                                    while (rs.next())
                                        System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                                break;
                            case 3:
                                try {
                                    stmt.executeUpdate("drop view prof_view");
                                    stmt.executeUpdate("drop view prof_dept_view");
                                    System.out.println("Query OK");
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                        }
                        break;
                    case 2:
                        int choice2;
                        System.out.println("1.Simple Index\n2.Compound Index\n3.Unique Index\n4.Drop Index");
                        choice2 = sc.nextInt();
                        switch (choice2) {
                            case 1:
                                try {
                                    stmt.executeUpdate("create index shift_index on shift(shift)");
                                    System.out.println("Query OK");
                                    ResultSet rs = stmt.executeQuery("show index from shift");
                                    while (rs.next()) {
                                        System.out.println(rs.getString(1)+"\t"+rs.getString(3) + "\t" + rs.getString(5));
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                                break;
                            case 2:
                                try {
                                    stmt.executeUpdate("create index prof_index on professor(prof_fname,prof_lname)");
                                    System.out.println("Query OK");
                                    ResultSet rs = stmt.executeQuery("show index from professor");
                                    while (rs.next()) {
                                        System.out.println(rs.getString(1)+"\t"+rs.getString(3) + "\t" + rs.getString(5));
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                                break;
                            case 3:
                                try {
                                    stmt.executeUpdate("create unique index dept_index on department(dept_name)");
                                    System.out.println("Query OK");
                                    ResultSet rs = stmt.executeQuery("show index from department");
                                    while (rs.next()) {
                                        System.out.println(rs.getString(1)+"\t"+rs.getString(3) + "\t" + rs.getString(5));
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                                break;
                            case 4:
                                try {
                                    stmt.executeUpdate("alter table shift drop index shift_index");
                                    stmt.executeUpdate("alter table professor drop index prof_index");
                                    stmt.executeUpdate("alter table department drop index dept_index");
                                } catch (SQLException e) {
                                    System.out.println(e);
                                }
                                break;
                        }
                        break;
                    case 3:
                        stmt.executeUpdate("create table stud(id int unsigned not null auto_increment, primary key(id), stud_name varchar(20))");

                        String sql = "INSERT INTO stud " +
                                "VALUES (null, 'Ram')";
                        stmt.executeUpdate(sql);
                        sql = "INSERT INTO stud " +
                                "VALUES (null, 'Seema')";
                        stmt.executeUpdate(sql);
                        ResultSet r = stmt.executeQuery("select * from stud");
                        System.out.println("ID\tName");
                        while(r.next()) {
                            System.out.println(r.getInt(1) + "\t" + r.getString(2));
                        }
                        stmt.executeUpdate("drop table stud");
                }
            }while (choice != 4) ;
            conn.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
