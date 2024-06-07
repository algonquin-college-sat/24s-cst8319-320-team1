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

