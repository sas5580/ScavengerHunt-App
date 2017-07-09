package com.yan.sh.sh_android.engine.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.cloudinary.Cloudinary;
import com.yan.sh.sh_android.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Created by yan on 7/8/17.
 */

public class CloudinaryManager extends Manager {

    private Cloudinary cloudinary;

    public CloudinaryManager(Context context){
        Map config = new HashMap();
        config.put("cloud_name", context.getResources().getString(R.string.cloud_name));
        config.put("api_key", context.getResources().getString(R.string.api_key));
        config.put("api_secret", context.getResources().getString(R.string.api_secret));
        cloudinary = new Cloudinary(config);

        this.startup();
    }

    public void uploadPicture(final String url){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if(cloudinary == null || url == null){
                    Timber.w("Uninitialized cloudinary / bitmap");
                    return;
                }

                Map options = new HashMap();
                options.put("public_id", "test2");
                try{
                    FileInputStream is = new FileInputStream(new File(url));
                    Timber.i(cloudinary.uploader().upload(is, options).toString());
                } catch (IOException ex){
                    Timber.e(ex, "Upload error!");
                }
            }
        });
    }
}
