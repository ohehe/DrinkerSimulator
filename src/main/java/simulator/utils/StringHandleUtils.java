package simulator.utils;/**
 * Created by Administrator on 2016/7/1.
 */

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author Administrator
 * @create 2016-07-01 11:35
 */
public class StringHandleUtils {
    //16进制 ascll转换数组
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static byte[] convertInt2HEXByteArr(int digit, long num) throws UnsupportedEncodingException {
        byte[] arrResult = new byte[digit];
        for (int i = 0; i < digit; ++i) {
            arrResult[i] = 0X30;
        }
        byte[] arrTemp = Long.toHexString(num).toUpperCase().getBytes("US-ASCII");

        if (digit < arrTemp.length) {
            throw new IllegalArgumentException(StringHandleUtils.class.getName() + ":10进制转换16进制ascll码长度错误");
        }

        int j = 0;
        for (int i = digit - arrTemp.length; i < digit; ++i, ++j) {
            arrResult[i] = arrTemp[j];
        }
        return arrResult;
    }

    public static byte[] bytesFillZero(String str, int arrLen) throws UnsupportedEncodingException {
        byte[] result = new byte[arrLen];
        if (arrLen < str.length()) {
            throw new IllegalArgumentException(StringHandleUtils.class.getName() + ":");
        }
        byte[] temp = str.getBytes("US-ASCII");
        int i;
        for (i = 0; i < arrLen - str.length(); ++i) {
            //补零
            result[i] = 0x30;
        }
        int mid = arrLen - str.length();
        for (i = mid; i < arrLen; ++i) {
            result[i] = temp[i - mid];
        }
        return result;
    }

    //start 为开始位置在数组中序号
    public static long unpackHexByteArr2Long(byte[] arr, int start, int infoLength) {
        byte[] temp = new byte[infoLength];
        int i = start;
        while (i < start + infoLength) {
            temp[i - start] = arr[i];
            ++i;
        }
        return resolveLongFromByteArr(temp);
    }

    public static long resolveLongFromByteArr(byte[] arr) {
        int i = 0;  //不为0的个数 第一个不为0的序号
        long result = 0;
        while (arr[i] == 0x30 && ++i < arr.length) ;
        for (int k = i; k < arr.length; ++k) {//转为DIGITS_UPPER形式
            arr[k] = arr[k] > 0X5A ? (byte) (arr[k] - 32) : arr[k];
            //ascll 数字在大写字母前面  小写字母在大写字母后面
        }
        int sqrtN = 0;
        for (int j = arr.length - 1; j >= i; --j) {
            //int pos = Arrays.binarySearch(DIGITS_UPPER,(char)arr[j]) ;
            //判0-9字符
            if (arr[j] - 0x30 < 10 && arr[j] - 0x30 >= 0) {
                result += (arr[j] - 0x30) * (long) Math.pow(16, sqrtN++);
                continue;
            }
            //判A-F字符
            int check = arr[j] - 0X41;
            if (check >= 0) {
                result += (check + 10) * (long) Math.pow(16, sqrtN++);
                continue;
            }
        }
        return result;
    }

    public static String resolveByteArr2Str(int digit, byte[] arr, int start) throws UnsupportedEncodingException {
        byte[] temp = Arrays.copyOfRange(arr, start, start + digit);
        //蛋疼 可能抛出
        return new String(temp, "US-ASCII");
    }
}
