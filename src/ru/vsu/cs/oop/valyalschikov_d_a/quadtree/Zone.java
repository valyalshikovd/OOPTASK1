package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

class Zone {
    final private double x;
    final private double y;
    final private double height;
    final private double width;

    double getX() {return x;}
    double getY() {
        return y;
    }
    double getHeight() {
        return height;
    }
    double getWidth() {
        return width;
    }
    Zone(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    double yPlusHeight(){return y + height;}
    double xPlusWidth(){return x + width;}
}
