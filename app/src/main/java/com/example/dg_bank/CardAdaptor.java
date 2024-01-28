package com.example.dg_bank;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;

public class CardAdaptor extends BaseAdapter {
    Card[] cards;
    LayoutInflater inflater;
    public CardAdaptor(Context context, Card[] cards){
        this.cards = cards;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return cards.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_card_view, null);
        TextView number1 = view.findViewById(R.id.card_number_1);
        TextView number2 = view.findViewById(R.id.card_number_2);
        TextView number3 = view.findViewById(R.id.card_number_3);
        TextView number4 = view.findViewById(R.id.card_number_4);
        TextView cvv = view.findViewById(R.id.card_cvv);
        TextView expiryDate = view.findViewById(R.id.card_expr);
        TextView title = view.findViewById(R.id.card_title_my_cards);
        TextView status = view.findViewById(R.id.card_status);
        Switch lock = view.findViewById(R.id.card_lock);
        ImageView image = view.findViewById(R.id.card_image);
        String[] cardSegments = cards[i].cardNumber.split(" ");
        number1.setText(cardSegments[0]);
        number2.setText(cardSegments[1]);
        number3.setText(cardSegments[2]);
        number4.setText(cardSegments[3]);
        cvv.setText(cards[i].cvv);
        expiryDate.setText(cards[i].expiryDate);
        lock.setChecked(cards[i].lock);
        if(cards[i].lock)
            status.setText("Locked");
        else
            status.setText("Active");
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lock.isChecked()) {
                    new Thread(new Runnable() {
                        public void run() {
                            Data.sqlManager.updateQuery("UPDATE card SET lock = 'true' WHERE number = '" + cards[i].cardNumber + "'");
                        }
                    }).start();
                    cards[i].lock = true;
                    lock.setChecked(cards[i].lock);
                    status.setText("Locked");
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            Data.sqlManager.updateQuery("UPDATE card SET lock = 'false' WHERE number = '" + cards[i].cardNumber + "'");
                        }
                    }).start();
                    cards[i].lock = false;
                    lock.setChecked(cards[i].lock);
                    status.setText("Active");
                }
            }
        });
        title.setText(cards[i].cardHolderName);
        if(Objects.equals(cards[i].cardType, "Visa Debit Card"))
            image.setImageResource(R.drawable.visa_card);
        else if(Objects.equals(cards[i].cardType, "Mastercard Debit Card"))
            image.setImageResource(R.drawable.master_card);
        else
            image.setImageResource(R.drawable.pakpay_card);
        return view;
    }
}
