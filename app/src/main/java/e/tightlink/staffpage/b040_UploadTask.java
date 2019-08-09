package e.tightlink.staffpage;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static e.tightlink.staffpage.b001_ChatActivity.tablename;
import static e.tightlink.staffpage.b001_ChatActivity.telname;


public class b040_UploadTask extends AsyncTask<String, Void, String> {

    private Listener listener;

    // 非同期処理
    @Override
    protected String doInBackground(String... params) {

        //FIREBASE//////////////////////////////////////////////////////////////////////////////////
//        User user = new User( "99","火事","googleのサーバーです。","2019/08/07" );
//        // インスタンスの取得
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        // ファイルパスを指定してリファレンスを取得
//        DatabaseReference refName = database.getReference("info/user");
//        // データを登録
//        refName.setValue("test");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("takahashikazunori");

        ////////////////////////////////////////////////////////////////////////////////////////////

        // 使用するサーバーのURLに合わせる
        String urlSt = "xx";

        HttpURLConnection httpConn = null;
        String result = null;
        String word = "word="+params[0]+ "&name="+ telname+ "&table=" + tablename;

        try {
            // URL設定
            URL url = new URL(urlSt);

            // コネクション取得
            httpConn = (HttpURLConnection) url.openConnection();

            // request POST
            httpConn.setRequestMethod("POST");

            // no Redirects
            httpConn.setInstanceFollowRedirects(false);

            // データを書き込む
            httpConn.setDoOutput(true);

            // 時間制限
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);

            // 接続
            httpConn.connect();

            // POSTデータ送信処理
            OutputStream outStream = null;

            try {
                outStream = httpConn.getOutputStream();
                outStream.write( word.getBytes("UTF-8"));
                outStream.flush();
                Log.d("debug","flush");
            } catch (IOException e) {
                // POST送信エラー
                e.printStackTrace();
                result="POST送信エラー";
            } finally {
                if (outStream != null) {
                    outStream.close();
                }
            }

            final int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // レスポンスを受け取る処理等
                result="メッセージ送信成功！";
            }
            else{
                result="status="+String.valueOf(status);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return result;
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(String result);
    }
}
