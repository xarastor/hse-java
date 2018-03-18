import junit.framework.TestCase;

public class TestAsciiImage extends TestCase {

    private AsciiImage asciiImage = new AsciiImage();

    public void testInvert() {
        double[] content = new double[] {0.4, 0.3, 0.2, 0.8};
        char[] palette = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        asciiImage = new AsciiImage(content, 2, 2, palette);
        String result = asciiImage.invert().getAsciiImage();
        assertEquals("43\n28\n", result);

        asciiImage = asciiImage.invert().invert();
        assertEquals((new AsciiImage(content, 2, 2, palette)).getAsciiImage(), asciiImage.getAsciiImage());
    }

    public void testGetSize() {
        double[] content = new double[] {0.4, 0.3, 0.2, 0.8};
        char[] palette = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        asciiImage = new AsciiImage(content, 2, 2, palette);
        assertEquals(2, asciiImage.getHeight());
        assertEquals(2, asciiImage.getWidth());

        asciiImage = new AsciiImage(content, 4, 1, palette);
        assertEquals(1, asciiImage.getHeight());
        assertEquals(4, asciiImage.getWidth());
    }

    public void testGetRectangle() {
        double[] content = new double[] {0.4, 0.3, 0.2, 0.8};
        char[] palette = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        asciiImage = new AsciiImage(content, 2, 2, palette);

        AsciiImage result = asciiImage.getRectangle(0, 0, 2, 2);
        assertEquals(asciiImage.getAsciiImage(), result.getAsciiImage());

        result = asciiImage.getRectangle(0, 0, 1, 2);
        assertEquals(new AsciiImage(new double[]{0.4, 0.2}, 1, 2, palette).getAsciiImage(), result.getAsciiImage());

        result = asciiImage.getRectangle(1, 1, 2, 2);
        assertEquals(new AsciiImage(new double[]{0.8}, 1, 1, palette).getAsciiImage(), result.getAsciiImage());

        try {
            asciiImage.getRectangle(1, 1, 3, 2);
            assertEquals(false, true);
        } catch (Exception e) {
            assertEquals(true, true);
        }
    }

    public void testResize() {
        double[] content = new double[] {0.4, 0.3, 0.2, 0.8};
        char[] palette = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        asciiImage = new AsciiImage(content, 2, 2, palette);

        asciiImage = asciiImage.resize(2, 2);
        assertEquals(2, asciiImage.getWidth());
        assertEquals(2, asciiImage.getHeight());

        asciiImage = asciiImage.resize(4, 4);
        assertEquals(4, asciiImage.getHeight());
        assertEquals(4, asciiImage.getWidth());
        assertEquals(asciiImage.getAsciiImage(), new AsciiImage(
                        new double[] {
                        0.4, 0.4, 0.3, 0.3,
                        0.4, 0.4, 0.3, 0.3,
                        0.2, 0.2, 0.8, 0.8,
                        0.2, 0.2, 0.8, 0.8}, 4, 4, palette).getAsciiImage());
    }

    public void testSetAsciiPalette() {
        double[] content = new double[] {0.4, 0.3, 0.2, 0.8};
        char[] palette = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        asciiImage = new AsciiImage(content, 2, 2, palette);

        asciiImage.setPalette(new char[] {'0', '1'});
        assertEquals("00\n01\n", asciiImage.getAsciiImage());

        asciiImage.setPalette(new char[] {'3'});
        assertEquals("33\n33\n", asciiImage.getAsciiImage());
    }

    public void testUpdate() {
        double[] content = new double[] {0.4, 0.3, 0.2, 0.8};
        asciiImage = new AsciiImage();

        try {
            asciiImage.updateImage(content, 2, 2);
            assertEquals(true, true);
        } catch (Exception e) {
            assertEquals(true, false);
        }

        try {
            asciiImage.updateImage(content, 2, 3);
            assertEquals(false, true);
        }
        catch (Exception e) {
            assertEquals(true, true);
        }
    }
}
