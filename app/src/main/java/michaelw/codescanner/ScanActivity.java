package michaelw.codescanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Michael.W on 6/1/2015.
 */
public class ScanActivity extends Activity {

    private SurfaceView sfvCamera;
    private SFHCamera sfhCamera;
    private ImageView imgView;
    private View centerView;
    private TextView txtScanResult;
    private Timer mTimer;
    private MyTimerTask mTimerTask;
    // 按照标准HVGA
    final static int width = 480;
    final static int height = 320;
    int dstLeft, dstTop, dstWidth, dstHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        try {
            Context context=createPackageContext("micharlw.codescanner", 0);						//Obtain the context.
        }catch(NameNotFoundException e) {

        }

        imgView = (ImageView) this.findViewById(R.id.ImageView01);
        centerView = (View) this.findViewById(R.id.centerView);
        sfvCamera = (SurfaceView) this.findViewById(R.id.sfvCamera);
        sfhCamera = new SFHCamera(sfvCamera.getHolder(), width, height, previewCallback);
        txtScanResult=(TextView)this.findViewById(R.id.txtScanResult);
        // 初始化定时器
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        mTimer.schedule(mTimerTask, 0, 80);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (dstLeft == 0) {//只赋值一次
                dstLeft = centerView.getLeft() * width / getWindowManager().getDefaultDisplay().getWidth();
                dstTop = centerView.getTop() * height / getWindowManager().getDefaultDisplay().getHeight();
                dstWidth = (centerView.getRight() - centerView.getLeft())* width / getWindowManager().getDefaultDisplay().getWidth();
                dstHeight = (centerView.getBottom() - centerView.getTop())* height / getWindowManager().getDefaultDisplay().getHeight();
            }
            sfhCamera.AutoFocusAndPreviewCallback();
        }
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera arg1) {
            //取得指定范围的帧的数据
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, width, height, dstLeft, dstTop, dstWidth, dstHeight, false);
            //取得灰度图
            //Bitmap mBitmap = source.renderCroppedGreyscaleBitmap();
            ;
            //显示灰度图
            //imgView.setImageBitmap(mBitmap);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader = new MultiFormatReader();
            try {
                Result result = reader.decode(bitmap);
                String strResult = "BarcodeFormat:"
                        + result.getBarcodeFormat().toString() + "  text:"
                        + result.getText();
                txtScanResult.setText(strResult);
            } catch (Exception e) {
                txtScanResult.setText("Scanning");
            }
        }
    };
}
