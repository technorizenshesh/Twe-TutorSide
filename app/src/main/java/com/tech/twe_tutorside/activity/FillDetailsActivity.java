
package com.tech.twe_tutorside.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.adapter.CountrySpinnerAdapter;
import com.tech.twe_tutorside.fragments.BottomSheetFragment;
import com.tech.twe_tutorside.listner.MyClickListner;
import com.tech.twe_tutorside.utils.FileUtil;
import com.tech.twe_tutorside.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;


public class FillDetailsActivity extends AppCompatActivity implements MyClickListner {

    private LinearLayout LL_user_profile;
    private LinearLayout LL_calender;
    private LinearLayout LL_add_certificate_one;
    private LinearLayout LL_add_certificate_two;
    private LinearLayout ll_certi;
    private LinearLayout LL_add_imgage;
    private LinearLayout ll_img;
    private TextView txt_dob;
    private TextView txt_age;

    private Bitmap bitmap;
    private Uri resultUri;
    public static File UserProfile_img, compressedImage, compressActualFile;
    public static File imageFilePath_certificate, compressedImage_two;
    public static File imageFilePath_certificate_one, compressedImage_two_one;
    public static File imageFilePath_certificate_two;
    public static File imageFilePath_certificate_three;
    public static File imageFilePath_certificate_four;
    public static File imageFilePath_certificate_five;
    boolean isProfileImage=false;
    boolean isCertificate=false;
    boolean isCertificate_one=false;
    private int mYear, mMonth,mDay;
    private ImageView img_userProfile;
    private ImageView img_one_certificate;
    private ImageView img_one_certificate_one;

    private Spinner spinnergender;

    private String code[] ={"Male","Female"};
    private int flags[]= {R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};

    private SessionManager sessionManager;
    private ProgressBar progressBar;

    private EditText edt_about;
    private EditText edt_education;
    private EditText edt_language;
    private EditText edt_Affilations;
    private EditText edt_Awards;
    private EditText edt_certificate;

    String about ="";
    String dob ="";
    String education ="";
    String language ="";
    String Affilations ="";
    String Awards ="";
    String certificate ="";
    String Location ="";
    TextView txt_location;
    MyClickListner listner;
    String Gender="";
    String LocationId="";

    LinearLayout ll_image;
    LinearLayout ll_img_set;
    LinearLayout ll_img_set_img;


    ImageView img_certi_one;
    ImageView img_certi_two;
    ImageView img_certi_three;
    ImageView img_certi_four;
    ImageView img_certi_five;

    ImageView img_one;
    ImageView img_two;
    ImageView img_three;
    ImageView img_four;
    ImageView img_five;

