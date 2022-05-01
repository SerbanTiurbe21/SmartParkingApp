package com.example.services;

import com.example.exceptions.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class BazaDeDate {
    static String url = "jdbc:mysql://localhost:3306/bazameadedate";
    static String user = "root";
    static String password = "21102001serby";

    public static Connection getConnection() throws Exception{
        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;
    }

    public static void connectToTheDatabase() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println("Connection is successfull to the database! " + url);
    }

    public static void insertValues(Connection connection,String username, String password, String role, int balance, String plateNumber) throws SQLException, CompleteRegisterDataException, UsernameNotLongEnoughException, PasswordNotLongEnoughException, PasswordNotStrongEnoughException, UserAlreadyExistsException {
        String sqlInsertwithParams = "INSERT INTO users(username,password,role,balance,platenumber)" + "VALUES(?,?,?,?,?)";
        //checkCredentials(username,password);
        checkRegisterDataNotEmpty(username,password);
        userAlreadyExists(connection,username);
        checkUsernameLength(username);
        checkPasswordStrength(password);
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertwithParams);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,encodePassword(username,password));
            preparedStatement.setString(3,role);
            preparedStatement.setInt(4,balance);
            preparedStatement.setString(5,plateNumber);

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

}
