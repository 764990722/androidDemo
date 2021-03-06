package com.example.android_demo.ui.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_demo.App;
import com.example.android_demo.R;
import com.example.android_demo.databinding.ActivityUpdataBinding;
import com.example.android_demo.repository.Network;
import com.example.android_demo.repository.Scheduler;
import com.example.android_demo.repository.Subs;
import com.example.android_demo.repository.entity.receive.RUserList;
import com.example.android_demo.repository.entity.submit.SUpData;
import com.example.android_demo.shared.GifSizeFilter;
import com.example.android_demo.shared.MyGlideEngine;
import com.example.android_demo.ui.base.BaseActivity;
import com.example.android_demo.ui.dialog.ReturnDialog;
import com.example.android_demo.utils.Toast;
import com.example.android_demo.utils.Utils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by: PeaceJay
 * Created date: 2021/03/30.
 * Description: ????????????-??????????????????
 */
public class UpDataActivity extends BaseActivity<ActivityUpdataBinding> {

    private static final String TAG = UpDataActivity.class.getSimpleName();
    private RUserList.ListBean bean = new RUserList.ListBean();
    private static final int REQUEST_CODE_CHOOSE = 23;//?????????????????????
    private static final int REQUEST_CODE_TAKE_PICTURE = 24;//??????
    private List<String> imgsPath = new ArrayList<>();
    private MultipartBody.Part part;


    @Override
    protected int onLayout() {
        return R.layout.activity_updata;
    }

    @Override
    protected void onObject() {
        binding.setActivity(this);
    }

    @Override
    protected void onView() {

    }

    @Override
    protected void onData() {
        bean = (RUserList.ListBean) getIntent().getSerializableExtra("userData");
        if (bean != null) {
            binding.editPhone.setText(bean.getPhone());
            binding.editName.setText(bean.getUsername());
            binding.editPassword.setText(bean.getPassword());
            if (!TextUtils.isEmpty(bean.getHerdImage())) {
                Glide.with(this)
                        .load(bean.getHerdImage())
                        .apply(new RequestOptions().centerCrop().error(R.mipmap.ic_logo))
                        .into(binding.imageView);
            }
        }
    }

