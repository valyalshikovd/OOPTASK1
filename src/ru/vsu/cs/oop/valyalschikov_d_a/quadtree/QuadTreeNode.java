package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
    private QuadTreeNode<T> nodeParent;
    public QuadTreeNode<T> getNW() {
        return NW;
    }
    void setNW(QuadTreeNode<T> NW) {
        this.NW = NW;
    }
    QuadTreeNode<T> getNE() {
        return NE;
    }
    void setNE(QuadTreeNode<T> NE) {
        this.NE = NE;
    }
    QuadTreeNode<T> getSE() {
        return SE;
    }
    void setSE(QuadTreeNode<T> SE) {
        this.SE = SE;
    }
    QuadTreeNode<T> getSW() {
        return SW;
    }
    void setSW(QuadTreeNode<T> SW) {
        this.SW = SW;
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
    void setParent(QuadTreeNode<T> parent) {
        nodeParent = parent;
    }
    QuadTreeNode<T> getParent() {
        return nodeParent;
    }
    String getId() {
        return id;
    }
    void setId(String id) {
        this.id = id;
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
    QuadTreeNode<T> getNodeParent() {
        return nodeParent;
    }
    void setNodeParent(QuadTreeNode<T> nodeParent) {
        this.nodeParent = nodeParent;
    }
    QuadTreeNode(int maxCountObject, QuadTreeNode<T> parent, Zone zone, String id) {
        this.maxCountObject = maxCountObject;
        this.countObject = 0;
        this.nodeParent = parent;
        this.zone = zone;
        this.id = id;
    }
    void addValue(Point<T> point){
        if (countObject < maxCountObject && countObject != -1){
            values.add(point);
            countObject++;
            return;
        }
        if(countObject >= maxCountObject ){
            this.divide();
        }
        Zone tmpZone = NW.zone;
        if(Double.compare(point.getX() , tmpZone.getX() + tmpZone.getWidth()) <0
                && Double.compare(point.getY() , tmpZone.getY() + tmpZone.getHeight()) < 0){
            NW.addValue(point);
        }
        tmpZone = NE.zone;
        if(Double.compare(point.getX(), tmpZone.getX()) > 0
                && Double.compare(point.getX(), tmpZone.getX() + tmpZone.getWidth()) < 0
                && Double.compare(point.getY(), tmpZone.getY() + tmpZone.getHeight()) < 0){
            NE.addValue(point);
        }
        tmpZone = SE.zone;
        if(Double.compare(point.getX(), tmpZone.getX()) > 0
                && Double.compare(point.getX(), tmpZone.getX() + tmpZone.getWidth()) < 0
                && Double.compare(point.getY(), tmpZone.getY() + tmpZone.getHeight()) < 0
                && Double.compare(point.getY(), tmpZone.getY()) > 0){
            SE.addValue(point);
        }
        tmpZone = SW.zone;
        if(Double.compare(point.getX(), tmpZone.getX() + tmpZone.getWidth()) < 0
                && Double.compare(point.getY() , tmpZone.getY() + tmpZone.getHeight()) < 0
                && Double.compare(point.getY() , tmpZone.getY()) > 0){
            SW.addValue(point);
        }
    }
    private void divide(){
        NW = new QuadTreeNode<>(maxCountObject,
                this, new Zone(
                        zone.getX(),
                zone.getY(),
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_nw");
        NE = new QuadTreeNode<>(maxCountObject,
                this, new Zone(
                zone.getX()+ zone.getWidth()/2,
                zone.getY(),
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_ne");
        SE = new QuadTreeNode<>(maxCountObject,
                this, new Zone(
                zone.getX()+ zone.getWidth()/2,
                zone.getY() + zone.getHeight()/2,
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_se");
        SW = new QuadTreeNode<>(maxCountObject,
                this, new Zone(
                zone.getX(),
                zone.getY() + zone.getHeight()/2,
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_sw");
        for(Point<T> value : values){
            Zone tmpZone = NW.zone;
            if(Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0){
                NW.values.add(value);
                NW.countObject++;
                continue;
            }
            tmpZone = NE.zone;
            if(Double.compare(value.getX() , tmpZone.getX()) >= 0
                    && Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0){
                NE.values.add(value);
                NE.countObject++;
                continue;
            }
            tmpZone = SE.zone;
            if(Double.compare(value.getX() , tmpZone.getX()) >= 0
                    && Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0
                    && Double.compare(value.getY() , tmpZone.getY()) >= 0){
                SE.values.add(value);
                SE.countObject++;
                continue;
            }
            tmpZone = SW.zone;
            if(Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0
                    && Double.compare(value.getY() , tmpZone.getY()) >= 0){
                SW.values.add(value);
                SW.countObject++;
            }
        }
        this.values.clear();
        this.countObject = -1;
    }
    void write(){
        if(countObject == -1){
            NW.write();
            NE.write();
            SE.write();
            SW.write();
            return;
        }
        for (Point<T> value : values){
            value.write();
        }
    }
    void remove(Point<T> value){
        for(int i = 0; i < countObject; i++){
            if(Double.compare( values.get(i).getX() , value.getX()) == 0
                    && Double.compare(values.get(i).getY() , value.getY()) == 0){
                values.remove(i);
                this.countObject --;
                return;
            }
        }
        Zone tmpZone = NW.zone;
        if(Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0){
            NW.remove(value);
            return;
        }
        tmpZone = NE.zone;
        if(Double.compare(value.getX() , tmpZone.getX()) >= 0
                && Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0){
            NE.remove(value);
            return;
        }
        tmpZone = SE.zone;
        if(Double.compare(value.getX() , tmpZone.getX()) >= 0
                && Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0
                && Double.compare(value.getY() , tmpZone.getY()) >= 0){
            SE.remove(value);
            return;
        }
        tmpZone = SW.zone;
        if(Double.compare(value.getX() , tmpZone.getX() + tmpZone.getWidth()) <= 0
                && Double.compare(value.getY() , tmpZone.getY() + tmpZone.getHeight()) <= 0
                && Double.compare(value.getY() , tmpZone.getY()) >= 0){
            SW.remove(value);
        }
    }
    void getZoneAsArray(Stack<int[]> stack){
        stack.push(new int[]{zone.getX(), zone.getY(), zone.getWidth(), zone.getWidth()});
        if(countObject < 0){
            NW.getZoneAsArray(stack);
            NE.getZoneAsArray(stack);
            SE.getZoneAsArray(stack);
            SW.getZoneAsArray(stack);
        }
    }

    void check(Stack<Point<T>> stack){
        if(countObject != 0){
            stack.addAll(values);
        }
        if(NW != null){ NW.check(stack);}
        if(NE != null){ NE.check(stack);}
        if(SE != null){ SE.check(stack);}
        if(SW != null){ SW.check(stack);}
    }

    T findByPointCoords(double x, double y){
        if(countObject == -1){
            Zone tmpZone = NW.zone;
            if(Double.compare(x , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y , tmpZone.getY() + tmpZone.getHeight()) <= 0){
                return NW.findByPointCoords(x, y);
            }
            tmpZone = NE.zone;
            if(Double.compare(x , tmpZone.getX()) >= 0
                    && Double.compare(x , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y , tmpZone.getY() + tmpZone.getHeight()) <= 0){
                return NE.findByPointCoords(x, y);
            }
            tmpZone = SE.zone;
            if(Double.compare(x , tmpZone.getX()) >= 0
                    && Double.compare(x , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y , tmpZone.getY() + tmpZone.getHeight()) <= 0
                    && Double.compare(y , tmpZone.getY()) >= 0){
                return SE.findByPointCoords(x,y);
            }
            tmpZone = SW.zone;
            if(Double.compare(x , tmpZone.getX() + tmpZone.getWidth()) <= 0
                    && Double.compare(y , tmpZone.getY() + tmpZone.getHeight()) <= 0
                    && Double.compare(y , tmpZone.getY()) >= 0){
                return SW.findByPointCoords(x,y);
            }
        }
        for (Point<T> point : values){
            if(Double.compare(point.getX(), x) == 0
            && Double.compare(point.getY(), y) == 0){
                return point.getValue();
            }
        }
        return null;
    }


}
