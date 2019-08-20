package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import com.google.gson.annotations.SerializedName;

public class Egg_Quality {
    @SerializedName("id")
    private Integer id;
    private String tag;
    @SerializedName("breeder_inventory_id")
    private Integer egg_breeder_inv_id;
    @SerializedName("egg_quality_at")
    private Integer week;
    @SerializedName("date_collected")
    private String date;
    @SerializedName("weight")
    private Float weight;
    @SerializedName("color")
    private String color;
    @SerializedName("shape")
    private String shape;
    @SerializedName("length")
    private Float length;
    @SerializedName("width")
    private Float width;
    @SerializedName("albumen_height")
    private Float albument_height;
    @SerializedName("albumen_weight")
    private Float albument_weight;
    @SerializedName("yolk_weight")
    private Float yolk_weight;
    @SerializedName("yolk_color")
    private String yolk_color;
    @SerializedName("shell_weight")
    private Float shell_weight;
    @SerializedName("thickness_top")
    private Float shell_thickness_top;
    @SerializedName("thickness_mid")
    private Float shell_thickness_middle;
    @SerializedName("thickness_bot")
    private Float shell_thickness_bottom;
    @SerializedName("deleted_at")
    private String deleted_at;
    Egg_Quality(){

    }

    Egg_Quality(   Integer id,
                   String tag,
                   Integer egg_breeder_inv_id,
                   String date,
                   Integer week,
                   Float weight,
             String color,
             String shape,
                   Float length,
                   Float width,
                   Float albument_height,
                   Float albument_weight,
                   Float yolk_weight,
             String yolk_color,
                   Float shell_weight,
                   Float shell_thickness_top,
                   Float shell_thickness_middle,
                   Float shell_thickness_bottom
    ){
        this.setId(id);
        this.setTag(tag);
        this.setEgg_breeder_inv_id(egg_breeder_inv_id);
        this.setDate(date);
        this.setWeek(week);
        this.setWeight(weight);
        this.setColor(color);
        this.setShape(shape);
        this.setLength(length);
        this.setWidth(width);
        this.setAlbument_height(albument_height);
        this.setAlbument_weight(albument_weight);
        this.setYolk_weight(yolk_weight);
        this.setYolk_color(yolk_color);
        this.setShell_weight(shell_weight);
        this.setShell_thickness_top(shell_thickness_top);
        this.setShell_thickness_middle(shell_thickness_middle);
        this.setShell_thickness_bottom(shell_thickness_bottom);
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEgg_breeder_inv_id() {
        return egg_breeder_inv_id;
    }

    public void setEgg_breeder_inv_id(Integer egg_breeder_inv_id) {
        this.egg_breeder_inv_id = egg_breeder_inv_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getAlbument_height() {
        return albument_height;
    }

    public void setAlbument_height(Float albument_height) {
        this.albument_height = albument_height;
    }

    public Float getAlbument_weight() {
        return albument_weight;
    }

    public void setAlbument_weight(Float albument_weight) {
        this.albument_weight = albument_weight;
    }

    public Float getYolk_weight() {
        return yolk_weight;
    }

    public void setYolk_weight(Float yolk_weight) {
        this.yolk_weight = yolk_weight;
    }

    public String getYolk_color() {
        return yolk_color;
    }

    public void setYolk_color(String yolk_color) {
        this.yolk_color = yolk_color;
    }

    public Float getShell_weight() {
        return shell_weight;
    }

    public void setShell_weight(Float shell_weight) {
        this.shell_weight = shell_weight;
    }

    public Float getShell_thickness_top() {
        return shell_thickness_top;
    }

    public void setShell_thickness_top(Float shell_thickness_top) {
        this.shell_thickness_top = shell_thickness_top;
    }

    public Float getShell_thickness_middle() {
        return shell_thickness_middle;
    }

    public void setShell_thickness_middle(Float shell_thickness_middle) {
        this.shell_thickness_middle = shell_thickness_middle;
    }

    public Float getShell_thickness_bottom() {
        return shell_thickness_bottom;
    }

    public void setShell_thickness_bottom(Float shell_thickness_bottom) {
        this.shell_thickness_bottom = shell_thickness_bottom;
    }
}
