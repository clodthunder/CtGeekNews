package gt.lskj.com.geeknew.ui.module.main.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import gt.lskj.com.geeknew.R;
import gt.lskj.com.geeknew.ui.base.BaseNActivity;
import gt.lskj.com.geeknew.utils.NetworkUtil;
import gt.lskj.com.geeknew.utils.SharePreferenceUtil;

/**
 * Created by codeest on 16/8/20.
 */

public class TechDetailActivity extends BaseNActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.wv_tech_content)
    WebView wvTechContent;
    @BindView(R.id.view_loading)
    RotateLoading viewLoading;

    //    RealmHelper mRealmHelper;
    MenuItem menuItem;
    String title, url, id;
    boolean isLiked;

    @Override
    protected void initToolBar() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_tech_detail;
    }

    @Override
    protected void initEventAndData() {
//        mRealmHelper = App.getAppComponent().realmHelper();
        Intent intent = getIntent();
//        tech = intent.getExtras().getString("tech");
        title = intent.getExtras().getString("title");
        url = intent.getExtras().getString("url");
        id = intent.getExtras().getString("id");
        setToolBar(toolBar, title);
        WebSettings settings = wvTechContent.getSettings();
        if (SharePreferenceUtil.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (SharePreferenceUtil.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (NetworkUtil.isNetworkConnected()) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        wvTechContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(TechDetailActivity.this, "image url:" + url, Toast.LENGTH_SHORT).show();
                if (url == null) {
                    return false;
                    //use whatever image formats you are looking for here.
                } else if (url.trim().toLowerCase().endsWith(".img")) {
                    String imageUrl = url;//here is your image url, do what you want with it
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
//                super.onPageFinished(webView, s);
                //如果渲染后有视频播发 就得把加速器关闭
                wvTechContent.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        });
        wvTechContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    viewLoading.stop();
                } else {
                    if (!viewLoading.isStart()) {
                        viewLoading.start();
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
        if (Build.VERSION.SDK_INT >= 19) {//硬件加速器的使用
            wvTechContent.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            wvTechContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        wvTechContent.loadUrl(url);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvTechContent.canGoBack()) {
            wvTechContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.tech_meun, menu);
//        menuItem = menu.findItem(R.id.action_like);
//        setLikeState(mRealmHelper.queryLikeId(id));
//        return true;
//    }

//    @Override public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_like:
//                if(isLiked) {
//                    item.setIcon(R.drawable.ic_toolbar_like_n);
//                    mRealmHelper.deleteLikeBean(this.id);
//                } else {
//                    item.setIcon(R.drawable.ic_toolbar_like_p);
//                    RealmLikeBean bean = new RealmLikeBean();
//                    bean.setId(this.id);
//                    bean.setImage(url);
//                    bean.setTitle(title);
//                    bean.setType(TechPresenter.getTechType(tech));
//                    bean.setTime(System.currentTimeMillis());
//                    mRealmHelper.insertLikeBean(bean);
//                }
//                break;
//            case R.id.action_copy:
//                ClipBoardUtil.copyToClipBoard(mContext,url);
//                return true;
//            case R.id.action_share:
//                ShareUtil.shareText(mContext,url,"分享一篇文章");
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void setLikeState(boolean state) {
//        if(state) {
//            menuItem.setIcon(R.drawable.ic_toolbar_like_p);
//            isLiked = true;
//        } else {
//            menuItem.setIcon(R.drawable.ic_toolbar_like_n);
//            isLiked = false;
//        }
//    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            }
        }
    }
}
