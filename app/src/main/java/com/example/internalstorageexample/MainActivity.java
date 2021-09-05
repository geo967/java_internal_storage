package com.example.internalstorageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText text;
    Button save,load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text=findViewById(R.id.enter_text_id);
        save=findViewById(R.id.save_button_id);
        load=findViewById(R.id.load_button_id);

        handleSaveButtonClick();

        handleLoadButtonClick();
    }

    private void handleLoadButtonClick() {
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fis=null;
                try {
                    fis=openFileInput("internal_file");
                    InputStreamReader isr=new InputStreamReader(fis);
                    BufferedReader br=new BufferedReader(isr);
                    StringBuilder sb=new StringBuilder();
                    String inputText;
                    while((inputText=br.readLine()) !=null){
                        sb.append(inputText).append("\n");
                    }
                    text.setText(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally{
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void handleSaveButtonClick() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText=text.getText().toString().trim();
                FileOutputStream fos=null;
                try {
                    fos=openFileOutput("internal_file",MODE_PRIVATE);
                    fos.write(inputText.getBytes());
                    text.getText().clear();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this,"saved to"+getFilesDir()+"/"+"internal_file",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                finally{
                    if(fos!=null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}