import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Array;
import java.util.*;

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息

public class Main {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static void main(String[] args){
        threeSum();
        arrayIntersection();
        findCommonStrArray();
        findCommonStrMap();
        validAnagram();
        spiralMatrix();
        minSubArrayLenSlidWindow();
        minSubArrayLenBinarySearch();
        minSubArrayLenBaoli();
        sortedSquares();
        deleteArrayVal();
        halfSearch();
        isIsomorphic();
        linkedListFindCycleEntry();
        linkedListDelete();
        //回文，空间复杂度O(n)
        linkedListHuiwen();
        linkedListHuiwenBad();
    //链表节点两两替换
        linkedListChangeTwo();
        linkedListChangeTwoBad();
    }

    /**
     * 提供数组，三数之和为0，要求三元组不能重复，返回三元组
     */
    public static void threeSum() {
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        Arrays.sort(nums);
        List <List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length-2; i++) {
            int val1 = nums[i];
            if (val1>0) break;//不可能和为0
            if (i>0 && nums[i] == nums[i-1]) {
                continue; //去重，不需要单独写循环
            }
            int left = i+1, right = nums.length-1;
            while (left < right) {
                int val2 = nums[left];
                int val3 = nums[right];
                int sum = nums[i] +val2+val3;
                if (sum == 0) {
                    Integer [] arr = new Integer[]{nums[i],val2,val3};
                    res.add(Arrays.asList(arr));
                    // 去重逻辑
                    while (right > left && nums[right] == nums[right - 1]) right--;
                    while (right > left && nums[left] == nums[left + 1]) left++;
                    //上面取到了最后一个跟之前相同的位置，还需要再移动一次才行！
                    right--;
                    left++;
                }
                else if (sum > 0) {
                    //这里不需要去重
                    right--;
                } else {
                    left++;
                }
            }
        }

