package com.example.helpcasamobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList house_id, house_title, house_nbrchambre, house_ville, house_description, house_price, house_image, house_type;


    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList house_id,
                  ArrayList house_title,
                  ArrayList house_nbrchambre,
                  ArrayList house_ville,
                  ArrayList house_description,
                  ArrayList house_image,
                  ArrayList house_price,
                  ArrayList house_type){
        this.activity = activity;
        this.context = context;
        this.house_id = house_id;
        this.house_title = house_title;
        this.house_nbrchambre = house_nbrchambre;
        this.house_ville = house_ville;
        this.house_image = house_image;
        this.house_description = house_description;
        this.house_price = house_price;
        this.house_type = house_type;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.getAdapterPosition();
        holder.house_id_txt.setText(String.valueOf(house_id.get(position)));
        holder.house_title_txt.setText(String.valueOf(house_title.get(position)));
        holder.house_nbrchambre.setText(String.valueOf(house_nbrchambre.get(position)));
        holder.house_ville_txt.setText(String.valueOf(house_ville.get(position)));
        holder.house_description_txt.setText(String.valueOf(house_description.get(position)));
        holder.house_price_txt.setText(String.valueOf(house_price.get(position)));
        holder.house_type_txt.setText(String.valueOf(house_type.get(position)));

        //Recyclerview onClickListener
        byte[] imageBytes = (byte[]) house_image.get(position);
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.house_image.setImageBitmap(bitmap);
        }
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(house_id.get(position)));
                intent.putExtra("title", String.valueOf(house_title.get(position)));
                intent.putExtra("nbrchambre", String.valueOf(house_nbrchambre.get(position)));
                intent.putExtra("ville", String.valueOf(house_ville.get(position)));
                intent.putExtra("description", String.valueOf(house_description.get(position)));
                intent.putExtra("price", String.valueOf(house_price.get(position)));
                intent.putExtra("image", String.valueOf(house_image.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.reserverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReservationActivity.class);
                intent.putExtra("id", String.valueOf(house_id.get(position)));
                intent.putExtra("title", String.valueOf(house_title.get(position)));
                intent.putExtra("price", String.valueOf(house_price.get(position)));
                intent.putExtra("house_type", String.valueOf(house_type.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return house_id.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView house_id_txt, house_title_txt, house_nbrchambre, house_ville_txt, house_description_txt, house_price_txt, house_type_txt;
        ConstraintLayout mainLayout;
        Button reserverbtn,editbtn;
        ImageView house_image;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            house_id_txt = itemView.findViewById(R.id.house_id_txt);
            house_title_txt = itemView.findViewById(R.id.house_title_txt);
            house_nbrchambre = itemView.findViewById(R.id.house_nbrchambre_txt);
            house_ville_txt = itemView.findViewById(R.id.house_ville_txt);
            house_description_txt = itemView.findViewById(R.id.house_desciption_txt);
            house_price_txt = itemView.findViewById(R.id.house_price_txt);
            house_image = itemView.findViewById(R.id.house_img);
            house_type_txt = itemView.findViewById(R.id.house_type_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            reserverbtn = itemView.findViewById(R.id.reserverbtn);
            editbtn = itemView.findViewById(R.id.editbtn);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }



}
