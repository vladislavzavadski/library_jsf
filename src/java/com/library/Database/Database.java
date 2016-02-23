/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Владислав
 */
public class Database {
    private static InitialContext ic;
    private static DataSource ds;
    public static Connection getConnection() throws NamingException, SQLException{
        ic = new InitialContext();
        ds = (DataSource)ic.lookup("jdbc/Library");
        return ds.getConnection();
    } 
}
