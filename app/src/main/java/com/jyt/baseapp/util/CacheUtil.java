package com.jyt.baseapp.util;

import com.orhanobut.hawk.Hawk;

import java.util.List;

/**
 * @author LinWei on 2017/11/3 15:10
 */
public class CacheUtil {
    private static final String KEY_PSD = "KEY_PSD";

    public List<String> getPsd(){
        return Hawk.get(KEY_PSD);
    }
    public void setPsd(List<String> onj){
        Hawk.put(KEY_PSD,onj);
    }


}
