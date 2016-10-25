package com.example.perecastor.mosquitofinder10;

import android.app.Application;

/**
 * Created by Père Castor on 18/06/2016.
 */
public class LoopBoolean {

    private static LoopBoolean mInstance = null;

    private boolean loopboolean = true;

    public boolean getLoopBoolean(){
        return loopboolean;
    }

    public void setLoopboolean(boolean b){
        this.loopboolean = b;
    }

    protected LoopBoolean() {}

    public static synchronized LoopBoolean getmInstance(){
        if (null == mInstance){
            mInstance = new LoopBoolean();
        }
        return mInstance;
    }

}
