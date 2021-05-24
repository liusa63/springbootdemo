package com.example.demo.datastructure;

/**
 * @author liusa
 * @Description 查找
 * @createTime 2021-05-24 11:08:00
 * @Version 1.0
 */
public class FindMain {

    public static void main(String[] args) {
        int[] array = new int[]{2,3,4,5,6,7,8,9,10};

        System.out.println(halfFind(array, 0, array.length-1, 7));
        System.out.println(halfFind(array, 0, array.length-1, 0));
        System.out.println(halfFind(array, 0, array.length-1, 30));

    }

    /**
     * 二分查找
     * @param array 递增数组
     * @param minIndex
     * @param maxIndex
     * @param num
     * @return
     */
    public static int halfFind(int[] array, int minIndex, int maxIndex, int num){
        if (minIndex > maxIndex || array[minIndex] > num || array[maxIndex] < num){
            return -1;
        }
        if(array[minIndex] == num){
            return minIndex;
        }
        if(array[maxIndex] == num){
            return maxIndex;
        }
        int half = (maxIndex + minIndex)/2;
        if (array[half] <= num){
            return halfFind(array, half, maxIndex -1, num);
        }
        return halfFind(array, minIndex+1, half, num);
    }



}