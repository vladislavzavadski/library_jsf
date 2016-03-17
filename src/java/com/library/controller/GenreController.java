/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.Database.DataHelper;
import com.library.entity.Genre;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 *
 * @author Владислав
 */
@ManagedBean(eager = false)
@SessionScoped
public class GenreController implements Serializable, Converter{
    private ArrayList<Genre> genreList;
    private List<SelectItem> list = new ArrayList<>();
    private Map<Long, Genre> map;
    public GenreController(){
        genreList = (ArrayList<Genre>) DataHelper.getInstance().getAllGenres();
        map = new HashMap<>();
        for(Genre genre: genreList){
            map.put(genre.getId(), genre);
            list.add(new SelectItem(genre, genre.getName()));
        }
    }
    
    public ArrayList<Genre> getGenrelist(){
        return genreList;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Genre)value).getId().toString();
    }
}
