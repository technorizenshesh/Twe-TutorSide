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
import com.tech.twe_tutorside.utils.FileUtil;
import com.tech.twe_tutorside.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;


public class FillDetailsActivity extends AppCompatActivity {

    private LinearLayout LL_user_profile;
    private LinearLayout LL_calender;
    private LinearLayout LL_add_certificate_one;
    private LinearLayout LL_add_certificate_two;
    private TextView txt_dob;
    private TextView txt_age;

    private Bitmap bitmap;
    private Uri resultUri;
    public static File UserProfile_img, compressedImage, compressActualFile;
    private File imageFilePath_certificate, compressedImage_two;
    private File imageFilePath_certificate_one, compressedImage_two_one;
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

    String about ="";
    String dob ="";
    String education ="";
    String language ="";
    String Affilations ="";
    String Awards ="";
    String Location ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}

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

                                txt_age.setText(age+" Year");

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
                                    Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(FillDetailsActivity.this);
                                    startActivityForResult(intent, 3);
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

       String AddressId = Preference.get(FillDetailsActivity.this, Preference.KEY_Address_id);

        Toast.makeText(this, AddressId, Toast.LENGTH_SHORT).show();
    }

    private void findView() {
        sessionManager = new SessionManager(FillDetailsActivity.this);

        edt_about=findViewById(R.id.edt_about);
        edt_education=findViewById(R.id.edt_education);
        edt_language=findViewById(R.id.edt_language);
        edt_Affilations=findViewById(R.id.edt_Affilations);
        edt_Awards=findViewById(R.id.edt_Awards);

        img_one_certificate=findViewById(R.id.img_one_certificate);
        img_one_certificate_one=findViewById(R.id.img_one_certificate_one);
        LL_user_profile=findViewById(R.id.LL_user_profile);
        LL_add_certificate_two=findViewById(R.id.LL_add_certificate_two);
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
                    img_userProfile.setImageResource(R.drawable.check_one);
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
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_certificate = FileUtil.from(FillDetailsActivity.this, resultUri);
                   // img_two.setImageBitmap(bitmap);
                    img_one_certificate.setImageResource(R.drawable.check_one);
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
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_certificate_one = FileUtil.from(FillDetailsActivity.this, resultUri);
                    // img_two.setImageBitmap(bitmap);
                    img_one_certificate_one.setImageResource(R.drawable.check_one);
                    isCertificate_one=true;

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

        BottomSheetFragment bottomSheetFragment= new BottomSheetFragment();
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

        if(isProfileImage)
        {
            Toast.makeText(this, "Please insert Profile Image", Toast.LENGTH_SHORT).show();

        }if(about.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter about.", Toast.LENGTH_SHORT).show();

        }else if(dob.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please enter Dob.", Toast.LENGTH_SHORT).show();

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
        }else
        {
            Intent intent=new Intent(this,YourLessonActivity.class);
            intent.putExtra("about",about);
            intent.putExtra("location","15");
            intent.putExtra("dob",dob);
            intent.putExtra("education",education);
            intent.putExtra("gender","Male");
            intent.putExtra("language",language);
            intent.putExtra("Affilations",Affilations);
            intent.putExtra("Awards",Awards);
            intent.putExtra("certification","certification");
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

}