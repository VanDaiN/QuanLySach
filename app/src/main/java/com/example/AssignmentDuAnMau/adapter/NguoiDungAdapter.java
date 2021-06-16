package com.example.AssignmentDuAnMau.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AssignmentDuAnMau.NguoiDungDetailActivity;
import com.example.AssignmentDuAnMau.R;
import com.example.AssignmentDuAnMau.dao.NguoiDungDAO;
import com.example.AssignmentDuAnMau.model.NguoiDung;

import java.util.List;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {
    private List<NguoiDung> arrNguoiDung;
    Context context;
    LayoutInflater inflater;
    NguoiDungDAO nguoiDungDAO;

    public NguoiDungAdapter(Context context, List<NguoiDung> list) {
        this.context = context;
        this.arrNguoiDung = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguoi_dung, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NguoiDung nguoiDung = (NguoiDung) arrNguoiDung.get(position);
        holder.txtName.setText(nguoiDung.getHoTen());
        holder.txtPhone.setText(nguoiDung.getPhone());

        //doi anh
        if (position % 8 == 0) {
            holder.img.setImageResource(R.drawable.user1);
        } else if (position % 8 == 1) {
            holder.img.setImageResource(R.drawable.emtwo);
        } else if (position % 8 == 2) {
            holder.img.setImageResource(R.drawable.emthree);
        } else if (position % 8 == 3) {
            holder.img.setImageResource(R.drawable.emone);
        }
        holder.setOnItemClick(new ItemClick() {
            @Override
            public void setOnClick(int position) {
                Intent intent = new Intent(context, NguoiDungDetailActivity.class);
                Bundle b = new Bundle();
                b.putString("USERNAME", nguoiDung.getUserName());
                b.putString("PHONE", nguoiDung.getPhone());
                b.putString("FULLNAME", nguoiDung.getHoTen());
                intent.putExtras(b);
                context.startActivity(intent);
            }


        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Bạn có chắc muốn xóa " + arrNguoiDung.get(position).getHoTen());
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                nguoiDungDAO = new NguoiDungDAO(context);
                                nguoiDungDAO.deleteNguoiDungByID(arrNguoiDung.get(position).getUserName());
                                Toast.makeText(context, "Xoa thành công" + arrNguoiDung.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
                                arrNguoiDung.remove(position);
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }




    @Override
    public long getItemId(int position) {
        return arrNguoiDung.size();
    }

    @Override
    public int getItemCount() {
        return arrNguoiDung.size();
    }
    interface ItemClick{
        void setOnClick(int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;
        ItemClick itemClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tvName);
            txtPhone = itemView.findViewById(R.id.tvPhone);
            imgDelete = itemView.findViewById(R.id.ivDelete);
            img = itemView.findViewById(R.id.ivIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClick.setOnClick(getAdapterPosition());
        }
        public void setOnItemClick(ItemClick itemClick) {
            this.itemClick = itemClick;
        }
    }




    public void changeDataset(List<NguoiDung> items){
        this.arrNguoiDung = items;
        notifyDataSetChanged();
    }

}

