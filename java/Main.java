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

     public static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }

    public static void main(String[] args){
        revertStr();
        quickSort();
        gasStation();
        foreachTreeProblem();
        isPalindrome();
        comblieChain();
        myAtoi();
        longestCommanPre();
        waiguanNum();
        huaweiChitaozi();
        huaweiCPUCost();
        findStringFirstOccur();
        replaceString2();
        replaceString2Function();
        replaceString();
        reverseString();
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

    static public void revertStr(){
        printFuncName();
        String [] s = {"h","e","l","l","o"};
//        for (int i = 0; i < s.length/2; i++) {
//            String left = s[i];
//            s[i] = s[s.length-i-1];
//            s[s.length-i-1] = left;
//        }
        for (int left = 0, right = s.length - 1; left < right; ++left, --right) {
            String tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
        }
        System.out.println(s);
    }

    static public void quickSort() {
        printFuncName();
        int []input = {3,4,6,5,7,2,11,1,8};
        quickSortC(input, 0, input.length-1);

        for (int i: input
             ) {
            System.out.println(i);
        }
    }
    static public void quickSortC(int [] input, int start, int end) {
        if (start >= end) {
            return;
        }
        int part = quickSortPart(input, start, end);
        quickSortC(input, start, part-1);
        quickSortC(input, part+1, end);
    }
    static int quickSortPart(int [] input, int start, int end) {
        int pivot = input[end];
        int i = start; //指向小于pivot位置
        int j = i; //游走指针
        while (j <= end){
            int iv = input[i];
            int jv = input[j];
            if (jv < pivot) {
                input[i] = jv;
                input[j] = iv;
                i++;
            }
            j++;
        }
        int iv = input[i];
        input[end] = iv;
        input[i] = pivot;
        return i;
    }
    static public void gasStation() {
        printFuncName();
        int []gas = {1,2,3,4,5};
        int []cost = {3,4,5,1,2};

        int curSum = 0;
        int min = 0;
        int minIndex = 0;
        for (int i = 0; i < gas.length; i++) {
            int res = gas[i] - cost[i];
            curSum += res;
            if (curSum < min) {
                min = curSum;
                minIndex = i;
            }
        }
        int result = -1;
        if (curSum < 0 ) {
            result = -1;
        } else {
            result = minIndex < gas.length ? minIndex + 1 : 0;
        }
        System.out.println(result);
    }
    /**
     *
     * @param treeNum 用-1表示null
     * @return root
     */
    static public TreeNode FuncFillTreeByNum(int[] treeNum) {
        TreeNode[] tree = new TreeNode[treeNum.length];
        for (int i = 0; i < treeNum.length; i++
        ) {
            tree[i] = new TreeNode(treeNum[i]);
        }

        /**
         逐层遍历，上一层跟下一层关联
         */
        int h = 0;
        int sum = 0;
        int count = treeNum.length;
        while (sum < count) {
            int i = 0;
            int subC = 1<<h;
            while (sum+subC < count && i < subC) { //注意下一层可能越界
                int cur = sum + i;
                int left = sum+subC+2*i;
                int right = sum+subC+2*i+1;
                if (tree[left].val != -1) {
                    tree[cur].left = tree[left];
                }
                if (tree[right].val != -1) {
                    tree[cur].right = tree[right];
                }
                i++;
            }
            sum += subC;
            h++;
        }
        return tree[0];
    }
    static public void foreachTreeProblem() {
        printFuncName();

        int[] treeNum = {1,2,3,4,5,6,7};
        TreeNode root = FuncFillTreeByNum(treeNum);

        int depth = 0;
        foreachTreeModel(root,  depth);
        //注意 depth 不能被函数内部修改！为什么？
        System.out.println(depth);

        System.out.println(BFSmaxDepth(root));
        System.out.println(DFSmaxDepth(root));
    }

    static int DFSmaxDepth(TreeNode root) {
        if (root == null)
            return 0;
        //stack记录的是节点，而level中的元素和stack中的元素
        //是同时入栈同时出栈，并且level记录的是节点在第几层
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> level = new Stack<>();
        stack.push(root);
        level.push(1);
        int max = 0;
        while (!stack.isEmpty()) {
            //stack中的元素和level中的元素同时出栈
            TreeNode node = stack.pop();
            int temp = level.pop();
            max = Math.max(temp, max);
            if (node.left != null) {
                //同时入栈
                stack.push(node.left);
                level.push(temp + 1);
            }
            if (node.right != null) {
                //同时入栈
                stack.push(node.right);
                level.push(temp + 1);
            }
        }
        return max;
    }
    //一层一层遍历
    static int BFSmaxDepth(TreeNode root) {
        if (root == null)
            return 0;
        //创建一个队列
        Deque<TreeNode> deque = new LinkedList<>();
        deque.push(root);
        int count = 0;
        while (!deque.isEmpty()) {
            //每一层的个数
            int size = deque.size();
            while (size-- > 0) {
                TreeNode cur = deque.pop();
                if (cur.left != null)
                    deque.addLast(cur.left);
                if (cur.right != null)
                    deque.addLast(cur.right);
            }
            count++;
        }
        return count;
    }

    //用外部变量记录遍历次数，用局部变量会被栈帧记录
    static int foreachIndex = 0;
    static public void foreachTreeModel(TreeNode cur, int depth) {
        if (cur == null) {
            return;
        }
        //借助局部变量被栈帧记录统计遍历深度
        depth++;
        foreachIndex++;
        String s = "index: " + foreachIndex + " val: " + cur.val + " depth: " + depth;
        System.out.println(s);
        if (cur.left == null && cur.right == null){
            return;
        }
        //left
        foreachTreeModel(cur.left, depth);
        //right
        foreachTreeModel(cur.right, depth);
    }


    static public void isPalindrome() {
    printFuncName();
        int[] input = new int[]{1, 2, 2, 1};
        int val = 6;
        ListNode head = null;
        for (int i = input.length - 1; i >= 0; i--) {
            head = new ListNode(input[i], head);
        }
        ListNode recHead = head;
        ListNode s = head;
        ListNode q = head;
        while (q!=null){
            s=s.next;
            if (q.next == null) {
                q=null;
            } else {
                q=q.next.next;
            }
        }

        //reverse next
        ListNode pre = null;
        ListNode cur = s;
        while (cur!=null) {
            ListNode tempNext = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tempNext;
        }

        boolean res = true;
        while (pre!= null && recHead !=null) {
            if (pre.val != recHead.val) {
                res = false;
            }
            pre = pre.next;
            recHead = recHead.next;
        }
        System.out.println(res);
    }
    /**
     * 合并链表
     * 注意取到空的情况
     */
    public static void comblieChain(){
        printFuncName();
        int [] a1 = {1,2,4};
        int [] a2 = {1, 3, 4};
        ListNode n1 = null;
        for (int i = a1.length - 1; i >= 0; i--) {
            n1 = new ListNode(a1[i], n1);
        }

        ListNode n2 = null;
        for (int i = a2.length - 1; i >= 0; i--) {
            n2 = new ListNode(a2[i], n2);
        }

        ListNode list1 = n1;
        ListNode list2 = n2;
        ListNode first = new ListNode(-1, null);
        ListNode cur = first;
        while (list1 != null || list2 != null) {
            ListNode aimNode = null;
            if (list1 == null || list2 == null) {
                aimNode = list1 == null ? list2 : list1;
            } else {
                aimNode = list1.val < list2.val ? list1 : list2;
            }

            if (aimNode == list1) {
                list1 = list1.next;
            } else {
                list2 = list2.next;
            }
            cur.next = aimNode;
            cur = cur.next;
        }

        ListNode res = first.next;
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }

    /**
     * 字符串转数字
     * 注意判断越界
     */
    public static void myAtoi(){
        String str = " -4141 word";
//        str = str.trim();
        int s = 0;
        while (s < str.length() && str.charAt(s) == ' ') {
            s++;
        }
        str = str.substring(s);
        int index = 0;
        int sign = 1;
        int res = 0;
        if (str.charAt(0) == '-' || str.charAt(0) == '+') {
            sign = str.charAt(0) == '-'? -1 : 1;
            index++;
        }
        while (index < str.length()) {
            int digit = str.charAt(index)-'0';
            if (digit < 0 || digit > 9) break;
            int temp = res;
            res = res*10+digit;
            //越界数字会改变
            if (res/10 != temp) {
                res = sign ==1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                break;
            }
            index ++;
        }
        System.out.println(res*sign);
    }

    /**
     * 最长公共子串
     * 常规办法同时按列遍历，遍历两层变量较多，分治变成字符串两两求公共子串后代码明显简单
     */
    public static void longestCommanPre() {
        printFuncName();
        String []strs = {"flower","flow","flight"};
        String pre = strs[0];
        int index = 0;
        while (index < strs.length) {
            while (strs[index].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length()-1);
            }
            index++;
        }
        System.out.println(pre);
    }

    /**
     * 外观数列，数字读起来在描述前一个数字，如 1，11 1个1，21 两个1，1211，1个2两个1
     * 注意生成字符串的时机有两个：前面跟当前不一样，遍历结束。注意不是一回事情，可能当前字符既要结束之前，又要遍历结束。
     * 这就是基本功要多思考，要是分不清楚也没办法解决问题了。
     */
    public static void waiguanNum() {
        printFuncName();

        int n = 5;
        //从2开始到n，因为核心循环只支持两位及以上的入参
        String res = "11";
        int nNum = 3;
        while (nNum <= n){
            StringBuilder str = new StringBuilder();
            //拆分数字，按顺序相同数字计数
            char pre = res.charAt(0);
            int index = 1;
            int count = 1;
            char[] chars = res.toCharArray();
            while (index < res.length()) {
                char ch = chars[index];
                //不一样的数字时生成字符串
                if (pre != ch) {
                    str.append(count);
                    str.append(pre);
                    pre = ch;
                    count = 1;
                } else {
                    count++;
                }
                index ++;
                //结束时生成字符串，注意区分两个字符串生成的区别，不是一个阶段事情，不能写在一起
                if (index == chars.length) {
                    str.append(count);
                    str.append(pre);
                    break;
                }
            }
            res = str.toString();
            nNum ++;
        }

        System.out.println(Integer.valueOf(res));
    }

    /**
     * 吃桃子，每棵树上桃子个数存在数组nums，求限时 timeLimit 小时内吃完的最慢速度，1小时内如果吃光一个树上必须等下个小时才能换一棵数
     * 教训：一定要想清楚，出问题也不能慌张
     */
    public static void huaweiChitaozi(){
        int []nums = new int[]{1, 3, 6, 8};
        int timeLimit = 5;
        int sum =0;
        for (int i: nums
             ) {
            sum+= i;
        }
        int left = sum%timeLimit > 0? sum/timeLimit+1 : sum/timeLimit;
        int right = left + nums.length;
        //速度由慢到快递增，寻找第一个cost < limit 的 speed
        //由于数量很大，二分查找找到最接近 timelimit 的 speed
        //取中间可能得出比 timelimit 略大的结果，需要检查并增加一个速度!
        int lastCost = 0;
        while (left < right) {
            int mid = left + (right-left)/2;
            int cost = 0;
            for (int i: nums
                 ) {
                cost += i%mid > 0 ? i/mid+1:i/mid;
            }
            if (cost > timeLimit) {
                left = mid+1;
            } else {
                right = mid -1;
            }
            lastCost = cost;
        }
        //这块的逻辑其实不复杂，但是考试时慌张放弃了思考，丢失了对核心问题的关注，出现预期外的问题时不知所措。
        //思路一定要理清楚，问题的本质，解决办法的原理，可能存在的问题一定要非常清楚才行！！！
        int res = left;
        if (lastCost > timeLimit) {
            res +=1;
        }
        printFuncName();
        System.out.println(res);
    }

    /**
     * cpu个数m，任务耗时数组 nums，安排任务先安排耗时最短，任务结束后安排未安排任务，求完成所有任务总耗时
     * 教训：审题一定要认真，不要怕描述复杂的问题，阅读问题的时间不会花太久不要着急
     */
    public static  void huaweiCPUCost(){
        int m = 3;
        int n = 5;
        int []nums = new int[]{8,4,3,1,10};
        Arrays.sort(nums);
        List <Integer>list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            list.add(nums[i]);
        }

        int cost = 0;
        Map <Integer,Integer>map = new HashMap<Integer,Integer>();
        while (true) {
            int index = 0;
            while (index < m) {
                //运行1秒减去耗时，首次不减，无任务不减
                if (cost !=0 && map.getOrDefault(index, 0) > 0) {
                    map.put(index, map.getOrDefault(index, 0)-1);
                }

                //无任务从未完成里领取
                if (map.getOrDefault(index, 0) == 0 && list.size() > 0) {
                    int curCost = list.get(0);
                    map.put(index, curCost);
                    list.remove(0);
                }
                index++;
            }

            //所有cpu完成任务，结束
            Boolean allFinish = true;
            for (Integer tail: map.keySet()
            ) {
                if (map.getOrDefault(tail, 0) != 0){
                    allFinish = false;
                    break;
                }
            }
            if (allFinish) {
                break;
            }
            cost++;
        }
        printFuncName();
        System.out.println(cost);
    }
    public static void findStringFirstOccur(){
        printFuncName();
        System.out.println(strStr("acacacd", "acacd"));
    }

    /**
     * KMP算法
     * 预先计算出不匹配时回退后新的匹配位置，在不匹配时不必重新匹配，时间复杂度为n，而不是m*n
     * next[j] 存储j之前不含j字符串最大前后缀相同长度
     * acac[a]cd 不匹配 acac[d]时，回退到 ac[a]cd
     */
    public static int strStr(String haystack, String needle) {
        char[] s = haystack.toCharArray();
        char[] p = needle.toCharArray();
        int m = s.length;
        int n = p.length;
        int[] next = new int[n];
        next[0] = -1;
        int i = 0, j = -1;
        /**
         * 字符串i存在next[i+1]
         * 不匹配时回退，回退位置如果相等更新位置，否则回退到初始值
         */
        while(i < n - 1) {
            if(j == -1 || p[i] == p[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                //回退
                j = next[j];
            }
        }
        i = 0;
        j = 0;
        while(i < m && j < n) {
            if(j == -1 || s[i] == p[j]) {
                i++;
                j++;
            } else {
                // i = i - j + 1;
                j = next[j];
            }
        }
        return j == n ? i - j : -1;
    }

    //反转字符串中的单词
    public static void replaceString2() {
        String s = "we are  family!";
        //调API
        // 除去开头和末尾的空白字符
        s = s.trim();
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        String res = String.join(" ", wordList);

        //实现拆分，空格合并，反转，组合，逻辑杂糅在一起。缺点可读性差，而且写完就完了收获很小
        List<String> strs = new ArrayList<String>();
        int start = 0, end = 0;
        while (start < s.length()) {
            char c = s.charAt(start);
            if (c == ' '){
                start++;
                end++;
            } else {
                while (end < s.length()) {
                    if (end == s.length()-1) {
                        strs.add(String.join("",s.substring(start,s.length())));
                        start=s.length();
                        break;
                    } else {
                        char c1 = s.charAt(end);
                        //空格结束
                        if (c1 == ' ' && end > 0 && s.charAt(end-1)!=' '){
                            strs.add(String.join("",s.substring(start, end)));
                        }
                        //字符串开始
                        if (c1 != ' ' && end > 0 && s.charAt(end-1)==' '){
                            start=end;
                            end++;
                            break;
                        }
                    }
                    end ++;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = strs.size()-1; i >= 0; i--) {
            builder.append(strs.get(i));
            if (i!=0){
                builder.append(" ");
            }
        }
        String res2 = builder.toString();



        printFuncName();
        System.out.println(res);
        System.out.println(res2);
    }

    //实现拆分，空格合并，反转，组合，功能拆分成函数
    public static void replaceString2Function() {
        String s = "we are  family!";
        StringBuilder builder = new StringBuilder(s);
        //去多余空格
        FuncTrimMoreSpace(builder);
        //反转所有字符
        FuncReverseChar(builder, 0, builder.toString().length()-1);
        //反转单词内字符
        FuncReverseWordChar(builder);
        printFuncName();
        System.out.println(builder.toString());
    }
    public static void FuncTrimMoreSpace(StringBuilder s) {
        int i = 0;
        while (i<s.length()) {
            if (s.charAt(i) == ' ') {
                if (i==0
                        || i==s.length()-1
                        || (i>0 && s.charAt(i-1)== ' ')){
                    s.deleteCharAt(i);
                }
            }
            i++;
        }
        //双指针法，需要多练习，能熟练用这一模式可以提高效率
    }
    public static void FuncReverseChar(StringBuilder s, int start, int end) {
        int left = start, right = end;
        while (left<right) {
            char temp = s.charAt(left);
            s.setCharAt(left, s.charAt(right));
            s.setCharAt(right, temp);
            left++;
            right--;
        }
    }
    public static void FuncReverseWordChar(StringBuilder s) {
        int end = 0;
        //注意需要修改 for 循环的变量时 for 循环的 i++
        for (int i = 0; i < s.length(); ) {
            // 循环至单词的末尾
            while (end < s.length() && s.charAt(end) != ' ') {
                end++;
            }
            // 翻转单词
            FuncReverseChar(s, i, end-1);
            // 更新start，去找下一个单词
            i=end+1;
            end++;
        }
    }
    public static void replaceString() {
        String s = "we are family!";
        //string builder
        StringBuilder builder = new StringBuilder();
        for (Character c:s.toCharArray()
             ) {
            if (c == ' '){
                builder.append("%20");
            } else {
                builder.append(c);
            }
        }
        String res = builder.toString();
        //char array
        char[] charArr = new char[res.length()*3];
        int change = 0;
        for (int i = 0; i < res.length(); i++) {
            char c = res.charAt(i);
            if (c == ' '){
                charArr[i+change]='%';
                charArr[i+change+1]='2';
                charArr[i+change+2]='0';
                change+=2;
            } else {
                charArr[i]=c;
            }
        }
        String res2 = new String(charArr, 0 , res.length()+change);

        printFuncName();
        System.out.println(res);
        System.out.println(res2);
    }
    public static void reverseString(){
        char[]s = new char[]{'h','e','l','l','o'};

        int left = 0, right = s.length-1;
        while (left < right) {
            char temp = s[left];
            s[left]=s[right];
            s[right]=temp;
            left++;
            right--;
        }
        printFuncName();
        System.out.println(s);
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

    //https://leetcode.cn/problems/find-common-characters/ 找出所有在 words 的每个字符串中都出现的共用字符（ 包括重复字符）
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
