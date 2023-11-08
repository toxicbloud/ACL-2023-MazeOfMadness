package com.engine.utils;

/**
 * Node class.
 * Represents a node in the path finder.
 * (It's a bloc of the map).
 */
public class Node {
    /** The x position of the node. */
    private int x;
    /** The y position of the node. */
    private int y;
    /** The distance from the node to the target. */
    private float dst;
    /** The parent node. */
    private Node parent;

    /**
     * Node default constructor.
     * @param x The x position of the node
     * @param y The y position of the node
     * @param dst The distance from the node to the target
     */
    Node(int x, int y, float dst) {
        this.x = x;
        this.y = y;
        this.dst = dst;
    }

    /**
     * Get the parent node.
     * @return The parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Set the parent node.
     * @param parent The parent node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Get the distance from the node to the target.
     * @return The distance from the node to the target
     */
    public float getDst() {
        return dst;
    }

    /**
     * Get the x position of the node.
     * @return The x position of the node
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y position of the node.
     * @return The y position of the node
     */
    public int getY() {
        return y;
    }

    /**
     * Check if the node is near another node.
     * @param n The other node
     * @param d The distance to check
     * @return True if the node is near the other node, false otherwise
     */
    public boolean near(Node n, float d) {
        return Math.abs(x - n.x) < d && Math.abs(y - n.y) < d;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }

        Node node = (Node) obj;
        return node.x == x && node.y == y;
    }

    @Override
    public int hashCode() {
        return x * 1000 + y;
    }

    @Override
    public String toString() {
        return "Node(" + x + ", " + y + ", " + dst + ")";
    }
}
