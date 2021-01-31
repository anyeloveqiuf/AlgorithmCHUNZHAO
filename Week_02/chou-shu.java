// 丑数
import java.util.*;

class chou-shu {
    public int nthUglyNumber(int n) {
        int[] nums = new int[n];
        nums[0] = 1;
        int temp = 0, x = 0, y = 0, z = 0;
        for (int i = 1; i < n; i++) {
            temp = Math.min(Math.min(nums[x] * 2, nums[y] * 3), nums[z] * 5);
            nums[i] = temp;
            if (temp == nums[x] * 2) x++;
            if (temp == nums[y] * 3) y++;
            if (temp == nums[z] * 5) z++;
        }
        return nums[n - 1];
    }
}