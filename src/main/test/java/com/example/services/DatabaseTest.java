package com.example.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DatabaseTest {

    @BeforeEach
    public void setUp() throws Exception {
        BazaDeDate.connectToTheDatabase();
    }

    @Test
    @DisplayName("Test the database connection")
    public void testDbConnection() throws Exception {
        Assertions.assertNotNull(BazaDeDate.getConnection());
    }

    @Test
    @DisplayName("Test password encryption")
    public void testPassword(){
        String username = "Alin2001";
        String parola = "@Alin2001";
        String encoded = BazaDeDate.encodePassword(username,parola);
        Assertions.assertEquals(encoded,BazaDeDate.encodePassword(username,parola));
    }

    @Test
    @DisplayName("Test for successfully inserting in the database!")
    void testInsertValues() throws Exception {
        Connection connection = BazaDeDate.getConnection();
        String username = "Alin2999";
        String pass = "@Alin2001";
        int value = 1000;
        String role = "client";
        String numberPlate = "BH 99 ALN";
        BazaDeDate.insertValues(connection,username,pass,role,value,numberPlate);
        String userName = BazaDeDate.getUsername(connection,username);
        Assertions.assertNotNull(userName);
    }

    @Test
    @DisplayName("Test for failed insertion in the database!")
    public void testInsertionFail() throws Exception{
        Connection connection = BazaDeDate.getConnection();
        String username = "Alin2001";
        String pass = "@Alin2001";
        int value = 1000;
        String role = "client";
        String numberPlate = "BH 99 ALN";
        BazaDeDate.insertValues(connection,username,pass,role,value,numberPlate);
        String userName = BazaDeDate.getUsername(connection,username);
        Assertions.assertNotNull(userName);
    }

    @Test
    @DisplayName("Test for seeing if the numbers of records is incremented")
    public void testIncrementation() throws Exception {
        int count = BazaDeDate.getIdUsers(BazaDeDate.getConnection());
        BazaDeDate.insertValues(BazaDeDate.getConnection(),"Aux2001","@Aux2001","client",0,"BH 99 WWW");
        Assertions.assertNotEquals(count+1,count);
    }

}
