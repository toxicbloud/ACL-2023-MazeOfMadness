package com.game.generators.tree;

import com.game.Maze;
import com.game.generators.MazeFactory;

public class Leaf {

    private int x;
    private int y;
    private int width;
    private int height;

    private Leaf left;
    private Leaf right;

    private String halls;


    public Leaf(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    /**
     * Splitting Method. It is used to generate the mazes and split them.
     * @return If the leaf was separated or not.
     */
    public boolean split() {
        // We start by splitting the leaf into two children
        if (this.left != null || this.right != null) {
            return false;  // We're already split ! Abort !
        }

        // We determine the direction of the split.
        // If the width is >25% larger than height, we split vertically
        // If the height is >25% larger than the width, we split horizontally
        // Otherwise we split randomly
        boolean splitH = MazeFactory.RNG.nextFloat(1) > 0.5;
        if (width > height && (double) width / height >= 1.25) {
            splitH = false;
        } else if (height > width && (double) height / width >= 1.25) {
            splitH = true;
        }

        if (width > height && (double) width / height >= 1.25) {
            splitH = false;
        } else if (height > width && (double) height / width >= 1.25) {
            splitH = true;
        }

        int max = (splitH ? height : width) - MazeFactory.min_room_size; // Determine the maximum height or width
        if (max <= MazeFactory.min_room_size) {
            return false; // The area is too small to split anymore...
        }

        // Determine where we're going to split
        int split = MazeFactory.RNG.nextInt(max + MazeFactory.min_room_size) - MazeFactory.min_room_size ;

        // Create our left and right children based on the direction of the split
        if (splitH) {
            left = new Leaf(x, y, width, split);
            right = new Leaf(x, y + split, width, height - split);
        } else {
            left = new Leaf(x, y, split, height);
            right = new Leaf(x + split, y, width - split, height);
        }

        return true;    // Split successful!
    }
}
