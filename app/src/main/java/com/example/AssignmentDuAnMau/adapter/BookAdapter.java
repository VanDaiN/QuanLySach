package com.example.AssignmentDuAnMau.adapter;

import android.app.Activity;
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
import com.example.AssignmentDuAnMau.ThemSachActivity;
import com.example.AssignmentDuAnMau.dao.SachDAO;
import com.example.AssignmentDuAnMau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements Filterable{
    private List<Sach> arrSach;
    private List<Sach> arrSortSach;
    private Filter sachFilter;
    public Activity context;
    public LayoutInflater inflater;
    SachDAO sachDAO;

    public BookAdapter(Activity context) {
        super();
        this.context = context;
        sachDAO = new SachDAO(context);
    }

    public void swap(List<Sach> arrSach) {
        this.arrSach = arrSach;
        this.arrSortSach = arrSach;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final Sach sach = (Sach) arrSach.get(position);
        holder.txtBookName.setText("Mã sách: "+sach.getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+sach.getSoLuong());
        holder.txtBookPrice.setText("Giá: "+ sach.getGiaBia()+"");
        holder.setOnItemClick(new ItemClick() {
            @Override
            public void setOnClick(int position) {
                Intent intent = new Intent(context, ThemSachActivity.class);
                Bundle b = new Bundle();
                b.putString("MASACH", sach.getMaSach());
                b.putString("MATHELOAI", sach.getMaTheLoai());
                b.putString("TENSACH", sach.getTenSach());
                b.putString("TACGIA", sach.getTacGia());
                b.putString("NXB", sach.getNXB());
                b.putString("GIABIA", String.valueOf(sach.getGiaBia()));
                b.putString("SOLUONG", String.valueOf(sach.getSoLuong()));
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        //xóa sach
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Ban co muon xoa" + arrSach.get(position).getMaSach());
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface , int i) {
                                sachDAO = new SachDAO(context);
                                sachDAO.deleteSachByID(arrSach.get(position).getMaSach());
                                Toast.makeText(context, "Xóa thành công" + arrSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
                                arrSach.remove(position);
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
        return arrSach.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView txtBookName;
        TextView txtBookPrice;
        TextView txtSoLuong;
        ImageView imgDelete;
        ItemClick itemClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtBookName = (TextView) itemView.findViewById(R.id.tvBookName);
            txtBookPrice = (TextView) itemView.findViewById(R.id.tvBookPrice);
            txtSoLuong = (TextView) itemView.findViewById(R.id.tvSoLuong);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClick.setOnClick(getAdapterPosition());
        }
        public void setOnItemClick(ItemClick itemClick){
            this.itemClick = itemClick;
        }
    }
    interface ItemClick{
        void setOnClick(int position);
    }

    public void changeDataset(List<Sach> items){
        this.arrSach = items;
        notifyDataSetChanged();

    }
    public void resetData() {
        arrSach = arrSortSach;
    }


    public Filter getFilter() {
        if (sachFilter == null)
            sachFilter = new CustomFilter();

        return sachFilter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortSach;
                results.count = arrSortSach.size();
            }
            else {
                List<Sach> lsSach = new ArrayList<Sach>();

                for (Sach p : arrSach) {
                    if (p.getMaSach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsSach.add(p);
                }

                results.values = lsSach;
                results.count = lsSach.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0)
                notifyDataSetChanged();
            else {
                arrSach = (List<Sach>) results.values;
                notifyDataSetChanged();
            }

        }


    }

}

