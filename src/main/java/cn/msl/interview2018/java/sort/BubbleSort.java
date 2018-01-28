package cn.msl.interview2018.java.sort;
/*遍历待排序的数组，每次遍历比较相邻的两个元素，如果他们的排列顺序错误就交换他们的位置，经过一趟排序后，
最大的元素会浮置数组的末端。重复操作，直到排序完成。*/

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {5, 4, 3, 6, 8, 2, 9};

        for (int i = 0; i < array.length - 1; i++) {//最多排序n-1次
            for (int j = 0; j < array.length - i - 1; j++) {//需要交换的次数
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
