package com.tuanhoang.quanlysinhvien;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button btnAdd, btnShow, btnEdit, btnDel, btnExit;
    private DatabaseReference mData;
    List<SinhVien> arr;
    EditText edtIDDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = FirebaseDatabase.getInstance().getReference("SinhVien");
        arr = new ArrayList<>();
        //arr = null;

        mapped();
        clickBtnAdd();
        clickBtnExit();
        clickBtnShow();
        clickBtnDelete();
    }
    private void getFirebase(){
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dts : dataSnapshot.getChildren()){
                    arr.add(dts.getValue(SinhVien.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    protected void mapped(){
        btnAdd = findViewById(R.id.btnAdd);
        btnShow = findViewById(R.id.btnShow);
        btnEdit = findViewById(R.id.btnEdit);
        btnDel = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.btnExit);
    }
    protected void clickBtnAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edtName, edtID, edtAge, edtPoint;
                final Button btnBack;
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.addstudent);
                edtID = dialog.findViewById(R.id.edtID);
                edtName = dialog.findViewById(R.id.edtName);
                edtAge = dialog.findViewById(R.id.edtAge);
                edtPoint = dialog.findViewById(R.id.edtPoint);


                btnBack = dialog.findViewById(R.id.btnBack);

                arr = null;
                arr = new ArrayList<>();
                getFirebase();
                Button btnAdd2 = dialog.findViewById(R.id.btnAdd);
                btnAdd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(edtID.getText().toString().trim()) || TextUtils.isEmpty(edtName.getText().toString().trim())
                        || TextUtils.isEmpty(edtAge.getText().toString().trim()) || TextUtils.isEmpty(edtPoint.getText().toString().trim())){
                            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            final String id = (edtID.getText().toString());
                            String name = (edtName.getText().toString());
                            int age = (int)convertEditText(edtAge);
                            float point = convertEditText(edtPoint);
                            final SinhVien sv = new SinhVien(id, name, age, point);

                            boolean kt = false;
                            for (int i = 0; i < arr.size(); i++) {
                                if (arr.get(i).getId().compareToIgnoreCase(id) == 0) {
                                    kt = true;
                                    break;
                                }
                            }
                            if(!checkAge(age)){
                                Toast.makeText(MainActivity.this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else if(!checkPoint(point)){
                                Toast.makeText(MainActivity.this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else if (kt) {
                                Toast.makeText(MainActivity.this, "MSSV đã được dùng", Toast.LENGTH_SHORT).show();
                            } else {
                                mData.child(id).setValue(sv);
                                Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }

        });

    }
    protected void clickBtnShow(){
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, showSinhVien.class);

                startActivity(intent);
            }
        });
    }
    protected void clickBtnEdit(){
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edtName, edtID, edtAge, edtPoint;
                final Button btnBack;
                final Dialog dialog = new Dialog(MainActivity.this);
                //dialog.setTitle("Thêm sinh viên");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.addstudent);
                edtID = dialog.findViewById(R.id.edtID);
                edtName = dialog.findViewById(R.id.edtName);
                edtAge = dialog.findViewById(R.id.edtAge);
                edtPoint = dialog.findViewById(R.id.edtPoint);


                btnBack = dialog.findViewById(R.id.btnBack);


                Button btnAdd = dialog.findViewById(R.id.btnAdd);


                dialog.show();
            }
        });
    }
    protected void clickBtnDelete(){
        Button btnDel = findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.deletesv);
                Button btnDel2 = dialog.findViewById(R.id.btnDel);
                Button btnBack = dialog.findViewById(R.id.btnDelBack);

                edtIDDel = dialog.findViewById(R.id.edtDeleteID);
                arr = null;
                arr = new ArrayList<>();
                getFirebase();

                btnDel2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final String idDel = edtIDDel.getText().toString().trim();
                        if (TextUtils.isEmpty(idDel)) {
                            Toast.makeText(MainActivity.this, "Vui lòng nhập MSSV ", Toast.LENGTH_SHORT).show();
                        } else {

                            boolean kt = false;
                            for (int i = 0; i < arr.size(); i++) {
                                if (arr.get(i).getId().compareToIgnoreCase(idDel) == 0) {
                                    kt = true;
                                    break;
                                }
                            }
                            if (kt) {
                                mData.child(idDel).removeValue();
                                Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                                edtIDDel.setText("");
                            }else{
                                Toast.makeText(MainActivity.this, "Không tìm thấy sinh viên cần xóa", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
    protected void clickBtnExit(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
                //builder.setTitle("Exit app");
                builder.setMessage("Click Yes to exit");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.show();
            }
        });

    }
    private float convertEditText(EditText edt){
        float n = -1;
        try {
            n = Float.parseFloat(edt.getText().toString().trim());
        }catch (Exception e){
            //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT ).show();
        }
        return n;
    }
    private boolean checkPoint(float n){
        if(n<0 || n>10){
            return false;
        }
        return true;
    }
    private boolean checkAge(int n){
        if(n<18 || n>24){
            return false;
        }
        return true;
    }
}
