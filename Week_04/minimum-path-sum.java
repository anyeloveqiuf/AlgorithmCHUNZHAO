// 最小路径和
import java.util.*;

class minimum-path-sum {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rows = grid.length, columns = grid[0].length;
        int[] dp = new int[columns];
        // 只存一行的dp值，初始为第一行的最小和，第二行开始dp[0]+=grid[1][0]，dp[1]=dp[0]和dp[1]（上一行）+grid[1][1]
        dp[0] = grid[0][0];
        for (int i = 1; i < columns; i++) {
            dp[i] = dp[i - 1] + grid[0][i];
        }
        for (int i = 1; i < rows; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < columns; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + grid[i][j];
            }
        }
        return dp[columns - 1];
    }
}