
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Predictor {
    Map<Integer, AsciiImage> map;
    public Predictor(Map<Integer, AsciiImage> map) {
        this.map = map;
    }

    public int predict(AsciiImage image) {
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
