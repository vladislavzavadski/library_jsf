/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.Database.DataHelper;
import com.library.entity.Author;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
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
public class AuthorController implements Serializable, Converter {
    private List<SelectItem> selectItems = new ArrayList<>();
    private List<Author> list;
    private Map<Long, Author> map;
    /**
     * Creates a new instance of AuthorController
     */
    public AuthorController() {
        list = DataHelper.getInstance().getAllAuthors();
        map = new HashMap<>();
        for(Author author : list){
            selectItems.add(new SelectItem(author, author.getFio()));
            map.put(author.getId(), author);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Author)value).getId().toString();
    }
    
}
