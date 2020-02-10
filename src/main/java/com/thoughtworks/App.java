package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

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
        int[] itemCount = getItemCount(selectedItems);
        double[] itemSubtotal = getItemSubtotal(itemCount);
        String[] promMsg = readProms(itemSubtotal);
        System.out.println(Arrays.toString(promMsg));
        return getItemSubtotal(getItemCount(selectedItems)).toString();
    }

    /**
     * 读取优惠列表，寻找最佳优惠
     */
    public static String[] readProms(double[] itemSubtotal) {
        double totalCost = sumArr(itemSubtotal);
        String[] noProm = new String[2];
        noProm[0] = String.valueOf(totalCost);
        String[] reachProm = reachPromCal(totalCost);
        String[] halfProm = halfPromCal(totalCost, itemSubtotal);
        double[] costArr = new double[3];
        costArr[0] = totalCost;
        costArr[1] = Double.parseDouble(reachProm[0]);
        costArr[2] = Double.parseDouble(halfProm[0]);
        int lowestIndex = findMinNumIndex(costArr);
        switch (lowestIndex) {
            case 0:
                return noProm;
            case 1:
                return reachProm;
            case 2:
                return halfProm;
            default:
        }
        return noProm;
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
        String reachPromMsg = "满30减6元，省" + (int) (totalCost - reachPromTotal) + "元\n"
                + "-----------------------------------\n";
        String[] reachProm = new String[2];
        reachProm[0] = String.valueOf(reachPromTotal);
        reachProm[1] = reachPromMsg;

        System.out.println(Arrays.toString(reachProm));
        return reachProm;
    }

    /**
     * 按指定菜品半价促销计算总价
     */
    public static String[] halfPromCal(double totalCost, double[] itemSubtotal) {
        String[] itemNames = getItemNames();
        double[] halfPromSubtotal = Arrays.copyOf(itemSubtotal,itemSubtotal.length);
        String[] itemIds = getItemIds();
        String[] halfPromIds = getHalfPriceIds();
        ArrayList<String> halfPromNames = new ArrayList<>();
        StringBuilder halfPromMsg = new StringBuilder();
        halfPromMsg.append("指定菜品半价(");

        for (String halfPromId : halfPromIds) {
            int halfPromIdIndex = findFirstIndexOf(itemIds, halfPromId);
            if (-1 != halfPromIdIndex) {
                halfPromSubtotal[halfPromIdIndex] *= 0.5;
                halfPromNames.add(itemNames[halfPromIdIndex]);
            }
        }
        double halfPromTotal = sumArr(halfPromSubtotal);
        String[] halfNamesArr = new String[halfPromNames.size()];
        halfPromMsg.append(joinStringArr(halfPromNames.toArray(halfNamesArr), ", "));
        halfPromMsg.append(")，省");
        halfPromMsg.append((int) (totalCost - halfPromTotal));
        halfPromMsg.append("元\n");
        halfPromMsg.append("-----------------------------------\n");
        String[] halfProm = new String[2];
        halfProm[0] = String.valueOf(halfPromTotal);
        halfProm[1] = halfPromMsg.toString();

        System.out.println(Arrays.toString(halfProm));
        return halfProm;
    }

    /**
     * 将字符串数组中寻找特定字符串，并返回第一次出现位置的下标
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
     * 采用指定分隔符，将字符串数组合并成字符串
     */
    public static String joinStringArr(String[] stringArr, String delimiter) {
        StringBuilder joinedString = new StringBuilder();
        for (int index = 0; index < stringArr.length; index++) {
            joinedString.append(stringArr[index]);
            if (index != stringArr.length - 1) {
                joinedString.append(delimiter);
            }
        }
        return joinedString.toString();
    }

    /**
     * 将双精度浮点数加和
     */
    public static double sumArr(double[] arrToSum) {
        double sum = 0;
        for (double num : arrToSum) {
            sum += num;
        }
        return sum;
    }

    /**
     * 在双精度浮点数组中寻找最小元素的下标
     */
    public static int findMinNumIndex(double[] numArr) {
        int minNumIndex = 0;
        for (int index = 0; index < numArr.length; index++) {
            if (numArr[index] < numArr[minNumIndex]) {
                minNumIndex = index;
            }
        }
        return minNumIndex;
    }

}


