package com.example.mypregnancyjourney.reviewdoc;
import com.example.mypregnancyjourney.R;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class PdfViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_pdf);

        WebView webView = findViewById(R.id.pdfview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        // TODO: Replace here with PDFfile URL or local URL
        String pdfUrl = "https://www.clickdimensions.com/links/TestPDFfile.pdf";
        //https://css4.pub/2015/icelandic/dictionary.pdf
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl);
    }
}

//import com.example.mypregnancyjourney.R;
//import android.graphics.Bitmap;
//import android.graphics.pdf.PdfRenderer;
//import android.os.Bundle;
//import android.os.ParcelFileDescriptor;
//import android.widget.ImageView;
//import androidx.appcompat.app.AppCompatActivity;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class PdfViewActivity extends AppCompatActivity {
//
//    private PdfRenderer pdfRenderer;
//    private PdfRenderer.Page currentPage;
//    private ParcelFileDescriptor parcelFileDescriptor;
//    private ImageView pdfImageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_review_pdf);
//
////        pdfImageView = findViewById(R.id.pdfImageView);
//
//        try {
//            openRenderer();
//            showPage(0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            closeRenderer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void openRenderer() throws IOException {
//        File file = new File(getCacheDir(), "TestPDFfile.pdf");
//        if (!file.exists()) {
//            InputStream asset = getAssets().open("TestPDFfile.pdf");
//            FileOutputStream output = new FileOutputStream(file);
//            byte[] buffer = new byte[1024];
//            int size;
//            while ((size = asset.read(buffer)) != -1) {
//                output.write(buffer, 0, size);
//            }
//            asset.close();
//            output.close();
//        }
//        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
//        pdfRenderer = new PdfRenderer(parcelFileDescriptor);
//    }
//
//    private void closeRenderer() throws IOException {
//        if (currentPage != null) {
//            currentPage.close();
//        }
//        pdfRenderer.close();
//        parcelFileDescriptor.close();
//    }
//
//    private void showPage(int index) {
//        if (pdfRenderer.getPageCount() <= index) return;
//
//        if (currentPage != null) {
//            currentPage.close();
//        }
//        currentPage = pdfRenderer.openPage(index);
//        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
//        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//        pdfImageView.setImageBitmap(bitmap);
//    }
//}
