[comment]: <> ([4,5,6,7,0,1,2])

class findIndex{
    public List<Integer> findIndex(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid - 1]) return new ArrayList<Integer>() {mid - 1, mid};
            if (nums[mid] > nums[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return new ArrayList<Integer>();
    }
}