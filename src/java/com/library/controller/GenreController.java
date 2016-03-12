/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.Database.DataHelper;
import com.library.entity.Genre;
import java.util.ArrayList;
import java.util.Collections;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Владислав
 */
@ManagedBean
@ApplicationScoped
public class GenreController {
        private static ArrayList<Genre> genreList = null;

    public ArrayList<Genre> getGenrelist(){
        if(genreList==null){
            genreList = (ArrayList<Genre>) DataHelper.getInstance().getAllGenres();
           /* try {
                Connection con = Database.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select *from genre");
                while(rs.next()){
                    Genre genre = new Genre();
                    genre.setName(rs.getString("name"));
                    genre.setId(rs.getLong("id"));
                    genreList.add(genre);
                }
                con.close();
                stmt.close();
                rs.close();
            } catch (NamingException | SQLException ex) {
                Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            Collections.sort(genreList);
            return genreList;
        }
        else{
            return genreList;
        }
    }
}
