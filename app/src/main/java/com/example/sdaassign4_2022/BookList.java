package com.example.sdaassign4_2022;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Images used are sourced from Public Domain Day 2019.
 * by Duke Law School's Center for the Study of the Public Domain
 * is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan
 */
public class BookList extends Fragment {

    /**
     * Initialize
     */
    public BookList() {

    }

    /**
     * view page adapter
     */
    ViewPageAdapter adapter;

    /**
     * create book list fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.bookView_view);


        ArrayList<BookDetails> bookDetails = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        LibraryViewAdapter recyclerViewAdapter = new LibraryViewAdapter(getContext(),bookDetails);// mAuthor, mTitle, mImageID);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

              HashMap data =  (HashMap)dataSnapshot.getValue();
             HashMap r = (HashMap) data.get(Constants.Books);
             for (int i=1;i<15;i++)
             {
                 HashMap book = (HashMap) r.get(Constants.Book+i);
                 BookDetails bookDetail = new BookDetails();
                 bookDetail.setBookName(book.get(Constants.BookName).toString());
                 bookDetail.setAuthorName(book.get(Constants.AuthorName).toString());
                 bookDetail.setImageUrl(book.get(Constants.ImageUrl).toString());
                 if(book.containsKey(Constants.BID))
                 {
                     bookDetail.setBorrowerId(book.get(Constants.BID).toString());
                 }

                 if(book.containsKey(Constants.CurrentDate))
                 {
                     bookDetail.setCurrentDate(book.get(Constants.CurrentDate).toString());
                 }

                 if(book.containsKey(Constants.ExpireDate)) {
                     bookDetail.setExpiredDate(book.get(Constants.ExpireDate).toString());
                 }
bookDetails.add(bookDetail);

             }
             if(recyclerViewAdapter!=null)
             {
                 recyclerViewAdapter.notifyDataSetChanged();
             }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), getResources().getString(R.string.fail_to_get_data), Toast.LENGTH_SHORT).show();
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return root;
    }

}
