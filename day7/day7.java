import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Laboratories {
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        Path path = FileSystems.getDefault().getPath(filename);
        List<String> input = Files.readAllLines(path);
        Grid grid = new Grid(input);
        grid.simulate();
        System.out.println("Part1: " + grid.getSplits());
        System.out.println("Part2: " + grid.getRouteCount());
//        System.out.println(grid);
    }
}

public class Grid {
    private final char[][] cells;
    private final long[][] routes;
    private int splits = 0;
    public Grid(List<String> rows) {
        this.cells = rows.stream().map(String::toCharArray).toArray(char[][]::new);
        this.routes = new long[cells.length][cells[0].length];
    }
    public char get(int x, int y) {
        return cells[y][x];
    }
    public void simulate() {
        for (int row = 0; row < cells.length-1; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cells[row][col] == 'S') {
                    cells[row + 1][col] = '|';
                    routes[row + 1][col] = 1;
                }
                if (cells[row][col] == '|') {
                    if (cells[row + 1][col] == '^') {
                        cells[row + 1][col - 1] = '|';
                        cells[row + 1][col + 1] = '|';
                        routes[row + 1][col - 1] += routes[row][col];
                        routes[row + 1][col + 1] += routes[row][col];
                        splits++;
                    } else {
                        cells[row + 1][col] = '|';
                        routes[row + 1][col] += routes[row][col];
                    }
                }
            }
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : cells) {
            sb.append(row);
            sb.append('\n');
        }
        return sb.toString();
    }
    public int getSplits() {
        return splits;
    }
    public long getRouteCount() {
        long total = 0;
        long[] lastRow = routes[routes.length - 1];
        for (int i = 0; i < lastRow.length; i++) {
            total += lastRow[i];
        }
        return total;
    }
}
