package com.jyt.baseapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.WorkAdapter;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.itemDecoration.RecycleViewDivider;
import com.jyt.baseapp.model.ManeuverModel;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.CircleImageView;
import com.jyt.baseapp.view.widget.JumpItem;
import com.jyt.baseapp.view.widget.MapSelector;
import com.jyt.baseapp.view.widget.MyDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddPersonActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.civ_pic)
    CircleImageView mCivPic;
    @BindView(R.id.rl_upload)
    RelativeLayout mRlUpload;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.jump_work)
    JumpItem mJumpWork;
    @BindView(R.id.jump_city)
    JumpItem mJumpCity;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.rl_parent)
    RelativeLayout mRlParent;
    private File pictureFile;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private int mtotalWidth;


    private MyDialog mDialog_city;
    private MyDialog mDialog_work;
    private ManeuverModel mManeuverModel;
    private MapModel mMapModel;
    private MapBean mMapBean;
    public StringBuilder SPCA;//省市区
    private WorkAdapter mWorkAdapter;
    private List<WorkBean> mWorkList;
    private boolean isUpload;
    public String SProvince="";//省
    public String SCity="";//市
    public String SArea="";//区
    private String SName="";//姓名
    private String STel="";
    private String SWork="";//工种
    private String SWorkID="";//工种ID

    private MapSelector mCitySelector;
    private RecyclerView mRv_work;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_person;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initFile();
        initDialog();
        initData();
        initListener();
    }

    private void init(){
        setTextTitle("添加机动人员");
        mManeuverModel=new ManeuverModel();
        mWorkAdapter = new WorkAdapter();
        mWorkList =new ArrayList<>();
        mMapModel =new MapModel();
        WindowManager wm = (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth = wm.getDefaultDisplay().getWidth();
        mMapBean=new MapBean();
        SPCA =new StringBuilder();
    }

    /**
     * 创建pic文件
     */
    private void initFile() {
        pictureFile = new File(getCacheDir(), "pic.jpg");
        if (!pictureFile.exists()) {
            try {
                pictureFile.getParentFile().mkdirs();
                pictureFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initDialog() {
        mDialog_city=new MyDialog(this,R.layout.pop_city);
        View view_city = mDialog_city.getView();
        mCitySelector = (MapSelector) view_city.findViewById(R.id.selector_city);
        mCitySelector.setHideDeleteIV(true);
        mCitySelector.getLayoutParams().width = (int) (mtotalWidth * 0.8);
        mCitySelector.requestLayout();
        mCitySelector.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID, final String ProvinceName) {
                mMapModel.getCityAreaData(ProvinceID, new MapModel.onResultCityListener() {
                    @Override
                    public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                        if (isSuccess){
                            mMapBean.mCities=data;
                            mCitySelector.notifyData(mMapBean);
                            SProvince=ProvinceName;
                        }
                    }
                });

            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {
                SPCA.setLength(0);
                SPCA.append(SProvince+CityName+AreaName);
                mJumpCity.setNext(false, SPCA.toString());
                SCity=CityName;
                SArea=AreaName;
                isCanUpLoad();
                mDialog_city.dismiss();
            }

            @Override
            public void onClickBack() {

            }
        });

        mDialog_work=new MyDialog(this,R.layout.pop_work);
        View view_work=mDialog_work.getView();
        LinearLayout ll= (LinearLayout) view_work.findViewById(R.id.ll_work);
        mRv_work = (RecyclerView) view_work.findViewById(R.id.rv_work);
        ll.getLayoutParams().width=(int) (mtotalWidth * 0.8);
        ll.requestLayout();
    }

    private void initData() {
        mMapModel.getProvinceData(new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.Province> data) {
                if (isSuccess){
                    mMapBean.mProvinces=data;
                    SProvince=mMapBean.mProvinces.get(0).ProvinceName;
                    mMapBean.mProvinces.get(0).isCheckProvince=true;
                    mCitySelector.setProvinceAdapter(mMapBean, AddPersonActivity.this);
                }
            }
        });

        mMapModel.getCityAreaData(3, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                if (isSuccess){
                    mMapBean.mCities=data;
                    mCitySelector.setCityAdapter(mMapBean, AddPersonActivity.this);
                }
            }
        });

        mManeuverModel.getAllWorkType(new ManeuverModel.OngetAllWorkTypeListener() {
            @Override
            public void Result(boolean isSuccess, List<WorkBean> data) {
                if (isSuccess) {
                    mWorkList = data;
                    mWorkAdapter.notifyData(mWorkList);
                }
            }
        });

        mRv_work.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRv_work.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        mRv_work.setAdapter(mWorkAdapter);

    }

    private void initListener(){
        mJumpCity.setOnClickListener(this);
        mJumpWork.setOnClickListener(this);
        mCivPic.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mWorkAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //点击切换选中的工种颜色
                for (int i = 0; i <mWorkList.size() ; i++) {
                    if (i==holder.getPosition()){
                        mWorkList.get(i).setCheck(true);
                        SWork=mWorkList.get(i).getType();
                        SWorkID=mWorkList.get(i).getId();
                        mJumpWork.setNext(false,SWork);
                        isCanUpLoad();
                        continue;
                    }
                    mWorkList.get(i).setCheck(false);
                }
                mWorkAdapter.notifyData(mWorkList);

                mDialog_work.dismiss();
            }
        });

        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isCanUpLoad();
            }
        });

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isCanUpLoad();
            }
        });

    }




    /**
     * 按下头像的操作
     */
    public void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 从相册返回图片的相关处理
     */
    private void Logigallery(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            crop(uri);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                Logigallery(data);
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    savePicture(bitmap, pictureFile);
                }
                break;
        }
    }


    /*
    * 剪切图片
    */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    //保存图片到本地,以便今后显示头像
    public void savePicture(Bitmap bitmap, File picture) {
        if (picture.exists()) {
            //删除上一张头像
            picture.delete();
        }
        BufferedOutputStream ops = null;
        try {
            ops = new BufferedOutputStream(new FileOutputStream(picture));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ops);
            Glide.with(AddPersonActivity.this)
                    .load(picture)
                    .into(mCivPic);
            ops.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ops != null) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jump_city:
                if (mDialog_city.isShowing()){
                    mDialog_city.dismiss();
                }else {
                    mDialog_city.show();
                }
                break;
            case R.id.jump_work:
                if (mDialog_work.isShowing()){
                    mDialog_work.dismiss();
                }else {
                    mDialog_work.show();
                }
                break;
            case R.id.civ_pic:
                gallery();
                break;
            case R.id.btn_submit:
                if (!isUpload){
                    BaseUtil.makeText("参数缺失");
                    return;
                }
                mManeuverModel.addManeuver("", SName, STel, SWorkID, SProvince, SCity, SCity, new ManeuverModel.OnaddManeuverListener() {
                    @Override
                    public void Result(boolean isSuccess) {
                        if (isSuccess){
                            BaseUtil.makeText("新增机动人员成功");
                            finish();
                        }
                    }
                });

                break;


            default:
                break;
        }
    }

    private void isCanUpLoad(){
        SName=mEtName.getText().toString();
        STel=mEtPhone.getText().toString();
//        pictureFile!=null
//                &&
        if ( SName.length()>0
                && STel.length()>0
                && SWorkID.length()>0
                && SProvince.length()>0
                && SCity.length()>0
                && SArea.length()>0){
            mBtnSubmit.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
            isUpload=true;
        }else {
            mBtnSubmit.setBackground(getResources().getDrawable(R.drawable. btn_add_off));
            isUpload=false;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictureFile.delete();
    }


}
