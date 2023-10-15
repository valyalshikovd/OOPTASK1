package ru.vsu.cs.oop.valyalschikov_d_a.quadtree;

import java.util.HashSet;
import java.util.List;

public class Path {
    private String path;

    private final static List<String> validValues = List.of(new String[]{"se", "sw", "ne", "nw", "00"});


    public Path(String string){
        path = "";
        for(int i = 0; i < string.length()-2; i++){
            String res = string.substring(string.length() - 2);
            string = string.substring(0, string.length() - 2);
            if(!validValues.contains(res)){
                throw new RuntimeException("Недопустимое имя пути");
            }
            path += res;
        }
    }
    public Path(Path path) {
        this.path = path.getPath();
    }
    public Path(Path path, String str) {
        if(!validValues.contains(str)){
            throw new RuntimeException("Недопустимое имя пути");
        }
        this.path = path.getPath() + str;
    }
    public void addNW(){path += "nw";}
    public void addNE(){path += "ne";}
    public void addSW(){path += "sw";}
    public void addSE(){path += "se";}
    public String getPath(){
        if(path.length() == 0){
            return null;
        }
        return path;
    }
    public String getLastPath(){
        return path.substring(path.length() - 2);
    }
    String popLastPath(){
        String res = path.substring(path.length() - 2);
        path = path.substring(0, path.length() - 2);
        return res;
    }
}
