package com.alex.photopicker.utils;

import android.os.Environment;

import java.io.File;

/**
 * copywrite 2015-2020 金地物业
 * 不能修改和删除上面的版权声明
 * 此代码属于信息与社区平台管理部门编写，在未经允许的情况下不得传播复制
 * 类描述:
 * ${CURSOR}
 * 2017-12-18 18:54
 * zhangchuanjia
 */


class SystemConfig {
    public static final String BASE_CACHE_DIR = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + "EnjoyLink";
    public static final String FILE_IMG_CASH_PATH = BASE_CACHE_DIR + "/cache/";
    public static final String FILE_NEW_IMG_CASH_PATH = BASE_CACHE_DIR + "/imageCache/";
    public static final String FILE_IMG_UPLOAD_PATH = BASE_CACHE_DIR + "/upload/";
}
