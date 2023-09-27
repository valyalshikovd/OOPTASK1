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
        if (isNW(point, tmpZone)) {
            NW.addValue(point);
        }
        tmpZone = NE.zone;
        if (isNE(point, tmpZone)) {
            NE.addValue(point);
        }
        tmpZone = SE.zone;
        if (isSE(point, tmpZone)) {
            SE.addValue(point);
        }
        tmpZone = SW.zone;
        if (isSW(point, tmpZone)) {
            SW.addValue(point);
        }
    }
     boolean outOfRange(Point<T> pt){
        return zone.getX() > pt.getX() || zone.getY() > pt.getY() || zone.xPlusWidth() < pt.getX() || zone.yPlusHeight() < pt.getY();
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
            if (isNW(value, tmpZone)) {
                NW.values.add(value);
                NW.countObject++;
                continue;
            }
            tmpZone = NE.zone;
            if (isNE(value, tmpZone)) {
                NE.values.add(value);
                NE.countObject++;
                continue;
            }
            tmpZone = SE.zone;
            if (isSE(value, tmpZone)) {
                SE.values.add(value);
                SE.countObject++;
                continue;
            }
            tmpZone = SW.zone;
            if (isSW(value, tmpZone)) {
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
        if (isNW(value,tmpZone)) {
            NW.remove(value);
            return;
        }
        tmpZone = NE.zone;
        if (isNE(value, tmpZone)) {
            NE.remove(value);
            return;
        }
        tmpZone = SE.zone;
        if (isSE(value,tmpZone)) {
            SE.remove(value);
            return;
        }
        tmpZone = SW.zone;
        if (isSW(value, tmpZone)) {
            SW.remove(value);
        }
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
     private boolean isSW(Point<T> value, Zone tmpZone){
        return Double.compare(value.getX(), tmpZone.xPlusWidth()) <= 0
                && Double.compare(value.getY(), tmpZone.yPlusHeight()) <= 0
                && Double.compare(value.getY(), tmpZone.getY()) >= 0;
     }
     private boolean isSE(Point<T> value, Zone tmpZone){
        return Double.compare(value.getX(), tmpZone.getX()) > 0
                && Double.compare(value.getX(), tmpZone.xPlusWidth()) < 0
                && Double.compare(value.getY(), tmpZone.yPlusHeight()) < 0
                && Double.compare(value.getY(), tmpZone.getY()) > 0;
     }
    private boolean isNW(Point<T> value, Zone tmpZone){
        return Double.compare(value.getX(), tmpZone.xPlusWidth()) <= 0
                && Double.compare(value.getY(), tmpZone.yPlusHeight()) <= 0;
    }
    private boolean isNE(Point<T> value, Zone tmpZone){
        return Double.compare(value.getX(), tmpZone.getX()) >= 0
                && Double.compare(value.getX(), tmpZone.xPlusWidth()) <= 0
                && Double.compare(value.getY(), tmpZone.yPlusHeight()) <= 0;
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
            if (Double.compare(x, tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y, tmpZone.getY() + tmpZone.getHeight()) <= 0) {
                return NW.findByPointCoords(x, y);
            }
            tmpZone = NE.zone;
            if (Double.compare(x, tmpZone.getX()) >= 0
                    && Double.compare(x, tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y, tmpZone.getY() + tmpZone.getHeight()) <= 0) {
                return NE.findByPointCoords(x, y);
            }
            tmpZone = SE.zone;
            if (Double.compare(x, tmpZone.getX()) >= 0
                    && Double.compare(x, tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y, tmpZone.getY() + tmpZone.getHeight()) <= 0
                    && Double.compare(y, tmpZone.getY()) >= 0) {
                return SE.findByPointCoords(x, y);
            }
            tmpZone = SW.zone;
            if (Double.compare(x, tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y, tmpZone.getY() + tmpZone.getHeight()) <= 0
                    && Double.compare(y, tmpZone.getY()) >= 0) {
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