    int Count = 0;
    int Count_img = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_fill_details);

        findView();

        CountrySpinnerAdapter customAdapter=new CountrySpinnerAdapter(FillDetailsActivity.this,flags,code);
        spinnergender.setAdapter(customAdapter);

        LL_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
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
        ll_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
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

        LL_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FillDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                view.setVisibility(View.VISIBLE);
                                dob = (dayOfMonth+"-"+(monthOfYear)+"-"+year);
                                txt_dob.setText(dob);

                                String age = getAge(year,(monthOfYear+1),dayOfMonth);

                                String CalcuAge= getAge(year,monthOfYear,dayOfMonth);

                                txt_age.setText(CalcuAge+" Years");


                            }
                        }, mYear, mMonth, mDay);

                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();



            }
        });

        LL_add_certificate_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
                                    startActivityForResult(intent, 2);
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

        LL_add_certificate_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    if(Count<5)
                                    {
                                        Count++;
                                        Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
                                        startActivityForResult(intent, 3);
                                    }else
                                    {
                                        Toast.makeText(FillDetailsActivity.this, "Maximum five certificate Upload.", Toast.LENGTH_SHORT).show();
                                    }

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

        ll_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    if(Count<5)
                                    {
                                        Count++;
                                        Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
                                        startActivityForResult(intent, 3);
                                    }else
                                    {
                                        Toast.makeText(FillDetailsActivity.this, "Maximum five certificate Upload.", Toast.LENGTH_SHORT).show();
                                    }

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

        LL_add_imgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    if(Count_img<5)
                                    {
                                        Count_img++;
                                        Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
                                        startActivityForResult(intent, 4);
                                    }else
                                    {
                                        Toast.makeText(FillDetailsActivity.this, "Maximum five certificate Upload.", Toast.LENGTH_SHORT).show();
                                    }

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

        ll_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(FillDetailsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    if(Count_img<5)
                                    {
                                        Count_img++;
                                        Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
                                        startActivityForResult(intent, 4);
                                    }else
                                    {
                                        Toast.makeText(FillDetailsActivity.this, "Maximum five certificate Upload.", Toast.LENGTH_SHORT).show();
                                    }

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

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3){

                Gender = code[pos];

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }



    private void findView() {

        sessionManager = new SessionManager(FillDetailsActivity.this);

        img_one=findViewById(R.id.img_one);
        img_two=findViewById(R.id.img_two);
        img_three=findViewById(R.id.img_three);
        img_four=findViewById(R.id.img_four);
        img_five=findViewById(R.id.img_five);
        img_certi_one=findViewById(R.id.img_certi_one);
        img_certi_two=findViewById(R.id.img_certi_two);
        img_certi_three=findViewById(R.id.img_certi_three);
        img_certi_four=findViewById(R.id.img_certi_four);
        img_certi_five=findViewById(R.id.img_certi_five);
        ll_img_set=findViewById(R.id.ll_img_set);
        ll_img_set_img=findViewById(R.id.ll_img_set_img);

        LL_user_profile=findViewById(R.id.LL_user_profile);
        ll_image=findViewById(R.id.ll_image);
        txt_location=findViewById(R.id.txt_location);
        edt_about=findViewById(R.id.edt_about);
        edt_education=findViewById(R.id.edt_education);
        edt_language=findViewById(R.id.edt_language);
        edt_Affilations=findViewById(R.id.edt_Affilations);
        edt_Awards=findViewById(R.id.edt_Awards);
        edt_certificate=findViewById(R.id.edt_certificate);

        img_one_certificate=findViewById(R.id.img_one_certificate);
        img_one_certificate_one=findViewById(R.id.img_one_certificate_one);
        LL_user_profile=findViewById(R.id.LL_user_profile);
        LL_add_certificate_two=findViewById(R.id.LL_add_certificate_two);
        ll_certi=findViewById(R.id.ll_certi);
        LL_add_imgage=findViewById(R.id.LL_add_imgage);
        ll_img=findViewById(R.id.ll_img);
        img_userProfile=findViewById(R.id.img_userProfile);
        LL_calender=findViewById(R.id.LL_calender);
        txt_dob=findViewById(R.id.txt_dob);
        txt_age=findViewById(R.id.txt_age);
        spinnergender=findViewById(R.id.spinnergender);
        LL_add_certificate_one=findViewById(R.id.LL_add_certificate_one);
    }


    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(FillDetailsActivity.this);
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                   // Glide.with(this).load(bitmap).circleCrop().into(img_userProfile);
                    UserProfile_img = FileUtil.from(this, resultUri);
                      ll_image.setVisibility(View.GONE);
                      img_userProfile.setVisibility(View.VISIBLE);
                   // img_userProfile.setImageBitmap(bitmap);

                   Glide.with(this).load(bitmap).circleCrop().into(img_userProfile);

                    isProfileImage = true;

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
                            .compressToFile(UserProfile_img);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(FillDetailsActivity.this.getContentResolver(), resultUri);

                    imageFilePath_certificate = FileUtil.from(FillDetailsActivity.this, resultUri);
                   // img_two.setImageBitmap(bitmap);
                //    img_one_certificate.setImageResource(R.drawable.check_one);
                    Glide.with(this).load(bitmap).circleCrop().into(img_one_certificate);
                    isCertificate=true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    compressedImage_two = new Compressor(FillDetailsActivity.this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_certificate);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_two);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FillDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(FillDetailsActivity.this.getContentResolver(), resultUri);


                    // img_two.setImageBitmap(bitmap);
                   // Glide.with(this).load(bitmap).circleCrop().into(img_one_certificate_one);
              if(Count ==1)
              {
                  imageFilePath_certificate_one = FileUtil.from(FillDetailsActivity.this, resultUri);

                  ll_img_set.setVisibility(View.VISIBLE);
                  img_certi_one.setVisibility(View.VISIBLE);
                  //Glide.with(this).load(bitmap).circleCrop().into(img_certi_one);
                  img_certi_one.setImageBitmap(bitmap);
                  isCertificate_one=true;

              }else if(Count ==2)
              {
                  imageFilePath_certificate_two = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set.setVisibility(View.VISIBLE);
                  img_certi_two.setVisibility(View.VISIBLE);
                  img_certi_two.setImageBitmap(bitmap);
                 // Glide.with(this).load(bitmap).circleCrop().into(img_certi_two);

              }else if(Count ==3)
              {
                  imageFilePath_certificate_three = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set.setVisibility(View.VISIBLE);
                  img_certi_three.setVisibility(View.VISIBLE);
                  img_certi_three.setImageBitmap(bitmap);
                  //Glide.with(this).load(bitmap).circleCrop().into(img_certi_three);

              }else if(Count ==4)
              {
                  imageFilePath_certificate_four = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set.setVisibility(View.VISIBLE);
                  img_certi_four.setVisibility(View.VISIBLE);
                  img_certi_four.setImageBitmap(bitmap);
                 // Glide.with(this).load(bitmap).circleCrop().into(img_certi_four);

              }else if(Count ==5)
              {
                  imageFilePath_certificate_five = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set.setVisibility(View.VISIBLE);
                  img_certi_five.setVisibility(View.VISIBLE);
                  img_certi_five.setImageBitmap(bitmap);
                //  Glide.with(this).load(bitmap).circleCrop().into(img_certi_five);
              }

                  //  img_one_certificate_one.setImageResource(R.drawable.check_one);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    compressedImage_two_one = new Compressor(FillDetailsActivity.this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_certificate_one);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_two_one);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FillDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else   if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(FillDetailsActivity.this.getContentResolver(), resultUri);
                    // img_two.setImageBitmap(bitmap);
                   // Glide.with(this).load(bitmap).circleCrop().into(img_one_certificate_one);
              if(Count_img ==1)
              {
                  imageFilePath_certificate_one = FileUtil.from(FillDetailsActivity.this, resultUri);

                  ll_img_set_img.setVisibility(View.VISIBLE);
                  ll_img_set.setVisibility(View.VISIBLE);
                  img_one.setVisibility(View.VISIBLE);
                  //Glide.with(this).load(bitmap).circleCrop().into(img_certi_one);
                  img_one.setImageBitmap(bitmap);
                  isCertificate_one=true;

              }else if(Count_img ==2)
              {
                  imageFilePath_certificate_two = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set_img.setVisibility(View.VISIBLE);
                  img_two.setVisibility(View.VISIBLE);
                  img_two.setImageBitmap(bitmap);
                 // Glide.with(this).load(bitmap).circleCrop().into(img_certi_two);

              }else if(Count_img ==3)
              {
                  imageFilePath_certificate_three = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set_img.setVisibility(View.VISIBLE);
                  img_three.setVisibility(View.VISIBLE);
                  img_three.setImageBitmap(bitmap);
                  //Glide.img_three(this).load(bitmap).circleCrop().into(img_certi_three);

              }else if(Count_img ==4)
              {
                  imageFilePath_certificate_four = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set_img.setVisibility(View.VISIBLE);
                  img_four.setVisibility(View.VISIBLE);
                  img_four.setImageBitmap(bitmap);
                 // Glide.with(this).load(bitmap).circleCrop().into(img_certi_four);

              }else if(Count_img ==5)
              {
                  imageFilePath_certificate_five = FileUtil.from(FillDetailsActivity.this, resultUri);
                  ll_img_set_img.setVisibility(View.VISIBLE);
                  img_five.setVisibility(View.VISIBLE);
                  img_five.setImageBitmap(bitmap);
                //  Glide.with(this).load(bitmap).circleCrop().into(img_certi_five);
              }

                  //  img_one_certificate_one.setImageResource(R.drawable.check_one);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    compressedImage_two_one = new Compressor(FillDetailsActivity.this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_certificate_one);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_two_one);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FillDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void locationInit(View view) {

        BottomSheetFragment bottomSheetFragment= new BottomSheetFragment(FillDetailsActivity.this );
        bottomSheetFragment.show(getSupportFragmentManager(),"ModalBottomSheet");

    }

    public void continuesignup(View view) {
        validation();
      // startActivity(new Intent(this,YourLessonActivity.class));
    }

    private void validation() {
        about = edt_about.getText().toString();
        education = edt_education.getText().toString();
        language = edt_language.getText().toString();
        Affilations = edt_language.getText().toString();
        Awards = edt_Awards.getText().toString();
        certificate = edt_certificate.getText().toString();

        if(isProfileImage == false)
        {
            Toast.makeText(this, "Please insert Profile Image", Toast.LENGTH_SHORT).show();

        }else if(about.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter about.", Toast.LENGTH_SHORT).show();

        }else if(dob.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter Dob.", Toast.LENGTH_SHORT).show();

        }else if(LocationId.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please add Location.", Toast.LENGTH_SHORT).show();

        }else if(education.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter education.", Toast.LENGTH_SHORT).show();

        }else if(language.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter language.", Toast.LENGTH_SHORT).show();

        }else if(Affilations.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter Affilations.", Toast.LENGTH_SHORT).show();

        }else if(Awards.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter Awards.", Toast.LENGTH_SHORT).show();

        }else if(certificate.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter Awards.", Toast.LENGTH_SHORT).show();
        }else
        {
            Intent intent=new Intent(this,YourLessonActivity.class);
            intent.putExtra("about",about);
            intent.putExtra("location",LocationId);
            intent.putExtra("dob",dob);
            intent.putExtra("education",education);
            intent.putExtra("gender",Gender);
            intent.putExtra("language",language);
            intent.putExtra("Affilations",Affilations);
            intent.putExtra("Awards",Awards);
            intent.putExtra("certification",certificate);
            startActivity(intent);
           // startActivity(new Intent(this,YourLessonActivity.class));
        }

    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @Override
    public void clickListen(String listenId) {
        LocationId =  Preference.get(FillDetailsActivity.this,Preference.KEY_location_id);
       String Address =   Preference.get(FillDetailsActivity.this,Preference.KEY_location_addreess);
        txt_location.setText(Address);
    }
}