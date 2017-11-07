package rv.daimhim.rvdecorationtest;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rv.daimhim.rvdecorationtest.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.rl_RelativeLayout)
    View mRlRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.rl_RelativeLayout, R.id.cl_ConstraintLayout,R.id.tv_content})
    public void onViewClicked() {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        mTvContent.getGlobalVisibleRect(rect);
        mRlRelativeLayout.getGlobalVisibleRect(rect2);
        Log.i("TAG", rect.toString() + ":" + rect2.toString() + ":" + rect2.contains(rect)+":"+mTvContent.getGlobalVisibleRect(rect));
        Snackbar.make(mTvContent, rect.toString() + ":" + rect2.toString(), Snackbar.LENGTH_SHORT).show();

    }

}
