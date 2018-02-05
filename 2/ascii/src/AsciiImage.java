import javax.imageio.ImageIO;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.Stack;


public class AsciiImage {
    private double[] content;
    private int width = 0;
    private int height = 0;

    static char[] default_palette = "MWNXK0Okxdolc:;,'...   ".toCharArray(); // {' ', '.', '*', '#'};
    private char[] ascii_palette = default_palette;

    public AsciiImage() {
    }

    public AsciiImage(File file, char[] ascii_palette) throws IOException {
        this();
        final BufferedImage image = ImageIO.read(file);
        if (image == null)
            throw new IllegalArgumentException("didn't read image");

        this.updateImage(image);
        this.setPalette(ascii_palette);
    }

    public AsciiImage(final BufferedImage image, char[] ascii_palette) {
        this.updateImage(image);
        this.setPalette(ascii_palette);

    }

    public void updateImage(final BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.content = new double[width * height];

        for (int y = 0, i = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x, ++i) {

                Color pixel = new Color(image.getRGB(x, y));
                double R = (double) pixel.getRed() / 255.0;
                double G = (double) pixel.getGreen() / 255.0;
                double B = (double) pixel.getBlue() / 255.0;

                double Y = (R + G + B) / 3;

                this.content[i] = Y;
            }

        }
    }

    public void updateImage(double[] content, int width, int height) {
        this.width = width;
        this.height = height;
        this.content = content;
    }

    public void setPalette(char[] ascii_palette) {
        this.ascii_palette = ascii_palette;
    }

    public String getAsciiImage(){
        StringBuilder ascii = new StringBuilder((width + 1) * height);
        for (int y = 0, i = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x, ++i) {
                int pos = (int)Math.round((ascii_palette.length - 1) * this.content[i]);
                ascii.append(ascii_palette[pos]);
            }
            ascii.append("\n");
        }
        return ascii.toString();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public AsciiImage getRectangle(int x1, int y1, int x2, int y2) {
        int width = this.width;
        int height = this.height;

        if (x1 > width || x1 < 0 || x2 > width || x2 < 0 || y1 > height || y1 < 0 || y2 > height || y2 < 0) {
            throw new IllegalArgumentException("point value out of range");
        }

        AsciiImage newImage = new AsciiImage();

        int tmp;
        if (x1 > x2) {
            tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }

        int newImageWidth = x2 - x1;
        int newImageHeight = y2 - y1;

        double[] newImageContent = new double[newImageWidth * newImageHeight];

        for (int y = y1, i = 0; y < y2; ++y) {
            int j = y * width + x1;
            for (int x = x1; x < x2; ++x, ++i, ++j) {
                newImageContent[i] = this.content[j];
            }
        }

        newImage.updateImage(newImageContent, newImageWidth, newImageHeight);

        return newImage;
    }

    public AsciiImage invert() {
        int len = this.width * this.height;
        for (int i = 0; i < len; ++i) {
            this.content[i] = 1 - this.content[i];
        }

        return this;
    }

    public Vector<AsciiImage> splitByThreshold(double threshold) {
        Vector<AsciiImage> images = new Vector<AsciiImage>();

        List<Integer> xs = new Vector<Integer>();
        List<Integer> ys = new Vector<Integer>();

        // horizontal lines, fill ys
        for (int y = 0; y < this.height; ++y) {
            boolean free_line = true;
            for (int x = 0; x < this.width; ++x) {
                if (this.content[y * this.width + x] <= threshold) {
                    free_line = false;
                    break;
                }
            }
            if (free_line) {
                ys.add(y);
            }
        }

        // vertical lines, fill xs
        for (int x = 0; x < this.width; ++x) {
            boolean free_line = true;
            for (int y = 0; y < this.height; ++y) {
                if (this.content[y * this.width + x] <= threshold) {
                    free_line = false;
                    break;
                }
            }
            if (free_line) {
                xs.add(x);
            }
        }

        int yprev = 0;

        int xprev = 0;
        for (int i = 0; i < ys.size(); ++i) {
            int ycur = ys.get(i);


            xprev = 0;
            for (int j = 0; j < xs.size(); ++j) {
                int xcur = xs.get(j);

                images.add(this.getRectangle(xprev, yprev, xcur, ycur));
                xprev = xcur;
            }
            images.add(this.getRectangle(xprev, yprev, this.width, ycur));

            yprev = ycur;
        }
        images.add(this.getRectangle(xprev, yprev, this.width, this.height));

        return images;
    }

    public AsciiImage resize(int width, int height) {
        // algorithm source http://www.cplusplus.com/forum/general/2615/

        double[] newData = new double[width * height];

        double scaleWidth =  (double)width / (double)this.width;
        double scaleHeight = (double)height / (double)this.height;

        for(int cy = 0; cy < height; cy++) {
            for(int cx = 0; cx < width; cx++) {
                int pixel = (cy * (width)) + (cx);
                int nearestMatch =  (((int)(cy / scaleHeight) * (this.width)) + ((int)(cx / scaleWidth)) );

                newData[pixel] =  this.content[nearestMatch];
            }
        }

        AsciiImage newImage = new AsciiImage();
        newImage.updateImage(newData, width, height);
        return newImage;
    }

    public AsciiImage scale(double widthScale, double heightScale) {
        return this.resize((int) (this.width * widthScale), (int) (this.height * heightScale));
    }

    public AsciiImage scale(double k) {
        return this.scale(k, k);
    }

    public BufferedImage convertToImage() throws IOException {
        int[] pixels = new int[this.width * this.height];
        int len = this.width * this.height;

        for (int i = 0; i < len; ++i) {
            int pixel = (int) (this.content[i] * 255);
            pixels[i] = pixel << 16 | pixel << 8 | pixel;
        }

        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, this.width, this.height, pixels, 0, this.width);
        return image;
    }
}
