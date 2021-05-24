package com.example.demo.datastructure;

import java.util.Arrays;

/**
 * @author liusa
 * @Description https://www.runoob.com/w3cnote/heap-sort.html
 * @createTime 2021-05-17 16:42:00
 * @Version 1.0
 */
public class SortMain {

    public static void main(String[] args) {
        int[] array = new int[]{2,7,5,3,44,0, 1,98,6};
        printFun(bubbleSort(array),"冒泡:");
        printFun(selectionSort(array), "选择:");
        printFun(insertionSort(array), "插入:");
        printFun(countSort(array), "计数:");
//        printFun(shellSort(array), "希尔:");
//        printFun(mergeSort(array), "归并:");
//        printFun(quickSort(array), "快速:");
//        printFun(heapSort(array), "堆:");
//        printFun(bucketSort(array), "桶:");
//        printFun(radixSort(array), "基数:");
    }

    /**
     * 计数排序
     * 将输入的数据值转化为键存储在额外开辟的数组空间中
     * 作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。
     * 当输入的元素是 n 个 0 到 k 之间的整数时，它的运行时间是 Θ(n + k)
     * @param array
     * @return
     */
    private static int[] countSort(int[] array) {
        int[] arr = Arrays.copyOf(array, array.length);
        // 数组中的最大数
        int max = arr[0];
        for (int i : arr) {
            if (i > max){
                max = i;
            }
        }

        int[] bucket = new int[max];
        for (int value : arr) {
//            bucket[value]++;
            bucket[value] = bucket[value] + 1;
        }
        int sortedIndex = 0;
        for (int j = 0; j < max; j++) {
            while (bucket[j] > 0) {
//                arr[sortedIndex++] = j;
//                bucket[j]--;
                arr[sortedIndex] = j;
                bucket[j] = bucket[j] - 1;
                sortedIndex = sortedIndex+1;
            }
        }
        return arr;

//        return countingSort(arr, max);
    }
    private static int[] countingSort(int[] arr, int maxValue) {
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];

        for (int value : arr) {
            bucket[value]++;
        }

        int sortedIndex = 0;
        for (int j = 0; j < bucketLen; j++) {
            while (bucket[j] > 0) {
                arr[sortedIndex++] = j;
                bucket[j]--;
            }
        }
        return arr;
    }


    /**
     * 插入排序
     *
     * 从第一个元素开始，该元素可以认为已经被排序；
     * 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 将新元素插入到该位置后；
     * @param array
     * @return
     */
    private static int[] insertionSort(int[] array) {
        int[] arr = Arrays.copyOf(array, array.length);
        int temp;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1] > arr[i]){
                temp = arr[i];
                int j = i;
                for(; j > 0; j--){
                    if (arr[j-1] < temp){
                        break;
                    }
                    arr[j] = arr[j-1];
                }
                arr[j] = temp;
            }
        }
        return arr;
    }


    /**
     * 冒泡排序
     *
     * 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 针对所有的元素重复以上的步骤，除了最后一个；
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array){
        int[] arr = Arrays.copyOf(array, array.length);
        int temp;
        for (int i = 0; i < arr.length; i++){ // 循环次数
            for(int j = 0; j < arr.length - i -1; j++){ //内部比较的次数依次减少
                if(arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 选择排序
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array){
        int[] arr = Arrays.copyOf(array, array.length);
        int temp;
        int minIndex;
        for (int i = 0; i< arr.length; i++){ // 循环次数
            minIndex = i;
            for(int j = i+1; j < arr.length; j++){ // 查找最小元素的小标
                if(arr[minIndex] > arr[j]){
                    minIndex = j;
                }
            }
            if (i != minIndex){ // 位置交换
                temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
        return arr;
    }

    public static void printFun(int[] array, String name){
        System.out.print(name+" ");
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println("");

    }



}