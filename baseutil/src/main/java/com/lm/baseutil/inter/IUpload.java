package com.lm.baseutil.inter;

import java.io.File;
import java.util.List;

/**
 * @author
 * @description
 * @Date 2019/3/1
 */
public interface IUpload {

    /**
     * 压缩图片的方法
     */
    void compress();

    /**
     * 上传图片
     * @param files
     */
    void doUpload(List<File> files);
}
