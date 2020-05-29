package Lesson_2;

public class Main {

    public static void main(String[] args) {

        String[][] arr;
        int[][] arrInt;

        String str = "10a 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
        String str2 = "9 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
        String str3 = "a10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
        String str4 = "5 6 7 1\n300 3 1 0";

        try {
            arr = strDouble(str4);
            arrInt = half(arr);

            System.out.println("Сумма цифр в массиве равна " + arrSum(arrInt));
        }catch (MyOutBounds e){
            e.printStackTrace();
        }catch (MyNumbFormatExeption e){
            e.printStackTrace();
        }
    }

    static String[][] strDouble(String str) {

        String[][] arr = new String[4][4];
        String[] tmp;

        tmp = str.split("\\n");

        for (int i = 0; i < tmp.length; i++)
            arr[i] = tmp[i].split(" ");

        for (int i = 0; i < arr.length; i++)
            for (int k = 0; k < arr[i].length; k++)
                if (i != 3 || k != 3)
                    throw new MyOutBounds();
        return arr;
    }

    static int[][] half(String[][] arr) {

        int[][] numbers = new int[4][4];

        for (int i = 0; i < arr.length; i++)
            for (int k = 0; k < arr[i].length; k++){
                for (int m = 0; m < arr[i][k].length(); m++)
                    if (arr[i][k].charAt(m) > 57 || arr[i][k].charAt(m) < 48)
                        throw new MyNumbFormatExeption();
                numbers[i][k] = Integer.parseInt(arr[i][k]);
            }

        return numbers;
    }

    static int arrSum(int[][] arrInt){
        int ans = 0;

        for (int i = 0; i < arrInt.length; i++)
            for (int k = 0; k < arrInt[i].length; k++)
                ans += arrInt[i][k];
        return ans;
    }
}
