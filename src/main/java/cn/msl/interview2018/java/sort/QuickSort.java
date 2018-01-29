package cn.msl.interview2018.java.sort;

import java.util.Arrays;

/*快速排序：基于递归和分治（挖坑）
 时间复杂度：O(n log n)*/
public class QuickSort {

    public static void main(String[] args) {
        int[] array = {5, 4, 3, 6, 8, 2};

        qsort(array, 0, array.length - 1);

        System.out.println("排序结果：" + Arrays.toString(array));
    }

    private static void qsort(int[] arr, int L, int R) {
        if (L < R) {
            int pivot = partition(arr, L, R);        //将数组分为两部分
            qsort(arr, L, pivot - 1);                   //递归排序左子数组
            qsort(arr, pivot + 1, R);                  //递归排序右子数组
        }
    }

    private static int partition(int[] array, int L, int R) {
        int pivot = array[L];     //选取基准值(挖好坑)
        while (L < R) {
            while (L < R && array[R] >= pivot) --R;
            array[L] = array[R];             //从右找到小于基准的值，填坑。

            while (L < R && array[L] <= pivot) ++L;
            array[R] = array[L];           //从左找到大于基准的值，填坑。
        }

        array[L] = pivot;//扫描完成，填入基准值。(此时L=R)
        return L;//返回基准值的位置
    }

}
