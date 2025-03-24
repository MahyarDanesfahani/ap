package excercise.ex1;

public class DataSet {
    public static int getSmallest(int[] array) {
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (min > array[i]) {
                min = array[i];
            }
        }
        return min;
    }

    public static int getLargest(int[] array) {
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

    public static int getAverage(int[] array) {
        int aveg = 0;
        for (int i = 0; i < array.length; i++) {
            aveg += array[i];
        }
        return aveg / array.length;
    }

    public static void getRange(int[] array) {
        int min = getSmallest(array);
        int max = getLargest(array);
            System.out.print("Elements between " + min + " and " + max + " are: ");
            for (int num : array) {
                if (num > min && num < max) {
                    System.out.print(num + " ");
                }
            }
            System.out.println();
        }
    }

