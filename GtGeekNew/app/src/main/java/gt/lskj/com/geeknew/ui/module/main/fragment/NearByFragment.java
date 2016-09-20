package gt.lskj.com.geeknew.ui.module.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gt.lskj.com.geeknew.R;

/**
 * Created by Home on 16/9/20.
 */

public class NearByFragment extends Fragment {
    private View mView;

    //该周期在第二条路的情况下只会执行一次
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //实例化数据集合
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_nearby, container, false);
        }
        return mView;
    }
}
