import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Math;

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

    public String getImage(){
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
            int j = y * height + x1;
            for (int x = x1; x < x2; ++x, ++i, ++j) {
                newImageContent[i] = this.content[j];
            }
        }

        newImage.updateImage(newImageContent, newImageWidth, newImageHeight);

        return newImage;
    }


}
