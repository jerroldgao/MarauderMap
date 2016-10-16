import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
/**
 * Created by Yirugao on 10/16/16.
 */
public class ImageProcessDemo extends Component{
    BufferedImage img;
    Mat resizeImage;
    private static final int RESIZEWIDTH = 640;
    private static final int RESIZEHEIGHT = 480;
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public ImageProcessDemo() {
        try {
            img = ImageIO.read(new File("Floor_Plan.jpg"));
            byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer())
                    .getData();
            Mat matImg = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
            matImg.put(0, 0, pixels);

            resizeImage = new Mat();
            Size sz = new Size(RESIZEWIDTH, RESIZEHEIGHT);
            Imgproc.resize(matImg, resizeImage, sz);

            byte[] data = new byte[RESIZEWIDTH * RESIZEHEIGHT * (int)resizeImage.elemSize()];
            int type;
            resizeImage.get(0, 0, data);

            if(resizeImage.channels() == 1)
                type = BufferedImage.TYPE_BYTE_GRAY;
            else
                type = BufferedImage.TYPE_3BYTE_BGR;

            img = new BufferedImage(RESIZEWIDTH, RESIZEHEIGHT, type);

            img.getRaster().setDataElements(0, 0, RESIZEWIDTH, RESIZEHEIGHT, data);
            for(int y = 0; y<img.getHeight();y++){
                for(int x = 0; x <img.getWidth(); x++){
                    if (img.getRGB(x,y)==-1){
                        System.out.printf("%-5s"," ");
                    }else{
                        System.out.printf("%-5s",1);
                    }

                }
                System.out.println();
            }

        } catch (IOException e) {
        }

    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(100,100);
        } else {
            return new Dimension(img.getWidth(null), img.getHeight(null));
        }
    }



    public BufferedImage resize(int width, int height, BufferedImage image){
        return null;
    }

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        JFrame f = new JFrame("Load Image Sample");

        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(new ImageProcessDemo());
        f.pack();
        f.setVisible(true);
    }

}
