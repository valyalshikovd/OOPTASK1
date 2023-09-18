package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;
import java.awt.*;

public class QuadTree<T>  {
    private QuadTreeNode<T> root;
    private int maxCounterObject;
    private final Zone zone;
    public QuadTreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(QuadTreeNode<T> root, int maxCounterObject) {
        this.root = root;
        this.maxCounterObject = maxCounterObject;
    }
    public QuadTree(int x, int y, int height, int width, int maxCounterObject) {
        root = null;
        this.maxCounterObject = maxCounterObject;
        this.zone = new Zone(x,y,height,width);
    }
    public void add(int x, int y, String desc, T data){
        Point<T> value = new Point<>(x, y, desc, data );
        if(root == null){
            root = new QuadTreeNode<>( maxCounterObject, null, zone, "0");
            root.addValue(value);
            return;
        }
        root.addValue(value);
    }
    public void consoleWrite(){
        if(root == null){
            System.out.println("QuadTree is a void");
        }
        root.write();
    }
    public void remove(Point<T> value){
        root.remove(value);
    }
    public QuadTreeNode<T> find(String desc){
        String[] ar = desc.split("_");
        QuadTreeNode<T> curr = root;
        for (String s : ar) {
            switch (s) {
                case "nw": curr = curr.getNW();
                case "ne": curr = curr.getNE();
                case "se": curr = curr.getSE();
                case "sw": curr = curr.getSW();
                    break;
            }
        }
        return curr;
    }
    public void draw(Graphics2D g){
        if(this.getRoot() != null){
            this.getRoot().draw(g);
        }
    }
}
