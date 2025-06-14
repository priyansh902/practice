import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class nn {

    private static final String url = "jdbc:mysql://localhost:3306/mydb";

    private static final String username =  "root";

    private static final String password = "*********";

    

    public static void main(String[] args) {
        System.out.println("hello");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // try {    USING SIMPLE STATEMENT  QUERY FOR INSERTING DATA
        //     Connection connection = DriverManager.getConnection(url, username, password);
        //     Statement statement = connection.createStatement();

        //     String query = String.format("INSERT INTO students(name,age,marks) VALUES('%s',%o,%f)","Rahul",17,73.4);

        //     int rowsaff = statement.executeUpdate(query);
        //     if(rowsaff>0) {
        //         System.out.println("Data inserted succesfully");
        //     } else {
        //         System.out.println("DAta not insertef");
        //      }



        //     // String query = "select * from students";      QUERY FOR RETRIVIMG DATA
        //     // ResultSet resultSet = statement.executeQuery(query);
        //     // while(resultSet.next()) {
        //     //     int id = resultSet.getInt("id");
        //     //     String name = resultSet.getString("name");
        //     //     int age = resultSet.getInt("age");
        //     //     double marks = resultSet.getDouble("marks");
        //     //     System.out.println("Id:"+ id);
        //     //     System.out.println("Name: "+ name);
        //     //     System.out.println("age: "+age);
        //     //     System.out.println("marks: "+marks);
        //     // }


        //       } catch (SQLException e) {
        //     System.out.println(e.getMessage());
        // }
       

        // try {   INSERTING DATA BY PREPARED STATEMENT
        //     Connection connection = DriverManager.getConnection(url, username, password);
        //     String query = "INSERT INTO students(name, age,marks )VALUES(?,?,?)";
        //     PreparedStatement preparedStatement = connection.prepareStatement(query);
        //     preparedStatement.setString(1, "Ank");
        //     preparedStatement.setInt(2, 15);
        //     preparedStatement.setDouble(3,45.5);
        //     int rowsaff = preparedStatement.executeUpdate();
        //     if(rowsaff>0) {
        //         System.out.println("data inserted succesfull");
        //     } else {
        //         System.out.println("daata not inserted");
        //     }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO students(name, age, marks) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.println("Enter name: ");
                String name = scanner.next();
                System.out.println("Enter age: ");
                int age = scanner.nextInt();
                System.out.println("enter marks: ");
                double marks = scanner.nextDouble();
                System.out.println("Enter more Data(Y/N)");
                String choice = scanner.next();
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setDouble(3, marks);

                preparedStatement.addBatch();
                if(choice.toUpperCase().equals("N")) {
                    break;
                }
            }
            int [] arr = preparedStatement.executeBatch();
        }


         catch(SQLException e){
                System.out.println(e.getMessage());
        }


    }
}
