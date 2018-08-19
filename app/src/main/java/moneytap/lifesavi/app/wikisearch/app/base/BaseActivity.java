package moneytap.lifesavi.app.wikisearch.app.base;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

@SuppressLint("Registered")
abstract public class BaseActivity extends AppCompatActivity {

    public void replace(BaseFragment fragment, @IdRes int resourceId){
        FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("");
        transaction.replace(resourceId,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1){
            super.onBackPressed();
        } else {
            finish();
        }
    }
}
