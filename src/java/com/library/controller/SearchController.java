/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.database.Database;
import com.library.enums.SearchType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.library.usebean.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
/**
 *
 * @author Владислав
 */
@ManagedBean(eager = true)
@SessionScoped
public class SearchController implements Serializable {    
    private String searchString;
    private SearchType searchType;
    private ArrayList<Book> currentBookList;
    private ArrayList<Integer> pageNumber;
    private int booksOnPage = 2;
    private int totalBookNumber;
    private int currentGenre = -1;
    private String currentLetter = " ";
    private String lastSqlQuery;
    private boolean editMode;
    private String name;
    private int checkedBooks;
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public boolean isEditMode() {
        return editMode;
    }
    
    public void switchEdit(){
        editMode=!editMode;
    }
    
    public void changeBookCheck(boolean flag){
       if(flag){
           checkedBooks++;
       }
       else{
           checkedBooks--;
       }
    }
    
    public int getBooksOnPage() {
        return booksOnPage;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }
    private int currentPageNumber = 1;
    public ArrayList<Integer> getPageNumber() {
        return pageNumber;
    }
    public SearchController(){
        this.fillBooksAll();
        editMode=false;
        currentLetter = " ";
        checkedBooks=0;
    }

    public void setCheckedBooks(int checkedBooks) {
        this.checkedBooks = checkedBooks;
    }

    public int getCheckedBooks() {
        return checkedBooks;
    }

    public SearchType getSearchType() {
        return searchType;
    }
   
    public void setSearchType(SearchType st){
        this.searchType = st;
    }

    public ArrayList<Book> getCurrentBookList() {
        return currentBookList;
    }
    
    private void fillNumberArray(int rowNumber){
        pageNumber = new ArrayList<>();
        for(int i=1; i<=(rowNumber/2)+((rowNumber%2==1)?1:0); i++){
            pageNumber.add(i);
        }
    }
    
