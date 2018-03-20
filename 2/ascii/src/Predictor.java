
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Predictor {
    private Map<Integer, AsciiImage> map;
    /*
    * Из кода не совсем понятно, зачем необходимо использование именно map
    */ 
    private int mappedWidth = -1;
    private int mappedHeight = -1;

    public Predictor() {
        mappedWidth = 0;
        mappedHeight = 0;
        map = new HashMap<>();
    }

    public Predictor(Map<Integer, AsciiImage> map) {
        this();
        updateMap(map);

    }

    public void updateMap(Map<Integer, AsciiImage> map) {
        // find width and height
        for (Map.Entry<Integer, AsciiImage> entry : map.entrySet()) {
            AsciiImage value = entry.getValue();
            int width = value.getWidth();
            int height = value.getHeight();
            if (width > mappedWidth)
                mappedWidth = width;
            if (height > mappedHeight)
                mappedHeight = height;
            /*
            * Можно переписать как mappedWidth = max(mappedWidth, width) тогда будет более понятен код
            */
        }

        // save resized images
        for (Map.Entry<Integer, AsciiImage> entry : map.entrySet()) {
            this.map.put(
                    entry.getKey(),
                    entry.getValue().resize(mappedWidth, mappedWidth));
        }
    }

    public int predict(AsciiImage image) {
        if (image.getWidth() != mappedHeight || image.getHeight() != mappedHeight)
            image = image.resize(mappedWidth, mappedHeight);

        double minDistance = Float.POSITIVE_INFINITY;
        int minKey = 0;
        for (Map.Entry<Integer, AsciiImage> entry : map.entrySet()) {
            AsciiImage value = entry.getValue();

            double distance = AsciiImage.distance(value, image);
            if (distance < minDistance) {
                minDistance = distance;
                minKey = entry.getKey();
            }
        }

        return minKey;
    }
}
