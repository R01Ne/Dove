/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dove;

/**
 *
 * @author Jonas
 */
public class TracerThread extends Thread {

    private Ray[] rayPool;
    private int startIndex;
    private int endIndex;
    private OctTreeNode world;

    public TracerThread(Ray[] rayPool, int startIndex, int endIndex, OctTreeNode world) {
        this.rayPool = rayPool;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.world = world;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            rayPool[i].voxel = trace(rayPool[i]);
        }
    }

    private int abs(int a) {
        return a > 0 ? a : -a;
    }

    IntPosition delta = new IntPosition(), step = new IntPosition(), p = new IntPosition(), c = new IntPosition();
    IntPosition lastVisited = new IntPosition();

    private ByteVoxel trace(Ray r) {
        ByteVoxel ret = VoxelBatch.nullVoxels[0];

        IntPosition p0 = r.start;
        IntPosition p1 = r.finish;
        //IntPosition delta,step,p,c;

        boolean swap_xy, swap_xz;
        int drift_xy, drift_xz;
        int bitMap;
        //'steep' xy Line, make longest delta x plane  
        swap_xy = abs(p1.y - p0.y) > abs(p1.x - p0.x);

        if (swap_xy) {
            //Swap(x0, y0)
            int temp = p0.x;
            p0.x = p0.y;
            p0.y = temp;
            //Swap(x1, y1)
            temp = p1.x;
            p1.x = p1.y;
            p1.y = temp;
        }
        //do same for xz
        swap_xz = abs(p1.z - p0.z) > abs(p1.x - p0.x);

        if (swap_xz) {
            //Swap(x0, y0)
            int temp = p0.x;
            p0.x = p0.z;
            p0.z = temp;
            //Swap(x1, y1)
            temp = p1.x;
            p1.x = p1.z;
            p1.z = temp;
        }
        //delta is Length in each plane
        delta.set(
                abs(p1.x - p0.x),
                abs(p1.y - p0.y),
                abs(p1.z - p0.z));

        //drift controls when to step in 'shallow' planes
        //starting value keeps Line centred
        drift_xy = (delta.x >> 1);
        drift_xz = (delta.x >> 1);

        //direction of line
        step.set(
                p0.x > p1.x ? -1 : 1,
                p0.y > p1.y ? -1 : 1,
                p0.z > p1.z ? -1 : 1);

        //starting point
        p.Copy(p0);
        c.Copy(p0);
        lastVisited.Copy(p);

//step through longest delta (which we have swapped to x)
        //for x = x0 to x1 step step_x
        boolean positiveDirection = p0.x < p1.x;
        for (; positiveDirection ? (p.x <= p1.x) : (p.x >= p1.x); p.x += step.x) {
            if (p.differsOnLevel(lastVisited, ret.ID)) {

                c.Copy(p);
                if (swap_xz) {
                    int temp = c.x;
                    c.x = c.z;
                    c.z = temp;
                }

                if (swap_xy) {
                    int temp = c.x;
                    c.x = c.y;
                    c.y = temp;
                }
                lastVisited.Copy(p);
                ret = world.Get(c);
                if (!ret.isEmpty()) {
                    return ret;
                }
            }
            //update progress in other planes
            drift_xy -= delta.y;
            drift_xz -= delta.z;

            //step in y plane
            if (drift_xy < 0 && drift_xz >= 0) {
                p.y += step.y;
                drift_xy += delta.x;

                if (p.differsOnLevel(lastVisited, ret.ID)) {

                    c.Copy(p);
                    if (swap_xz) {
                        int temp = c.x;
                        c.x = c.z;
                        c.z = temp;
                    }

                    if (swap_xy) {
                        int temp = c.x;
                        c.x = c.y;
                        c.y = temp;
                    }
                    lastVisited.Copy(p);
                    ret = world.Get(c);
                    if (!ret.isEmpty()) {
                        return ret;
                    }
                }
            } else //same in z
            if (drift_xz < 0 && drift_xy >= 0) {
                p.z += step.z;
                drift_xz += delta.x;

                if (p.differsOnLevel(lastVisited, ret.ID)) {

                    c.Copy(p);
                    if (swap_xz) {
                        int temp = c.x;
                        c.x = c.z;
                        c.z = temp;
                    }

                    if (swap_xy) {
                        int temp = c.x;
                        c.x = c.y;
                        c.y = temp;
                    }
                    lastVisited.Copy(p);
                    ret = world.Get(c);
                    if (!ret.isEmpty()) {
                        return ret;
                    }
                }
            } else if (drift_xz < 0 && drift_xy < 0) {
                drift_xz += delta.x;
                drift_xy += delta.x;

                p.z += step.z;

                if (p.differsOnLevel(lastVisited, ret.ID)) {

                    c.Copy(p);
                    if (swap_xz) {
                        int temp = c.x;
                        c.x = c.z;
                        c.z = temp;
                    }

                    if (swap_xy) {
                        int temp = c.x;
                        c.x = c.y;
                        c.y = temp;
                    }
                    lastVisited.Copy(p);
                    ret = world.Get(c);
                    if (!ret.isEmpty()) {
                        return ret;
                    }

                }
                p.z -= step.z;
                p.y += step.y;

                if (p.differsOnLevel(lastVisited, ret.ID)) {

                    c.Copy(p);
                    if (swap_xz) {
                        int temp = c.x;
                        c.x = c.z;
                        c.z = temp;
                    }

                    if (swap_xy) {
                        int temp = c.x;
                        c.x = c.y;
                        c.y = temp;
                    }
                    lastVisited.Copy(p);
                    ret = world.Get(c);
                    if (!ret.isEmpty()) {
                        return ret;
                    }

                }

                p.z += step.z;

                if (p.differsOnLevel(lastVisited, ret.ID)) {

                    c.Copy(p);
                    if (swap_xz) {
                        int temp = c.x;
                        c.x = c.z;
                        c.z = temp;
                    }

                    if (swap_xy) {
                        int temp = c.x;
                        c.x = c.y;
                        c.y = temp;
                    }
                    lastVisited.Copy(p);
                    ret = world.Get(c);
                    if (!ret.isEmpty()) {
                        return ret;
                    }

                }
            }
        }
        return ret;
    }

    public void setWorld(OctTreeNode world) {
        this.world = world;
    }

}
