import java.util.*;
import java.lang.Cloneable;

class DataMatrix implements BarcodeIO {
    
    public static final char BLACK_CHAR = '*';
    public static final char WHITE_CHAR = ' ';
    private BarcodeImage image;
    private String text;
    private int actualWidth;
    private int actualHeight;
    
    public DataMatrix() {
        actualWidth = 0;
        actualHeight = 0;
        image = new BarcodeImage();
        text = "";
    }
    
    public DataMatrix(BarcodeImage image) {
        this();
        scan(image);
    }
    
    public DataMatrix(String text) {
        this();
        readText(text);
    }
    
    public boolean readText(String text) {
        if (text != null) {
            this.text = text;
        }
        return true;
    }
    
    public boolean scan(BarcodeImage image) {
        try {
            this.image = (BarcodeImage) image.clone();
            actualHeight = computeSignalHeight();
            actualWidth = computeSignalWidth();
            return true;
        } catch(CloneNotSupportedException ex){}
        return true;
    }
    
    public int getActualWidth() {
        return actualWidth;
    }
    
    public int getActualHeight() {
        return actualHeight;
    }
    
    private int computeSignalWidth() {
        int i = 0;
        while (i < BarcodeImage.MAX_WIDTH && image.getPixel(actualHeight - 1, i)) {
            i++;
        }
        return i;
    }
    
    private int computeSignalHeight() {
        int i = 0;
        while (i < BarcodeImage.MAX_HEIGHT && image.getPixel(i, 0)) {
            i++;
        }
        return i;
    }
        
    public void displayTextToConsole() {
        System.out.println("displayTextToConsole:");
        System.out.println(text);
    }
    
    public void displayImageToConsole() {
        System.out.println("displayImageToConsole:");
        for (int i = 0; i < actualWidth + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 0; i < actualHeight; i++) {
            System.out.print("|");
            for (int j = 0; j < actualWidth; j++) {
                System.out.print(image.getPixel(i, j) ? BLACK_CHAR : WHITE_CHAR);
            }
            System.out.println("|");
        }
    }
    
    public boolean generateImageFromText() {
        if (text == null || text.length() > BarcodeImage.MAX_WIDTH - 2) {
            return false;
        }
        image = new BarcodeImage();
        actualWidth = text.length() + 2;
        char max = (char) 0;
        for (char ch : text.toCharArray()) {
            max = ch > max ? ch : max;
        }
        int maxNum = (int) max;
        actualHeight = 2;
        while (maxNum > 0) {
            maxNum /= 2;
            actualHeight++;
        }
        // borders
        for (int i = 0; i < actualWidth; i += 2) {
            image.setPixel(0, i, true);
        }
        for (int i = 0; i < actualHeight; i++) {
            image.setPixel(i, 0, true);
        }
        for (int i = 0; i < actualWidth; i++) {
            image.setPixel(actualHeight - 1, i, true);
        }
        for (int i = actualHeight - 1; i >= 0; i -= 2) {
            image.setPixel(i, actualWidth - 1, true);
        }
        int col = 1;
        for (char ch : text.toCharArray()) {
            WriteCharToCol(col++, (int) ch);
        }
        actualHeight = computeSignalHeight();
        actualWidth = computeSignalWidth();
        return true;
    }
    
    public boolean translateImageToText() {
        if (image == null || actualHeight <= 2 || actualWidth <= 2) {
            text = "";
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < actualWidth - 1; i++) {
            sb.append(readCharFromCol(i));
        }
        text = sb.toString();
        return true;
    }
    
    private char readCharFromCol(int col) {
        int num = 0;
        int factor = 1;
        for (int i = actualHeight - 2; i >= 1; i--) {
            if (image.getPixel(i, col)) {
                num += factor;
            }
            factor *= 2;
        }
        return (char) num;
    }
    
    private boolean WriteCharToCol(int col, int code) {
        if (col >= actualWidth) {
            return false;
        }
        for (int i = 0; i <= actualHeight - 3; i++) {
            if (((code >> i) & 1) == 1) {
                image.setPixel(actualHeight - 2 - i, col, true);
            }
        }
        return true;
    }
    
    public void displayRawImage() {
        image.displayToConsole();
    }
    
    private void clearImage() {
        image = new BarcodeImage();
        actualWidth = 0;
        actualHeight = 0;
    }
}
