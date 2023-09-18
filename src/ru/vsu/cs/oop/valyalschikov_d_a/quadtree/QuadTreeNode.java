package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
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
    void addValue(Point<T> value){
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
        for (ru.vsu.cs.oop.valyalschikov_d_a.quadtree.Point<T> value: values){
            Ellipse2D ellipse2D = new Ellipse2D.Double(value.getX()-1, value.getY()-1, 3, 3);
            g.draw(ellipse2D);
        }
        g.setColor(Color.black);
    }
}
