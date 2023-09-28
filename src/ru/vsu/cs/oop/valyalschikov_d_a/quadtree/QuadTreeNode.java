package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class QuadTreeNode<T> {
    final private int maxCountObject;
    private int countObject;
    private String id;
    final private Zone zone;
    private QuadTreeNode<T> NW;
    private QuadTreeNode<T> NE;
    private QuadTreeNode<T> SE;
    private QuadTreeNode<T> SW;
    private List<Point<T>> values = new ArrayList<>();


    public QuadTreeNode<T> getNW() {
        return NW;
    }
    QuadTreeNode<T> getNE() {
        return NE;
    }
    QuadTreeNode<T> getSE() {
        return SE;
    }
    QuadTreeNode<T> getSW() {
        return SW;
    }
    int getMaxCountObject() {
        return maxCountObject;
    }

    int getCountObject() {
        return countObject;
    }

    void setCountObject(int countObject) {
        this.countObject = countObject;
    }
    String getId() {
        return id;
    }
    Zone getZone() {
        return zone;
    }

    List<Point<T>> getValues() {
        return values;
    }

    void setValues(List<Point<T>> values) {
        this.values = values;
    }
    QuadTreeNode(int maxCountObject,  Zone zone, String id) {
        this.maxCountObject = maxCountObject;
        this.countObject = 0;
        this.zone = zone;
        this.id = id;
    }

    void addValue(Point<T> point) {
        if(outOfRange(point)){
            return;
        }
        if (countObject < maxCountObject && countObject != -1) {
            values.add(point);
            countObject++;
            return;
        }
        if (countObject >= maxCountObject) {
            this.divide();
        }
        Zone tmpZone = NW.zone;
        if (tmpZone.isInside(point)) {NW.addValue(point);}
        tmpZone = NE.zone;
        if (tmpZone.isInside(point)) {NE.addValue(point);}
        tmpZone = SE.zone;
        if (tmpZone.isInside(point)) {SE.addValue(point);}
        tmpZone = SW.zone;
        if (tmpZone.isInside(point)) {SW.addValue(point);}
    }
     boolean outOfRange(Point<T> pt){
        return !this.zone.isInside(pt);
    }

    private void divide() {
        NW = new QuadTreeNode<>(maxCountObject,
                new Zone(
                zone.getX(),
                zone.getY(),
                zone.getHeight() * 0.5,
                zone.getWidth() *0.5),
                id + "_nw");
        NE = new QuadTreeNode<>(maxCountObject,
                new Zone(
                zone.getX() + zone.getWidth() *0.5,
                zone.getY(),
                zone.getHeight() *0.5,
                zone.getWidth() *0.5),
                id + "_ne");
        SE = new QuadTreeNode<>(maxCountObject,
                new Zone(
                zone.getX() + zone.getWidth() *0.5,
                zone.getY() + zone.getHeight() *0.5,
                zone.getHeight() *0.5,
                zone.getWidth() *0.5),
                id + "_se");
        SW = new QuadTreeNode<>(maxCountObject,
                new Zone(
                zone.getX(),
                zone.getY() + zone.getHeight() *0.5,
                zone.getHeight() *0.5,
                zone.getWidth() *0.5),
                id + "_sw");
        for (Point<T> value : values) {
            Zone tmpZone = NW.zone;
            if (tmpZone.isInside(value)) {
                NW.values.add(value);
                NW.countObject++;
                continue;
            }
            tmpZone = NE.zone;
            if (tmpZone.isInside(value)) {
                NE.values.add(value);
                NE.countObject++;
                continue;
            }
            tmpZone = SE.zone;
            if (tmpZone.isInside(value)) {
                SE.values.add(value);
                SE.countObject++;
                continue;
            }
            tmpZone = SW.zone;
            if (tmpZone.isInside(value)) {
                SW.values.add(value);
                SW.countObject++;
            }
        }
        this.values.clear();
        this.countObject = -1;
    }

    void write() {
        if (countObject == -1) {
            NW.write();
            NE.write();
            SE.write();
            SW.write();
            return;
        }
        for (Point<T> value : values) {
            value.write();
            System.out.println(id);
        }
    }

    void remove(Point<T> value) {
        if (NW != null
                && SW != null
                && SE != null
                && NE != null
                && NW.countObject + NE.countObject + SE.countObject + SW.countObject == maxCountObject
                && NW.countObject != -1
                && NE.countObject != -1
                && SE.countObject != -1
                && SW.countObject != -1) {
            values.addAll(NW.values);
            values.addAll(NE.values);
            values.addAll(SW.values);
            values.addAll(SE.values);
            countObject = maxCountObject - 1;
            NW = null;
            SW = null;
            SE = null;
            NE = null;
        }
        for (int i = 0; i < countObject; i++) {
            if (Double.compare(values.get(i).getX(), value.getX()) == 0
                    && Double.compare(values.get(i).getY(), value.getY()) == 0) {
                values.remove(i);
                this.countObject--;
                return;
            }
        }
        if(countObject != -1){
            return;
        }
        Zone tmpZone = NW.zone;
        if (tmpZone.isInside(value)) {NW.remove(value);}
        tmpZone = NE.zone;
        if (tmpZone.isInside(value)) {NE.remove(value);}
        tmpZone = SE.zone;
        if (tmpZone.isInside(value)) {SE.remove(value);}
        tmpZone = SW.zone;
        if (tmpZone.isInside(value)) {SW.remove(value);}
    }

    void getZoneAsArray(Stack<double[]> stack) {
        stack.push(new double[]{zone.getX(), zone.getY(), zone.getWidth(), zone.getWidth()});
        if (countObject < 0) {
            NW.getZoneAsArray(stack);
            NE.getZoneAsArray(stack);
            SE.getZoneAsArray(stack);
            SW.getZoneAsArray(stack);
        }
    }
    void check(Stack<Point<T>> stack) {

        if (countObject != 0) {
            stack.addAll(values);
        }
        if (NW != null) {
            NW.check(stack);
        }
        if (NE != null) {
            NE.check(stack);
        }
        if (SE != null) {
            SE.check(stack);
        }
        if (SW != null) {
            SW.check(stack);
        }
    }

    T findByPointCoords(double x, double y) {
        if (countObject == -1) {
            Zone tmpZone = NW.zone;
            if (tmpZone.isInside(x,y)) {
                return NW.findByPointCoords(x, y);
            }
            tmpZone = NE.zone;
            if (tmpZone.isInside(x,y)) {
                return NE.findByPointCoords(x, y);
            }
            tmpZone = SE.zone;
            if (tmpZone.isInside(x,y)) {
                return SE.findByPointCoords(x, y);
            }
            tmpZone = SW.zone;
            if (tmpZone.isInside(x,y)) {
                return SW.findByPointCoords(x, y);
            }
        }
        for (Point<T> point : values) {
            if (Double.compare(point.getX(), x) == 0
                    && Double.compare(point.getY(), y) == 0) {
                return point.getValue();
            }
        }
        return null;
    }


}
