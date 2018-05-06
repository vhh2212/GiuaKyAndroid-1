package com.tuanhoang.quanlysinhvien;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tuấn Hoàng on 4/17/2018.
 */

public class EditSinhVien extends AppCompatActivity {
    private DatabaseReference mData;
    private Button btnEdit, btnDel;
    private TextView twID, twName, twAge, twPoint;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rowsv);
        mData = FirebaseDatabase.getInstance().getReference("SinhVien");
        btnEdit = findViewById(R.id.btnEdit);
        btnDel = findViewById(R.id.btnDel);
        twID = findViewById(R.id.txtID);
        twName = findViewById(R.id.txtName);
        twAge = findViewById(R.id.txtAge);
        twPoint = findViewById(R.id.txtPoint);



    }
//    private void clickBtnEdit(){
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog();
//                //dialog.setTitle("Thêm sinh viên");
//                dialog.setCancelable(false);
//                dialog.setContentView(R.layout.editstudent);
//                edtID = dialog.findViewById(R.id.edtID);
//                edtName = dialog.findViewById(R.id.edtName);
//                edtAge = dialog.findViewById(R.id.edtAge);
//                edtPoint = dialog.findViewById(R.id.edtPoint);
//
//
//                btnBack = dialog.findViewById(R.id.btnBack);
//                btnBack.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.cancel();
//                    }
//                });
//
//                int count = adapter.getCount();
//                final Object item = (SinhVien) adapter.getItem(count);
//                final long itemId = adapter.getItemId(count);
//
//                System.out.println(String.valueOf(checkedItemPosition));
//                System.out.println(String.valueOf(String.valueOf(id)));
//                System.out.println(String.valueOf(String.valueOf(count)));
//
//                System.out.println(String.valueOf(String.valueOf(itemId)));
////                Toast.makeText(showSinhVien.this, String.valueOf(checkedItemPosition), Toast.LENGTH_SHORT).show();
////                Toast.makeText(showSinhVien.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
////                Toast.makeText(showSinhVien.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
////                Toast.makeText(showSinhVien.this, String.valueOf(itemId), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//    }


}
