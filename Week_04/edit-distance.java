// 编辑距离
import java.util.*;

class edit-distance {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        if (m * n == 0) return m + n;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) dp[i][0] = i;
        for (int j = 0; j < n + 1; j++) dp[0][j] = j;
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                int a = dp[i - 1][j] + 1;
                int b = dp[i][j - 1] + 1;
                int c = dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);
                dp[i][j] = Math.min(Math.min(a,b), c);
            }
        }
        return dp[m][n];
    }
}
