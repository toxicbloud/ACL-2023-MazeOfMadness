package com.game.generators.tree;

import com.badlogic.gdx.math.Vector2;
import com.engine.utils.Vector3;
import com.game.generators.MazeFactory;
import com.game.tiles.Tile;
import com.game.tiles.WallRock;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Leaf Class. It is used to define a BSP tree to allow for a maze generation.
 * @see <a href="https://gamedevelopment.tutsplus.com/how-to-use-bsp-trees-to-generate-game-maps--gamedev-12268t">
 *     Méthode de génération de labyrinthe par BSP.
 * </a>
 */
public class Leaf {
    /** REAL_MIN_ROOM_SIZE : real min room size. */
    static final int REAL_MIN_ROOM_SIZE = 3;
    /** x : x starting coordinate of the current leaf. */
    private final int x;
    /** y : y starting coordinate of the current leaf. */
    private final int y;
    /** width : Maximum span of the leaf in width. */
    private final int width;
    /** height : Maximum span of the leaf in height. */
    private final int height;
    /** left : left-child of the current leaf. */
    private Leaf left;
    /** right : right-child of the current leaf. */
    private Leaf right;
    /** halls : ArrayList of rectangles that joins rooms. */
    private final ArrayList<Rectangle> halls;
    /** room : Room positioned into the current leaf. */
    private Rectangle room;
    /** this.midThreshold : Mid-threshold. */
    private final float midThreshold = 0.5F;

    /**
     * Main constructor. Builds a Leaf.
     *
     * @param x      x starting coordinate.
     * @param y      y starting coordinate.
     * @param height maximum height that the leaf can take.
     * @param width  maximum width that the leaf can take.
     */
    public Leaf(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.halls = new ArrayList<>();
    }

    /**
     * Recursive Method, calls itself to split the maze in rooms.
     * It uses a tree-like structure to generate the rooms.
     *
     * @param height     Desired height of the maze.
     * @param width      Desired width of the maze.
     * @param depth      Desired depth of the maze.
     * @param spawnpoint Variable to store the spawnpoint for the maze.
     * @return Tile array for the Maze constructor.
     */
    public static Tile[] generateRooms(int height, int width, int depth, Vector3 spawnpoint) {

        // We first fill the maze with walls.
        Tile[] maze = new Tile[height * width * depth];
        for (int i = 0; i < height * width * depth; i++) {
            maze[i] = new WallRock();
        }

        // We initialize the root leaf.
        Leaf root = new Leaf(0, 0, height, width);

        // We split the base Leaf until we have an array of leaves.
        ArrayList<Leaf> leafArray = root.splitLeaves();

        // We now have an array full of leaves. We can populate it with rooms and generate halls.
        for (Leaf l : leafArray) {
            l.createRooms();
        }

        // Setting up the spawnpoint.
        // Careful ! Leaf.getRoom() returns a random room in the maze, not the first one that is encountered.
        Rectangle room = root.getRoom();
        spawnpoint.x = room.getX();
        spawnpoint.y = room.getY();
        spawnpoint.z = 1;

        // Finally, we carve the rooms inside the given maze.
        for (Leaf l : leafArray) {
            l.exportToArray(maze, height, width, depth);
        }

        return maze;
    }

    /**
     * This method is the core of the BSP maze generation. It splits leaves until we get leaves
     * that are on the desired size. It applies to the root leaf.
     *
     * @return An array of split leaves.
     */
    private ArrayList<Leaf> splitLeaves() {

        // We initialize the array with the root leaf.
        ArrayList<Leaf> leafArray = new ArrayList<>();
        leafArray.add(this);

        boolean didSplit = true;

        // We loop through the Leaf array, until we can't split anymore leaves.
        while (didSplit) {
            didSplit = false;

            for (int i = 0; i < leafArray.size(); i++) {
                Leaf leaf = leafArray.get(i);
                // Split the Leaf. Here are the conditions to verify before continuing :
                // -> We can't split the leaf if there are already child leaves.
                // -> We can't split the leaf if the leaf is smaller or equal to the BASE_ROOM_SIZE constant.
                // -> We try to split the leaf and observe the result of the splitting (if it did work or not).
                if (leaf.getLeft() == null && leaf.getRight() == null
                        && leaf.getWidth() > MazeFactory.BASE_LEAF_SIZE
                        && leaf.getHeight() > MazeFactory.BASE_LEAF_SIZE
                        && leaf.split()) {
                    // If we did split, we push the child leafs to the ArrayList, so we can loop into them next
                    // iteration.
                    leafArray.add(leaf.getLeft());
                    leafArray.add(leaf.getRight());
                    didSplit = true;        // We ensure that we continue to iterate on the leaves.
                }
            }
        }
        return leafArray;
    }

