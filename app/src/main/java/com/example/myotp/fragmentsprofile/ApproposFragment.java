package com.example.myotp.fragmentsprofile;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codesgood.views.JustifiedTextView;
import com.example.myotp.R;

public class ApproposFragment extends Fragment {
    TextView textView;
    WebView webView;
    JustifiedTextView justifiedTextView;
    String dummyText;
    public ApproposFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appropos,container,false);
        textView = view.findViewById(R.id.text_view);
        webView = view.findViewById(R.id.web_view);
        justifiedTextView = view.findViewById(R.id.justified_text_view);
        dummyText = getResources().getString(R.string.justifytext);
        textView.setText(dummyText);
        String webtext = String.valueOf(Html.fromHtml(
                "<![CDATA[<body style=\"text-align:justify\">"
                        +dummyText+"</body>]]>"
        ));
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webtext, "text/htlm;charset=utf-8", "UTF-8");
        justifiedTextView.setText(Html.fromHtml(dummyText));
        return view;
    }
}
