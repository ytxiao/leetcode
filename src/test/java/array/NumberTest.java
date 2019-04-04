package array;

import com.alibaba.fastjson.JSON;
import org.testng.annotations.Test;

public class NumberTest {

    private NumberClass numberClass = new NumberClass();

    @Test
    public void twoSumTest(){
        int[] nums = new int[]{3,2,4};
        int target = 6;
        int[] result = numberClass.twoSum(nums,target);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void findMedianSortedArraysTest(){

    }
}
