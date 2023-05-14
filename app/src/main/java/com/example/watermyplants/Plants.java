package com.example.watermyplants;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Plants {
    private String name;
    private String watering;
    private int imageResource;
    private Drawable imageDrawable;

    public Plants(String name, String watering, int imageResource, Context context) {
        this.name = name;
        this.watering = watering;
        this.imageResource = imageResource;
        this.imageDrawable = context.getResources().getDrawable(imageResource);
    }

    public Plants(String name, String watering, Drawable imageDrawable) {
        this.name = name;
        this.watering = watering;
        this.imageDrawable = imageDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWatering() {
        return watering;
    }

    public void setWatering(String watering) {
        this.watering = watering;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    @Override
    public String toString() {
        return "Plants{" +
                "name='" + name + '\'' +
                ", watering='" + watering + '\'' +
                ", imageResource=" + imageResource +
                ", imageDrawable=" + imageDrawable +
                '}';
    }
}
