package edu.snow.whiskipe;

public class Item {
    private int id;
    private String name;
    private int qty;
    private double size;

    public Item(int newId, String newName, int newQty, double newSize){
        setId(newId);
        setName(newName);
        setQty(newQty);
        setSize(newSize);
    }

    public Item(){
        //make an empty item object that can be set later.
    }

    public Item(String newName, int newQty, double newSize){
        setName(newName);
        setQty(newQty);
        setSize(newSize);
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

    public String toString(){
        return id + "; " + name + "; " + qty + ";" + size;
    }


}
