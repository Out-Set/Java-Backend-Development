package com.example.project.librarymanagement.Util;

import com.example.project.librarymanagement.DataAccessLayer.Book;

public class BookValidator {

    public boolean isValid(Book book){

        if(book.getTitle()=="" || book.getTitle()==null)
            return false;
        return true;
    }
}
