package com.example.plquang.zenfonewatermark;

import android.content.Intent;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vinaygaba.rubberstamp.RubberStamp;
import com.vinaygaba.rubberstamp.RubberStampConfig;
import com.vinaygaba.rubberstamp.RubberStampPosition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class MainActivity extends AppCompatActivity {
    private Spinner spnCategory;
    private static final int PICK_IMAGE = 100;
    ImageView image,bg;
    Button btn;
    Uri imageUri;
android.graphics.Bitmap  bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=(ImageView) findViewById(R.id.imageView3);
        btn=(Button) findViewById(R.id.button);
        bg=(ImageView) findViewById(R.id.imageView3);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Point p=new Point();
               p.offset(50,50);
               Bitmap logo=BitmapFactory.decodeResource(getResources(), R.drawable.zf2);
               Bitmap b=waterMark(BitmapFactory.decodeResource(getResources(), R.drawable.pokemon),"Watermark - Le Quang",p,Color.BLACK,100,50,true,logo);
               image.setImageBitmap(b);

           }
       });
        spnCategory = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Zenfone 2 Black");
        list.add("Zenfone 2 White");
        list.add("Zenfone 3 Black");
        list.add("Zenfone 3 White");
        list.add("Zenfone Max");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             //   Toast.makeText(MainActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE)
        {
            imageUri=data.getData();
           image.setImageURI(imageUri);


         }
        else
        {
            Toast.makeText(this,"Đã xảy ra lỗi!",Toast.LENGTH_LONG ).show();
        }
    }
    public  Bitmap waterMark(Bitmap src, String watermark, Point location, int color, int alpha, int size, boolean underline,Bitmap logo) {
        //get source image width and height
        int w = src.getWidth();
        int h = src.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        //create canvas object
        Canvas canvas = new Canvas(result);
        //draw bitmap on canvas
        canvas.drawBitmap(src, 0, 0, null);
        //create paint object
        Paint paint = new Paint();
        //apply color
        paint.setColor(color);
        //set transparency
        paint.setAlpha(alpha);
        //set text size
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        //set should be underlined or not
        paint.setUnderlineText(underline);
        //draw text on given location
        canvas.drawBitmap(logo,location.x,location.y,paint);
        //canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }


    }

