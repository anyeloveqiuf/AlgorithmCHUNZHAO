// 岛屿数量
import java.util.*;

class number-of-islands {
    public int number-of-islands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int res = 0, rows = grid.length, columns = grid[0].length;
        for (int i = 0; i < rows; i++) {
            if (grid[i][j] == '1') {
                res++;
                dfs(grid, i ,j);
            }
        }
        return res;
    }
    public void dfs(char[][] grid, int row, int column) {
        int rows = grid.length, columns = grid[0].length;
        if (row < 0 || column < 0 || row >= rows || column >= columns || grid[row][column] == '0') {
            return;
        }
        grid[row][column] = '0';
        dfs(grid, row, column + 1);
        dfs(grid, row, column - 1);
        dfs(grid, row + 1, column);
        dfs(grid, row - 1, column);
    }
}