package com.trvl.travelinc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class wisata_bali extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata_bali);
    }

    public static class user1 {
        public String name;
        public String status;
        public String image;
        public String thumb_image;
        user1(){ }

        public user1(String name, String status, String image, String thumb_image) {
            this.name = name;
            this.status = status;
            this.image = image;
            this.thumb_image=thumb_image;
        }

        public String getName() { return name; }


        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getThumbImage() {
            return thumb_image;
        }

        public void setThumbImage(String thumbImage) {
            this.thumb_image = thumb_image;
        }


    }
}
