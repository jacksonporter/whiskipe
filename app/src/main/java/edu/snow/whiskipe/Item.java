package edu.snow.whiskipe;

public class Item {
    private int id;
    private String name;
    private int qty;
    private double size;
    private int userid;

    public Item(int newId, String newName, int newQty, float newSize, int userid){
        setId(newId);
        setName(newName);
        setQty(newQty);
        setSize(newSize);
        setUserid(userid);
    }

    public Item(){
        //make an empty item object that can be set later.
    }

    public void setId(int newId){
        id = newId;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setQty(int newQty){
        if (newQty >= 0)
            qty = newQty;
    }

    public void setSize(double newSize){
        size = newSize;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getQty(){
        return qty;
    }

    public double getSize(){return size;}

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String toString(){
        return id + "; " + name + "; " + qty + ";" + size;
    }


}
