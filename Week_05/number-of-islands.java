// 岛屿数量
import java.util.*;

class number-of-islands {
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length, res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }
    public void dfs(char[][] grid, int row, int column) {
        int rows = grid.length, columns = grid[0].length;
        if (row >= rows || row < 0 || column >= columns || column < 0 || grid[row][column] == '0') {
            return;
        }
        grid[row][column] = '0';
        dfs(grid, row + 1, column);
        dfs(grid, row - 1, column);
        dfs(grid, row, column + 1);
        dfs(grid, row, column - 1);
    }
}
