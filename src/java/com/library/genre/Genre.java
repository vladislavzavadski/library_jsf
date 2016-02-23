/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.genre;

/**
 *
 * @author Владислав
 */
public class Genre implements Comparable{
    private String name;
    private long id;
    public Genre(String name, long id){
        this.name = name;
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public long getId(){
        return id;
    }
    @Override
    public int compareTo(Object o) {
        Genre temp = (Genre)o;
        return name.compareTo(temp.getName());
    }
    
    @Override
    public String toString(){
        return name;
    }
}
