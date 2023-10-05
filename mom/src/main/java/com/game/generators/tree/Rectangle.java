package com.game.generators.tree;

public class Rectangle {

    public int x, y, width, height;
    public int top, bottom, left, right;

    public Rectangle(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.bottom = y + height;
        this.top = y;
        this.left = x;
        this.right = x + width;

    }




}
