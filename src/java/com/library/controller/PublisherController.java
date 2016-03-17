/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.Database.DataHelper;
import com.library.entity.Publisher;
import javax.inject.Named;
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
public class PublisherController implements Serializable, Converter {
    private List<SelectItem> publisherItems = new ArrayList<>();
    private List<Publisher> publishers;
    private Map<Long, Publisher> map;
    
    public PublisherController() {
        publishers = (ArrayList<Publisher>)DataHelper.getInstance().getAllPublishers();
        map = new HashMap<>();
        for(Publisher pub:publishers){
            publisherItems.add(new SelectItem(pub, pub.getName()));
            map.put(pub.getId(), pub);
        }
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public List<SelectItem> getPublisherItems() {
        return publisherItems;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Publisher)value).getId().toString();
    }
    
}
