package com.tuanhoang.quanlysinhvien;

/**
 * Created by Tuấn Hoàng on 4/17/2018.
 */

public class SinhVien {
    private String name, id;
    private int age;
    private float point;

    public SinhVien() {
    }

    public SinhVien(String id, String name, int age, float point) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
