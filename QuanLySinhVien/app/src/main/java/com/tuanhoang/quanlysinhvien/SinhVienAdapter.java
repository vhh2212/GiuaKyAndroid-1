package com.tuanhoang.quanlysinhvien;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Tuấn Hoàng on 4/17/2018.
 */

public class SinhVienAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    List<SinhVien> arrSV;

    public SinhVienAdapter() {
    }

    public SinhVienAdapter(Context context, int myLayout, List<SinhVien> arrSV) {
        this.context = context;
        this.myLayout = myLayout;
        this.arrSV = arrSV;
    }


    @Override
    public int getCount() {
        return arrSV.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout, null);

        //ánh xạ và gán giá trị
        if(i%2==0){
            view.setBackgroundColor(Color.GRAY);
        }else{
            view.setBackgroundColor(Color.LTGRAY);
        }
        TextView txtID = view.findViewById(R.id.txtID);
        txtID.setText(txtID.getText().toString() + arrSV.get(i).getId() + " Arr " + arrSV.indexOf(arrSV.get(i)));

        TextView txtName = view.findViewById(R.id.txtName);
        txtName.setText(txtName.getText().toString() + arrSV.get(i).getName());

        TextView txtAge = view.findViewById(R.id.txtAge);
        txtAge.setText(txtAge.getText().toString() + arrSV.get(i).getAge());

        TextView txtPoint = view.findViewById(R.id.txtPoint);
        txtPoint.setText(txtPoint.getText().toString() + arrSV.get(i).getPoint());

        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.apple);
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
