package com.lm.baseutil.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lm.baseutil.R;
import com.lm.baseutil.util.MGlide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author dj
 * @description 图片选择适配器
 * @Date 2019/8/6
 */
public class ImageAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private int selectMax = 9;

    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public ImageAdapter(@Nullable List<LocalMedia> data,int selectMax) {
        super(R.layout.item_child_img,data);
        this.selectMax = selectMax;
    }

    public ImageAdapter(@Nullable List<LocalMedia> data,int selectMax,onAddPicClickListener mOnAddPicClickListener) {
        super(R.layout.item_child_img,data);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        this.selectMax = selectMax;
    }


    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    @Override
    public void remove(int position) {
        super.remove(position);
    }


    @Override
    public int getItemCount() {
        if (mData.size() < selectMax) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = mData.size() == 0 ? 0 : mData.size();
        return position == size;
    }


    @Override
    protected void convert(final BaseViewHolder helper, LocalMedia item) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(helper.getLayoutPosition()) == TYPE_CAMERA) {
            helper.setImageResource(R.id.img, R.mipmap.image_add_nor)
                    .setVisible(R.id.delete, false);
        } else {
            helper.getView(R.id.delete).setVisibility(View.VISIBLE);
            helper.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = helper.getLayoutPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        remove(index);
                    }
                }
            });

            String path = "";
            if (item.isCut() && !item.isCompressed()) {
                // 裁剪过
                path = item.getCutPath();
            } else if (item.isCompressed() || (item.isCut() && item.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = item.getCompressPath();
            } else {
                // 原图
                path = item.getPath();
            }

            MGlide.loadWithPlaceHolder(mContext,path,R.mipmap.image_add_nor,
                    (ImageView) helper.getView(R.id.img));

        }



        helper.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                // 进入相册 以下是例子：不需要的api可以不写
                RxPermissions rxPermissions =new RxPermissions((Activity) mContext);
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            PictureSelector.create((Activity) mContext)
                                    .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                                    .maxSelectNum(selectMax)// 最大图片选择数量
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
                                    .selectionMedia(mData)// 是否传入已选图片
                                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        }
                    }
                });


            }
        });

    }
}
