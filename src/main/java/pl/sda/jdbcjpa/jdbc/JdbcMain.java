package pl.sda.jdbcjpa.jdbc;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcMain {
    public static void main(String[] args) {
//        statement();
//        prepareStatement();
//        callableStatement();
//        prepareStatement2();
//        System.out.println(findEmployeeBySalaryRange(500, 2500));
//        sqlInjectionStatement("KING");
       //hakowanie sql
       // sqlInjectionStatement("KING'; delete from sdajdbc.employee where empno = 7369; -- ");

        addPayout(new BigDecimal(500));
    }

    private static void addPayout(BigDecimal ammount) {
        String query = "UPDATE sdajdbc.employee SET total_payouts = COALESCE(total_payouts, 0)+ ?";
// to poniżej druga gorsza opcja
 //        try {
//            Connection connection = getConnection();
//        } plus jeszcze
//                finally getConnection().close();{
//             i kolejny
//        }

        try(Connection connection = getConnection()){ // to się samo zamyka i jest ładne (try with resurces)
            PreparedStatement preparedStatement  = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1,ammount);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void prepareStatement2() {
        try(Connection connection = getConnection()){
            int minSalary=1000;
            String query = "select ename, job, sal, mgr " +
                    "from sdajdbc.employee " +
                    "where sal > ?"; // znak zapytania bezpieczny bo jak by było sal >" minSalary to ktoś może coś dopisać np. drop DB
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,minSalary);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                Integer mgr = resultSet.getInt("mgr");
                if(resultSet.wasNull()){
                    mgr=null;
                }
                System.out.println(ename + " " + job + " " + sal+" "+mgr);
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to wykorzystuje procedury
    private static void callableStatement() {
        try(Connection connection = getConnection()){
            String query="{call sdajdbc.getName(?,?)}";
            CallableStatement callableStatement=connection.prepareCall((query));
            callableStatement.setInt(1,7654);
            callableStatement.registerOutParameter(2,Types.VARCHAR);

            callableStatement.execute();
            String name = callableStatement.getString(2);
            System.out.println(name);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void prepareStatement() {
        try(Connection connection = getConnection()){
            int minSalary=1000;
            String query = "select ename, job, sal " +
                    "from sdajdbc.employee " +
                    "where sal > ?"; // znak zapytania bezpieczny bo jak by było sal >" minSalary to ktoś może coś dopisać np. drop DB
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,minSalary);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void statement() {
        try (Connection connection = getConnection()) {
            String query = "select ename, job, sal " +
                    "from sdajdbc.employee " +
                    "where sal > 100";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
            System.out.println();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306?" +
                            "useUnicode=true&" +
                            "serverTimezone=UTC", "root", "Nerek0jeden");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // jest możliwy do scashowania. mogą się zapytania szybciej wykonywać.
    public static List<String> findEmployeeBySalaryRange(int salaryMin, int salaryMax) {
        List<String> employees = new ArrayList<>();
        String query = "SELECT empno, ename, sal FROM sdajdbc.employee WHERE sal > ? " +
                "AND sal < ? ";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, salaryMin);
            preparedStatement.setInt(2, salaryMax);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                employees.add(""+
                        resultSet.getInt("empno")+
                        resultSet.getString("ename")+
                        resultSet.getInt("sal")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

//bez zabazpieczenia przed sql injection,
    private static void sqlInjectionStatement(String firstName) {
        try (Connection connection = getConnection()) {
            String query = "select ename, job, sal " +
                    "from sdajdbc.employee " +
                    "where ename= '"+firstName +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int sal = resultSet.getInt("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
            System.out.println();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
