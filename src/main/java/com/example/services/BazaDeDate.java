package com.example.services;

import com.example.exceptions.*;
import com.example.model.Parking;
import com.example.model.UserPaymentHistoryClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BazaDeDate {
    static String url = "jdbc:mysql://localhost:3306/bazameadedate";
    static String user = "root";
    static String password = "admin";

    public static Connection getConnection() throws Exception{
        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;
    }

    public static void connectToTheDatabase() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println("Connection is successfull to the database! " + url);
    }

    public static void insertValues(Connection connection,String username, String password, String role, int balance, String plateNumber) throws SQLException, CompleteRegisterDataException, UsernameNotLongEnoughException, PasswordNotLongEnoughException, PasswordNotStrongEnoughException, UserAlreadyExistsException, InvalidPlateNumberException, NotAnAdminException {
        String sqlInsertwithParams = "INSERT INTO users(username,password,role,balance,platenumber,parkA,parkB,parkC)" + "VALUES(?,?,?,?,?,?,?,?)";
        //checkCredentials(username,password);
        checkRegisterDataNotEmpty(username,password);
        userAlreadyExists(connection,username);
        checkUsernameLength(username);
        checkPasswordStrength(password);
        if(role.equals("client")) {
            checkPlateNumber(plateNumber);
        }
        else if (role.equals("admin")){
            checkPlateNumberForAdmin(plateNumber);
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertwithParams);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,encodePassword(username,password));
            preparedStatement.setString(3,role);
            preparedStatement.setInt(4,balance);
            preparedStatement.setString(5,plateNumber);
            preparedStatement.setInt(6,0);
            preparedStatement.setInt(7,0);
            preparedStatement.setInt(8,0);

            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String encodePassword(String salt, String password){
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword,StandardCharsets.UTF_8);
    }

    private static MessageDigest getMessageDigest(){
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("SHA-512");
        }catch(NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    private static void userAlreadyExists(Connection connection, String id) throws UserAlreadyExistsException, SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            throw new UserAlreadyExistsException();
        }
    }

    private static void checkUsernameLength(String username) throws UsernameNotLongEnoughException {
        if(username.length() < 6){
            throw new UsernameNotLongEnoughException();
        }
    }

    private static void checkRegisterDataNotEmpty(String usernameField, String passwordField) throws CompleteRegisterDataException {
        if(usernameField.equals(new String("")) || passwordField.equals(new String(""))){
            throw new CompleteRegisterDataException();
        }
    }

    private static void checkPasswordStrength(String password) throws PasswordNotStrongEnoughException, PasswordNotLongEnoughException {
        int upChars=0, lowChars=0;
        int special=0, digits=0;

        if(password.length()<8){
            throw new PasswordNotLongEnoughException();
        }
        else{
            for(int i=0; i<password.length(); i++){
                char ch = password.charAt(i);
                if(Character.isUpperCase(ch))
                    upChars = 1;
                else if(Character.isLowerCase(ch))
                    lowChars = 1;
                else if(Character.isDigit(ch))
                    digits = 1;
                else
                    special = 1;
            }
        }
        if(upChars!=1 || lowChars!=1 || digits!=1 || special!=1){
            throw new PasswordNotStrongEnoughException();
        }
    }

    public static boolean isLoggedIn(Connection connection,String username, String password, String role, String plateNumber) throws CompleteLoginDataException {
        String sql = "SELECT * FROM users WHERE username = ? and password = ? and role = ? and platenumber = ?";
        String myPassword = encodePassword(username,password);
        try{
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,myPassword);
            pst.setString(3,role);
            pst.setString(4,plateNumber);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
            else{
                throw new CompleteLoginDataException();
            }
        }catch (Exception e){
            throw new CompleteLoginDataException();
        }
    }

    private static void checkPlateNumber(String plateNumber) throws InvalidPlateNumberException {
        Matcher matcher = Pattern.compile("^[A-Z]{2}\\s[1-9]{2}\\s[A-Z]{3}$").matcher(plateNumber);
        if(!matcher.find()){
            throw new InvalidPlateNumberException();
        }
    }

    private static void checkPlateNumberForAdmin(String plateNumber) throws NotAnAdminException {
        if(!plateNumber.equals("SECHQFAWYA")){
            throw new NotAnAdminException();
        }
    }

    public static String getUserRole(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,username);
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("role");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getUserPlateNumber(Connection connection, String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,username);
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("platenumber");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static int getUsersBalance(Connection connection, String username) throws Exception {
        String sql = "SELECT * from users where username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,username);
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("balance");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public static ObservableList<Parking> getDataUsersFromTable1(Connection connection) throws Exception {
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Integer> getParcare1Number(Connection connection){
        ArrayList<Integer> myValuesList = new ArrayList<>();
        try{
            String sql = "SELECT number from parcare1";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                myValuesList.add(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myValuesList;
    }

    public static ArrayList<Integer> getParcare2Number(Connection connection){
        ArrayList<Integer> myValuesList = new ArrayList<>();
        try{
            String sql = "SELECT number from parcare2";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                myValuesList.add(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myValuesList;
    }

    public static ObservableList<Parking> getDataUsersFromTable2(Connection connection) throws Exception {
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare2";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Integer> getParcare3Number(Connection connection){
        ArrayList<Integer> myValuesList = new ArrayList<>();
        try{
            String sql = "SELECT number from parcare3";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                myValuesList.add(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myValuesList;
    }

    public static ObservableList<Parking> getFreeSpotsTableA(Connection connection) throws Exception{
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare1 where isoccupied = false ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<Parking> getFreeSpotsTableB(Connection connection) throws Exception{
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare2 where isoccupied = false ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<Parking> getFreeSpotsTableC(Connection connection) throws Exception{
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare3 where isoccupied = false ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static boolean checkSufficientFounds(Connection connection, String username, int value) throws Exception {
        int founds = getUsersBalance(connection,username);
        if(founds- value < 0){
            throw new InsufficientFoundsException();
        }
        return true;
    }

    public static void decreaseUsersBalance(Connection connection, String username, int value) throws SQLException {
        String sql = "UPDATE users SET balance = balance - ? WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,value);
        ps.setString(2,username);

        ps.executeUpdate();
    }


    public static void addInPaymentHistory(Connection connection, String username1, ArrayList<Integer> collection, ArrayList<String> date) throws SQLException, UserAlreadyExistsException {
        ArrayList<Integer> collection1 = new ArrayList<>();
        List<String> collection2 = new ArrayList<>();
        List<String> dateCollection = new ArrayList<>();

        for(int i=0;i<collection.size();i++){
            collection2.add(Integer.toString(collection.get(i)));
        }

        for(int i=0;i< date.size();i++){
            dateCollection.add(date.get(i));
        }

        try{
            //sa adaug in lista ce are in momentu de fata
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT date from userpaymenthistory where username = ?");
            preparedStatement2.setString(1,username1);
            ResultSet resultSet = preparedStatement2.executeQuery();
            while(resultSet.next()){
                dateCollection.add(resultSet.getString(1));
            }

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT payment from userpaymenthistory where username = ?");
            preparedStatement.setString(1,username1);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                collection2.add(rs.getString(1));
            }

            String result = collection2.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(" ", "", " "));
            String resultDate = dateCollection.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(" ", "", " "));

            PreparedStatement statement = connection.prepareStatement("update userpaymenthistory set payment = concat(?,'')  where username = ?");
            statement.setString(1, result);
            statement.setString(2,username1);
            statement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement("update userpaymenthistory set date = concat(?,'') where username = ?");
            preparedStatement1.setString(1,resultDate);
            preparedStatement1.setString(2,username1);
            preparedStatement1.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getUserCashHistory(Connection connection, String username){
        String result = new String();
        try{
            String sql = "SELECT payment from userpaymenthistory";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getUserDateHistory(Connection connection, String username){
        String result = new String();
        try{
            String sql = "SELECT date from userpaymenthistory";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result = rs.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void updateUsersFavouriteParkingA(Connection connection, String username){
        try{
            String sql = "UPDATE users set parkA = true where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateUsersFavouriteParkingB(Connection connection, String username){
        try{
            String sql = "UPDATE users set parkB = true where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateUsersFavouriteParkingC(Connection connection, String username){
        try{
            String sql = "UPDATE users set parkC = true where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateUsersBalance(Connection connection, String username, int value) throws SQLException {
        String sql = "UPDATE users SET balance = balance + ? WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,value);
        ps.setString(2,username);

        ps.executeUpdate();
    }

    public static ObservableList<UserPaymentHistoryClass> getUserPaymentHistory(Connection connection){
        ObservableList<UserPaymentHistoryClass> list = FXCollections.observableArrayList();
        Collection<String> stringList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM userpaymenthistory";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String payment = rs.getString("payment");
                list.add(new UserPaymentHistoryClass(rs.getInt("id"),rs.getString("username"),payment,rs.getString("date")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<Parking> getDataUsersFromTable3(Connection connection) throws Exception {
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare3";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static String getUserParkingAOption(Connection connection, String username){
        String result = new String();
        try{
            String sql = "select parkA from users where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = (String.valueOf(resultSet.getBoolean(1)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getUserParkingBOption(Connection connection, String username){
        String result = new String();
        try{
            String sql = "select parkB from users where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = (String.valueOf(resultSet.getBoolean(1)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getUserParkingCOption(Connection connection, String username){
        String result = new String();
        try{
            String sql = "select parkC from users where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = (String.valueOf(resultSet.getBoolean(1)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void deselectFromTable1(Connection connection, String username){
        try{
            String sql = "UPDATE parcare1 set username = null where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deselectFromTable2(Connection connection, String username){
        try{
            String sql = "UPDATE parcare2 set username = null where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deselectFromTable3(Connection connection, String username){
        try{
            String sql = "UPDATE parcare3 set username = null where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
