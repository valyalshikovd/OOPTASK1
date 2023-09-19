package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

import java.util.Objects;

public class Point<T>{
    private double x;
    private double y;
    private T value;
    private String desc;
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setValue(T value) { this.value = value;}
    public T getValue() { return this.value;}

    Point(int x, int y, String desc, T value) {
        this.x = x;
        this.y = y;
        this.desc = desc;
        this.value = value;
    }
    void write(){
        System.out.println("X = " +  x  + " Y = " + y + " Desc: " + desc + " ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point<?> point = (Point<?>) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0 && value.equals(point.value) && desc.equals(point.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, value, desc);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }
}
