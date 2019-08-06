package com.lm.baselib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.lm.baselib.base.BaseActivity;
import com.lm.baselib.constan.Constants;
import com.lm.baseutil.adapter.ImageAdapter;
import com.lm.baseutil.download.DownLoadCallBack;
import com.lm.baseutil.http.MCallBack;
import com.lm.baseutil.http.Result;
import com.lm.baseutil.util.MDownLoad;
import com.lm.baseutil.util.MToast;
import com.lm.baseutil.util.MUpdate;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {


    @BindView(R.id.login)
    Button login;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.mReclclerview)
    RecyclerView mReclclerview;

    private ImageAdapter mAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mReclclerview.setLayoutManager(new GridLayoutManager(this,4));
        mAdapter=new ImageAdapter(selectList,9);
        mReclclerview.setAdapter(mAdapter);

    }

    @Override
    public void loadData() {

    }


    @SuppressLint("CheckResult")
    private void clearImgFile(){
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private ImageAdapter.onAddPicClickListener onAddPicClickListener = new ImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(MainActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(3)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE )// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(false)// 是否可预览视频
                        .enablePreviewAudio(false) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .enableCrop(false)// 是否裁剪
                        .compress(false)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }

    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    mAdapter.setNewData(selectList);

                    break;
            }
        }
    }


    @OnClick({R.id.login, R.id.update})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
//                doLogin("ls", "123456");
                getData();

                break;
            case R.id.update:
                doUpdate();
//                MGlide.loadWithCircle(this,"https://www.krtimg.com/pic?file=http://img.ivsky.com/img/tupian/pre/201709/22/lugu_lake-001.jpg&param=20,20",img);
//                doDownloadFile();
                break;
            default:
        }
    }

    private void doDownloadFile() {
        new MDownLoad.Builder(this)
                .setUrl("http://223.84.197.214:80/group1/M00/00/02/rAA0RFrcLiuAOQUdAAAvp7JQ9Ow63.xlsx")
                .setFileName("rAA0RFrcLiuAOQUdAAAvp7JQ9Ow63.xlsx")
                .setShowDialog(true)
                .execute(new DownLoadCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        String f = file.getPath();
                    }
                });

    }


    private void doUpdate() {
        MUpdate.newBuilder(this)
                .setUrl(Constants.APP_Upload)
                .setForceUpdate(true)
                .build();

//        MUpload.<Result<String>>newBuilder(this)
//                .addHeader("accessToken", spUtil.getAccessToken())
//                .addFile("file", Arrays.asList(path))
//                .addParam("dir", "all")
//                .setUrl(Constants.BASE_URL + "upload/fileUpload")
//                //是否压缩
//                .setNeedCompress(true)
//                .execute(new UploadCallBack<Result<String>>() {
//                    @Override
//                    public void onSuccess(Result<String> data) {
//                        if (data.isSuccess()) {
//                            finishUpload(data.data);
//                        }
//                    }
//                });
    }

    private void doLogin(final String username, final String password) {
        OkGo.<Result<String>>post(Constants.BASE_URL + "login")
                .params("username", username)
                .params("password", password)
                .execute(new MCallBack<Result<String>>(this) {
                    @Override
                    public void onSuccess(Response<Result<String>> response) {
                        Result<String> mResult = response.body();
                        if (mResult.isSuccess()) {
                            MToast.showToast(MainActivity.this, "登陆成功");
                        } else {
                            MToast.showToast(MainActivity.this, mResult.msg);
                        }
                    }

                    @Override
                    public void onError(Response<Result<String>> response) {
                        super.onError(response);
                    }
                });
    }

    private void getData() {
        OkGo.<Result<List<WindowInfo>>>post("http://172.30.1.24:8888/zndt-v3/api/" + "getWindowInfo")
                .execute(new MCallBack<Result<List<WindowInfo>>>(this) {
                    @Override
                    public void onSuccess(Response<Result<List<WindowInfo>>> response) {
                        Result<List<WindowInfo>> mResult = response.body();
                        if (mResult.isSuccess()) {
                            if (mResult.date == null) return;
                            for (int i = 0; i < mResult.date.size(); i++) {

                            }

                        }
                    }

                    @Override
                    public void onError(Response<Result<List<WindowInfo>>> response) {
                        super.onError(response);
                        MToast.showToast(MainActivity.this, "数据获取失败,请重试！");

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