    /**
     * ??????
     */
    public void sureView() {
        if (TextUtils.isEmpty(binding.editPhone.getText().toString().trim())) {
            Toast.show("??????????????????");
            return;
        }
        if (TextUtils.isEmpty(binding.editName.getText().toString().trim())) {
            Toast.show("??????????????????");
            return;
        }
        if (TextUtils.isEmpty(binding.editPassword.getText().toString().trim())) {
            Toast.show("???????????????");
            return;
        }
        if (bean == null) return;
        SUpData body = new SUpData();
        body.setId("asdas");
        body.setPhone(binding.editPhone.getText().toString().trim());
        body.setUsername(binding.editName.getText().toString().trim());
        body.setPassword(binding.editPassword.getText().toString().trim());

        if (part == null) {
            Network.api().updateUser(body)
                    .compose(Scheduler.apply())
                    .subscribe(new Subs<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            Toast.show("????????????");
                            finish();
                        }
                    });
        } else {
            Network.api().updateHerd(bean.getId(),
                    binding.editName.getText().toString().trim(),
                    binding.editPassword.getText().toString().trim(),
                    binding.editPhone.getText().toString().trim(), part)
                    .compose(Scheduler.apply())
                    .subscribe(new Subs<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            Toast.show("????????????");
                            finish();
                        }
                    });
        }

    }

    /**
     * ??????
     */
    public void delete() {
        if (bean == null) return;
        ReturnDialog.getInstance().loadDialog(this, "?????????????????????");
        ReturnDialog.getInstance().buttonPosiSetOnclick(new ReturnDialog.ButtonPositiveView() {
            @Override
            public void onclick(View view) {
                SUpData body = new SUpData();
                body.setId(bean.getId());
                body.setPhone(binding.editPhone.getText().toString().trim());
                body.setUsername(binding.editName.getText().toString().trim());
                body.setPassword(binding.editPassword.getText().toString().trim());
                Network.api().deleteUser(body)
                        .compose(Scheduler.apply())
                        .subscribe(new Subs<Object>() {
                            @Override
                            public void onSuccess(Object data) {
                                Toast.show("????????????");
                                finish();
                            }
                        });
            }
        });

    }


    public void herdImage() {
        requestPermission(Permission.Group.STORAGE, Permission.Group.CAMERA);
    }


    private void requestPermission(String[] STORAGE, String... CAMERA) {
        AndPermission.with(this)
                .runtime()
                .permission(STORAGE, CAMERA)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //??????
                        callGallery(true);
                    }
                })
                .start();
    }

    /**
     * ??????????????????
     */
    private void callGallery(boolean isPhone) {
        try {
            Matisse.from(this)
                    .choose(MimeType.ofImage(), false)
//                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//????????????????????????MimeType.allOf()
                    .maxSelectable(1)//?????????????????????9
                    .capture(isPhone)//?????????????????????????????????7.0???????????????????????????
                    .countable(true)//true:?????????????????????;false:?????????????????????
                    .addFilter(new GifSizeFilter(30, 30, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.dp120))//???????????????????????????
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//??????????????????????????????????????????
                    .thumbnailScale(0.85f)//????????????
                    .imageEngine(new MyGlideEngine())//?????????????????????Glide4?????????????????????
                    .setOnSelectedListener((uriList, pathList) -> {
                        Log.e("onSelected", "onSelected: pathList=" + pathList);
                    })
                    .showSingleMediaType(true)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .autoHideToolbarOnSingleTap(true)
                    .setOnCheckedListener(isChecked -> {
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    })
                    .captureStrategy(new CaptureStrategy(false, "com.apple.hera.fileprovider", "test"))//???????????????
                    .forResult(REQUEST_CODE_CHOOSE);
        } catch (Exception e) {
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PICTURE) {//????????????????????????
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap == null) {
                        return;
                    }
                    if (Utils.isVivo()) {//vivo?????????
                        saveBitmap(bitmap);
                    } else {
                        //???Bitmap?????????uri
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                        if (uri != null) {
                            startPhotoZoom(uri);
                        }
                    }
                }
            } else if (requestCode == REQUEST_CODE_CHOOSE) {//?????????????????????
                try {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    for (Uri imageUri : mSelected) {
                        if (Utils.isVivo()) {//vivo?????????
                            //??????uri?????????bitmap??????
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            saveBitmap(bitmap);
                        } else {
                            startPhotoZoom(imageUri);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CROP_PICTURE) {//??????????????????
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        try {
                            Bitmap photo = extras.getParcelable("data");
                            saveBitmap(photo);
                        } catch (Exception e) {
                        }
                    }
                }
                Bitmap headShot = null;
                try {
                    headShot = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(cropImageUri));
                    if (headShot != null) {
                        saveBitmap(headShot);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * ????????????????????????
     *
     * @param uri
     */
    private static final int CROP_PICTURE = 2;//????????????????????????
    //???????????????????????????Uri
    private Uri cropImageUri;

    protected void startPhotoZoom(Uri uri) {
        File CropPhoto = new File(this.getExternalCacheDir(), "user_image.jpg");
        try {
            if (CropPhoto.exists()) {
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropImageUri = Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //???????????????????????????????????????????????????Uri??????????????????
        }
        // ????????????crop=true?????????????????????Intent??????????????????VIEW?????????
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 1800);
        intent.putExtra("outputY", 1800);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    /**
     * ??????Bitmap?????????
     *
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        binding.imageView.setImageBitmap(bitmap);
        File file = saveBitmap1(bitmap);
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Log.i("okhttp", "saveBitmap: " + file);

    }

    private File saveBitmap1(Bitmap bmp) {
        //????????????
//        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/android_demo/Huancun/Image");
        //????????????
        File dir = new File(App.ins().getFilesDir() + "/android_demo/Huancun/Image");
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
//        String timeStr = TimeUtils.getCurrentTime(TimeUtils.Format_TIME13);
        //???????????????????????????????????? ?????????????????????
        File file = new File(dir, "herdImage.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }





}