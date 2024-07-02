import java.util.*;
import java.awt.*;

// A class that represents Modrian Art and can create basic and complex Modrian Art.

public class Mondrian {

    // Behavior:
    // -  Creates random artwork which is in Modrian style. The canvas is repeatedly
    //    randomly divided into smaller regions until it reached a threshold (when the
    //    region is smaller than one-fourth the width and smaller than one-fourth the height
    //    of the canvas). The regions are randomly colored red, yellow, cyan, and white.
    // Paramater: 
    // - pixels represents the list of colors of each of the pixels in the artwork
    public void paintBasicMondrian(Color[][] pixels) {
        paintBasicMondrian(pixels, 0, pixels.length, 0, pixels[0].length);
    } 

    // Behavior:
    // -  A helper method of paintBasicMondrian(Color[][] pixels) to help create basic random artwork which is 
    //    in Modrian style.
    // Paramater: 
    // - pixels represents the list of colors of each of the pixels in the artwork
    // - row1 represent the top left row value
    // - row2 represents the bottom right row value
    // - col1 represents the top left column value
    // - col2 represents the bottom right column value
    private void paintBasicMondrian(Color[][] pixels, int row1, int row2, int col1, int col2) {
        int randomNumColor = (int)(Math.random() * (4) + 1);
        Color randomColor;
        if (randomNumColor == 1) {
            randomColor = Color.RED;
        } else if (randomNumColor == 2) {
            randomColor = Color.YELLOW;
        } else if (randomNumColor == 3) {
            randomColor = Color.CYAN;
        } else {
            randomColor = Color.WHITE;
        }
        if (row2 - row1 > 0 && col2 - col1 > 0) {
            if (row2 - row1 >= pixels.length / 4 && col2 - col1 >= pixels[0].length / 4) {
                int randomHorizontalLine = (int)(Math.random() * (row2 - row1) + row1);
                int randomVerticalLine = (int)(Math.random() * (col2 - col1) + col1);
                paintBasicMondrian(pixels, row1, randomHorizontalLine, col1, randomVerticalLine);
                paintBasicMondrian(pixels, randomHorizontalLine, row2, randomVerticalLine, col2);
                paintBasicMondrian(pixels, row1, randomHorizontalLine, randomVerticalLine, col2);
                paintBasicMondrian(pixels, randomHorizontalLine, row2, col1, randomVerticalLine);
            } else if (row2 - row1 >= pixels.length / 4) {
                int randomHorizontalLine = (int)(Math.random() * (row2 - row1) + row1);
                paintBasicMondrian(pixels, row1, randomHorizontalLine, col1, col2);
                paintBasicMondrian(pixels, randomHorizontalLine, row2, col1, col2);

            } else if (col2 - col1 >= pixels[0].length / 4) {
                int randomVerticalLine = (int)(Math.random() * (col2 - col1) + col1);
                paintBasicMondrian(pixels, row1, row2, col1, randomVerticalLine);
                paintBasicMondrian(pixels, row1, row2, randomVerticalLine, col2);
            } else {
                Color border = new Color(0, 0, 0);
                for (int i = row1; i < row2; i++) {
                    for (int j = col1; j < col2; j++) {
                        if(i == row1 || i == row2 - 1 || j == col1 || j == col2 - 1) {
                            pixels[i][j] = border;
                        } else {
                            pixels[i][j] = randomColor;
                        }
                    }
                }   
            }   
        } 
    }
    
    // Behavior:
    // -  Creates random artwork which is in Modrian style. The canvas is repeatedly
    //    randomly divided into smaller regions until it reached a threshold (when the
    //    region is smaller than one-fourth the width and smaller than one-fourth the height
    //    of the canvas). The regions are randomly colored based on the location of the region.
    //    Regions in the top left fourth of the canvas will have a different red in RGB value.
    //    Regions in the bottom right fourth of the canvas will have a different green in RGB value.
    // Paramater: 
    // - pixels represents the list of colors of each of the pixels in the artwork
    public void paintComplexMondrian(Color[][] pixels) {
        paintComplexMondrian(pixels, 0, pixels.length, 0, pixels[0].length);
    }

    // Behavior:
    // -  A helper method of paintComplexMondrian to help create complex random artwork which is in Modrian style.
    // Paramater: 
    // - pixels represents the list of colors of each of the pixels in the artwork
    // - row1 represent the top left row value
    // - row2 represents the bottom right row value
    // - col1 represents the top left column value
    // - col2 represents the bottom right column value
    private void paintComplexMondrian(Color[][] pixels, int row1, int row2, int col1, int col2) {
        int randomNumColor = (int)(Math.random() * (4) + 1);
        Color randomColor;
        if (randomNumColor == 1) {
            randomColor = Color.RED;
        } else if (randomNumColor == 2) {
            randomColor = Color.YELLOW;
        } else if (randomNumColor == 3) {
            randomColor = Color.CYAN;
        } else {
            randomColor = Color.WHITE;
        }
        if (row2 - row1 > 0 && col2 - col1 > 0) {
            if (row2 - row1 >= pixels.length / 4 && col2 - col1 >= pixels[0].length / 4) {
                int randomHorizontalLine = (int)(Math.random() * (row2 - row1) + row1);
                int randomVerticalLine = (int)(Math.random() * (col2 - col1) + col1);
                paintComplexMondrian(pixels, row1, randomHorizontalLine, col1, randomVerticalLine);
                paintComplexMondrian(pixels, randomHorizontalLine, row2, randomVerticalLine, col2);
                paintComplexMondrian(pixels, row1, randomHorizontalLine, randomVerticalLine, col2);
                paintComplexMondrian(pixels, randomHorizontalLine, row2, col1, randomVerticalLine);
            } else if (row2 - row1 >= pixels.length / 4) {
                int randomHorizontalLine = (int)(Math.random() * (row2 - row1) + row1);
                paintComplexMondrian(pixels, row1, randomHorizontalLine, col1, col2);
                paintComplexMondrian(pixels, randomHorizontalLine, row2, col1, col2);

            } else if (col2 - col1 >= pixels[0].length / 4) {
                int randomVerticalLine = (int)(Math.random() * (col2 - col1) + col1);
                paintComplexMondrian(pixels, row1, row2, col1, randomVerticalLine);
                paintComplexMondrian(pixels, row1, row2, randomVerticalLine, col2);
            } else {
                Color border = new Color(0, 0, 0);
                for (int i = row1; i < row2; i++) {
                    for (int j = col1; j < col2; j++) {
                        int red = randomColor.getRed();
                        int green = randomColor.getGreen();
                        int blue = randomColor.getBlue();
                        int randomNum = (int)(Math.random() * (255));
                        if (i == row1 || i == row2 - 1 || j == col1 || j == col2 - 1) {
                            pixels[i][j] = border;
                        } else if(row2 < pixels.length / 2 && col2 < pixels[0].length / 2) {
                            pixels[i][j] = new Color(Math.max(red - 100, 0), green, blue);
                        } else if (row2 > pixels.length / 2 && col2 > pixels[0].length / 2) {
                            pixels[i][j] = new Color(red, Math.max(green - 100, 0), blue);
                        } else {
                            pixels[i][j] = new Color(red, green, blue);
                        }
                    }
                }   
            }
        }
    }
}
