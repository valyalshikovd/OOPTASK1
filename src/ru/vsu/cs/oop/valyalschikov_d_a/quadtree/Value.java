package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

public class Value {
    private int x;
    private int y;
    private String desc;

    String getDesc() {
        return desc;
    }

    void setDesc(String desc) {
        this.desc = desc;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    Value(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }
    void write(){
        System.out.println("X = " +  x  + " Y = " + y + " Desc: " + desc + " ");
    }
}
