package ca.uwindsor.squiggle.squiggle_new;

import android.accounts.Account;
import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import com.google.android.gms.auth.*;

public class GetOAuthToken extends AsyncTask<Void, Void, Void> {
    Activity mActivity;
    String mScope;
    Account mAccount;
    int mRequestCode;

    GetOAuthToken(Activity activity, Account account, String scope, int requestCode) {
     this.mActivity = activity;
        this.mScope = scope;
        this.mAccount = account;
        this.mRequestCode = requestCode;
}

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String token = fetchToken();
            if (token != null) {
                ((api)mActivity).onTokenReceived(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchToken() throws IOException {
        String accessToken;
        try {
            accessToken = GoogleAuthUtil.getToken(mActivity, mAccount, mScope);
            GoogleAuthUtil.clearToken (mActivity, accessToken); // used to remove stale tokens.
            accessToken = GoogleAuthUtil.getToken(mActivity, mAccount, mScope);
            return accessToken;
        } catch (UserRecoverableAuthException userRecoverableException) {
            mActivity.startActivityForResult(userRecoverableException.getIntent(), mRequestCode);
        } catch (GoogleAuthException fatalException) {
            fatalException.printStackTrace();
        }
        return null;
    }
}