    /**
     * Splitting Method. It is used to generate the mazes and split them.
     *
     * @return If the leaf was separated or not.
     */
    public boolean split() {
        // We start by splitting the leaf into two children
        if (this.left != null || this.right != null) {
            return false;  // We're already split ! Abort !
        }
        // We check if we can split the leaf horizontally.
        boolean splitH = canSplitH();

        // Determine the maximum height or width for the future room.
        int max = (splitH ? height : width) - MazeFactory.BASE_LEAF_SIZE;
        if (max <= MazeFactory.BASE_LEAF_SIZE) {
            return false;       // The area is too small to split anymore. We abort the splitting.
        }

        // Determine the coordinate where we're going to split.
        int split = MazeFactory.randomInt(MazeFactory.BASE_LEAF_SIZE, max);

        // Create our left and right children based on the direction of the split
        if (splitH) {
            left = new Leaf(x, y, split, width);
            right = new Leaf(x, y + split, height - split, width);
        } else {
            left = new Leaf(x, y, height, split);
            right = new Leaf(x + split, y, height, width - split);
        }

        return true;    // Split successful!
    }

    /**
     * Determines if the current leaf can be split Horizontally.
     *
     * @return If we can split the leaf horizontally.
     */
    private boolean canSplitH() {
        // We determine the direction of the split.
        // If the width is >25% larger than height, we split vertically
        // If the height is >25% larger than the width, we split horizontally
        // Otherwise we split on a 50/50 chance.

        SecureRandom sr = new SecureRandom();
        boolean splitH = sr.nextFloat() > this.midThreshold;
        final float superiorHalfThreshold = 1.25F;

        if (width > height && (double) width / height >= superiorHalfThreshold) {
            splitH = false;     // We can't split horizontally.
        } else if (height > width && (double) height / width >= superiorHalfThreshold) {
            splitH = true;      // We can split horizontally.
        }
        return splitH;
    }

    /**
     * This function creates the rooms inside the leaves.
     * If the current leaf has no child, it creates 2 rooms inside the child leaves.
     * If the current leaf has 1 child, it creates 1 room inside the child leaf.
     */
    public void createRooms() {
        if (this.left != null || this.right != null) {
            // This leaf has been split, so go into the children leafs and try to create rooms inside them.
            if (this.left != null) {
                this.left.createRooms();
            }
            if (this.right != null) {
                this.right.createRooms();
            }

            // If there are both left and right children in this Leaf, create a hallway between them
            if (this.left != null && this.right != null) {
                this.createHall(this.left.getRoom(), this.right.getRoom());
            }

        } else {
            // This Leaf is the ready to make a room
            // The room can be between REAL_MIN_ROOM_SIZE x REAL_MIN_ROOM_SIZE tiles to the size of the leaf - 2.
            Vector2 roomSize = new Vector2(
                MazeFactory.randomInt(Leaf.REAL_MIN_ROOM_SIZE, width - 2),
                MazeFactory.randomInt(Leaf.REAL_MIN_ROOM_SIZE, height - 2)
            );
            // We find the room position inside the leaf.
            Vector2 roomPos = new Vector2(
                MazeFactory.randomInt(1, (int) (width - roomSize.x - 1)),
                MazeFactory.randomInt(1, (int) (height - roomSize.y - 1))
            );

            // Places the room within the Leaf, but don't put it right
            // against the side of the Leaf (that would merge rooms together)
            this.room = new Rectangle((int) (x + roomPos.x), (int) (y + roomPos.y), (int) roomSize.y, (int) roomSize.x);
        }
    }

