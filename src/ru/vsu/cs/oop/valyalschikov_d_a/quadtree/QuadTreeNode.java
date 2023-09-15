package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
class QuadTreeNode {
    final private int maxCountObject;
    private int countObject;
    private String id = "";
    final private Zone zone;
    private QuadTreeNode NW;
    private QuadTreeNode NE;
    private QuadTreeNode SE;
    public QuadTreeNode getNW() {
        return NW;
    }
    void setNW(QuadTreeNode NW) {
        this.NW = NW;
    }
    QuadTreeNode getNE() {
        return NE;
    }
    void setNE(QuadTreeNode NE) {
        this.NE = NE;
    }
    QuadTreeNode getSE() {
        return SE;
    }
    void setSE(QuadTreeNode SE) {
        this.SE = SE;
    }
    QuadTreeNode getSW() {
        return SW;
    }
    void setSW(QuadTreeNode SW) {
        this.SW = SW;
    }
    private QuadTreeNode SW;
    private List<Value> values = new ArrayList<>();
    private QuadTreeNode nodeParent;
    int getMaxCountObject() {
        return maxCountObject;
    }
    int getCountObject() {
        return countObject;
    }
    void setCountObject(int countObject) {
        this.countObject = countObject;
    }
    void setParent(QuadTreeNode parent) {
        nodeParent = parent;
    }
    QuadTreeNode getParent() {
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
    List<Value> getValues() {
        return values;
    }
    void setValues(List<Value> values) {
        this.values = values;
    }
    QuadTreeNode getNodeParent() {
        return nodeParent;
    }
    void setNodeParent(QuadTreeNode nodeParent) {
        this.nodeParent = nodeParent;
    }
    QuadTreeNode(int maxCountObject, QuadTreeNode parent, Zone zone, String id) {
        this.maxCountObject = maxCountObject;
        this.countObject = 0;
        this.nodeParent = parent;
        this.zone = zone;
        this.id = id;
    }
    void addValue(Value value){
        if (countObject < maxCountObject && countObject != -1){
            values.add(value);
            countObject++;
            return;
        }
        if(countObject >= maxCountObject ){
            this.divide();
        }
        Zone tmpZone = NW.zone;
        if(value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()){
            NW.addValue(value);
        }
        tmpZone = NE.zone;
        if(value.getX() > tmpZone.getX()
                && value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()){
            NE.addValue(value);
        }
        tmpZone = SE.zone;
        if(value.getX() > tmpZone.getX()
                && value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()
                && value.getY() > tmpZone.getY()){
            SE.addValue(value);
        }
        tmpZone = SW.zone;
        if(value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()
                && value.getY() > tmpZone.getY()){
            SW.addValue(value);
        }
    }
    private void divide(){
        NW = new QuadTreeNode(maxCountObject,
                this, new Zone(
                        zone.getX(),
                zone.getY(),
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_nw");
        NE = new QuadTreeNode(maxCountObject,
                this, new Zone(
                zone.getX()+ zone.getWidth()/2,
                zone.getY(),
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_ne");
        SE = new QuadTreeNode(maxCountObject,
                this, new Zone(
                zone.getX()+ zone.getWidth()/2,
                zone.getY() + zone.getHeight()/2,
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_se");
        SW = new QuadTreeNode(maxCountObject,
                this, new Zone(
                zone.getX(),
                zone.getY() + zone.getHeight()/2,
                zone.getHeight()/2,
                zone.getWidth()/2),
                id+ "_sw");
        for(Value value : values){
            Zone tmpZone = NW.zone;
            if(value.getX() < tmpZone.getX() + tmpZone.getWidth()
                    && value.getY() < tmpZone.getY() + tmpZone.getHeight()){
                NW.values.add(value);
                NW.countObject++;
                continue;
            }
            tmpZone = NE.zone;
            if(value.getX() > tmpZone.getX()
                    && value.getX() < tmpZone.getX() + tmpZone.getWidth()
                    && value.getY() < tmpZone.getY() + tmpZone.getHeight()){
                NE.values.add(value);
                NE.countObject++;
                continue;
            }
            tmpZone = SE.zone;
            if(value.getX() > tmpZone.getX()
                    && value.getX() < tmpZone.getX() + tmpZone.getWidth()
                    && value.getY() < tmpZone.getY() + tmpZone.getHeight()
                    && value.getY() > tmpZone.getY()){
                SE.values.add(value);
                SE.countObject++;
                continue;
            }
            tmpZone = SW.zone;
            if(value.getX() < tmpZone.getX() + tmpZone.getWidth()
                    && value.getY() < tmpZone.getY() + tmpZone.getHeight()
                    && value.getY() > tmpZone.getY()){
                SW.values.add(value);
                SW.countObject++;
                continue;
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
        for (Value value : values){
            value.write();
        }
    }
    void draw(Graphics2D g){
        Rectangle rectangle = new Rectangle(zone.getX(), zone.getY(), zone.getWidth(), zone.getWidth());
        g.draw(rectangle);
        if(countObject < 0){
            NW.draw(g);
            NE.draw(g);
            SE.draw(g);
            SW.draw(g);
        }
        g.setColor(Color.red);
        for (Value value: values){
            Ellipse2D ellipse2D = new Ellipse2D.Double(value.getX(), value.getY(), 3, 3);
            g.draw(ellipse2D);
        }
        g.setColor(Color.black);
    }
    void remove(Value value){
        for(int i = 0; i < countObject; i++){
            if(values.get(i).getX() == value.getX()
            && values.get(i).getY() == value.getY()){
                values.remove(i);
                this.countObject --;
                return;
            }
        }
        Zone tmpZone = NW.zone;
        if(value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()){
            NW.remove(value);
            return;
        }
        tmpZone = NE.zone;
        if(value.getX() > tmpZone.getX()
                && value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()){
            NE.remove(value);
            return;
        }
        tmpZone = SE.zone;
        if(value.getX() > tmpZone.getX()
                && value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()
                && value.getY() > tmpZone.getY()){
            SE.remove(value);
            return;
        }
        tmpZone = SW.zone;
        if(value.getX() < tmpZone.getX() + tmpZone.getWidth()
                && value.getY() < tmpZone.getY() + tmpZone.getHeight()
                && value.getY() > tmpZone.getY()){
            SW.remove(value);
        }
    }
}
