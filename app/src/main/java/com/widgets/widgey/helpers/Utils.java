package com.widgets.widgey.helpers;


import static android.app.job.JobInfo.Builder;
import static android.app.job.JobInfo.NETWORK_TYPE_ANY;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;



public class Utils {

    public static final long ONE_DAY = 24 * 60 * 60 * 1000L; // 1 Day
    public static final long SIX_HOUR = 6 * 60 * 60 * 1000L; // 6 Hour
    public static final long THREE_HOUR = 3*60 * 60 * 1000L; // 1 hour
    public static final long ONE_HOUR = 60 * 60 * 1000L; // 1 hour
    public static final long THREE_MINUTE = 10 * 60 * 1000; // 10 minutes

    public static String GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=";

    //************** caterogry string urls ***************

    public static String Abstract = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/80709e3b5a6473db704561bc5e9c65e0_thumbnail.jpg";
    public static String Amoled = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/a3f6188e419eee337463e74216afd24d_thumbnail.jpg";
    public static String Animal = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/56d687628af9c7ac78d5b393c5aca581_thumbnail.jpg";
    public static String Anime = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/e11fd79edbb4cb96f5ec7c8796bbc83c_thumbnail.jpg";
    public static String Art = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/22090edf6b5b93df5a0543ddac661ba3_thumbnail.jpg";
    public static String Buildings = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/d4dc38240475ec4e6e8c851f7c7b87a3_thumbnail.jpg";
    public static String Cars = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/db275ea65d1bfd168e87c2104ee60680_thumbnail.jpg";
    public static String Cityscape = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/5f2d65132847be296529f7d30dbbe969_thumbnail.jpg";
    public static String Fantasy = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/41adc369deb2a5d55202e7a49374098a_thumbnail.jpg";
    public static String Flower = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/dc60a23e9ed5860cabcf15118ebe0d13_thumbnail.jpg";
    public static String Games = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/58f78ccdb03b2fd5770ba35543106577_thumbnail.jpg";
    public static String Light = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/16184666c53a24b11cb3cf75ea635b8d_thumbnail.jpg";
    public static String Love = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/04cff39c1b2eb0203aa03be610a66b4b_thumbnail.jpg";
    public static String Minimal = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/cf64326c0d8c0087652998fc5260afed_thumbnail.jpg";
    public static String Motercycles = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/2f9bbb3bdda5a95b7ce1522739d4959e_thumbnail.jpg";
    public static String Nature = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/f9565724716b6713d981332468d913ac_thumbnail.jpg";
    public static String Others = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/bd8883e3924d0c45b5cc0aed12caa329_thumbnail.jpg";
    public static String People = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/27a970ce4e8cfa847e5b360751571a93_thumbnail.jpg";
    public static String Space = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/820c8a5d6e6d4a527668571c4a01a753_thumbnail.jpg";
    public static String Sports = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/3d2851ccaa768352be6ac2f34255c14e_thumbnail.jpg";
    public static String Technology = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/c5ef88947b7d5cc6df8e694c6fc0db61_thumbnail.jpg";
    public static String Vehical = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/bb5e752dd849922dc82a265e603ceb21_thumbnail.jpg";
    public static String FourK = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/aa19f8597f1483b8f65fd5cf8745f687_thumbnail.jpg";
    public static String Word = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/548bda746038c70b0f9de69a2bbc55f9_thumbnail.jpg";
    public static String Premium = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/90946dee25666fe6eadcd9fc98c49b9c_thumbnail.jpg";
    public static String Desktop = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/7e38a44c32aa38357d3b8e136a31fea2_thumbnail.jpg";
    public static String Showcase = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/42ed01168acf67db09c7a646675bfbf7_thumbnail.jpg";
    public static String Quotes = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/efaad931185033bf7b8fa758be5e7b70_thumbnail.jpg";
    public static String Apple = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/25e33f2633f0998a5eebab8f6d033702_thumbnail.jpg";
    public static String Stock = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/77a22144fa0014065f54e22fb43f6cd6_thumbnail.jpg";
    public static String Premium2 = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/d1442243c5908a84d4cd2175b04b8cba_thumbnail.jpg";
    public static String Apple2 = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/2b37ab0857ece78ee4978efc416b28ed_thumbnail.jpg";
    public static String Stock2 = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/d7386b8bdf7ed9f4affa8091fba1a23f_thumbnail.jpg";
    public static String Dp = "https://cdn2.iconfinder.com/data/icons/avatars-99/62/avatar-374-456326-512.png";