    /**
     * Iterates all the way through these leafs to find a room, if one exists.
     *
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

            SecureRandom sr = new SecureRandom();
            if (lRoom == null && rRoom == null) {
                return null;
            } else if (rRoom == null) {
                return lRoom;
            } else if (lRoom == null) {
                return rRoom;
            } else if (sr.nextFloat() > this.midThreshold) {
                return lRoom;
            } else {
                return rRoom;
            }
        }
    }

    /**
     * This function creates a hall between 2 rooms.
     *
     * @param l room 1
     * @param r room 2
     */
    private void createHall(Rectangle l, Rectangle r) {
        // Now we connect these two rooms together with hallways.
        SecureRandom sr = new SecureRandom();

        // We find a starting point for the hall.
        Vector2 point1 = new Vector2(
            MazeFactory.randomInt(l.getLeft(), l.getRight() - 2),
            MazeFactory.randomInt(l.getTop(), l.getBottom() - 2)
        );
        // We find an ending point for the hall.
        Vector2 point2 = new Vector2(
            MazeFactory.randomInt(r.getLeft(), r.getRight() - 2),
            MazeFactory.randomInt(r.getTop(), r.getBottom() - 2)
        );

        // We compute height and width with the two Vector2 found.
        int w = Math.abs((int) (point2.x - point1.x));
        int h = Math.abs((int) (point2.y - point1.y));
        float randomFloat = sr.nextFloat();

        // We search an emplacement to add the halls.
        if (w < 0 && h < 0 && randomFloat < this.midThreshold) {
            this.addHallToLeaf(point2, point1, 1, w);
            this.addHallToLeaf(point2, point2, h, 1);
        } else if (w < 0 && h < 0 && randomFloat > this.midThreshold) {
            this.addHallToLeaf(point2, point2, 1, w);
            this.addHallToLeaf(point1, point2, h, 1);
        } else if (w < 0 && h > 0 && randomFloat < this.midThreshold) {
            this.addHallToLeaf(point2, point1, 1, w);
            this.addHallToLeaf(point2, point1, h, 1);
        } else if (w < 0 && h > 0 && randomFloat > this.midThreshold) {
            this.addHallToLeaf(point2, point2, 1, w);
            this.addHallToLeaf(point1, point1, h, 1);
        } else if (w > 0 && h < 0 && randomFloat < this.midThreshold) {
            this.addHallToLeaf(point1, point2, 1, w);
            this.addHallToLeaf(point1, point2, h, 1);
        } else if (w > 0 && h < 0 && randomFloat > this.midThreshold) {
            this.addHallToLeaf(point1, point1, 1, w);
            this.addHallToLeaf(point2, point2, h, 1);
        } else if (w > 0 && h > 0 && randomFloat < this.midThreshold) {
            this.addHallToLeaf(point1, point2, 1, w);
            this.addHallToLeaf(point1, point1, h, 1);
        } else if (w > 0 && h > 0 && randomFloat > this.midThreshold) {
            this.addHallToLeaf(point1, point1, 1, w);
            this.addHallToLeaf(point2, point1, h, 1);
        }
        if (w < 0 && h == 0) {
            this.addHallToLeaf(point2, point2, 1, w);
        } else if (w > 0 && h == 0) {
            this.addHallToLeaf(point1, point1, 1, w);
        } else if (w == 0 && h < 0) {
            this.addHallToLeaf(point2, point2, h, 1);
        } else if (w == 0 && h > 0) {
            this.addHallToLeaf(point1, point1, h, 1);
        }
    }

    /**
     * Method to add a hall inside the leaf.
     *
     * @param pointForX       X starting point for the rectangle to add to the halls array.
     * @param pointForY       Y starting point for the rectangle to add to the halls array.
     * @param rectangleHeight height of the hall to add.
     * @param rectangleWidth  width of the hall to add.
     */
    private void addHallToLeaf(Vector2 pointForX, Vector2 pointForY, int rectangleHeight, int rectangleWidth) {
        this.halls.add(new Rectangle(
                        (int) pointForX.x,
                        (int) pointForY.y,
                rectangleHeight, rectangleWidth
        ));
    }

    /**
     * This method exports the leaf into a Tile array. Usable for the Maze constructor and game.
     *
     * @param maze       Maze to populate.
     * @param mazeHeight Height of the maze to fill.
     * @param mazeWidth  Width of the maze to fill.
     * @param mazeDepth  Depth of the maze to fill.
     */
    public void exportToArray(Tile[] maze, int mazeHeight, int mazeWidth, int mazeDepth) {
        // We carve the halls if there is one inside the current leaf.
        for (Rectangle r : this.halls) {
            r.populateMazeWithRectangle(maze, mazeHeight, mazeWidth, mazeDepth);
        }
        // We carve the room if there is one inside the current leaf.
        if (this.room != null) {
            this.room.populateMazeWithRectangle(maze, mazeHeight, mazeWidth, mazeDepth);
        }
    }

    /**
     * Returns the left child of the current leaf.
     *
     * @return the left child of the current leaf.
     */
    public Leaf getLeft() {
        return this.left;
    }

    /**
     * Returns the right child of the current leaf.
     *
     * @return the right child of the current leaf.
     */
    public Leaf getRight() {
        return this.right;
    }

    /**
     * Returns the width of the current leaf.
     *
     * @return width of the current leaf.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the current leaf.
     *
     * @return height of the current leaf.
     */
    public int getHeight() {
        return this.height;
    }
}
