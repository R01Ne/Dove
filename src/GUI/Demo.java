/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import dove.ByteVoxel;
import dove.Camera;
import dove.IntPosition;
import dove.OctTreeNode;
import dove.Tracer;
import dove.VoxelBatch;
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
    private DemoKeyListener mDemoKeyListener;
    
    public Demo(DemoKeyListener demoKeyListener) {
        mDemoKeyListener = demoKeyListener;
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
            OctTreeNode world = new OctTreeNode(8);
            //OctTreeNode.fillCube(world, new IntPosition(12,1,1), new IntPosition(14,5,5), new ByteVoxel((byte)0x78));
            //OctTreeNode.fillCube(world, new IntPosition(1,12,1), new IntPosition(2,15,5), new ByteVoxel((byte)0xA8));
            world.Insert(VoxelBatch.batch[0xa5], new IntPosition(14,4,4));
            world.Insert(VoxelBatch.batch[0xc5], new IntPosition(14,3,4));
            world.Insert(VoxelBatch.batch[0xf5], new IntPosition(14,4,3));
            world.Insert(VoxelBatch.batch[0x85], new IntPosition(14,3,3));

            int floorSize = 30;
            for(int i = -floorSize; i < floorSize; i++){
                for (int j = -floorSize; j < floorSize; j++){
                    world.Insert(VoxelBatch.batch[(j^i)&0x000000ff], new IntPosition(i,j,-1));
                    world.Insert(VoxelBatch.batch[(j^i)&0x000000ff], new IntPosition(-floorSize,i,j));
                    world.Insert(VoxelBatch.batch[(j^i)&0x000000ff], new IntPosition(floorSize,i,j));
                    world.Insert(VoxelBatch.batch[(j^i)&0x000000ff], new IntPosition(i,j,20));
                    world.Insert(VoxelBatch.batch[(j^i)&0x000000ff], new IntPosition(i,floorSize,j));
                    world.Insert(VoxelBatch.batch[(j^i)&0x000000ff], new IntPosition(i,-floorSize,j));

                }
            }

            Tracer t = new Tracer();
            t.init();
            t.setWorld(world);
            t.cam.SetBackPlaneDistance(200);

            t.cam.x = 0;
            t.cam.y = 0;
            t.cam.z = 10;
            //t.cam.horizontalAngle = Math.PI/4;
            t.cam.verticalAngle=-Math.PI/8;
            int frames = 0;
            int[] mem = new int[300 * 240];
            long start = System.currentTimeMillis();
            long end = start + 15000;
            long last = start;
            boolean runForever = true;
            
            while (last < end || runForever){
                
                readInputAndUpdateCamera(t.cam);
                
                int col = colors[frames % colors.length].getRGB();
                
                t.Render(mem,300,240);
                
                //TODO: Re-Enable this
                if (!runForever)t.cam.horizontalAngle +=Math.PI/16 ;
                
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
        
        //TODO: Change this to something better
        //TODO: Add ESC to exit
        private void readInputAndUpdateCamera(Camera cam) {
            int angle = mDemoKeyListener.getKeyDirectionAngle();
            if (mDemoKeyListener.getMoveState()) {
                switch(angle) {
                    case 0:
                        cam.x += 1;
                        break;
                    case 45:
                        cam.x += 1;
                        cam.y += 1;
                        break;
                    case 90:
                        cam.y += 1;
                        break;
                    case 135:
                        cam.y += 1;
                        cam.x -= 1;
                        break;
                    case -180:
                        cam.x -= 1;
                        break;
                    case -135:
                        cam.x -= 1;
                        cam.y -= 1;
                        break;
                    case -90:
                        cam.y -= 1;
                        break;
                    case -45:
                        cam.y -= 1;
                        cam.x += 1;
                        break;   
                }
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                
                DemoKeyListener demoKeyListener = new DemoKeyListener();
                
                JFrame jf = new JFrame();
                jf.addKeyListener(demoKeyListener);
                
                
                jf.getContentPane().add(new Demo(demoKeyListener), BorderLayout.CENTER);
                jf.setSize(300,240);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}