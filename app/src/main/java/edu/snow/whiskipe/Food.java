package edu.snow.whiskipe;

public class Food {
    private int id;
    private String name;
    private double qty;

    public Food(int newId, String newName, double newQty){
        setId(newId);
        setName(newName);
        setQty(newQty);
    }

    public void setId(int newId){
        id = newId;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setQty(double newQty){
        if (newQty >= 0.0)
            qty = newQty;
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

    public String toString(){
        return id + "; " + name + "; " + qty;
    }
}
