// 两数之和
import java.util.*;

class two-sum {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hashmap = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (hashmap.containsKey(target - nums[i])) return new int[]{hashmap.get(target - nums[i]), i};
            hashmap.put(nums[i], i);
        }
        return null;
    }
}