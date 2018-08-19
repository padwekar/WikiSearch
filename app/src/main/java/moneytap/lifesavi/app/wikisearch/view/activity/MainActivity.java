package moneytap.lifesavi.app.wikisearch.view.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import moneytap.lifesavi.app.wikisearch.R;
import moneytap.lifesavi.app.wikisearch.app.base.BaseActivity;
import moneytap.lifesavi.app.wikisearch.view.fragment.SearchFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        replace(SearchFragment.newInstance(),R.id.container);
    }


}
