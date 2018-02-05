import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public final class main {

    public static void main(String[] args) {
        File f = new File("/home/vladimir/tmp/img4.png");

        try {
            AsciiImage image = new AsciiImage(f, AsciiImage.default_palette);

            // image = image.getRectangle(image.getWidth(), 0, 0,
            // image.getHeight());
            // image = image.downscale(100, 100);

            // split by lines and resize
            Vector<AsciiImage> subs = image.splitByThreshold(0.95);
            Map<Integer, AsciiImage> map = new HashMap<>();
            int real_i = 1;
            for (AsciiImage cur : subs) {
                if (cur.getWidth() < 2 || cur.getHeight() < 2)
                    continue;
                cur = cur.resize(50, 50);

                String filename =
                    "/home/vladimir/tmp/digits/" + real_i + ".png";

                System.out.println(filename);
                System.out.println(AsciiImage.distance(cur, cur));
                System.out.println(cur.getAsciiImage());

                ImageIO.write(cur.convertToImage(), "png", new File(filename));

                map.put(real_i, cur);

                real_i = (real_i + 1) % 10;
            }

            // print as image
            // BufferedImage realImage = image.resize(50, 50).convertToImage();
            BufferedImage realImage = image.scale(.5).convertToImage();
            ImageIO.write(realImage, "png",
                          new File("/home/vladimir/tmp/img5.png"));

            // print full ascii image
            final String ascii = image.resize(100, 100).invert().getAsciiImage();
            System.out.println(ascii);

            /*
            Predictor p = new Predictor(map);

            AsciiImage pimage =
                new AsciiImage(new File("/home/vladimir/tmp/img9.jpg"),
                               AsciiImage.default_palette)
                    .resize(50, 50);

            int value = p.predict(pimage);
            System.out.println(value);
            System.out.println(pimage.getAsciiImage());
            */

            System.exit(0);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
