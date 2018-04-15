package edu.snow.whiskipe;

import android.content.Context;
import android.widget.Button;

public class FoodButton extends Button {
    private Food food;

    public FoodButton(Context context, Food newFood){
        super(context);
        food = newFood;
    }

    public double getPrice(){
        return food.getQty();
    }
}

