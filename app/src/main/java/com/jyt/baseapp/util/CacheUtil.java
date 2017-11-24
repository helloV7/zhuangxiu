package com.jyt.baseapp.util;

import com.jyt.baseapp.bean.SearchBean;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinWei on 2017/11/3 15:10
 */
public class CacheUtil {
    private static final String KEY_PSD = "KEY_SEARCH_HISTORY";

    public List<SearchBean> getPsd(){
        List<SearchBean> onj= Hawk.get(KEY_PSD);
        if (onj==null){
            onj = new ArrayList<>();
        }
        return onj;
    }
    public void setPsd(SearchBean bean){
        List<SearchBean> onj=getPsd();
        onj.add(bean);
        Hawk.put(KEY_PSD,onj);
    }

    public void clearData(){
        Hawk.deleteAll();
    }


}
