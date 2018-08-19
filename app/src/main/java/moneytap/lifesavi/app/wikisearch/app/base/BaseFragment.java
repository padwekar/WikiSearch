package moneytap.lifesavi.app.wikisearch.app.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import moneytap.lifesavi.app.wikisearch.app.dependency.annotation.Layout;

abstract public class BaseFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int resId = 0;

        Annotation[] annotations = this.getClass().getAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof Layout){
                Layout layout = (Layout) annotation;
                resId = layout.resId();
                break;
            }
        }

        View view = inflater.inflate(resId, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void replace(BaseFragment fragment, @IdRes int resourceId){
        if(getActivity()!=null) {
            ((BaseActivity) getActivity()).replace(fragment, resourceId);
        }
    }

}
