import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class tran {
    
    private static final String url = "jdbc:mysql://localhost:3306/zom";

    private static final String username =  "root";

    private static final String password = "9027707502";

    public static void main(String[] args) {
          try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement debitprStatement = connection.prepareStatement(debit_query);
            PreparedStatement creditpreparedStatement = connection.prepareStatement(credit_query);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter account number : ");
            int account_number = scanner.nextInt();
            System.out.println("Enter amount: ");
            double amount = scanner.nextDouble();
            System.out.println("credit account number : ");
            int creaccount = scanner.nextInt();
            
            debitprStatement.setDouble(1, amount);
            debitprStatement.setInt(2, account_number);
            creditpreparedStatement.setDouble(1, amount);
            creditpreparedStatement.setInt(2, creaccount);

             int affectRows1 = debitprStatement.executeUpdate();
              int affectRows2 = creditpreparedStatement.executeUpdate();

            if(issufficient(connection, account_number, amount)) {
               connection.commit();
               System.out.println("Transaction succesful");
            } else {
                connection.rollback();
                System.out.println("transaction failed!!!");
            }

            // debitprStatement.executeUpdate();
            // creditpreparedStatement.executeUpdate();
            scanner.close();
            creditpreparedStatement.close();
            debitprStatement.close();
            connection.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    static boolean issufficient(Connection connection, int account_number, double amount){
        try{
            String query = "SELECT balance FROM accounts WHERE account_number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double current_balance = resultSet.getDouble("balance");
                if( amount > current_balance) {
                    return false;
                } else {
                    return true;
                }
                
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

}
