package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

import java.awt.*;
import java.util.*;

public class QuadTree<T> implements Collection<Point<T>> {
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
        this.zone = new Zone(x, y, height, width);
        this.size = 0;
    }

    public void add(int x, int y, String desc, T data) {
        size++;
        Point<T> value = new Point<>(x, y, desc, data);
        if (root == null) {
            root = new QuadTreeNode<>(maxCounterObject, null, zone, "0");
            root.addValue(value);
            return;
        }
        root.addValue(value);
    }
    public Stack<int[]> getZones(){
        Stack<int[]> stack = new Stack<>();
        if (this.getRoot() != null) {
            this.getRoot().getZoneAsArray(stack);
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

    public QuadTreeNode<T> find(String desc) {
        String[] ar = desc.split("_");
        QuadTreeNode<T> curr = root;
        for (String s : ar) {
            switch (s) {
                case "nw":
                    curr = curr.getNW();
                case "ne":
                    curr = curr.getNE();
                case "se":
                    curr = curr.getSE();
                case "sw":
                    curr = curr.getSW();
                    break;
            }
        }
        return curr;
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
        if (root == null) {
            root = new QuadTreeNode<>(maxCounterObject, null, zone, "0");
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

}
