package mz.org.csaude.mentoring.base.auth;

import android.content.Context;
import android.media.session.MediaSessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptorImpl implements Interceptor {

    private SessionManager sessionManager;
    private Context context;

    public AuthInterceptorImpl(Context context) {
        this.context = context;
        this.sessionManager = new SessionManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                                    .header("Authorization","Bearer "+ this.sessionManager.fetchAuthToken())
                                    .build();

        return chain.proceed(newRequest);
    }
}