    public static String Waves = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/d05f127b9ba91fccae23c1e66097e7a7_thumbnail.jpg";
    public static String Waves2 = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/eed25dfb2dea3984ad2d9428a203a398_thumbnail.jpg";
    public static String Notch = "https://parsefiles.back4app.com/5ZSBrcMuwOZbJcOqkPEnHhd7PbKEl4psH574lr1g/e39c7b90ed5099aaca893be0b37421d5_notchthumbnail.jpg";

    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#BAB7C1")), new ColorDrawable(Color.parseColor("#4C4C4C")),
                    new ColorDrawable(Color.parseColor("#B07C5E")), new ColorDrawable(Color.parseColor("#E3C077")),
                    new ColorDrawable(Color.parseColor("#E7EAEE")), new ColorDrawable(Color.parseColor("#603959")),
                    new ColorDrawable(Color.parseColor("#E7D5FC")), new ColorDrawable(Color.parseColor("#F2F5E8")),
                    new ColorDrawable(Color.parseColor("#9D6898")), new ColorDrawable(Color.parseColor("#E9EFD9")),
                    new ColorDrawable(Color.parseColor("#A2C4AE")), new ColorDrawable(Color.parseColor("#F4F9F9")),
                    new ColorDrawable(Color.parseColor("#414B39")), new ColorDrawable(Color.parseColor("#E97879")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };

    public static ColorDrawable[] darkColorList =
            {
                    new ColorDrawable(Color.parseColor("#27374D")), new ColorDrawable(Color.parseColor("#0C134F")),
                    new ColorDrawable(Color.parseColor("#1D267D")), new ColorDrawable(Color.parseColor("#D21312")),
                    new ColorDrawable(Color.parseColor("#1A120B")), new ColorDrawable(Color.parseColor("#182747")),
                    new ColorDrawable(Color.parseColor("#3C2A21")), new ColorDrawable(Color.parseColor("#735F32")),
                    new ColorDrawable(Color.parseColor("#ED5107")), new ColorDrawable(Color.parseColor("#892CDC")),
            };


    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static ColorDrawable getRandomDarkColor() {
        int idx = new Random().nextInt(darkColorList.length);
        return darkColorList[idx];
    }

    //********************************************************************


    public static boolean isJobServiceOn( Context context ) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == 12 ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }

    //*************** work manager ************************
    public static boolean isWorkScheduled(String name, Context context) {

        WorkManager instance = WorkManager.getInstance(context);
        ListenableFuture<List<WorkInfo>> statuses = instance.getWorkInfosForUniqueWork(name);

        boolean running = false;
        List<WorkInfo> workInfoList = Collections.emptyList(); // Singleton, no performance penalty

        try {
            workInfoList = statuses.get();
        } catch (ExecutionException e) {
            Log.d("Work", "ExecutionException in isWorkScheduled: " + e);
        } catch (InterruptedException e) {
            Log.d("Work", "InterruptedException in isWorkScheduled: " + e);
        }

        for (WorkInfo workInfo : workInfoList) {
            WorkInfo.State state = workInfo.getState();
            running = running || (state == WorkInfo.State.RUNNING | state == WorkInfo.State.ENQUEUED);
        }
        return running;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {

        Random generator = new Random();
        int n = 100000;
        n = generator.nextInt(n);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Wallcandy"+n, null);

        return Uri.parse(path);

    }

    public static Bitmap aspectRatioBitmap(Bitmap originalImage, int width, int height){

        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        float originalWidth;
        float originalHeight;

        if(originalImage != null ){
            originalWidth = originalImage.getWidth();
            originalHeight = originalImage.getHeight();
        }
        else {
            originalWidth = width;
            originalHeight = height;
        }

        Canvas canvas = new Canvas(background);

        float bitmapAspectRatio = originalWidth /originalHeight;
        float aspectRatio = (float) width/height;
        float scale, xTranslation, yTranslation;

        if (aspectRatio>=0.6){
            scale = width / originalWidth;
            xTranslation = 0.0f;
            yTranslation = (height - originalHeight * scale) / 2.0f;

            Log.i("walldim", "square device size  = "+ bitmapAspectRatio);

        }

        else if(aspectRatio>bitmapAspectRatio){
            scale = width / originalWidth;
            xTranslation = 0.0f;
            yTranslation = (height - originalHeight * scale) / 2.0f;

            Log.i("walldim", "wallpaper size to narrow compare to device = "+ bitmapAspectRatio);

        }
        else{
            scale = height/originalHeight;
            xTranslation = (width - originalWidth * scale) / 2.0f;
            yTranslation = 0.0f;

            Log.i("walldim", "extra long device size = "+ bitmapAspectRatio);
        }

        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        canvas.drawBitmap(originalImage, transformation, paint);
        Log.i("walldim", "aspect ration  = "+ aspectRatio);
        Log.i("walldim", "bitmap aspect ration  = "+ bitmapAspectRatio);


        return background;
    }


    public static void openUrl(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static String loadJSONFromAsset(Context context, String json) {

        try {
            InputStream is = context.getAssets().open(json);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    public static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }
    public static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if ( !prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }





}
