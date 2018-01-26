package rv.daimhim.rvdecorationtest;

import android.content.Context;
import android.content.Intent;
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

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_linearlayout:
                startActivity(new Intent(mContext,LinearLayoutActivity.class));
                break;
            case R.id.tv_gridlayout:
                startActivity(new Intent(mContext,GridLayoutActivity.class));
                break;
            case R.id.tv_staggeredgridlayout:
                startActivity(new Intent(mContext,StaggeredGridLayoutActivity.class));
                break;
            default:
                break;
        }

    }

}
