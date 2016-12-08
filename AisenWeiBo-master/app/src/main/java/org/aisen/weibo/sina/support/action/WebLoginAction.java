package org.aisen.weibo.sina.support.action;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.aisen.android.support.action.IAction;
import org.aisen.weibo.sina.R;
import org.aisen.weibo.sina.base.AppContext;
import org.aisen.weibo.sina.ui.activity.profile.WeiboClientActivity;
import org.aisen.weibo.sina.ui.fragment.basic.BizFragment;

import java.util.Random;

/**
 * Created by wangdan on 15/5/1.
 */
public class WebLoginAction extends IAction {

    BizFragment mBizFragment;

    int requestCode;

    public WebLoginAction(Activity context, BizFragment bizFragment) {
        super(context, null);

        requestCode = new Random().nextInt(1000);
        mBizFragment = bizFragment;
    }

    @Override
    protected boolean interrupt() {
        if (TextUtils.isEmpty(AppContext.getAccount().getCookie())) {
            doAction();

            return true;
        }

        return super.interrupt();
    }

    @Override
    public void doAction() {
        new MaterialDialog.Builder(mBizFragment.getActivity()).content(R.string.acount_timeout)
                .negativeText(R.string.no)
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mBizFragment.setDMLogin(getChild());

                        WeiboClientActivity.launchForAuth(mBizFragment, requestCode);
                    }

                })
                .show();
    }
    
    public int getRequestCode() {
        return requestCode;
    }

}
