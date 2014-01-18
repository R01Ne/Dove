/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Button;
import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niklas
 */
public class DemoKeyListenerTest {
    public DemoKeyListenerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testKeyListener() {
        DemoKeyListener demoKeyListener = new DemoKeyListener();
       
        assertEquals(0, demoKeyListener.getKeyDirectionAngle());
        assertEquals(false, demoKeyListener.getMoveState());
        
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_W));
        
        assertEquals(0, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_W));
        
        assertEquals(false, demoKeyListener.getMoveState());
        
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_W));
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_S));
        
        assertEquals(0, demoKeyListener.getKeyDirectionAngle());
        assertEquals(false, demoKeyListener.getMoveState());
        
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_W));
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_A));
        
        assertEquals(135, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_S));
        
        assertEquals(90, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_W));
        
        assertEquals(45, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_A));
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_D));
        
        assertEquals(-45, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_W));
        
        assertEquals(-90, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyPressed(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_S));
        
        assertEquals(-135, demoKeyListener.getKeyDirectionAngle());
        assertEquals(true, demoKeyListener.getMoveState());
        
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_D));
        demoKeyListener.keyReleased(new KeyEvent(new Button(), 1, 1, 1, KeyEvent.VK_S));
        
        assertEquals(0, demoKeyListener.getKeyDirectionAngle());
        assertEquals(false, demoKeyListener.getMoveState());
    }
}
