package luanvan.luanvantotnghiep.CheckInternet;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InternetCheck extends AsyncTask<Void, Void, Boolean> {

    private AsyncTaskListener mListener;

    public InternetCheck() {
    }

    public void setListener(AsyncTaskListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 10000);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        if (mListener != null) {
            mListener.passResultInternet(internet);
        }
    }
}

