package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class QuadTree<T> implements Collection<T> {
    private QuadTreeNode<T> root;
    private int maxCounterObject;
    private final Zone zone;
    private int size;
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
        this.size = 0;
    }
    public void add(int x, int y, String desc, T data){
        size++;
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
        size--;
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

    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        Random random = new Random();
        add(random.nextInt(zone.getX()), random.nextInt(zone.getY()), t.toString(), t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T item : c){
            this.add(item);
        }
        return true;
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
}
