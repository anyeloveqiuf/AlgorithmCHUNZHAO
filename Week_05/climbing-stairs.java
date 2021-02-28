// 爬楼梯
import java.util.*;

class climbing-stairs {
    public int climbStairs(int n) {
        int pre2 = 0, pre1 = 0, curr = 1;
        for (int i = 1; i <= n; i++) {
            pre2 = pre1;
            pre1 = curr;
            curr = pre2 + pre1;
        }
        return curr;
    }
}