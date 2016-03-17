/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.Database.DataHelper;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.enums.QueryType;
import com.library.enums.SearchType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;

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
    private QueryType queryType;
    private int totalBookNumber;
    private long currentGenre = -1;
    private String currentLetter = " ";
    private boolean editMode;
    private String name;
    private int checkedBooks;
    private ArrayList<Author> authors;
    private int authorNum = 5;

    public void setAuthorNum(int authorNum) {
        this.authorNum = authorNum;
    }

    public int getAuthorNum() {
        return authorNum;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
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
    
   public void changeBooksNumberOnPage(ValueChangeEvent e){
        this.stop();
        currentPageNumber = 1;
        booksOnPage = Integer.parseInt(e.getNewValue().toString());
        fillBooksByPrevQuery();
    }
   
    private void fillBooksByPrevQuery(){
        if(queryType==QueryType.All){
           currentBookList = (ArrayList<Book>) DataHelper.getInstance().getAllBooks((currentPageNumber-1)*booksOnPage, booksOnPage);

        }
        if(queryType==QueryType.Genre){
           currentBookList =  (ArrayList<Book>) DataHelper.getInstance().getBooksByGenre(currentGenre, (currentPageNumber-1)*booksOnPage, booksOnPage);
        }
        if(queryType==QueryType.Letter){
            currentBookList = (ArrayList<Book>) DataHelper.getInstance().getBooksByLetter(currentLetter.charAt(0), (currentPageNumber-1)*booksOnPage, booksOnPage);
        }
        if(queryType==QueryType.Search){
            if(searchType==SearchType.Author){
                currentBookList = (ArrayList<Book>) DataHelper.getInstance().getBookByAuthor(searchString, (currentPageNumber-1)*booksOnPage, booksOnPage);
            }
            if(searchType==SearchType.Name){
                currentBookList = (ArrayList<Book>) DataHelper.getInstance().getBookByName(searchString, (currentPageNumber-1)*booksOnPage, booksOnPage);
            }
        }
        totalBookNumber = DataHelper.getInstance().getTotalBooksNumber();
        fillNumberArray(totalBookNumber);
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
        fillBooksAll();
        authors = (ArrayList<Author>) DataHelper.getInstance().getAllAuthors();
        editMode=false;
        currentLetter = " ";
        checkedBooks=0;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
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
        
        for(int i=1; i<=(rowNumber/booksOnPage)+((rowNumber%booksOnPage==0)?0:1); i++){
            pageNumber.add(i);
        }
    }
    
    public int getTotalBookNumber() {
        return totalBookNumber;
    }
    
    public byte[] getPdf(long id){
        return DataHelper.getInstance().getContent(id);
    }
    
    public byte[] getImage(long id){
        return DataHelper.getInstance().getImage(id);
    }
    
    public void fillBooksByGenre(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        currentPageNumber = 1;
        currentLetter = " ";
        long id = Long.parseLong(params.get("genre_id"));
        currentGenre = id;
        queryType = QueryType.Genre;
        currentBookList = (ArrayList<Book>) DataHelper.getInstance().getBooksByGenre(id, 0, booksOnPage);
        totalBookNumber = DataHelper.getInstance().getTotalBooksNumber();
        fillNumberArray(totalBookNumber);
    }
    
    private void fillBooksAll() {
        currentPageNumber = 1;
        currentGenre = -1;
        currentLetter = " ";
        queryType = QueryType.All;
        currentBookList = (ArrayList<Book>) DataHelper.getInstance().getAllBooks(0, booksOnPage);
        totalBookNumber = DataHelper.getInstance().getTotalBooksNumber();
        fillNumberArray(totalBookNumber);
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
        queryType = QueryType.Letter;
        currentBookList = (ArrayList<Book>) DataHelper.getInstance().getBooksByLetter(let.charAt(0), 0, booksOnPage);
        totalBookNumber = DataHelper.getInstance().getTotalBooksNumber();
        fillNumberArray(totalBookNumber);
    } 
    
    public void fillBooksBySearch() {
        currentPageNumber = 1;
        currentGenre = -1;
        currentLetter = " ";
        if (searchString.trim().length() == 0) {
            fillBooksAll();
            return;
        }
        queryType = QueryType.Search;
        if(searchType==SearchType.Author)
           currentBookList =  (ArrayList<Book>) DataHelper.getInstance().getBookByAuthor(searchString, 0, booksOnPage);
        if(searchType==SearchType.Name)
           currentBookList = (ArrayList<Book>) DataHelper.getInstance().getBookByName(searchString, 0, booksOnPage);
        totalBookNumber = DataHelper.getInstance().getTotalBooksNumber();
        fillNumberArray(totalBookNumber);
    }

    public String getCurrentLetter() {
        return currentLetter;
    }

    public long getCurrentGenre() {
        return currentGenre;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }
    
    public void saveBookChanges(){
        DataHelper.getInstance().update(this.currentBookList);
        this.switchEdit();
        /////////////////////////////
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
        fillBooksByPrevQuery();
    }
}
