package com.game.generators.tree;

import com.game.generators.MazeFactory;
import com.game.tiles.Tile;
import com.game.tiles.WallRock;

import java.util.ArrayList;

/**
 * Leaf Class. It is used to define a BSP tree to allow for a maze generation.
 */
public class Leaf {

    /**
     * x : x starting coordinate of the current leaf.
     */
    private final int x;
    /**
     * y : y starting coordinate of the current leaf.
     */
    private final int y;
    /**
     * width : Maximum span of the leaf in width.
     */
    private final int width;
    /**
     * height : Maximum span of the leaf in height.
     */
    private final int height;
    /**
     * left : left-child of the current leaf.
     */
    private Leaf left;
    /**
     * right : right-child of the current leaf.
     */
    private Leaf right;
    /**
     * halls : ArrayList of rectangles that joins rooms.
     */
    private ArrayList<Rectangle> halls;
    /**
     * room : Room positioned into the current leaf.
     */
    private Rectangle room;
    /**
     * this.midThreshold : Mid-threshold.
     */
    private final float midThreshold = 0.5F;

    /**
     * Main constructor. Builds a Leaf.
     * @param x x starting coordinate.
     * @param y y starting coordinate.
     * @param width maximum witdh that the leaf can take.
     * @param height maximum height that the leaf can take.
     */
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

        boolean splitH = MazeFactory.RNG.nextFloat() > this.midThreshold;
        final float superiorHalfThreshold = 1.25F;
        if (width > height && (double) width / height >= superiorHalfThreshold) {
            splitH = false;
        } else if (height > width && (double) height / width >= superiorHalfThreshold) {
            splitH = true;
        }

        if (width > height && (double) width / height >= superiorHalfThreshold) {
            splitH = false;
        } else if (height > width && (double) height / width >= superiorHalfThreshold) {
            splitH = true;
        }

        int max = (splitH ? height : width) - MazeFactory.MIN_ROOM_SIZE; // Determine the maximum height or width
        if (max <= MazeFactory.MIN_ROOM_SIZE) {
            return false;       // The area is too small to split anymore...
        }

        // Determine where we're going to split
        int split = this.randomInt(MazeFactory.MIN_ROOM_SIZE, max);

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

    /**
     * This function creates the rooms inside the leaves.
     * If the current leaf has no child, it creates 2 rooms inside the child leaves.
     * If the current leaf has 1 child, it creates 1 room inside the child leaf.
     */
    public void createRooms() {
        if (this.left != null || this.right != null) {
            // This leaf has been split, so go into the children leafs
            if (this.left != null) {
                this.left.createRooms();
            }
            if (this.right != null) {
                this.right.createRooms();
            }

            // If there are both left and right children in this Leaf, create a hallway between them
            if (this.left != null && this.right != null) {
                createHall(this.left.getRoom(), this.right.getRoom());
            }

        } else {
            // This Leaf is the ready to make a room
            // The room can be between MIN_ROOM_SIZE x MIN_ROOM_SIZE tiles to the size of the leaf - 2.

            Point roomSize = new Point(
                this.randomInt(MazeFactory.MIN_ROOM_SIZE, width - 2),
                this.randomInt(MazeFactory.MIN_ROOM_SIZE, height - 2)
            );
            Point roomPos = new Point(
                this.randomInt(1, width - roomSize.x - 1),
                this.randomInt(1, height - roomSize.y - 1)
            );

            // Places the room within the Leaf, but don't put it right
            // against the side of the Leaf (that would merge rooms together)
            this.room = new Rectangle(x + roomPos.x, y + roomPos.y, roomSize.x, roomSize.y);
        }
    }

