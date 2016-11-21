package com.example.perecastor.mosquitofinder10;

import android.app.Application;

/**
 * Created by Père Castor on 18/06/2016.
 */
public class LoopBoolean {

    //Instance Creation
    private static LoopBoolean mInstance = null;

    //Attributs
    private boolean loopboolean = true;
    private String loopId = "None so Error";

    //Getter and Setter
    public boolean getLoopBoolean(){
        return loopboolean;
    }

    public void setLoopboolean(boolean b){
        this.loopboolean = b;
    }

    public String getLoopId() {
        return loopId;
    }

    public void setLoopId(String s) {
        this.loopId = s;
    }

    protected LoopBoolean() {}

    public static synchronized LoopBoolean getmInstance(){
        if (null == mInstance){
            mInstance = new LoopBoolean();
        }
        return mInstance;
    }

}
