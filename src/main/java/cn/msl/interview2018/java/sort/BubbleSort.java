package cn.msl.interview2018.java.sort;

import java.util.Arrays;

/*冒泡排序：遍历待排序的数组，每次遍历找到当次的最大(最小值)，放到末尾，经过n-1次遍历后，排序完成。
时间复杂度：O(n2) 外层循环需要比较n-1次，内层循需环要比较n-x次。*/
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {5, 4, 3, 6, 8, 2, 9};

        for (int i = 0; i < array.length - 1; i++) {//外层最多n-1次，(最后一次只剩下一个数，无需遍历)
            for (int j = 0; j < array.length - i - 1; j++) {//外层第i次遍历时，对应内层的后i个数已经排好。
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
