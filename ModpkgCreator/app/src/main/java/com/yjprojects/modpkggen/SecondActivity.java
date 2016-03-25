package com.yjprojects.modpkggen;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.yugy.github.reveallayout.RevealLayout;

/**
 * Created by jyj on 2016-02-12.
 * https://github.com/dibakarece/AndroidFileExplorer
 */
public class SecondActivity extends AppCompatActivity implements View.OnTouchListener{

    LinearLayout arrowButton , cancelButton;
    RevealLayout mRevealLayout;
    View mRevealView;
    TextView arrowTextView , cancelTextView , pathTextView , pathTextView2 , pathTextView3;
    String jspath = "";
    String imgpath = "";
    View arrowObj4;
    ImageView arrowObj3, arrowImage;
    ExtraData extraData = new ExtraData();

    ViewFlipper viewFlipper;
    ImageView indic1 , indic2, indic3;
    RelativeLayout background;
    int level = 1;
    int[] accentColors = new int[4];


    private static final int PICKFILE_RESULT_CODE = 10001;
    private static final String CANCEL = "취소";
    private static final String NEXT = "다음";
    private static final String PREV = "이전";
    private static final String ADD = "추가";
    private static final String WRITE = "작성";
    private static final String COMPLETE = "완료";


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.secondlayout);

        init();
    }

    private void init() {
        arrowButton = (LinearLayout) findViewById(R.id.arrowButton);
        mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        mRevealView = findViewById(R.id.reveal_view);
        arrowTextView = (TextView) findViewById(R.id.arrowText);
        cancelTextView = (TextView) findViewById(R.id.cancelText);
        cancelButton = (LinearLayout) findViewById(R.id.cancelButton);
        pathTextView = (TextView) findViewById(R.id.pathTextView);
        pathTextView2 = (TextView) findViewById(R.id.pathTextView2);
        pathTextView3 = (TextView) findViewById(R.id.pathTextView3);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        indic1 = (ImageView) findViewById(R.id.indic1);
        indic2 = (ImageView) findViewById(R.id.indic2);
        indic3 = (ImageView) findViewById(R.id.indic3);
        background = (RelativeLayout) findViewById(R.id.background);
        arrowObj3 = (ImageView) findViewById(R.id.arrowobj3);
        arrowObj4 = findViewById(R.id.arrowobj4);
        arrowImage = (ImageView) findViewById(R.id.arrowImage);

        arrowButton.setOnTouchListener(this);
        cancelButton.setOnTouchListener(this);

        Animation inAnimation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.righttocenter);
        viewFlipper.setInAnimation(inAnimation);

        Animation outAnimation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.centertoleft);
        viewFlipper.setOutAnimation(outAnimation);

        accentColors[0] = R.color.colorAccent;
        accentColors[1] = R.color.colorAccent2;
        accentColors[2] = R.color.colorPrimary;
        accentColors[3] = R.color.colorPrimaryLight;

        indicator(level);

    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                break;
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(getResources().getColor(R.color.noColor));
                break;
        }

        switch(v.getId()){

            case R.id.cancelButton :
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    switch(level){
                        case 1 :
                            //Page 2 -> Page 1
                            jspath="";
                            cancelButton.setVisibility(View.INVISIBLE);
                            pathTextView.setVisibility(View.INVISIBLE);
                            arrowTextView.setText(ADD);
                            break;

                        case 2 :
                            if(imgpath.equals("")){
                                //Page 3 -> Page 2
                                prevPage();
                                reveal(v, event.getRawX(), event.getRawY(), 0, new Runnable() {
                                    @Override
                                    public void run() {
                                        arrowTextView.setText(NEXT);
                                        cancelTextView.setText(CANCEL);
                                    }
                                });
                            } else{
                                //Page 4 - > Page 3

                                imgpath = "";
                                pathTextView2.setVisibility(View.INVISIBLE);
                                arrowTextView.setText(ADD);
                                cancelTextView.setText(PREV);
                            }
                            break;

                        case 3 :
                            if(extraData.name.equals("")){
                                //Page 5 -> Page 4
                                prevPage();
                                reveal(v, event.getRawX(), event.getRawY(), 1, new Runnable() {
                                    @Override
                                    public void run() {
                                        arrowTextView.setText(NEXT);
                                        cancelTextView.setText(CANCEL);
                                    }
                                });
                            } else{
                                //Page 6 -> Page 5

                                extraData.name = "";
                                extraData.author = "";
                                extraData.version = "";
                                extraData.description = "";
                                pathTextView3.setVisibility(View.INVISIBLE);
                                arrowTextView.setText(WRITE);
                                cancelTextView.setText(PREV);
                            }
                            break;
                    }
                }
                break;

            case R.id.arrowButton :
                if(event.getAction() == MotionEvent.ACTION_UP){
                    switch(level){
                        case 1 :
                            if(jspath.equals("")){
                                //Page 1 -> Page Chooser

                                openFileChooser();
                            } else {
                                //Page 2 -> Page 3
                                nextPage();
                                reveal(v, event.getRawX(), event.getRawY(), 1, new Runnable() {
                                    @Override
                                    public void run() {
                                        arrowTextView.setText(ADD);
                                        cancelTextView.setText(PREV);
                                    }
                                });
                            }
                            break;

                        case 2 :
                            if(imgpath.equals("")){
                                //Page 3 -> Page Chooser

                                openFolderChooser();
                            } else {
                                //Page 4 -> Page 5
                                nextPage();
                                reveal(v, event.getRawX(), event.getRawY(), 2, new Runnable() {
                                    @Override
                                    public void run() {
                                        arrowTextView.setText(WRITE);
                                        cancelTextView.setText(PREV);
                                    }
                                });
                            }
                            break;

                        case 3 :
                            if(extraData.name.equals("")){
                                //Page 5 -> Page Editor

                                showEditExtraDataDialog();
                            } else {
                                //Page 6 -> Activity 3
                                //Arrow ANIMATION
                                compress();
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    arrowObj3.setVisibility(View.VISIBLE);
                                    arrowImage.setVisibility(View.INVISIBLE);
                                    arrowButton.setVisibility(View.INVISIBLE);
                                    mRevealLayout.bringToFront();
                                    arrowMove();

                                    v.setEnabled(false);
                                    int[] location = new int[2];
                                    location[0] = (int) event.getRawX();
                                    location[1] = (int) event.getRawY();

                                    mRevealView.setBackgroundColor(getResources().getColor(accentColors[3]));
                                    mRevealView.setVisibility(View.VISIBLE);
                                    mRevealLayout.setVisibility(View.VISIBLE);
                                    mRevealLayout.show(location[0], location[1], 900);
                                    v.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.fade_in, R.anim.hold);
                                            finish();
                                        }
                                    }, 900);
                                    v.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            v.setEnabled(true);
                                            mRevealLayout.setVisibility(View.INVISIBLE);
                                            mRevealView.setVisibility(View.INVISIBLE);
                                        }
                                    }, 1261);
                                }else{
                                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.hold);
                                    finish();
                                }

                            }
                            break;
                    }
                }
                break;
        }

        return true;
    }

    private void openFileChooser(){
        File path = new File(new Environment().getExternalStorageDirectory()+"/");
        FileDialog fileDialog = new FileDialog(SecondActivity.this, path);
        fileDialog.setFileEndsWith(".js");
        fileDialog.addFileListener(new FileDialog.FileSelectedListener(){
            @Override
            public void fileSelected(File file) {
                String[] splitedFileName = file.getName().split("\\.");
                if(splitedFileName[splitedFileName.length - 1].equals("js")){
                    // Page Chooser -> Page 2
                    jspath = file.getAbsolutePath();
                    pathTextView.setText("추가됨 : "+ file.getAbsolutePath());
                    pathTextView.setVisibility(View.VISIBLE);
                    cancelButton.setVisibility(View.VISIBLE);
                    cancelTextView.setText(CANCEL);
                    arrowTextView.setText(NEXT);
                }
                else{
                    Toast.makeText(SecondActivity.this,"js 파일이 아닙니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
        fileDialog.showDialog();
    }

    private void openFolderChooser(){

        File path = new File(new Environment().getExternalStorageDirectory()+"/");
        FileDialog fileDialog = new FileDialog(SecondActivity.this, path);
        fileDialog.setFileEndsWith(null);
        fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
            public void directorySelected(File directory) {
                if(directory.getName().equals("images")) {
                    //Page Chooser - > Page 4
                    imgpath = directory.getAbsolutePath();
                    pathTextView2.setText("추가됨 : " + directory.getAbsolutePath());
                    pathTextView2.setVisibility(View.VISIBLE);
                    cancelTextView.setText(CANCEL);
                    arrowTextView.setText(NEXT);
                } else{
                    Toast.makeText(SecondActivity.this,"텍스쳐 폴더 명이 아닙니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
        fileDialog.setSelectDirectoryOption(true);
        fileDialog.showDialog();

    }

    private void showEditExtraDataDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this, R.style.DialogStyle);
        builder.setCancelable(false);
        LinearLayout layout_dialog = (LinearLayout)getLayoutInflater().inflate(R.layout.extradata_dialog, null);

        final EditText editText1_dialog = (EditText) layout_dialog.findViewById(R.id.editText_1);
        final EditText editText2_dialog = (EditText) layout_dialog.findViewById(R.id.editText_2);
        final EditText editText3_dialog = (EditText) layout_dialog.findViewById(R.id.editText_3);
        final EditText editText4_dialog = (EditText) layout_dialog.findViewById(R.id.editText_4);

        builder.setTitle("파일 정보를 작성하세요");
        builder.setView(layout_dialog);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Page Editor -> Page 6
                if (editText1_dialog.getText().toString().equals("")) {
                    Toast.makeText(SecondActivity.this,"이름이 입력되지 않았습니다!",Toast.LENGTH_LONG).show();
                } else {
                    extraData.name = editText1_dialog.getText().toString();
                    extraData.version = editText2_dialog.getText().toString();
                    extraData.author = editText3_dialog.getText().toString();
                    extraData.description = editText4_dialog.getText().toString();

                    pathTextView3.setText("추가됨");
                    pathTextView3.setVisibility(View.VISIBLE);
                    cancelTextView.setText(CANCEL);
                    arrowTextView.setText(COMPLETE);
                }
            }
        });

        builder.setNegativeButton("취소", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void compress(){
        String SD = new Environment().getExternalStorageDirectory().getAbsolutePath();
        String mainPath = SD+"/ModPkg";
        String tempPath = SD+"/Modpkg/_temp";
        String scriptDirPath = tempPath+"/script";
        String imagesDirPath = tempPath+"/images";


        File main = new File(mainPath);
        File temp = new File(tempPath);
        File scriptDir = new File(scriptDirPath);
        File imagesDir = new File(imagesDirPath);
        File script = new File(jspath);
        File virtualScript = new File(scriptDirPath+"/"+script.getName());
        File images = new File(imgpath);

        if(temp.exists()) deleteRecursive(temp);
        main.mkdirs();
        temp.mkdirs();
        scriptDir.mkdirs();
        imagesDir.mkdirs();


        String dataStr = extraData.toString();

        try {
            copyDirectory(script, virtualScript);
            copyDirectory(images, imagesDir);
            generateFile(tempPath + "/manifest.json", dataStr);
        } catch (IOException e) {
            Toast.makeText(SecondActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }

        String resultname = mainPath + "/" + extraData.name;

        //Compress compress = new Compress();
        //compress.zipFileAtPath(tempPath, resultname + ".zip");
        // File(resultname + ".zip").renameTo(new File(resultname + ".modpkg"));

        try {
            ZipFile zipFile = new ZipFile(resultname+".modpkg");
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFolder(imagesDirPath, parameters);
            zipFile.addFolder(scriptDirPath, parameters);
            zipFile.addFile(new File(tempPath + "/manifest.json"), parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }



        //
    };

    private void reveal(final View v, float x, float y, final int colornum, Runnable runnable){
        v.setEnabled(false);
        final int[] location = new int[2];
        location[0] = (int) x;
        location[1] = (int) y;

        //
        mRevealView.setBackgroundColor(getResources().getColor(accentColors[colornum]));
        mRevealView.setVisibility(View.VISIBLE);
        mRevealLayout.setVisibility(View.VISIBLE);
        mRevealLayout.show(location[0], location[1], 1200);
        v.postDelayed(runnable, 1200);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setEnabled(true);
                mRevealLayout.setVisibility(View.INVISIBLE);
                mRevealView.setVisibility(View.INVISIBLE);
                background.setBackgroundColor(getResources().getColor(accentColors[colornum]));
            }
        }, 1201);
    }

    private void indicator(int level){
        indic1.setBackgroundResource(R.drawable.indicator);
        indic2.setBackgroundResource(R.drawable.indicator);
        indic3.setBackgroundResource(R.drawable.indicator);
        if((level-1)%3 == 0) indic1.setBackgroundResource(R.drawable.indicator_large);
        if((level-1)%3 == 1) indic2.setBackgroundResource(R.drawable.indicator_large);
        if((level-1)%3 == 2) indic3.setBackgroundResource(R.drawable.indicator_large);
    }

    private void prevPage(){
        Animation inAnimation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.lefttocenter);
        viewFlipper.setInAnimation(inAnimation);

        Animation outAnimation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.centertoright);
        viewFlipper.setOutAnimation(outAnimation);

        viewFlipper.showPrevious();
        level--;
        indicator(level);
    }

    private void nextPage(){
        Animation inAnimation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.righttocenter);
        viewFlipper.setInAnimation(inAnimation);

        Animation outAnimation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.centertoleft);
        viewFlipper.setOutAnimation(outAnimation);

        viewFlipper.showNext();
        level++;
        indicator(level);
    }

    private void arrowMove(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {


            Path path = new Path();

            float x1 = arrowObj3.getX();
            float y1 = arrowObj3.getY();
            float x3 = arrowObj4.getX()+getResources().getDimension(R.dimen.arrow_obj4_right);
            float y3 = arrowObj4.getY()+getResources().getDimension(R.dimen.arrow_obj4_down);
            float x2 = (x1 - x3)/4 + x3;
            float y2 = (y1 - y3)/4*3 + y3;

            path.moveTo(x1,y1);
            path.cubicTo(x1,y1,x2,y2,x3,y3);


            ObjectAnimator objectAnimator = null;
            objectAnimator = ObjectAnimator.ofFloat(arrowObj3, View.X, View.Y, path);

            objectAnimator.setDuration(900);
            PathInterpolator pip = new PathInterpolator(0.25f,0.1f,0.25f,1f);
            objectAnimator.setInterpolator(pip);
            objectAnimator.start();
        }
    }

    public void copyDirectory(File sourceLocation , File targetLocation) throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            // make sure the directory we plan to store the recording in exists
            File directory = targetLocation.getParentFile();
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                throw new IOException("Cannot create dir " + directory.getAbsolutePath());
            }

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public void generateFile(String directory, String stuff) throws IOException{
        File gpxfile = new File(directory);
        FileWriter writer = new FileWriter(gpxfile);
        writer.append(stuff);
        writer.flush();
        writer.close();
    }

    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }
}

class ExtraData{
    String name = "";
    String author = "";
    String description = "";
    String version = "";

    String creator = "Modpkg Gen - Triangle Function.";

    @Override
    public String toString(){
        String enter = "\n";
        String a = "{" + enter +
                "    \"name\" : " + "\"" + this.name + "\"," + enter +
                "    \"version\" : " + "\"" + this.version + "\"," +  enter +
                "    \"author\" : " + "\"" + this.author + "\"," + enter +
                "    \"description\" : " + "\"" + this.description + "\"," + enter + enter +
                "    \"app_creator\" : " + "\"" + this.creator + "\"" + enter +
                "}";

        return a;
    }
}