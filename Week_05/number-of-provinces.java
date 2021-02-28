// 省份数量
import java.util.*;

class number-of-provinces {
    public int findCircleNum(int[][] isConnected) {
        int cities = isConnected.length;
        boolean[] visited = new boolean[cities];
        int res = 0;
        for (int i = 0; i < cities; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, cities, i);
                res++;
            }
        }
        return res;
    }
    public void dfs(int[][] isConnected, boolean[] visited, int cities, int i) {
        for (int j = 0; j < cities; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(isConnected, visited, cities, j);
            }
        }
    }
}