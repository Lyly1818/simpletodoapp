package com.example.simpletodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{


    List<String> items;
    Button addbutton;
    EditText editText;
    RecyclerView rvItems;
     ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addbutton = findViewById(R.id.addbutton);
        editText = findViewById(R.id.editAdd);
        rvItems = findViewById(R.id.rvItems);



        loadItems();


     ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
         @Override
         public void onItemLongClicked(int position) {
             items.remove(position);
             itemsAdapter.notifyItemRemoved(position);
             Toast.makeText(getApplicationContext(), "A new itme was removed", Toast.LENGTH_SHORT ).show();
             saveItems();
         }
     };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));




        //adding custom item to the list

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editText.getText().toString();
                items.add(todoItem);//add item to the list in the model

                //notify the adapter that an item is inserter
                itemsAdapter.notifyItemChanged(items.size()-1);
                editText.setText("");//clear the field text

                //adding a Toast to notify the user that the operation succeeded
                Toast.makeText(getApplicationContext(), "A new itme was added", Toast.LENGTH_SHORT ).show();
                saveItems();

            }
        });

    }

    private File getDataFile()
    {
        return new File(getFilesDir(), "data.txt");
    }


    private void loadItems()
    {
        try
        {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e)
        {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
        catch (NullPointerException e)
        {
            Log.e("MainActivity", "Error reading items", e);
        }

    }

    private  void saveItems()
    {
        try {
            FileUtils.writeLines(getDataFile(),  items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }



    }



}