    private void fillBooksBySQL(String sql){
        checkedBooks=0;
       // this.switchEdit();
        try {
            Connection con =  Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            currentBookList = new ArrayList<>();
            lastSqlQuery = sql;
            rs.last();
            totalBookNumber = rs.getRow();
            fillNumberArray(rs.getRow());
            if(rs.getRow() > booksOnPage){
                //TODO:rghrehge///////////////////////////////////////////////////////////////////////////
                
                sql+=(" limit " + ((currentPageNumber-1)*2)+","+booksOnPage);
            }
                rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                Book book = new Book();
                book.setAuthor(rs.getString("author"));
                book.setBookId(rs.getLong("id"));
                book.setPageCount(rs.getInt("page_count"));
                book.setImage(rs.getBytes("image"));
                book.setName(rs.getString("name"));
                book.setIsbn(rs.getString("isbn"));
                book.setGenre(rs.getString("genre"));
                book.setDescription(rs.getString("descr"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishDate(rs.getInt("publish_year"));
                currentBookList.add(book);
            }
            con.close();
            stmt.close();
            rs.close();
        } catch (NamingException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getTotalBookNumber() {
        return totalBookNumber;
    }
    
    public byte[] getPdf(long id){
        byte[] bytePdf = null;
        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select content from book where id=" + id);
            } catch (SQLException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(rs.next()){
                bytePdf = rs.getBytes("content");
            }
            con.close();
            stmt.close();
            rs.close();
            return bytePdf;
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytePdf;
    }
    
    public byte[] getImage(long id){
        byte[] byteImage = null;
        try {
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select image from book where id=" + id);
            } catch (SQLException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(rs.next()){
                byteImage = rs.getBytes("image");
            }
            con.close();
            stmt.close();
            rs.close();
            return byteImage;
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byteImage;
    }
    
    public void fillBooksByGenre(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        currentPageNumber = 1;
        currentLetter = " ";
        int id = Integer.parseInt(params.get("genre_id"));
        currentGenre = id;
        fillBooksBySQL("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, a.fio as author, g.name as genre, b.descr, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id "
                + "where genre_id=" + id + " order by b.name ");
    }
    
    private void fillBooksAll() {
        currentPageNumber = 1;
        currentGenre = -1;
        currentLetter = " ";
        fillBooksBySQL("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, b.descr, "
                + "a.fio as author, g.name as genre, b.image from book b inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id inner join publisher p on b.publisher_id=p.id order by b.name");
    }
    
        public Character[] getRussianLetters() {
        Character[] letters = new Character[33];
        letters[0] = 'А';
        letters[1] = 'Б';
        letters[2] = 'В';
        letters[3] = 'Г';
        letters[4] = 'Д';
        letters[5] = 'Е';
        letters[6] = 'Ё';
        letters[7] = 'Ж';
        letters[8] = 'З';
        letters[9] = 'И';
        letters[10] = 'Й';
        letters[11] = 'К';
        letters[12] = 'Л';
        letters[13] = 'М';
        letters[14] = 'Н';
        letters[15] = 'О';
        letters[16] = 'П';
        letters[17] = 'Р';
        letters[18] = 'С';
        letters[19] = 'Т';
        letters[20] = 'У';
        letters[21] = 'Ф';
        letters[22] = 'Х';
        letters[23] = 'Ц';
        letters[24] = 'Ч';
        letters[25] = 'Ш';
        letters[26] = 'Щ';
        letters[27] = 'Ъ';
        letters[28] = 'Ы';
        letters[29] = 'Ь';
        letters[30] = 'Э';
        letters[31] = 'Ю';
        letters[32] = 'Я';

        return letters;
    }
        
    public void fillBooksByLetter(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        currentPageNumber = 1;
        currentGenre = -1;
        String let = params.get("letter");
        currentLetter = let;
        fillBooksBySQL("select b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, a.fio as author, g.name as genre, b.descr, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id "
                + "where substr(b.name,1,1)='" + let + "' order by b.name ");
    } 
    
    public void fillBooksBySearch() {
        currentPageNumber = 1;
        currentGenre = -1;
        currentLetter = " ";
        if (searchString.trim().length() == 0) {
            fillBooksAll();
            return;
        }

        StringBuilder sql = new StringBuilder("select b.descr, b.id,b.name,b.isbn,b.page_count,b.publish_year, p.name as publisher, a.fio as author, g.name as genre, b.image from book b "
                + "inner join author a on b.author_id=a.id "
                + "inner join genre g on b.genre_id=g.id "
                + "inner join publisher p on b.publisher_id=p.id ");

        if (searchType == SearchType.Author) {
            sql.append("where lower(a.fio) like '%" + searchString.toLowerCase() + "%' order by b.name ");

        } else if (searchType == SearchType.Name) {
            sql.append("where lower(b.name) like '%" + searchString.toLowerCase() + "%' order by b.name ");
        }



        fillBooksBySQL(sql.toString());


    }

    public String getCurrentLetter() {
        return currentLetter;
    }

    public int getCurrentGenre() {
        return currentGenre;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }
    
    public void saveBookChanges(){
        PreparedStatement prepStmt = null;
        Connection conn=null;
        
        try{
           conn=Database.getConnection();
           prepStmt = conn.prepareStatement("update book set name=?, isbn=?, page_count=?, publish_year=?, descr=? where id=?");
           for(Book book: currentBookList){
                prepStmt.setString(1, book.getName());
                prepStmt.setString(2, book.getIsbn());
                prepStmt.setString(1, book.getName());
                prepStmt.setString(2, book.getIsbn());
//                prepStmt.setString(3, book.getAuthor());
                prepStmt.setInt(3, book.getPageCount());
                prepStmt.setInt(4, book.getPublishDate());
//                prepStmt.setString(6, book.getPublisher());
                prepStmt.setString(5, book.getDescription());
                prepStmt.setLong(6, book.getBookId());
                prepStmt.addBatch();               
           }
           prepStmt.executeBatch();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.switchEdit();
        this.stop();    
       
    }
    
    private void stop(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showPage(){
        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        currentPageNumber = Integer.parseInt(params.get("page_number"));
        checkedBooks=0;
        fillBooksBySQL(lastSqlQuery);
    }
}
