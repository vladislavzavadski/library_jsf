/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.genre;

import com.library.database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;

/**
 *
 * @author Владислав
 */
@ManagedBean
@ApplicationScoped
public class Genres {
        private static ArrayList<Genre> genreList = null;

    public ArrayList<Genre> getGenrelist(){
        if(genreList==null){
            genreList = new ArrayList();
            try {
                Statement stmt = Database.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery("select *from genre");
                while(rs.next()){
                    genreList.add(new Genre(rs.getString("name"), rs.getLong("id")));
                }
            } catch (NamingException | SQLException ex) {
                Logger.getLogger(Genres.class.getName()).log(Level.SEVERE, null, ex);
            }
            Collections.sort(genreList);
            return genreList;
        }
        else{
            return genreList;
        }
    }
}
