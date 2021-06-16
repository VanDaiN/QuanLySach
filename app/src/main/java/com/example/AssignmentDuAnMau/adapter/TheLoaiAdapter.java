package com.example.AssignmentDuAnMau.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AssignmentDuAnMau.R;
import com.example.AssignmentDuAnMau.TheLoaiActivity;
import com.example.AssignmentDuAnMau.dao.TheLoaiDAO;
import com.example.AssignmentDuAnMau.model.TheLoai;

import java.util.List;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHolder> implements Filterable {
    List<TheLoai> arrTheLoai;
    Context context;
    public LayoutInflater inflater;
    TheLoaiDAO theLoaiDAO;

    public TheLoaiAdapter(Context context, List<TheLoai> list) {
        this.context = context;
        this.arrTheLoai = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theloai,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final TheLoai theLoai = (TheLoai) arrTheLoai.get(position);
        holder.txtTenTheLoai.setText("Tên thể loại: "+theLoai.getTenTheLoai());
        holder.txtMaTheLoai.setText("Mã thể loại: "+theLoai.getMaTheLoai());
        holder.setOnItemClick(new ViewHolder.ItemClick() {
            @Override
            public void setOnClick(int position) {
                Intent intent = new Intent(context, TheLoaiActivity.class);
                Bundle b = new Bundle();
                b.putString("MATHELOAI", theLoai.getMaTheLoai());
                b.putString("TENTHELOAI", theLoai.getTenTheLoai());
                b.putString("MOTA", theLoai.getMoTa());
                b.putString("VITRI", String.valueOf(theLoai.getViTri()));
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Ban co muon xoa" + arrTheLoai.get(position).getMaTheLoai());
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i) {
                                theLoaiDAO = new TheLoaiDAO(context);
                                theLoaiDAO.deleteTheLoaiByID(arrTheLoai.get(position).getMaTheLoai());
                                Toast.makeText(context, "Xóa thành công " + arrTheLoai.get(position).getTenTheLoai(), Toast.LENGTH_SHORT).show();
                                arrTheLoai.remove(position);
                                notifyDataSetChanged();
                                dialogInterface.cancel();
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
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrTheLoai.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        ImageView img;
        TextView txtMaTheLoai;
        TextView txtTenTheLoai;
        ImageView imgDelete;
        ItemClick itemClick;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtMaTheLoai = (TextView) itemView.findViewById(R.id.tvMaTheLoai);
            txtTenTheLoai = (TextView) itemView.findViewById(R.id.tvTenTheLoai);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClick.setOnClick(getAdapterPosition());
        }
        interface ItemClick{
            void setOnClick(int position);
        }
        public void setOnItemClick(ItemClick itemClick){
            this.itemClick = itemClick;
        }
    }




    public void changeDataset(List<TheLoai> items){
        this.arrTheLoai = items;
        notifyDataSetChanged();

    }

}

