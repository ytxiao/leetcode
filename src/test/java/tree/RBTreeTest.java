package tree;

import org.testng.annotations.Test;


public class RBTreeTest {
    @Test
    public void testRBTree(){
        Integer[] nums = {13,8,5,11,6,22,27,25,14,17};
        RBTree<Integer> tree = new RBTree<>();
        for (int i = 0; i < nums.length; i++) {
            Integer num = nums[i];
            tree.insertElement(num);
        }
        tree.showRBTree(tree.getRoot());

        tree.remove(25);
        System.out.println("---------------------------------------- ");
        tree.showRBTree(tree.getRoot());

        System.out.println("---------------------------------------- ");
        for (Integer num : nums) {
            tree.remove(num);
            tree.showRBTree(tree.getRoot());
            System.out.println("------------------------------------ : " + num);
        }

    }
}
