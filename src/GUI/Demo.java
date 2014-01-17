/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import dove.ByteVoxel;
import dove.IntPosition;
import dove.OctTreeNode;
import dove.Tracer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Jonas
 */
public class Demo extends javax.swing.JPanel {
    private Image src = null;
    
    public Demo() {
        new Worker().execute();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (src != null) g.drawImage(src, 0, 0, this);
    }
    private class Worker extends SwingWorker<Void, Image>{
        private final Color[] colors = { Color.red, Color.green, Color.blue };
        protected void process(List<Image> chunks){
            for (Image bufferedImage : chunks){
                src = bufferedImage;
                repaint();
            }
        }
        @Override
        protected Void doInBackground() throws Exception{
            OctTreeNode world = new OctTreeNode(10);
            OctTreeNode.fillCube(world, new IntPosition(32,1,1), new IntPosition(34,5,5), new ByteVoxel((byte)0x88));
            Tracer t = new Tracer();
            t.init();
            t.setWorld(world);
            t.cam.x = 0;
            t.cam.y = 0;
            t.cam.z = 0;
            //t.cam.horizontalAngle = Math.PI/4;
            int frames = 0;
            int[] mem = new int[300 * 240];
            long start = System.currentTimeMillis();
            long end = start + 15000;
            long last = start;
            while (last < end){
                int col = colors[frames % colors.length].getRGB();
                t.Render(mem,300,240);
                t.cam.horizontalAngle +=Math.PI/16 ;
                
                t.cam.SetBackPlaneDistance(40);
//for (int y = 0; y < 768; y++)
                //    for (int x = 0; x < 1024; x++)
                //        mem[x + y * 1024] = col;
                Image img = createImage(new MemoryImageSource(300,240, mem, 0, 300));
                BufferedImage bi = new BufferedImage(300, 240, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bi.createGraphics();
                g2.drawImage(img, 0, 0, null);
                g2.dispose();
                publish(bi);
                last = System.currentTimeMillis();
                frames++;
            }
            System.err.println("Frames = " + frames + ", fps = " + ((double) frames / (last - start) * 1000));
            return null;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                JFrame jf = new JFrame();
                jf.getContentPane().add(new Demo(), BorderLayout.CENTER);
                jf.setSize(300,240);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}