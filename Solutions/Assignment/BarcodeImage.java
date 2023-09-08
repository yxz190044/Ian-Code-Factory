import java.util.*;
import java.lang.Cloneable;

public class BarcodeImage implements Cloneable {
    
    public static final int MAX_HEIGHT = 30;
    public static final int MAX_WIDTH = 65;
    private boolean[][] imageData;
    
    public BarcodeImage() {
        imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
    }
    
    public BarcodeImage(String[] strData) {//lower-left index is 0
        imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
        if (checkSize(strData)) {
            for (int i = 0; i < strData.length; i++) {
                for (int j = 0; j < strData[i].length(); j++) {
                    if (strData[i].charAt(j) == '*') {
                        imageData[i][j] = true;
                    }
                }
            }
        }
    }
    
    public boolean getPixel(int row, int col) {
        if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH) {
            return false;
        }
        return imageData[row][col];
    }
    
    public boolean setPixel(int row, int col, boolean value) {
        if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH) {
            return false;
        }
        imageData[row][col] = value;
        return value;
    }
    
    private boolean checkSize(String[] data) {
        if (data == null || data.length == 0) {
            return false;
        }
        int m = data.length;
        int n = data[0].length();
        if (m > MAX_HEIGHT || n == 0 || n > MAX_WIDTH) {
            return false;
        }
        for (String s : data) {
            if (s.length() != n) {
                return false;
            }
        }
        return true;
    }
    
    public void displayToConsole() {
        System.out.println("displayToConsole:");
        for (boolean[] array : imageData) {
            for (boolean value : array) {
                if (value) {
                    System.out.print(DataMatrix.BLACK_CHAR);
                } else {
                    System.out.print(DataMatrix.WHITE_CHAR);
                }
            }
            System.out.println();
        }
    }
    
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        BarcodeImage obj = (BarcodeImage) super.clone();
        obj.imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                obj.setPixel(i, j, imageData[i][j]);
            }
        }
        return obj;
    }
    
}
