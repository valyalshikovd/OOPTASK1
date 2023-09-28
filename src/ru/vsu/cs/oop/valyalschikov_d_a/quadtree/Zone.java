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
    private double yPlusHeight(){return y + height;}
    private double xPlusWidth(){return x + width;}

    boolean isInside(Point pt ){
        if(pt.getX() < x || pt.getX() > xPlusWidth()){
            return false;
        }
        if(pt.getY() < y || pt.getY() > yPlusHeight()){
            return false;
        }
        return true;
    }
    boolean isInside(double x, double y ){
        if(x < this.x || x > xPlusWidth()){
            return false;
        }
        if(y < this.y || y > yPlusHeight()){
            return false;
        }
        return true;
    }
}
