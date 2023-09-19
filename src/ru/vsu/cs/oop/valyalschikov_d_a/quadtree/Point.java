package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

class Point<T>{
    private double x;
    private double y;
    private T value;
    private String desc;
    String getDesc() {
        return desc;
    }
    void setDesc(String desc) {
        this.desc = desc;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }
    void setValue(T value) { this.value = value;}
    T getValue() { return this.value;}

    Point(int x, int y, String desc, T value) {
        this.x = x;
        this.y = y;
        this.desc = desc;
        this.value = value;
    }
    void write(){
        System.out.println("X = " +  x  + " Y = " + y + " Desc: " + desc + " ");
    }
}
