
public class MoveZeros {
    public static void main(String[] args) {
        int[] arr = new int[] { 0, -1, 0, 4, 3, -10, 6, 0, -6, 7, 0, 8, 0 };

        int firstZero = 0;
        int end = arr.length - 1;
        int lenZero = 0;

        while (arr[firstZero] < 0) {
            firstZero++;
        }

        while (firstZero + lenZero < end) {
            while (arr[end] >= 0) {
                end--;
            }

            if (arr[end] == 0 && arr[firstZero] == 0) {
                int temp = firstZero;
                while (arr[temp] == 0) {
                    temp++;
                }
                if (temp < end) {
                    swap(arr, end, temp);
                    lenZero++;
                } else {
                    end--;
                }
            } else if (arr[end] < 0) {
                swap(arr, end, firstZero);
                firstZero++;
            } else {
                swap(arr, end, firstZero);
            }
            // end--;
            printArr(arr);
        }

    }

    public static void swap(int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    public static void printArr(int[] arr) {
        System.out.print("[ ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println("]");
    }
}
