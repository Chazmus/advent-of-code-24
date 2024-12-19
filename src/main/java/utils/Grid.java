package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a grid of characters. The grid is zero-indexed, with the top-left corner being (0, 0).
 */
public class Grid {

    Character[][] grid;

    /**
     * Create a new grid with the specified number of rows and columns.
     *
     * @param rows    The number of rows in the grid.
     * @param columns The number of columns in the grid.
     */
    public Grid(int rows, int columns) {
        grid = new Character[rows][columns];
    }

    /**
     * Create a new grid with the specified number of rows and columns.
     *
     * @param rows    The number of rows in the grid.
     * @param columns The number of columns in the grid.
     */
    public Grid(int rows, int columns, Character fillValue) {
        grid = new Character[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = fillValue;
            }
        }
    }


    /**
     * Create a new grid from a list of strings.
     *
     * @param contents The contents of the grid.
     */
    public Grid(List<String> contents) {
        grid = new Character[contents.size()][contents.get(0).length()];
        for (int i = 0; i < contents.size(); i++) {
            for (int j = 0; j < contents.get(i).length(); j++) {
                grid[i][j] = contents.get(i).charAt(j);
            }
        }
    }

    public Grid(Grid other) {
        grid = new Character[other.getRows()][other.getColumns()];
        for (int i = 0; i < other.getRows(); i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                grid[i][j] = other.get(i, j);
            }
        }
    }

    /**
     * Get the character at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The character at the specified coordinates.
     */
    public Character get(int x, int y) {
        return grid[x][y];
    }

    /**
     * Get the character at the specified coordinates.
     *
     * @param coordinate The coordinates.
     * @return The character at the specified coordinates.
     */
    public Character get(Vector2 coordinate) {
        return get(coordinate.y(), coordinate.x());
    }

    /**
     * Set the character at the specified coordinates.
     *
     * @param x     The x-coordinate.
     * @param y     The y-coordinate.
     * @param value The value to set.
     */
    public void set(int x, int y, Character value) {
        grid[x][y] = value;
    }

    /**
     * Set the character at the specified coordinates.
     *
     * @param coordinate The coordinates.
     * @param value      The value to set.
     */
    public void set(Vector2 coordinate, Character value) {
        set(coordinate.x(), coordinate.y(), value);
    }

    /**
     * Get the number of rows in the grid.
     *
     * @return The number of rows in the grid.
     */
    public int getRows() {
        return grid.length;
    }

    /**
     * Get the number of columns in the grid.
     *
     * @return The number of columns in the grid.
     */
    public int getColumns() {
        return grid[0].length;
    }

    /**
     * Check if the specified coordinates are out of bounds.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are out of bounds, otherwise false.
     */
    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= getColumns() || y < 0 || y >= getRows();
    }

    /**
     * Check if the specified coordinates are within bounds.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if the coordinates are within bounds, otherwise false.
     */
    public boolean isWithinBounds(int x, int y) {
        return !isOutOfBounds(x, y);
    }

    /**
     * Check if the specified coordinates are out of bounds.
     *
     * @param coordinate The coordinates.
     * @return True if the coordinates are out of bounds, otherwise false.
     */
    public boolean isOutOfBounds(Vector2 coordinate) {
        return isOutOfBounds(coordinate.x(), coordinate.y());
    }

    /**
     * Check if the specified coordinates are within bounds.
     *
     * @param coordinate The coordinates.
     * @return True if the coordinates are within bounds, otherwise false.
     */
    public boolean isWithinBounds(Vector2 coordinate) {
        return isWithinBounds(coordinate.x(), coordinate.y());
    }

    /**
     * Find any occurrence of the specified character in the grid.
     *
     * @param c The character to find.
     * @return The coordinates of the first occurrence of the character, or an empty optional if the character is not
     * found.
     */
    public Optional<Vector2> findAny(Character c) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                if (get(i, j) == c) {
                    return Optional.of(new Vector2(j, i));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Find all occurrences of the specified character in the grid.
     *
     * @param c The character to find.
     * @return A stream of the coordinates of all occurrences of the character.
     */
    public Stream<Cell> findAll(Character c) {
        return getStreamOfCells().filter(cell -> cell.value() == c);
    }

    /**
     * Get a stream of all cells in the grid.
     *
     * @return A stream of all cells in the grid.
     */
    public Stream<Cell> getStreamOfCells() {
        return Stream.iterate(0, i -> i < getColumns(), i -> i + 1)
                .flatMap(i -> Stream.iterate(0, j -> j < getRows(), j -> j + 1)
                        .map(j -> new Cell(new Vector2(j, i), get(i, j))));
    }

    /**
     * Get the cardinal neighbors of the specified cell.
     *
     * @param target The target cell.
     * @return A stream of the cardinal neighbors
     */
    public Stream<Cell> getCardinalNeighbors(Cell target) {
        return Direction.getCardinalDirections()
                .map(d -> target.position().add(d.toVector()))
                .filter(this::isWithinBounds)
                .map(this::getCell);
    }

    /**
     * Get the cell at the specified position.
     *
     * @param position The position.
     * @return The cell at the specified position.
     */
    public Cell getCell(Vector2 position) {
        return new Cell(position, get(position));
    }

    public void print() {
        for (int j = 0; j < getColumns(); j++) {
            for (int i = 0; i < getRows(); i++) {
                System.out.print(get(i, j));
            }
            System.out.println();
        }
    }

    public void print(FileWriter writer) throws IOException {
        for (int j = 0; j < getColumns(); j++) {
            for (int i = 0; i < getRows(); i++) {
                writer.write(get(i, j));
            }
            writer.write("\n");
        }
    }

    public Integer findShortestPath(Vector2 start, Vector2 end) {
        var solution = dijkstra(start, end);
        return solution;
    }


    private Integer dijkstra(Vector2 start, Vector2 end) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::cost));
        Map<Vector2, Integer> distances = new HashMap<>();
        Set<Vector2> visited = new HashSet<>();

        queue.add(new Node(start, 0));
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Vector2 currentPos = current.position();

            if (visited.contains(currentPos)) {
                continue;
            }
            visited.add(currentPos);

            if (currentPos.equals(end)) {
                return current.cost();
            }

            for (Vector2 direction : List.of(
                    new Vector2(0, 1), new Vector2(1, 0),
                    new Vector2(0, -1), new Vector2(-1, 0))) {
                Vector2 neighbor = currentPos.add(direction);
                if (isWithinBounds(neighbor) && get(neighbor) == '.') {
                    int newCost = current.cost() + 1;
                    if (newCost < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distances.put(neighbor, newCost);
                        queue.add(new Node(neighbor, newCost));
                    }
                }
            }
        }
        return null;
    }

    private record Node(Vector2 position, int cost) {
    }

    public record Cell(Vector2 position, Character value) {
    }
}
