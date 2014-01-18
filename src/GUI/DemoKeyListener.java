/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Niklas
 */
public class DemoKeyListener implements KeyListener{  
    
    private boolean mDirectionUp = false;
    private boolean mDirectionDown = false;
    private boolean mDirectionLeft = false;
    private boolean mDirectionRight = false;
    
    private int mDirectionAngle = 0;
    
    private boolean mMoveState = false;
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                mDirectionUp = true;
                break;
            case KeyEvent.VK_S:
                mDirectionDown = true;
                break;
            case KeyEvent.VK_A:
                mDirectionLeft = true;
                break;
            case KeyEvent.VK_D:
                mDirectionRight = true;
                break;
        }
        updateDirectionState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                mDirectionUp = false;
                break;
            case KeyEvent.VK_S:
                mDirectionDown = false;
                break;
            case KeyEvent.VK_A:
                mDirectionLeft = false;
                break;
            case KeyEvent.VK_D:
                mDirectionRight = false;
                break;
        }
        updateDirectionState();
    }
    
    private void updateDirectionState() {
        mDirectionAngle = 0;
        if (mDirectionUp && mDirectionDown) {
            mMoveState = false;
        } else if (mDirectionUp) {
            mMoveState = true;
        } else if (mDirectionDown) {
            mDirectionAngle = -180;
            mMoveState = true;
        } else {
            mMoveState = false;
        }
        
        if (mDirectionLeft && mDirectionRight) {
            mMoveState |= false;
        } else if (mDirectionLeft) {
            if (mDirectionAngle == -180) {
                mDirectionAngle = 135;
            } else if (mDirectionAngle == 0 && mMoveState) {
                mDirectionAngle = 45;
            } else {
                mDirectionAngle = 90;
            }
            mMoveState = true;
        } else if (mDirectionRight) {
            if (mDirectionAngle == -180) {
                mDirectionAngle = -135;
            } else if (mDirectionAngle == 0 && mMoveState) {
                mDirectionAngle = -45;
            } else {
                mDirectionAngle = -90;
            }
            mMoveState = true;
        } else {
            
            mMoveState |= false;
        }
    }
    
    /**
     * Retrieves the angle of the key input with the camera face being zero
     * @return Key input angle
     */
    public int getKeyDirectionAngle() {
        return mDirectionAngle;
    }
    
    /**
     * Whether or not the user wants the camera to move
     * @return The move state
     */
    public boolean getMoveState() {
        return mMoveState;
    }
}
