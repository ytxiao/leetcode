package linklist;

import org.testng.annotations.Test;

public class NumberTest {

    private NumberClass numberClass = new NumberClass();

    @Test
    public void addTwoNumbersTest(){

        ListNode l1 = new ListNode(2);
        ListNode l11 = new ListNode(4);
        l1.next = l11;
        ListNode l111 = new ListNode(3);
        l11.next = l111;

        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l222 = new ListNode(4);
        l2.next = l22;
        l22.next = l222;
        numberClass.addTwoNumbers(l1,l2);
    }
}
