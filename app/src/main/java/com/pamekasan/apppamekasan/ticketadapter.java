package com.pamekasan.apppamekasan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ticketadapter extends RecyclerView.Adapter<ticketadapter.myViewHolder> {

    Context context;
    ArrayList<myticket> myTicket;

    public ticketadapter (Context c, ArrayList<myticket> p){
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new myViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_profile,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder MyViewHolder, int i) {
        MyViewHolder.xnama_wisata.setText(myTicket.get(i).getNama_wisata());
        MyViewHolder.xlokasi.setText(myTicket.get(i).getLokasi());
        MyViewHolder.xjumlah_ticket.setText(myTicket.get(i).getJumlah_ticket() + " Tickets");

        final String getNamaWisata = myTicket.get(i).getNama_wisata();

        MyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyticketdetail = new Intent(context, myDetailTicket.class);
                gotomyticketdetail.putExtra("nama_wisata", getNamaWisata);
                context.startActivity(gotomyticketdetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_wisata, xlokasi, xjumlah_ticket;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_ticket = itemView.findViewById(R.id.xjumlah_ticket);
        }
    }

}
