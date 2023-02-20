package com.example.sdaassign4_2022;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/*
 * @author Chris Coughlan 2019
 */
public class LibraryViewAdapter extends RecyclerView.Adapter<LibraryViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;

    private ArrayList<BookDetails> bookDetailsArrayList;

    LibraryViewAdapter(Context mNewContext, ArrayList<BookDetails> bookDetailsArrayList) {//, ArrayList<String> author, ArrayList<String> title, ArrayList<String> imageId) {
        this.mNewContext = mNewContext;
        this.bookDetailsArrayList = bookDetailsArrayList;
    }

    /**
     * create on create view holder
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * bind view holder
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");


        viewHolder.authorText.setText(bookDetailsArrayList.get(position).getAuthorName());
        viewHolder.titleText.setText(bookDetailsArrayList.get(position).getBookName());

        Glide.with(mNewContext).load(bookDetailsArrayList.get(position).getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.imageItem);
        //should check here to see if the book is available.
        viewHolder.checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = mNewContext.getApplicationContext().getSharedPreferences(Constants.ApplicationSP, Context.MODE_PRIVATE);

                if (TextUtils.isEmpty(sharedPref.getString(Constants.UserName, ""))) {
                    showAlert();
                } else {

                    Intent myOrder = new Intent(mNewContext, CheckOut.class);
                    myOrder.putExtra(Constants.BookName, bookDetailsArrayList.get(position).getBookName());
                    myOrder.putExtra(Constants.Position, position);
                    mNewContext.startActivity(myOrder);
                }
            }
        });

    }

    /**
     * show alert
     */
    private void showAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mNewContext);
        builder1.setMessage(mNewContext.getResources().getString(R.string.update_user_setting_alert));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                mNewContext.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    /**
     * get item count
     * @return
     */
    @Override
    public int getItemCount() {
        return bookDetailsArrayList.size();
    }

    /**
     * create view holder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageItem;
        TextView authorText;
        TextView titleText;
        Button checkOut;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.bookImage);
            authorText = itemView.findViewById(R.id.authorText);
            titleText = itemView.findViewById(R.id.bookTitle);
            checkOut = itemView.findViewById(R.id.out_button);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);

        }
    }
}
