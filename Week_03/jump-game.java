// 跳跃游戏
import java.util.*;

class jump-game {
    public boolean jump-game(int[] nums) {
        int len = nums.length, rightmost = 0;
        for (int i = 0; i < len; i++) {
            rightmost = Math.max(rightmost, i + nums[i]);
            if (rightmost >= len - 1) return true;
        }
        return false;
    }
}
