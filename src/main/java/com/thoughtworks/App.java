package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
        String selectedItems = scan.nextLine();
        String summary = bestCharge(selectedItems);
        System.out.println(summary);
    }

    /**
     * 接收用户选择的菜品和数量，返回计算后的汇总信息
     *
     * @param selectedItems 选择的菜品信息
     */
    public static String bestCharge(String selectedItems) {
        // 此处补全代码
//    return selectedItems;

//    return getItemIds()[0];
        double[] itemSubtotal = getItemSubtotal(getItemCount(selectedItems));
        double totalCost = sumArr(itemSubtotal);
        System.out.println(totalCost);
        return getItemSubtotal(getItemCount(selectedItems)).toString();
    }

    /**
     * 获取每个菜品依次的编号
     */
    public static String[] getItemIds() {
        return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
    }

    /**
     * 获取每个菜品依次的名称
     */
    public static String[] getItemNames() {
        return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
    }

    /**
     * 获取每个菜品依次的价格
     */
    public static double[] getItemPrices() {
        return new double[]{18.00, 6.00, 8.00, 2.00};
    }

    /**
     * 获取半价菜品的编号
     */
    public static String[] getHalfPriceIds() {
        return new String[]{"ITEM0001", "ITEM0022"};
    }

    /**
     * 获取每个菜品依次的数量
     */
    public static int[] getItemCount(String selectedItems) {
        String[] itemIds = getItemIds();
        int itemNum = itemIds.length;
        int[] itemCount = new int[itemNum];
        String[] selectedItemsArr = selectedItems.split(",");
        for (String selectedItem : selectedItemsArr) {
            String[] itemIdAndCount = selectedItem.split(" x ");
            itemCount[findFirstIndexOf(itemIds, itemIdAndCount[0])] = Integer.parseInt(itemIdAndCount[1]);
        }
//    System.out.println(Arrays.toString(itemCount));
        return itemCount;
    }

    /**
     * 获取每个菜品依次的小计
     */
    public static double[] getItemSubtotal(int[] itemCount) {
        double[] itemPrices = getItemPrices();
        int itemNum = itemPrices.length;
        double[] itemSubtotal = new double[itemNum];
        for (int index = 0; index < itemNum; index++) {
            itemSubtotal[index] = itemCount[index] * itemPrices[index];
        }
//        System.out.println(Arrays.toString(itemSubtotal));
        return itemSubtotal;
    }
    /**
     * 按满30-6促销计算总价
     */
    public static String[] reachPromCal(double totalCost) {
        double reachPromTotal = totalCost;
        if (totalCost > 30) {
            reachPromTotal = totalCost - 6;
        }
        String reachPromMsg = "满30减6元，省" + (totalCost - reachPromTotal) + "元\n";
        String[] reachProm = new String[2];
        reachProm[0] = String.valueOf(reachPromTotal);
        reachProm[1] = reachPromMsg;

        System.out.println(Arrays.toString(reachProm));
        return reachProm;
    }
    /**
     * 在字符串数组中寻找特定字符串，并返回第一次出现位置的下标
     */
    public static int findFirstIndexOf(String[] stringArr, String strToFind) {
        for (int index = 0; index < stringArr.length; index++) {
            if (strToFind.equals(stringArr[index])) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 在字符串数组中寻找特定字符串，并返回第一次出现位置的下标
     */
    public static double sumArr(double[] arrToSum) {
        double sum = 0;
        for (double num : arrToSum) {
            sum += num;
        }
        return sum;
    }
}


