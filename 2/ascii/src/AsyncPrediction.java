public class AsyncPrediction implements Runnable {
    private Predictor predictor;
    private AsciiImage image;
    private int prediction = -1;
    private long delay = 0;

    public AsyncPrediction(Predictor predictor, AsciiImage image, int delay) {
        this.predictor = predictor;
        this.image = image;
        this.delay = delay;
    }


    public AsyncPrediction(Predictor predictor, AsciiImage image) {
        this(predictor, image, 0);
    }

    public void run() {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (java.lang.InterruptedException e) {
                System.out.println("Kek\n");
            }
        }
        prediction = predictor.predict(image);
    }

    public int getPrediction() {
        return prediction;
    }
}
