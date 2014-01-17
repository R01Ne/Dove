/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import GUI.EngineFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Jonas
 */
public class Engine {
    private Camera camera;
    private OctTreeNode world;

    public Engine(Camera camera, OctTreeNode world) {
        this.camera = camera;
        this.world = world;
    }
    
    public OctTreeNode getWorld(){
        return world;
    }
    
    public static void main(String[] args){
        System.out.println("main");
        OctTreeNode world = new OctTreeNode(10);
        OctTreeNode.fillCube(world, new IntPosition(25,25,25), new IntPosition(30,30,30), new ByteVoxel((byte)34));
        Engine e = new Engine(new Camera(), world);
        EngineFrame frame = new EngineFrame(e);
        
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.println("main");
        for(int i = 0; i < 60; i++){
            System.out.println("EngineLoop");
            frame.update(frame.getGraphics());
            frame.repaint();
            try {
                Thread.sleep(1000);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EngineLoop: Thread interupted");

                break;
            }
        }
        
        
    }
    
}
