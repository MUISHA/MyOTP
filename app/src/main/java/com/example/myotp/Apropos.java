package com.example.myotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.example.myotp.login.DeshboadActivity;
import com.example.myotp.menu.Drawables;

public class Apropos extends AppCompatActivity {
    TextView textView,versionIp;
    WebView webView;
    JustifiedTextView justifiedTextView;
    String dummyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);
        textView = findViewById(R.id.text_view);
        webView = findViewById(R.id.web_view);
        justifiedTextView = findViewById(R.id.justified_text_view);
        versionIp = findViewById(R.id.versionIp);
        dummyText = getResources().getString(R.string.justifytext);
        textView.setText(dummyText);
        String webtext = String.valueOf(Html.fromHtml(
                "<![CDATA[<body style=\"text-align:justify\">"
                +dummyText+"</body>]]>"
        ));

        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webtext, "text/htlm;charset=utf-8", "UTF-8");
        justifiedTextView.setText(Html.fromHtml(dummyText));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent  intent = new Intent(this, DeshboadActivity.class);
        startActivity(intent);
        finish();
    }
}