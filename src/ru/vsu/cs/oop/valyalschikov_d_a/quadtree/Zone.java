package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

public class Zone {
    private int x;
    private int y;
    private int height;
    private int width;

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

    int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    Zone(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
}
