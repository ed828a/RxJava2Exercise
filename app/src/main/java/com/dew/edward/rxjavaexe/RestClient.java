package com.dew.edward.rxjavaexe;
/*
 * Created by Edward on 6/13/2018.
 */

import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

public class RestClient {
    private Context mContext;

    public RestClient(Context context) {
        this.mContext = context;
    }

    public List<String> getFavoriateBooks() {
        SystemClock.sleep(5000); // 5 seconds
        return createBooks();
    }

    public List<String> getFavoriteBooksWithException(){
        SystemClock.sleep(5000);
        throw new RuntimeException("Failed to load");
    }

    private List<String> createBooks() {
        List<String> books = new ArrayList<>();
        books.add("Load of the Rings");
        books.add("The dark elf");
        books.add("Eclipse Introduction");
        books.add("History book");
        books.add("Der kleine Prinz");
        books.add("7 habits of highly effective people");
        books.add("Other book 1");
        books.add("Other book 2");
        books.add("Other book 3");
        books.add("Other book 4");
        books.add("Other book 5");
        books.add("Other book 6");
        return books;
    }
}
