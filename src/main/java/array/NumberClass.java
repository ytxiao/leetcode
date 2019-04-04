package array;

import org.apache.commons.lang3.ArrayUtils;

public class NumberClass {

    /**
     * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
     * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[]{};
        for (int i =0;i<nums.length-1;i++){
            for (int m = i+1;m<nums.length;m++){
                if ((nums[i] + nums[m]) == target){
                    result = new int[]{i,m};
                    return result;
                }
            }
        }
        return result;
    }

    /***
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。
     请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。
     示例 1:
     nums1 = [1, 3]
     nums2 = [2]
     中位数是 2.0
     示例 2:
     nums1 = [1, 2]
     nums2 = [3, 4]
     中位数是 (2 + 3)/2 = 2.5
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) { // to ensure m<=n
            int[] temp = nums1; nums1 = nums2; nums2 = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && nums2[j-1] > nums1[i]){
                iMin = iMin + 1; // i is too small
            }
            else if (i > iMin && nums1[i-1] > nums2[j]) {
                iMax = iMax - 1; // i is too big
            }
            else { // i is perfect
                int maxLeft = 0;
                if (i == 0) { maxLeft = nums2[j-1]; }
                else if (j == 0) { maxLeft = nums1[i-1]; }
                else { maxLeft = Math.max(nums1[i-1], nums2[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; }

                int minRight = 0;
                if (i == m) { minRight = nums2[j]; }
                else if (j == n) { minRight = nums1[i]; }
                else { minRight = Math.min(nums2[j], nums1[i]); }

                return (maxLeft + minRight) / 2.0;
            }
        }

        return 0.0;
    }
}
