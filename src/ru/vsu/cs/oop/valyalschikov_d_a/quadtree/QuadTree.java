package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

import java.util.*;
import java.util.List;

public class QuadTree<T> implements Collection<Point<T>> {
    private QuadTreeNode<T> root;
    private int maxCounterObject;
    private final Zone zone;
    private int size;

    public void setRoot(QuadTreeNode<T> root, int maxCounterObject) {
        this.root = root;
        this.maxCounterObject = maxCounterObject;
    }

    public QuadTree(int x, int y, int height, int width, int maxCounterObject) {
        root = null;
        this.maxCounterObject = maxCounterObject;
        this.zone = new Zone(x, y, height, width);
        this.size = 0;
    }

    public void add(int x, int y, String desc, T data) {
        Point<T> value = new Point<>(x, y, desc, data);
        if(!zone.isInside(x, y)){
            return;
        }
        size++;
        if (root == null) {
            root = new QuadTreeNode<>(maxCounterObject, zone, new Path("00"));
            root.addValue(value);
            return;
        }
        root.addValue(value);
    }
    public Stack<double[]> getZones(){
        Stack<double[]> stack = new Stack<>();
        if (root != null) {
            root.getZoneAsArray(stack);
        }
        return stack;
    }

    public void consoleWrite() {
        if (root == null) {
            System.out.println("QuadTree is a void");
        }
        root.write();
    }

    public void remove(Point<T> value) {
        size--;
        root.remove(value);
    }

    public List<Point<T>> find(Path path) {
        QuadTreeNode<T> curr = root;
        while (path.getPath() != null) {
            String s = path.popLastPath();
            if(s.equals("nw")){
                curr = curr.getNW();
            }
            if(s.equals("sw")){
                curr = curr.getSW();
            }
            if(s.equals("se")){
                curr = curr.getSE();
            }
            if(s.equals("ne")){
                curr = curr.getNE();
            }
        }
        if(curr != null){
            return curr.getValues();
        }
        return null;
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
        for (Point<T> pt : this) {
            if (Objects.equals(pt.getValue(), o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Point<T>> iterator() {
        Stack<Point<T>> stack = new Stack<>();
        root.check(stack);
        return new Iterator<Point<T>>() {
            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Point<T> next() {
                assert stack.peek() != null;
                return stack.pop();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Point<T>[] ptArr = new Point[size];
        int counter = 0;
        for (Point<T> pt : this) {
            ptArr[counter] = pt;
        }
        return ptArr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(Point<T> t) {
        size++;
        if (root == null) {
            root = new QuadTreeNode<>(maxCounterObject, zone, new Path("00"));
            root.addValue(t);
            return true;
        }
        root.addValue(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (Point<T> pt : this) {
            if (Objects.equals(pt.getValue(), o)) {
                this.remove(pt);
            }
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            boolean flag = false;
            for (Point<T> pt : this) {
                if (Objects.equals(pt.getValue(), o)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean addAll(Collection<? extends Point<T>> c) {
        for (Point<T> item : c) {
            this.add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for(Object o : c){
            for (Point<T> pt : this) {
                if (Objects.equals(pt.getValue(), o)) {
                    this.remove(pt);
                }
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(Object o : c){
            for (Point<T> pt : this) {
                if (!Objects.equals(pt.getValue(), o)) {
                    this.remove(pt);
                }
            }
        }
        return true;
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    public T getByPointCoords(double x, double y){
        return root.findByPointCoords(x, y);
    }
}
