package ru.vsu.cs.oop.valyalschikov_d_a;

public class Value {
    private int x;
    private int y;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Value(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }
    public void write(){
        System.out.println("X = " +  x  + " Y = " + y + " Desc: " + desc + " ");
    }
}