    /**
     * Iterates all the way through these leafs to find a room, if one exists.
     * @return A room found in children leaves or current leaf.
     */
    public Rectangle getRoom() {
        if (this.room != null) {
            return this.room;
        } else {
            Rectangle lRoom = null;
            Rectangle rRoom = null;

            if (this.left != null) {
                lRoom = this.left.getRoom();
            }
            if (this.right != null) {
                rRoom = this.right.getRoom();
            }

            if (lRoom == null && rRoom == null) {
                return null;
            } else if (rRoom == null) {
                return lRoom;
            } else if (lRoom == null) {
                return rRoom;
            } else if (MazeFactory.RNG.nextFloat() > this.midThreshold) {
                return lRoom;
            } else {
                return rRoom;
            }
        }
    }

    /**
     * This function creates a hall between 2 rooms.
     * @param l room 1
     * @param r room 2
     */
    private void createHall(Rectangle l, Rectangle r) {
        // Now we connect these two rooms together with hallways.
        this.halls = new ArrayList<>();

        Point point1 = new Point(this.randomInt(l.left + 1, l.right - 2), this.randomInt(l.top + 1, l.bottom - 2));
        Point point2 = new Point(this.randomInt(r.left + 1, r.right - 2), this.randomInt(r.top + 1, r.bottom - 2));

        int w = point2.x - point1.x;
        int h = point2.y - point1.y;

        if (w < 0) {
            if (h < 0) {
                if (MazeFactory.RNG.nextFloat() < this.midThreshold) {
                    this.halls.add(new Rectangle(point2.x, point1.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point2.x, point2.y, 1, Math.abs(h)));
                } else {
                    this.halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point1.x, point2.y, 1, Math.abs(h)));
                }
            } else if (h > 0) {
                if (MazeFactory.RNG.nextFloat() < this.midThreshold) {
                    this.halls.add(new Rectangle(point2.x, point1.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point2.x, point1.y, 1, Math.abs(h)));
                } else {
                    this.halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point1.x, point1.y, 1, Math.abs(h)));
                }
            } else { // if (h == 0)
                this.halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), 1));
            }
        } else if (w > 0) {
            if (h < 0) {
                if (MazeFactory.RNG.nextFloat() < this.midThreshold) {
                    this.halls.add(new Rectangle(point1.x, point2.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point1.x, point2.y, 1, Math.abs(h)));
                } else {
                    this.halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point2.x, point2.y, 1, Math.abs(h)));
                }
            } else if (h > 0) {
                if (MazeFactory.RNG.nextFloat() < this.midThreshold) {
                    this.halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point2.x, point1.y, 1, Math.abs(h)));
                } else {
                    this.halls.add(new Rectangle(point1.x, point2.y, Math.abs(w), 1));
                    this.halls.add(new Rectangle(point1.x, point1.y, 1, Math.abs(h)));
                }
            } else {  // if (h == 0)
                this.halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), 1));
            }
        } else {    // if (w == 0)
            if (h < 0) {
                this.halls.add(new Rectangle(point2.x, point2.y, 1, Math.abs(h)));
            } else if (h > 0) {
                this.halls.add(new Rectangle(point1.x, point1.y, 1, Math.abs(h)));
            }
        }
    }

    /**
     * This method exports the leaf into a Tile array. Usable for the Maze constructor and game.
     * @return Array that corresponds to the tiles of the generated maze.
     */
    public Tile[] exportToArray() {
        Tile[] maze = new Tile[this.height * this.width];

        for (int i = 0; i < this.width * this.height; i++) {
            maze[i] = new WallRock();
        }

        return maze;
    }

    /**
     * Returns the left child of the current leaf.
     * @return the left child of the current leaf.
     */
    public Leaf getLeft() {
        return this.left;
    }

    /**
     * Returns the right child of the current leaf.
     * @return the right child of the current leaf.
     */
    public Leaf getRight() {
        return this.right;
    }

    /**
     * Returns the width of the current leaf.
     * @return width of the current leaf.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the current leaf.
     * @return height of the current leaf.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Superset of the RNG method that allows to provide a random int between 2 numbers.
     * @param min Lower bound.
     * @param max Upper bound.
     * @return Random int between min and max parameters.
     */
    public int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max parameter must be greater than min parameter");
        }
        return MazeFactory.RNG.nextInt((max - min) + 1) + min;
    }

}
