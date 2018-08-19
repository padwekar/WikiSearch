package moneytap.lifesavi.app.wikisearch.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import moneytap.lifesavi.app.wikisearch.R;
import moneytap.lifesavi.app.wikisearch.app.base.BaseFragment;
import moneytap.lifesavi.app.wikisearch.app.constant.Constant;
import moneytap.lifesavi.app.wikisearch.app.dependency.annotation.Layout;

@Layout(resId = R.layout.fragment_details)
public class DetailFragment extends BaseFragment {

    @BindView(R.id.web_view)
    public WebView webView;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    public String pageId = "";

    public static DetailFragment newInstance(String pageId) {
        Bundle args = new Bundle();
        args.putString(Constant.Bundle.PAGE_ID,pageId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            pageId = getArguments().getString(Constant.Bundle.PAGE_ID);
        }

        String url = Constant.UrlConstant.DETAIL_URL + pageId;
        webView.getSettings().setUserAgentString("Android");
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
