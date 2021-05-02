
package com.tech.twe_tutorside.activity;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.utils.FileUtil;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PoliceVerifyActivity extends AppCompatActivity {

   private LinearLayout LL_polish_vrfy_img;
   private ImageView img_certif;
   private ImageView img_crt;
    private File Img_certificate_polish, compressedImage, compressActualFile;

    private ProgressBar progressBar;
    private SessionManager sessionManager;

    private String Teach_distance="";
    private String TimeZone="";
    private String Where_to_teach="";
    private String About ="";
    private String Dob ="";
    private String Education ="";
    private String Language ="";
    private String Affilations ="";
    private String Awards ="";

    private String Monday="";
    private  String Tuesday="";
    private  String Wednesday="";
    private  String Thursday="";
    private  String Friday="";
    private String Saturday="";
    private String Sunday="";

    private String Subject="";
    private String Location ="";
    private String Gender ="";
    private String Certification ="";
    private String Per_hour_payment ="";
    private String Full_course_time ="";

    private String Per_hour_payment_grp ="";
    private String Full_course_time_grp ="";
    String result="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}

        setContentView(R.layout.fragment_police_verify);

        LL_polish_vrfy_img=findViewById(R.id.LL_polish_vrfy_img);
        img_crt=findViewById(R.id.img_crt);
        img_certif=findViewById(R.id.img_certif);
        progressBar=findViewById(R.id.progressBar);
        sessionManager = new SessionManager(PoliceVerifyActivity.this);

        LL_polish_vrfy_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(PoliceVerifyActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(PoliceVerifyActivity.this);
                                    startActivityForResult(intent, 1);
                                } else {
                                    showSettingDialogue();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });


        Intent intent=getIntent();

        if(intent !=null)
        {
            About=intent.getStringExtra("about").toString();
            Dob=intent.getStringExtra("dob").toString();
            Education=intent.getStringExtra("education").toString();
            Language=intent.getStringExtra("language").toString();
            Affilations=intent.getStringExtra("Affilations").toString();
            Awards=intent.getStringExtra("Awards").toString();
            Where_to_teach=intent.getStringExtra("where_to_teach").toString();
            Teach_distance=intent.getStringExtra("teach_distance").toString();
            Location=intent.getStringExtra("location").toString();
            Gender=intent.getStringExtra("gender").toString();
            Certification=intent.getStringExtra("certification").toString();
            TimeZone=intent.getStringExtra("TimeZone").toString();
  /*
            Monday=intent.getStringExtra("mondayTime").toString();
            Tuesday=intent.getStringExtra("tuesday").toString();
            Wednesday=intent.getStringExtra("wednesday").toString();
            Thursday=intent.getStringExtra("thursday").toString();
            Friday=intent.getStringExtra("friday").toString();
            Saturday=intent.getStringExtra("saturday").toString();
            Sunday=intent.getStringExtra("sunday").toString();*/
            Subject=intent.getStringExtra("subject").toString();
            Per_hour_payment=intent.getStringExtra("per_hour_payment").toString();
            Full_course_time=intent.getStringExtra("full_course_time").toString();

            Per_hour_payment_grp=intent.getStringExtra("per_hour_payment_grp").toString();
            Full_course_time_grp=intent.getStringExtra("full_course_time_grp").toString();
        }

    }

    public void verifyInit(View view) {

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            tutorAddDetailsapi();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
       // startActivity(new Intent(this,HomeActvity.class));
    }

    public void policeVerificatioInit(View view) {
        init(view);
    }
    private void init(View view){
        // custom dialog
        final Dialog dialog =  new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.custom);
        dialog.setCancelable(false);
        LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.police_AgreeId);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_crt.setImageResource(R.drawable.check_one);
                dialog.dismiss();
                //Toast.makeText(PoliceVerifyActivity.this,"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        ImageView imageButton = (ImageView) dialog.findViewById(R.id.dialog_cancel_action);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
               Uri resultUri = result.getUri();
                try {
                  Bitmap  bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    // Glide.with(this).load(bitmap).circleCrop().into(img_userProfile);
                    Img_certificate_polish = FileUtil.from(this, resultUri);
                    img_certif.setImageResource(R.drawable.check_one);
                   // isProduct = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    compressedImage = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(Img_certificate_polish);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        }



    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PoliceVerifyActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSetting();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

    private void openSetting() {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }



    private void tutorAddDetailsapi() {

        //String Userid=Preference.get(PoliceVerifyActivity.this,Preference.KEY_USER_ID);
        String Userid="70";
        String Tutorcategory=Preference.get(PoliceVerifyActivity.this,Preference.KEY_tutor_category_id);
        String Tutorsubcategory=Preference.get(PoliceVerifyActivity.this,Preference.KEY_tutor_category_sub_id);
        String TutorsubjECTiD= Preference.get(PoliceVerifyActivity.this,Preference.KEY_tutor_category_subJECT_id);
        String Location_id= Preference.get(PoliceVerifyActivity.this,Preference.KEY_location_id);

        MultipartBody.Part imgFile = null;
        MultipartBody.Part imgFileOne = null;
        MultipartBody.Part imgFileTwo = null;
        MultipartBody.Part imgFileThree = null;
        MultipartBody.Part imgFilefour = null;
        MultipartBody.Part imgFilefive = null;
        if (FillDetailsActivity.UserProfile_img == null) {

        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), FillDetailsActivity.UserProfile_img);
            imgFile = MultipartBody.Part.createFormData("profile_image", FillDetailsActivity.UserProfile_img.getName(), requestFileOne);

            RequestBody requestFileOne1 = RequestBody.create(MediaType.parse("image/*"), FillDetailsActivity.imageFilePath_certificate_one);
            imgFileOne = MultipartBody.Part.createFormData("certifiateimage", FillDetailsActivity.imageFilePath_certificate_one.getName(), requestFileOne1);

            RequestBody requestFileOne2 = RequestBody.create(MediaType.parse("image/*"), FillDetailsActivity.imageFilePath_certificate_two);
            imgFileTwo = MultipartBody.Part.createFormData("certifiateimage2", FillDetailsActivity.imageFilePath_certificate_two.getName(), requestFileOne2);

            RequestBody requestFileOne3 = RequestBody.create(MediaType.parse("image/*"), FillDetailsActivity.imageFilePath_certificate_three);
            imgFileThree = MultipartBody.Part.createFormData("certifiateimage3", FillDetailsActivity.imageFilePath_certificate_three.getName(), requestFileOne3);

            RequestBody requestFileOne4 = RequestBody.create(MediaType.parse("image/*"), FillDetailsActivity.imageFilePath_certificate_four);
            imgFilefour = MultipartBody.Part.createFormData("certifiateimage4", FillDetailsActivity.imageFilePath_certificate_four.getName(), requestFileOne4);

            RequestBody requestFileOne5 = RequestBody.create(MediaType.parse("image/*"), FillDetailsActivity.imageFilePath_certificate_five);
            imgFilefive = MultipartBody.Part.createFormData("certifiateimage5", FillDetailsActivity.imageFilePath_certificate_five.getName(), requestFileOne5);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), Userid);
        RequestBody about = RequestBody.create(MediaType.parse("text/plain"), About);
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), Location_id);
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), Dob);
        RequestBody education = RequestBody.create(MediaType.parse("text/plain"), Education);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), Gender);
        RequestBody language = RequestBody.create(MediaType.parse("text/plain"), Language);
        RequestBody award = RequestBody.create(MediaType.parse("text/plain"), Awards);
        RequestBody certification = RequestBody.create(MediaType.parse("text/plain"), Certification);
        RequestBody affiliations = RequestBody.create(MediaType.parse("text/plain"),Affilations);
        RequestBody where_to_teach = RequestBody.create(MediaType.parse("text/plain"), Where_to_teach);
        RequestBody teach_distance = RequestBody.create(MediaType.parse("text/plain"), Teach_distance);
        RequestBody time_zone = RequestBody.create(MediaType.parse("text/plain"), TimeZone);
        //AllDay
        RequestBody monday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.monday.toString());
        RequestBody tuesday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.tuesday.toString());
        RequestBody wednesday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.wednesday.toString());
        RequestBody thursday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.thursday.toString());
        RequestBody friday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.friday.toString());
        RequestBody saturday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.saturday.toString());
        RequestBody sunday = RequestBody.create(MediaType.parse("text/plain"), TimeSelectedWeekView.sunday.toString());

        RequestBody tutorcategory = RequestBody.create(MediaType.parse("text/plain"), Tutorcategory);
        RequestBody tutorsubcategory = RequestBody.create(MediaType.parse("text/plain"), Tutorsubcategory);
        RequestBody tutorsubject = RequestBody.create(MediaType.parse("text/plain"), TutorsubjECTiD);
        RequestBody per_hour_payment = RequestBody.create(MediaType.parse("text/plain"), Per_hour_payment);
        RequestBody full_course_time = RequestBody.create(MediaType.parse("text/plain"), Full_course_time);

        RequestBody per_hour_payment_grp = RequestBody.create(MediaType.parse("text/plain"), Per_hour_payment_grp);
        RequestBody full_course_time_grp = RequestBody.create(MediaType.parse("text/plain"), Full_course_time_grp);

        RequestBody check_status = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_details(user_id,about,location,dob,education,gender,language,award,
                        certification,affiliations,where_to_teach,teach_distance,time_zone,monday,tuesday
                ,wednesday,thursday,friday,saturday,sunday,tutorcategory,tutorsubcategory,tutorsubject,
                        per_hour_payment,full_course_time,per_hour_payment_grp,full_course_time_grp,check_status,imgFile,imgFileOne,imgFileTwo,imgFileThree,imgFilefour,imgFilefive);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    result = jsonObject.getString("result");

                  //  String UserId = resultOne.getString("id");

                    if (status.equalsIgnoreCase("1")) {

                        startActivity(new Intent(PoliceVerifyActivity.this,HomeActvity.class));

                    } else {

                        Toast.makeText(PoliceVerifyActivity.this, result, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(PoliceVerifyActivity.this, result, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                } catch (IOException e) {
                    Toast.makeText(PoliceVerifyActivity.this, result, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PoliceVerifyActivity.this, result, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PoliceVerifyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}