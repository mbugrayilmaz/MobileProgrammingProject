package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Tools {
    public static Bitmap drawOutfit(Context context, String... uriList) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        int eachHeight = 384;

        int maxWidth = 0;

        for (String uri : uriList) {
            Bitmap bitmap = getBitmap(context, uri);

            bitmap = Bitmap.createScaledBitmap(bitmap, eachHeight * bitmap.getWidth() / bitmap.getHeight(), eachHeight, true);

            if (bitmap.getWidth() > maxWidth) {
                maxWidth = bitmap.getWidth();
            }

            bitmaps.add(bitmap);
        }

        Bitmap outfit = Bitmap.createBitmap(maxWidth, 1920, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(outfit);

        float prevTop = 0;

        for (Bitmap bitmap : bitmaps) {
            canvas.drawBitmap(bitmap, ((float) maxWidth - bitmap.getWidth()) / 2, prevTop, null);

            prevTop += bitmap.getHeight();
        }

        return outfit;
    }

    public static Bitmap getBitmap(Context context, String path) {
        try (InputStream stream = context.getContentResolver().openInputStream(Uri.parse(path))) {
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String saveImage(Context context, Bitmap bitmap, String fileName) {
        ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());

        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);

        File newFile = new File(directory, fileName + ".png");

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(newFile);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);
            }

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile.getAbsolutePath();
    }

    public static String saveImage(Context context, String path, String fileName) {
        ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());

        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);

        File newFile = new File(directory, fileName);

        Bitmap bitmap;
        InputStream inputStream;
        FileOutputStream fileOutputStream;

        try {
            inputStream = context.getContentResolver().openInputStream(Uri.parse(path));

            bitmap = BitmapFactory.decodeStream(inputStream);

            fileOutputStream = new FileOutputStream(newFile);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);
            }

            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile.getAbsolutePath();
    }

    public static String saveImage(Context context, String path) {
        String fileName = String.valueOf(System.currentTimeMillis() / 1000);

        ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());

        File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);

        File newFile = new File(directory, fileName);

        Bitmap bitmap;
        InputStream inputStream;
        FileOutputStream fileOutputStream;

        try {
            inputStream = context.getContentResolver().openInputStream(Uri.parse(path));

            bitmap = BitmapFactory.decodeStream(inputStream);

            fileOutputStream = new FileOutputStream(newFile);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);
            }

            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile.getAbsolutePath();
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);

        return file.delete();
    }

    public static String renameFile(String path, String newName) {
        File oldFile = new File(path);

        File newFile = new File(oldFile.getParent() + File.separator + newName);

        oldFile.renameTo(newFile);

        return newFile.getAbsolutePath();
    }
}
