import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Math;
import java.util.Vector;


public final class main {
    boolean negative;
    float min = 1.0f / 255.0f;
    boolean invert = false;
    char[] ascii_values = "   ...',;:clodxkO0KXNWM".toCharArray(); // {' ', '.', '*', '#'};

    public String convert(final BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        StringBuilder ascii = new StringBuilder((width + 1) * height);

        float t = 0.1f; // threshold
        float i = 1.0f - t;
        int colr = 0;
        int highl = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color pixel = new Color(
                    image.getRGB(x, y)); // maybe it wont work with gray
                // System.out.println(image.getColorModel());
                // System.out.println(image.);
                double R = (double)pixel.getRed() / 255.0;
                double G = (double)pixel.getGreen() / 255.0;
                double B = (double)pixel.getBlue() / 255.0;

                double Y = (R + G + B) / 3;

                int pos = (int)Math.round((ascii_values.length * (!invert ? 1 - Y : Y)));
                char ch = ascii_values[pos];

                /*

                                // ANSI highlite, only use in grayscale
                                if ( Y>=0.95f && R<min && G<min && B<min ) highl
                   = 1; // ANSI highlite

                                boolean convert_grayscale = false; // TODO: add
                   available true value
                                if ( !convert_grayscale ) {
                                    if ( R-t>G && R-t>B )            colr = 31;
                   // red
                                    else if ( G-t>R && G-t>B )            colr =
                   32; // green
                                    else if ( R-t>B && G-t>B && R+G>i )   colr =
                   33; // yellow
                                    else if ( B-t>R && B-t>G && Y<0.95f ) colr =
                   34; // blue
                                    else if ( R-t>G && B-t>G && R+B>i )   colr =
                   35; // magenta
                                    else if ( G-t>R && B-t>R && B+G>i )   colr =
                   36; // cyan
                                    else if ( R+G+B>=3.0f*Y )             colr =
                   37; // white
                                } else { // not available yet
                                    if ( Y>=0.7f ) { highl=1; colr = 37; }
                                }

                                colr = 0; // TODO: add available true value
                                if ( !colr ) {
                                    if ( !highl ) fprintf(f, "%c", ch);
                                    else          fprintf(f, "%c[1m%c%c[0m", 27,
                   ch, 27);
                                } else {
                                    if ( colorfill ) colr += 10;          // set
                   to ANSI background color
                                    fprintf(f, "%c[%dm%c", 27, colr, ch); //
                   ANSI color
                                    fprintf(f, "%c[0m", 27);              //
                   ANSI reset
                                }

                                */
                ascii.append(ch);
            }
            ascii.append("\n");
        }
        return ascii.toString();
    }

    public static void main(String[] args) {
        File f = new File("/home/vladimir/tmp/img3.png");

        try {
            AsciiImage image = new AsciiImage(f, AsciiImage.default_palette);

            image = image.getRectangle(image.getWidth(), 0, 0, image.getHeight());

            final String ascii = image.getAsciiImage();



            Vector<AsciiImage> subs = image.splitByThreshold(0.9);
            for (int i = 0; i < subs.size(); ++i) {
                System.out.println(subs.get(i).getAsciiImage());
            }
            // BufferedImage realImage = image.convertToImage();
            // ImageIO.write(realImage, "png", new File("/home/vladimir/tmp/img5.png"));
            System.out.println(ascii);
            System.exit(0);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }
}