        printFuncName();
        for (List a: res
             ) {
            System.out.println(a);
        }
    }


    /**
     * 双指针找数组交集
     * 排序后，双指针分别指向数组头，值小的指针右移，当值相等时存储下来，右移，值记为pre避免重复，直到越界
     */
    //https://leetcode.cn/problems/intersection-of-two-arrays/solution/liang-ge-shu-zu-de-jiao-ji-by-leetcode-solution/
    public static void arrayIntersection(){
        int [] s = new int[]{1,2,2,3,4};
        int [] t = new int[]{2,2,3};
        List res = new ArrayList<Integer>();
        int sp = 0, tp = 0, pre = 0;
        while (sp<s.length && tp <t.length) {
            int v1 = s[sp];
            int v2 = t[tp];
            if (v1 == v2) {
                if (res.size() == 0) {
                    pre = v1;
                    res.add(v1);
                } else if (v1 != pre) {
                    res.add(v1);
                }
                sp++;
                tp++;
            } else {
                if (v1 < v2){
                    sp++;
                } else {
                    tp++;
                }
            }
        }
        printFuncName();
        System.out.println(res);
    }

    //https://leetcode.cn/problems/find-common-characters/
    public static void findCommonStrArray(){
        String[] input = new String[]{"bella","label","roller"};
        int[] minFre = new int[26];
        Arrays.fill(minFre, Integer.MAX_VALUE); //初始化赋值保证遍历首次逻辑清楚
        for (String str: input
        ) {
            int[] curFre = new int[26];
            char[] cs = str.toCharArray();
            for (Character c: cs
            ) {
                curFre[c-'a']++;
            }
            for (int i = 0; i < 26; i++) {
                minFre[i] = Math.min(minFre[i],curFre[i]);
            }
        }

        List<String> ans = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            int count = minFre[i];
            while (count-->0){
                ans.add(String.valueOf((char) (i+'a')));
            }
        }
        printFuncName();
        System.out.println(ans);
    }
    public static void findCommonStrMap(){
        String[] input = new String[]{"bella","label","roller"};
        HashMap<Character, Integer> commMap = new HashMap<Character, Integer>();
        for (String str: input
             ) {
            char[] cs = str.toCharArray();
            HashMap<Character, Integer> curMap = new HashMap<Character, Integer>();
            for (Character c: cs
            ) {
                curMap.put(c, curMap.getOrDefault(c,0)+1);
            }
            if (commMap.isEmpty()){
                commMap = curMap;
            } else {
                HashMap<Character, Integer> tempMap = new HashMap<Character, Integer>();
                for (Character key:
                     commMap.keySet()) {
                    if (curMap.containsKey(key)) {
                        tempMap.put(key, Math.min(commMap.get(key),curMap.get(key)));
                    }
                }
                commMap = tempMap;
            }
        }
        List res = new ArrayList<>();
        for (Character key:
                commMap.keySet()) {
            int count = commMap.get(key);
            while (count-->0){
                res.add(key);
            }
        }
        printFuncName();
        System.out.println(res);
    }

    /**
     * https://leetcode.cn/problems/valid-anagram/
     */
    public static void validAnagram() {
        String s = "anagram";
        String t = "nagaraa";
        Map m1 = new HashMap<Character,Integer>();
        for (Character c:s.toCharArray()
             ) {
            m1.put(c, (Integer)m1.getOrDefault(c, 0)+1);
        }

        boolean res = true;
        for (Character c:t.toCharArray()
             ) {
            m1.put(c,(Integer)m1.getOrDefault(c,0)-1);
            if ((Integer)m1.getOrDefault(c,-1)<0) {
                res = false;
                break;
            }
        }
        printFuncName();
        System.out.println(res);
    }
    /**
     * https://leetcode.cn/problems/spiral-matrix-ii/
     */
    public static void spiralMatrix(){
        int n = 3;
        int count = n*n;
        //模拟螺旋过程
        int[][]direct = new int[][]{{0, 1}, {1,0}, {0,-1}, {-1, 0}};
        int directIndex = 0;
        int[][]res = new int[n][n];
        int row = 0;
        int column = 0;
        int cur = 0;
        while (cur++<count) {
            res[row][column] = cur;
            //当碰到边界，或下一个格子有数字时换方向
            int nextRow = row + direct[directIndex][0];
            int nextColumn = column + direct[directIndex][1];
            if (nextRow<0||nextRow==n||nextColumn<0||nextColumn==n||res[nextRow][nextColumn]!=0){
                //注意这里 directIndex++ 结果是0！
                directIndex = ++directIndex%4;
            }
            row += direct[directIndex][0];
            column += direct[directIndex][1];
        }
        printFuncName();
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(res[i]));
        }
    }


    /**
     * 滑动窗口，总时间复杂度 On
     * 总结：找到满足条件的右边界 right++，再找不满足条件的左边界 left ++，不断重复到结束，使得窗口朝右滑动
     */
    public static void minSubArrayLenSlidWindow(){
        int[] nums = new int[]{2,3,1,2,4,3};
        int aim = 7;

        int res = Integer.MAX_VALUE;
        int left = 0, right = 0;
        int sum = 0;

        while (right < nums.length) {
            sum += nums[right];
            //右边界确定后确认新的左边界，使sum不满足aim，这样才能使窗口不断朝右边滑动
            while (sum >= aim) {
                if (right-left+1 < res) {
                    res = right - left+1;
                }
                sum-=nums[left];
                left++;
            }
            right++;
        }
        res = res == Integer.MAX_VALUE ? 0 : res;
        printFuncName();
        System.out.println(res);
    }
    /**
     * 求长度最小子数组，二分查找对数时间复杂度
     * 1. 求前缀和 presum[]，presum[i] 表示num[0]到num[i-1]的和
     * 2. 二分查找满足 presum[i]+aim >= presum[bound] 找到最小的 bound，bound - i
     *
     * 时间复杂度 O(nlogn)，n是遍历的，logn是二分查找的
     * 注意：二分查找找不到指定值时会返回最近大于指定值的负数
     */
    public static void minSubArrayLenBinarySearch(){
        int[] nums = new int[]{2,3,1,2,4,3};
        int aim = 7;

        // sum[n] 代表前n+1个数字之和
        int [] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i-1] + nums[i];
        }

        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int bounds = Arrays.binarySearch(sum, sum[i] + aim);
            if (bounds < 0) {
                bounds = -bounds;
            }
            if (bounds - i < minLength) {
                minLength = bounds -i;
            }
        }
        minLength = minLength == Integer.MAX_VALUE ? 0 : minLength;
        printFuncName();
        System.out.println(minLength);
    }

    //长度最小子数组 https://leetcode.cn/problems/minimum-size-subarray-sum/
    public static void minSubArrayLenBaoli(){
        int[] nums = new int[]{2,3,1,2,4,3};
        int aim = 7;

        int minLength = Integer.MAX_VALUE;
        int[] resArray = null;
        for (int i = 0; i < nums.length; i++) {
            int[]subs = new int[nums.length];
            int sum = 0;
            int length = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                subs[j-i]=nums[j];
                length = j-i;
                if (sum >= aim) {
                    if (length < minLength) {
                        minLength = length;
                        resArray = subs;
                    }
                    break;
                }
            }
            if (sum < aim) {
                break;
            }

        }
        printFuncName();
        System.out.println(Arrays.toString(resArray));
    }
    /**
     * 主要问题是排序带来的时间复杂度
     * 负数平方后变成正数，
     */
    public static void sortedSquares(){
        int[] nums = new int[]{-4,-1,0,3,10};
        int[]res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i]*nums[i];
        }
        Arrays.sort(res);

        //双指针
        int pos = nums.length-1;
        int j = nums.length-1;
        int[] ans = new int[nums.length];
        for (int i = 0; i <=j; ) {
            if (nums[i]*nums[i] > nums[j]*nums[j]) {
                ans[pos] = nums[i]*nums[i];
                i++;
            } else {
                ans[pos] = nums[j]*nums[j];
                j--;
            }
            pos--;
        }
        printFuncName();
        System.out.println(Arrays.toString(res));
        System.out.println(Arrays.toString(ans));
    }


    /**
     * 遍历时记录出现目标数字的次数n，非目标数字向前移动n个位置
     * 也可以用双指针，left指向待赋值位置，right指向待检查位置
     */
    public static void deleteArrayVal() {
        int[] nums = new int[]{3, 2, 2, 3};
        int val = 3;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                count ++;
            } else {
                nums[i-count]=nums[i];
                nums[i]=2;
            }
        }
        List res = new ArrayList<Integer>();
        for (int i = 0; i < nums.length-count; i++) {
            res.add(nums[i]);
        }
        printFuncName();
        System.out.println(res);
    }
    /**
     * 排序数组，查找
     * 取一半在剩余位置查找，结束条件：找到或查完
     * https://leetcode.cn/problems/binary-search/
     */
    public static void halfSearch() {
    int[] nums = new int[]{-1,0,3,5,9,12};
    int aim = 9;
    
    int goodRes = -1;
    int left = 0, right = nums.length-1;
    while (left<=right) {
        int mid = left + (right-left)/2;
        int num = nums[mid];
        if (num == aim) {
            goodRes = mid;
            break;
        } else if (num > aim) {
            right = mid-1;
        } else {
            left = mid +1;
        }
    }
    int res = binarySearch(nums, 0, nums.length-1, aim);

    printFuncName();
        System.out.println(goodRes);
    System.out.println(res);
    }
    public static int binarySearch(int[] nums, int left, int right, int aim) {
        if (right-left<=0){
            return -1;
        }
        int halfPos = left + (right-left)/2;
        int half = nums[halfPos];
        if (half == aim) {
            return halfPos;
        } else {
            if (half < aim) {
                return binarySearch(nums, half, right, aim);
            } else {
                return binarySearch(nums, left, half, aim);
            }
        }
    }

    //https://leetcode.cn/problems/isomorphic-strings/solution/tong-gou-zi-fu-chuan-by-leetcode-solutio-s6fd/

    /**
     * 同构字符串，注意解题思路
     * s在t中每个字符的映射关系的数量必须相同
     * map1: a:b, b:a
     * map2: b:a, a:b
     * 遍历字符串，如果存在映射检查是不是互相映射的
     */
    public  static  void isIsomorphic() {
        String s = "abbb";
        String t = "baac";

        Map<Character, Character> sm = new HashMap<Character, Character>();
        Map<Character, Character> tm = new HashMap<Character, Character>();
        boolean res = true;
        for (int i = 0; i < s.length(); i++) {
            Character x = s.charAt(i);
            Character y = t.charAt(i);
            if ((sm.containsKey(x) && sm.get(y)!=x)
            ||(tm.containsKey(y) && tm.get(x)!=y)) {
                res = false;
                break;
            }
            sm.put(x,y);
            tm.put(y,x);
        }
        printFuncName();
        System.out.println(res);
    }
    public static  void printFuncName() {
        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    //https://leetcode.cn/problems/linked-list-cycle-ii/solution/huan-xing-lian-biao-ii-by-leetcode-solution/
    public static  void linkedListFindCycleEntry() {
        int[] input = new int[]{3, 2, 0, 4};
        int val = 6;
        ListNode head = null;
        for (int i = input.length - 1; i >= 0; i--) {
            head = new ListNode(input[i], head);
        }
        ListNode tail = head;
        while (tail.next!=null){
            tail=tail.next;
        }
        tail.next=head.next;

        //快慢指针找到重合点，根据
        /**
         * ｜--a--|cycle entry|---b---(slow=quick)
         *         +------------c-----------+
         * a = c + (n-1)(b+c)
         * 根据以上，slow 到达重合点时，从head取 ptr，同时步进会在环入口相遇！
         */
        ListNode res = null;
        ListNode slow = head, quic = head;
        while (quic != null) {
            slow = slow.next;
            if (quic.next != null) {
                quic = quic.next.next;
            } else {
               break;
            }
            if (slow == quic) {
                ListNode ptr = head;
                while (ptr!=slow) {
                    ptr=ptr.next;
                    slow=slow.next;
                }
                res = ptr;
                break;
            }
        }
        printFuncName();
        System.out.println(res.val);
    }
    public static  void linkedListDelete() {
        int[] input = new int[]{1, 2, 3, 6, 4, 5, 6};
        int val = 6;
        ListNode tail = null;
        for (int i = input.length - 1; i >= 0; i--) {
            tail = new ListNode(input[i], tail);
        }
        ListNode head = tail;
        //在上个节点检查下个6，如果是6删除下面的节点
        while (head.next != null) {
            if (head.next.val == 6) {
                head.next = head.next.next;
            } else {
                head = head.next;
            }
        }
        printFuncName();
        ListNode res = tail;
        while (res != null) {
            System.out.print(res.val);
            res = res.next;
        }
        System.out.println("");
    }
    public static void linkedListHuiwen(){
        int[] input = new int[]{1, 2, 3, 4 , 2, 1};
        ListNode tail = null;
        for (int i = input.length-1; i >= 0; i--) {
            tail = new ListNode(input[i], tail);
        }
        //找到中间点，反转之后的，返回钱再反转回来
        //注意中间点是左边的末尾
        ListNode firstHalfEnd = halfNodeList(tail);
        //注意反转时取右边要在左边末尾取next
        ListNode secondHalfStart = reverseNode(firstHalfEnd.next);
        ListNode l = tail;
        ListNode r = secondHalfStart;
        Boolean isEqual = true;
        while (isEqual && l != null && r != null) {
            if (l.val != r.val) {
                isEqual = false;
            }
            l = l.next;
            r = r.next;
        }

        printFuncName();

        System.out.println(isEqual);
        firstHalfEnd.next = reverseNode(secondHalfStart);

        ListNode res = tail;
        while (res != null) {
            System.out.print(res.val);
            res=res.next;
        }
        System.out.println("");
    }
    public static ListNode reverseNode(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode tempNext = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tempNext;
        }
        return pre;
    }

    public static ListNode halfNodeList(ListNode head) {
        ListNode s = head;
        ListNode q = head;
        //注意快指针步进取到空时已经指到末尾
        while (q.next != null && q.next.next != null) {
            s=s.next;
            q=q.next.next;
        }
        return s;
    }

    public static void linkedListHuiwenBad() {
        int[] input = new int[]{1, 2, 3, 4 , 2, 1};
        ListNode tail = null;
        for (int i = input.length-1; i >= 0; i--) {
            tail = new ListNode(input[i], tail);
        }

        //找到中点，取数组比较是否相同；快慢指针
        ListNode s = tail;
        ListNode q = tail;
        //简单的分两个数组不能处理121这类情况
        List left = new ArrayList<Integer>();
        List right = new ArrayList<Integer>();
        boolean qishu = false;
        while (q !=null) {
            left.add(s.val);
            s=s.next;
            q=q.next;
            if (q!=null) {
                q=q.next;
            } else {
                qishu = true;
            }
        }
        //奇数时最后一位不影响是否回文可以删除
        left.remove(left.size()-1);
        while (s!=null) {
            right.add(0,s.val);
            s=s.next;
        }

        printFuncName();
        boolean res =
                left.equals(right);
        System.out.println(res);
    }

    //https://leetcode.cn/problems/swap-nodes-in-pairs/solution/liang-liang-jiao-huan-lian-biao-zhong-de-jie-di-91/
    public static void linkedListChangeTwo() {
        int[] input = new int[]{1, 2, 3, 4 };
        ListNode tail = null;
        for (int i = input.length-1; i >= 0; i--) {
            tail = new ListNode(input[i], tail);
        }

        //0-1-2-3
        /**
         * 0指2
         * 1指3
         * 2指1
         */
        ListNode dumy = new ListNode(0, tail);
        ListNode temp = dumy;
        while (temp.next != null && temp.next.next != null) {
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            temp.next=node2;
            node1.next=node2.next;
            node2.next=node1;
            temp = node1;
        }

        printFuncName();
        ListNode res = dumy.next;
        while (res != null) {
            System.out.print(res.val);
            res=res.next;
        }
        System.out.println("");

        ListNode tail1 = null;
        for (int i = input.length-1; i >= 0; i--) {
            tail1 = new ListNode(input[i], tail1);
        }
        ListNode carlRes = swapPairs(tail1);
        res = carlRes;
        while (res != null) {
            System.out.print(res.val);
            res=res.next;
        }
        System.out.println("");
    }
    //0-1-2-3
    /**
     * 0指2
     * 2指1
     * 1指3
     */
    public static ListNode swapPairs(ListNode head) {
        ListNode dumyhead = new ListNode(-1); // 设置一个虚拟头结点
        dumyhead.next = head; // 将虚拟头结点指向head，这样方面后面做删除操作
        ListNode cur = dumyhead;
        ListNode temp; // 临时节点，保存两个节点后面的节点
        ListNode firstnode; // 临时节点，保存两个节点之中的第一个节点
        ListNode secondnode; // 临时节点，保存两个节点之中的第二个节点
        while (cur.next != null && cur.next.next != null) {
            temp = cur.next.next.next;
            firstnode = cur.next;
            secondnode = cur.next.next;
            cur.next = secondnode;       // 步骤一
            secondnode.next = firstnode; // 步骤二
            firstnode.next = temp;      // 步骤三
            cur = firstnode; // cur移动，准备下一轮交换
        }
        return dumyhead.next;
    }

    public  static  void linkedListChangeTwoBad(){
        int[] input = new int[]{1, 2, 3, 4 };
        ListNode tail = null;
        for (int i = input.length-1; i >= 0; i--) {
            tail = new ListNode(input[i], tail);
        }
        ListNode dumy = new ListNode(0, tail);
        int index = 0;
        ListNode cur = dumy;
        ListNode pre = null;
        ListNode prePre = null;
        while (cur != null) {
            ListNode tempCur = cur;
            ListNode tempNext = tempCur.next;
            index++;
            /**
             * 出现循环有潜在的风险，单线程时暂时出现循环问题不大
             * 0->2
             * 2->1 注意这里因为之前1->2出现循环
             * 1->3
             *
             * 1->3
             * 2->1
             * 0->2
             */
            if (index >2 && index%2==1){
                                ListNode temp0 = prePre; //0-1-2 //
                                ListNode temp1 = pre; //1-2-3
                                temp0.next = tempCur;//0(-)2-3-4..
                                tempCur.next = temp1;//0-2(-)1-2-1.. 出现循环
                                temp1.next = tempNext;//
//                ListNode temp0 = prePre; //0-1-2 //
//                ListNode temp1 = pre; //1-2-3
//                temp1.next = tempNext;
//                tempCur.next = temp1;
//                temp0.next = tempCur;

                //注意修改后这里变了！不光要修改指针，还要取下个节点
                prePre = temp0.next;
                pre = tempCur.next;
                cur = temp1.next;
            }
            else {
                prePre = pre;
                pre = cur;
                cur = tempNext;
            }

        }

        printFuncName();
        ListNode res = dumy.next;
        while (res != null) {
            System.out.print(res.val);
            res=res.next;
        }
        System.out.println("");
    }

}
/**
 # 结字谜
 {
 String mimian = "bdni,wooood";
 String midi = "bind,wrong,wood";
 String[] mimians = mimian.split(",");
 String[] midis = midi.split(",");

 List res = new ArrayList<String>();
 for (String midiStr:midis
 ) {

 Boolean isFound = false;
 for (String mimianStr:mimians
 ) {
 if (quchong(mimianStr, midiStr)) {
 isFound = true;
 res.add(midiStr);
 } else if (hebing(mimianStr, midiStr)) {
 isFound = true;
 res.add(midiStr);
 }
 if (isFound == true) {
 break;
 }
 }

 }
 System.out.println(res);
 }
 public static boolean quchong (String mimian, String midi) {
 String[]midis = midi.split("");
 Arrays.sort(midis);
 String[]mimians = mimian.split("");
 Arrays.sort(mimians);
 return Arrays.equals(mimians, midis);
 }

 public static  boolean hebing (String mimian, String midi) {
 Set mimians = new HashSet<String>();
 for (String a: mimian.split("")
 ) {
 mimians.add(a);
 }
 Set midis = new HashSet<String>();
 for (String a: midi.split("")
 ) {
 midis.add(a);
 }
 return mimians.equals(midis);
 }

 # 不含101数超时
 {
 int l = 1;
 int r = 1000000; //975211935
 int res = handle(r)-handle(l-1);

 System.out.println(res);

 int res2 = 0;
 for (int i = l; i < r+1; i++) {
 String binary = Integer.toBinaryString(i);
 if (!binary.contains("101")) {
 res2++;
 }
 }
 System.out.println(res2);
 }

 public static int handle(int num) {
 //满二叉树
 int[]dp = new int[31];
 dp[0]=1; dp[1]=2; dp[2]=4;
 for (int n = 3; n < 31; n++) {
 dp[n] = dp[n-1] + dp[n-3] + dp[n-1] - dp[n-2];
 }
 int pre = 0;
 int prepre = 0;
 int res = 0;

 for (int i = 30; i >= 0; i--) {
 int val = 1 << i;
 if ((num & val) != 0) {
 //有右子树
 int preNum = num;
 num-=val;
 if (pre == 0) {
 //上一层0这层左子树是满二叉树
 res += dp[i];
 } else {
 //上层1这层左子树左子树是满二叉树
 res+=i == 0 ? 1 : dp[i-1];
 }
 if (prepre == 1 && pre == 0) {
 System.out.println(preNum);
 break;
 }
 prepre = pre;
 pre = 1;
 } else {
 prepre = pre;
 pre = 0;
 }
 if (i==0){
 res+=1;
 }
 }
 return res;
 }

 */
