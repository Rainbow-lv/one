package lll.com.pindao.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag_01 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        Bundle bundle = getArguments();
        double random = Math.random();
        textView.setText("我是第一个Tab");
        return textView;
    }

    public static Frag_01 getInstances(String name){
        Frag_01 blankFragment = new Frag_01();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        blankFragment.setArguments(bundle);
        return blankFragment;
    }
}
