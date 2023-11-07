package com.engine.utils;

import com.game.Game;
import com.game.Maze;
import com.game.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * PathFinder class.
 * This is the path finder class.
 * For path finding calculations (used for AI movements).
 */
public class PathFinder {
    /** List of open nodes (not visited or neighbour nodes to the path). */
    private List<Node> open = new ArrayList<Node>();
    /** List of closed nodes (already visited, no need to check it again). */
    private List<Node> closed = new ArrayList<Node>();
    /** List of sede nodes (direct neighbours to current path). */
    private List<Node> side = new ArrayList<Node>();
    /** Start node for path. */
    private Node start;
    /** End node for path (target). */
    private Node end;
    /** Found path movements list. */
    private Movement path;

    /**
     * PathFinder default constructor.
     * This is the default constructor for the path finder class.
     */
    public PathFinder() {

    }

    /**
     * PathFinder full constructor.
     * This is the full constructor for the path finder class.
     * @param open List of open nodes (available blocks of the map)
     * @param start The start node
     * @param end The end node
     */
    public PathFinder(List<Node> open, Node start, Node end) {
        this.open = open;
        this.start = start;
        this.end = end;

        Node[] neighbours = {
            findNode(start.getX() - 1, start.getY()),
            findNode(start.getX() + 1, start.getY()),
            findNode(start.getX(), start.getY() - 1),
            findNode(start.getX(), start.getY() + 1)
        };
        for (Node n : neighbours) {
            if (n == null) {
                continue;
            }
            open.remove(n);
            side.add(n);
        }
    }

    /**
     * Creates a PathFinder from the given maze.
     * @param start The start position
     * @param end The end position
     * @return The path finder initialized from the maze.
     */
    public static PathFinder fromMaze(Vector3 start, Vector3 end) {
        return fromMaze(Game.getInstance().getMaze(), start, end);
    }

    /**
     * Creates a PathFinder from the given maze.
     * @param maze The maze to use
     * @param start The start position
     * @param end The end position
     * @return The path finder initialized from the maze.
     */
    public static PathFinder fromMaze(Maze maze, Vector3 start, Vector3 end) {
        if (end.z != start.z) {
            return null; // Cannot find path between different floors yet
        }

        Node startNode = new Node(Math.round(start.getX()), Math.round(start.getY()), start.dst(end));
        Node endNode   = new Node(Math.round(end.getX()),   Math.round(end.getY()),   0.0f);

        List<Node> open = new ArrayList<Node>();
        for (Tile t : maze.getTiles()) {
            if (t.getPosition().z != start.z || t.isSolid()) {
                continue;
            }

            open.add(new Node(
                (int) t.getPosition().getX(),
                (int) t.getPosition().getY(),
                t.getPosition().dst(end)
            ));
        }

        return new PathFinder(open, startNode, endNode);
    }

    /**
     * Find the path.
     * This method finds the path from the start node to the end node.
     * @return True if the path is found, false otherwise
     */
    public boolean findPath() {
        Node current = this.start;

        // find the path to the target
        while (!current.equals(this.end)) {
            // no more nodes to check, no path found (stuck)
            if (open.size() == 0) {
                return false;
            }

            // get the closest node to the target
            Node next = side.size() > 0 ? side.get(0) : null;
            for (Node n : side) {
                if (n.getDst() < current.getDst()) {
                    next = n;
                }
            }

            // if no node found, we are stuck
            if (next == null) {
                return false;
            } else {
                // get the neighbours to [next] node
                Node[] neighbours = {
                    findNode(next.getX() - 1, next.getY()),
                    findNode(next.getX() + 1, next.getY()),
                    findNode(next.getX(), next.getY() - 1),
                    findNode(next.getX(), next.getY() + 1)
                };
                // add the neighbours to the side list (and remove them from the open list)
                for (Node n : neighbours) {
                    if (n == null) {
                        continue;
                    }

                    n.setParent(next);

                    // if already added, skip it
                    if (side.contains(n)) {
                        continue;
                    } else {
                        // add the node to the side list (and remove it from open/closed list)
                        side.add(n);
                        open.remove(n);
                    }
                }

                // add the new node to the closed list (and remove it from side list or open list)
                if (!side.remove(next)) {
                    open.remove(next);
                }
                closed.add(next);

                // repeat the process with the next node
                current = next;
            }
        }

        // build the path movements objects
        Movement last = null;
        while (current.getParent() != null) {
            Movement m = new Movement(current.getX(), current.getY(), last);
            last = m;
            current = current.getParent();
        }
        path = last;

        return true;
    }

    /**
     * Find a node from its position in the open list.
     * @param x The x position of the node
     * @param y The y position of the node
     * @return The node if found, null otherwise
     */
    private Node findNode(int x, int y) {
        for (Node n : open) {
            if (n.getX() == x && n.getY() == y) {
                return n;
            }
        }
        return null;
    }

    /**
     * Get the path movements.
     * @return The path movements;
     */
    public Movement getPath() {
        return path;
    }
}
