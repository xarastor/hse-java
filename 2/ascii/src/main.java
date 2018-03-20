import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

/* Comment
* Нет использования входных параметров, то есть мы не можем никак изменить поведение программы извне кода
* Нет подсказки о пользовании программы для пользователя
*/

public final class main {

    public static void main(String[] args) {
        File f = new File("input_images/digits.png");

        try {
            AsciiImage image = new AsciiImage(f, AsciiImage.default_palette);

            // split by lines and resize
            Vector<AsciiImage> subs = image.splitByThreshold(0.95);
            /*
            * Все константы стоит выносить в отдельные переменные с осмысленными названиями (к примеру defaultTreshhold)
            */
            Map<Integer, AsciiImage> map = new HashMap<>();
            int real_i = 1;
            
            /*
            * Нарушение JCC нельзя использовать нижнее подчеркивание в названиях переменных
            */
            for (AsciiImage cur : subs) {
                /*
                * "cur" - это плохое название для перменной, так как не является осмысленным.
                * Лучше назвать эту переменную currentImage, тогда далее по контексту будет понятно, что обозначает эта переменная
                */
                if (cur.getWidth() < 2 || cur.getHeight() < 2)
                    continue;
                cur = cur.resize(50, 50);

                String filename = "output_images/digits/" + real_i + ".png";

                System.out.println(filename);
                System.out.println(AsciiImage.distance(cur, cur));
                System.out.println(cur.getAsciiImage());

                ImageIO.write(cur.convertToImage(), "png", new File(filename));

                if (real_i == 0)
                    map.put(real_i, cur);
                else
                    map.put(real_i, cur.resize(20, 20));


                real_i = (real_i + 1) % 10;
            }

            // print as image
            // BufferedImage realImage = image.resize(50, 50).convertToImage();
            BufferedImage realImage = image.scale(.5).invert().convertToImage();
            ImageIO.write(realImage, "png", new File("output_images/img5.png"));

            // print full ascii image
            final String ascii = image.resize(100, 100).invert().getAsciiImage();
            System.out.println(ascii);


            Predictor predictor = new Predictor(map);

            AsciiImage pimage =
                new AsciiImage(new File("input_images/digit_test.jpg"), AsciiImage.default_palette)
                    .resize(40, 40);

            System.out.println(pimage.getAsciiImage());
            System.out.println("Predicted values: ");

            int n = 10;
            Thread[] threads = new Thread[n];
            AsyncPrediction[] predictions = new AsyncPrediction[n];
            for (int i = 0; i < n; ++i) {
                predictions[i] = new AsyncPrediction(predictor, pimage);
                threads[i] = new Thread(predictions[i]);
                threads[i].start();
            }

            for (int i = 0; i < n; ++i) {
                try {
                    threads[i].join();
                    System.out.println(predictions[i].getPrediction());
                } catch (Exception e) { 
                    /* Слишком общий Exception он отловит все исключения и будет непонятно что конкрентно произошло
                    * Если есть подозрения о возможных ошибках, то лучше отловить только их, и если возникает какое-то другое исключение
                    * то будет понятно, что необходимо поправить и изменить.
                    */
                    System.out.println("Exception in join loop");
                    return;
                }
            }

            System.exit(0);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
