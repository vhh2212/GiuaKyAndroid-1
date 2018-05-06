package com.tuanhoang.quanlysinhvien;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuấn Hoàng on 4/17/2018.
 */

public class showSinhVien extends AppCompatActivity{
    private ListView lvSV;
    private List<SinhVien> arrSV , arr, arrSearch;
    private Button btnBackToMain, btnSearch, btnAdd, btnEdit;
    private EditText edtSearch, edtInputID, edtIDDel;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlistsv);
        setTitle("Quản Lý Sinh Viên");
        mData = FirebaseDatabase.getInstance().getReference("SinhVien");
        lvSV = findViewById(R.id.lvSV);
        arrSV = new ArrayList<>();
        //arrSearch = new ArrayList<>();
        //arr = new ArrayList<>();
        getFirebase();
        Toast.makeText(showSinhVien.this, "Arr size: " + arrSV.size(), Toast.LENGTH_SHORT).show();

        show();
        edit();
        clickBtnShow();
        clickBtnSearch();
        clickBtnBackToMain();
        clickBtnDelete();
        clickBtnAdd();
        arrSV.clear();
    }
    private void getFirebase(){
        //arrSV.clear();
        arrSV = new ArrayList<>();
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dts : dataSnapshot.getChildren()){
                    arrSV.add(dts.getValue(SinhVien.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void show(){
        //arrSV.clear();
        //getFirebase();
        SinhVienAdapter sinhVienAdapter = new SinhVienAdapter(showSinhVien.this, R.layout.rowsv, arrSV );
        lvSV.setAdapter(sinhVienAdapter);
    }
    private void clickBtnBackToMain(){
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(showSinhVien.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void clickBtnSearch(){
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch = findViewById(R.id.edtSearch);
                final String id = edtSearch.getText().toString().trim();
                //arrSearch = new ArrayList<>();

                //getFirebase();
                if(TextUtils.isEmpty(id)){
                    //arrSV.clear();
                    //show();
                    //arrSV.clear();
                    //show();
                    ArrayList<SinhVien> a = new ArrayList<>();
                    SinhVienAdapter sinhVienAdapter = new SinhVienAdapter(showSinhVien.this, R.layout.rowsv, a );
                    lvSV.setAdapter(sinhVienAdapter);
                    Toast.makeText(showSinhVien.this, "Vui lòng nhập MSSV", Toast.LENGTH_SHORT).show();
                }else{

                arr = null;
                for(int i=0; i<arrSV.size(); i++){
                    if (arrSV.get(i).getId().compareToIgnoreCase(id) == 0){
                        arr = new ArrayList<>();
                        arr.add(arrSV.get(i));
                    }
                }
                if(arr == null){
                    ArrayList<SinhVien> a = new ArrayList<>();
                    SinhVienAdapter sinhVienAdapter = new SinhVienAdapter(showSinhVien.this, R.layout.rowsv, a );
                    lvSV.setAdapter(sinhVienAdapter);
                    Toast.makeText(showSinhVien.this,"Không tìm thấy sinh viên nào", Toast.LENGTH_SHORT).show();
                }else{
                    SinhVienAdapter sinhVienAdapter = new SinhVienAdapter(showSinhVien.this, R.layout.rowsv, arr );
                    lvSV.setAdapter(sinhVienAdapter);
                }}
            }
        });
    }
    private void edit(){
        Button btnEditSV = findViewById(R.id.btnEditSV);
        btnEditSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(showSinhVien.this);
                dialog.setContentView(R.layout.inputid);
                edtInputID = dialog.findViewById(R.id.edtInputID);
                Button btnDialogEdit = dialog.findViewById(R.id.btnDialogEdit);
                getFirebase();
                Toast.makeText(showSinhVien.this, "Edit Arr size: " + arrSV.size(), Toast.LENGTH_SHORT).show();
                dialog.show();
                btnDialogEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        String idEdit = edtInputID.getText().toString().trim();
                        final SinhVien sv  = checkFirebase(idEdit);
                        if(sv == null ){
                            Toast.makeText(showSinhVien.this, "Không tìm thấy sinh viên nào", Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.cancel();
                            final Dialog dialog2 = new Dialog(showSinhVien.this);
                            dialog2.setContentView(R.layout.editstudent);
                            dialog2.setCancelable(false);

                            final TextView txtID = dialog2.findViewById(R.id.txtEditID);
                            final EditText edtEditName = dialog2.findViewById(R.id.edtEditName);
                            final EditText edtEditAge = dialog2.findViewById(R.id.edtEditAge);
                            final EditText edtEditPoint = dialog2.findViewById(R.id.edtEditPoint);
                            Button btnUpdate = dialog2.findViewById(R.id.btnUpdate);
                            final Button btnEditBack = dialog2.findViewById(R.id.btnEditBack);
                            txtID.setText(txtID.getText().toString() + sv.getId());
                            edtEditName.setText(sv.getName());
                            edtEditAge.setText(String.valueOf(sv.getAge()));
                            edtEditPoint.setText(String.valueOf(sv.getPoint()));
                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (TextUtils.isEmpty(edtEditName.getText().toString().trim())
                                            || TextUtils.isEmpty(edtEditAge.getText().toString().trim()) || TextUtils.isEmpty(edtEditPoint.getText().toString().trim())){
                                        Toast.makeText(showSinhVien.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String name = (edtEditName.getText().toString());
                                        int age = (int)convertEditText(edtEditAge);
                                        float point = convertEditText(edtEditPoint);
                                        final SinhVien tmp = new SinhVien(sv.getId(), name, age, point);
                                        if(!checkAge(age)){
                                            Toast.makeText(showSinhVien.this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show();
                                        }else if(!checkPoint(point)){
                                            Toast.makeText(showSinhVien.this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mData.child(sv.getId()).setValue(tmp);
                                            Toast.makeText(showSinhVien.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                                            dialog2.cancel();
                                            //arr = new ArrayList<>();
                                            //arrSV.removeAll(arrSV);
                                            //getFirebase();
                                            //show();
                                        }
                                    }
                                }
                            });
                            btnEditBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog2.cancel();
                                }
                            });
                            dialog2.show();
                        }
                    }
                });
            }
        });
    }
    private SinhVien checkFirebase(final String s){
       SinhVien  sv = null;
        for (int i = 0; i<arrSV.size(); i++){
            if(arrSV.get(i).getId().compareToIgnoreCase(s) == 0){
                sv = arrSV.get(i);
                break;
            }
        }
        return sv;
    }
    protected void clickBtnShow(){
        Button btnShowSV = findViewById(R.id.btnShowSV);
        btnShowSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFirebase();
                Toast.makeText(showSinhVien.this, "Show Arr size: " + arrSV.size(), Toast.LENGTH_SHORT).show();

                show();
            }
        });
    }
    protected void clickBtnAdd(){
        btnAdd = findViewById(R.id.btnAddSV);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edtName, edtID, edtAge, edtPoint;
                final Button btnBack;
                final Dialog dialog = new Dialog(showSinhVien.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.addstudent);
                edtID = dialog.findViewById(R.id.edtID);
                edtName = dialog.findViewById(R.id.edtName);
                edtAge = dialog.findViewById(R.id.edtAge);
                edtPoint = dialog.findViewById(R.id.edtPoint);


                btnBack = dialog.findViewById(R.id.btnBack);

                arrSV = null;
                arrSV = new ArrayList<>();
                getFirebase();
                Toast.makeText(showSinhVien.this, "Add Arr size: " + arrSV.size(), Toast.LENGTH_SHORT).show();

                Button btnAdd2 = dialog.findViewById(R.id.btnAdd);
                btnAdd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(edtID.getText().toString().trim()) || TextUtils.isEmpty(edtName.getText().toString().trim())
                                || TextUtils.isEmpty(edtAge.getText().toString().trim()) || TextUtils.isEmpty(edtPoint.getText().toString().trim())){
                            Toast.makeText(showSinhVien.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            final String id = (edtID.getText().toString());
                            String name = (edtName.getText().toString());
                            int age = (int)convertEditText(edtAge);
                            float point = convertEditText(edtPoint);
                            final SinhVien sv = new SinhVien(id, name, age, point);

                            boolean kt = false;
                            for (int i = 0; i < arrSV.size(); i++) {
                                if (arrSV.get(i).getId().compareToIgnoreCase(id) == 0) {
                                    kt = true;
                                    break;
                                }
                            }
                            if(!checkAge(age)){
                                Toast.makeText(showSinhVien.this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else if(!checkPoint(point)){
                                Toast.makeText(showSinhVien.this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else if (kt) {
                                Toast.makeText(showSinhVien.this, "MSSV đã được dùng", Toast.LENGTH_SHORT).show();
                            } else {
                                mData.child(id).setValue(sv);
                                Toast.makeText(showSinhVien.this, "Đã thêm", Toast.LENGTH_SHORT).show();
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
    protected void clickBtnDelete(){
        Button btnDel = findViewById(R.id.btnDelSV);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(showSinhVien.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.deletesv);
                Button btnDel2 = dialog.findViewById(R.id.btnDel);
                Button btnBack = dialog.findViewById(R.id.btnDelBack);

                edtIDDel = dialog.findViewById(R.id.edtDeleteID);
                //arrSV = null;
                //arrSV = new ArrayList<>();
                //arr
                getFirebase();
                Toast.makeText(showSinhVien.this, "Del Arr size: " + arrSV.size(), Toast.LENGTH_SHORT).show();


                btnDel2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final String idDel = edtIDDel.getText().toString().trim();
                        if (TextUtils.isEmpty(idDel)) {
                            Toast.makeText(showSinhVien.this, "Vui lòng nhập MSSV ", Toast.LENGTH_SHORT).show();
                        } else {

                            boolean kt = false;
                            for (int i = 0; i < arrSV.size(); i++) {
                                if (arrSV.get(i).getId().compareToIgnoreCase(idDel) == 0) {
                                    kt = true;
                                    arrSV.remove(i);
                                    break;
                                }
                            }
                            if (kt) {
                                mData.child(idDel).removeValue();

                                Toast.makeText(showSinhVien.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                                edtIDDel.setText("");
                            }else{
                                Toast.makeText(showSinhVien.this, "Không tìm thấy sinh viên cần xóa", Toast.LENGTH_SHORT).show();
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
