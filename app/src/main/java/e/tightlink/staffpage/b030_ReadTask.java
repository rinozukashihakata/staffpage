package e.tightlink.staffpage;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.OutputStream;

import static e.tightlink.staffpage.b001_ChatActivity.tablename;

public final class b030_ReadTask extends AsyncTask<URL, Void, String> {

    private ReadListener readlistener;

    @Override
    protected String doInBackground(URL... urls) {

        String read_word = "table=" + tablename;

        // 取得したテキストを格納する変数
        final StringBuilder receive_result = new StringBuilder();
        // 取得した日付を格納する変数
        final StringBuilder receive_date = new StringBuilder();

        // アクセス先URL
        final URL url = urls[0];

        HttpURLConnection con = null;
        try {
            // ローカル処理
            // コネクション取得
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // no Redirects
            con.setInstanceFollowRedirects(false);
            con.setDoInput(true);
            con.setDoOutput(true);

            // 時間制限
            con.setReadTimeout(10000);
            con.setConnectTimeout(20000);

            // 接続
            con.connect();

            // POSTデータ送信処理
            OutputStream outStream = null;

            try {
                outStream = con.getOutputStream();
                outStream.write( read_word.getBytes("UTF-8"));
                outStream.flush();
                Log.d("debug","flush");
            } catch (IOException e) {
                // POST送信エラー
                e.printStackTrace();
            } finally {
                if (outStream != null) {
                    outStream.close();
                }
            }

            // HTTPレスポンスコード
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // テキストを取得する
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding){
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while((line = bufReader.readLine()) != null) {
                    receive_result.append(line);
                    receive_result.append("=");                         /*"="を改行コードとして使用*/
                }
                bufReader.close();
                inReader.close();
                in.close();
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                // コネクションを切断
                con.disconnect();
            }
        }
        return receive_result.toString();
    }
    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(String receive_result) {
        super.onPostExecute(receive_result);

        if (readlistener != null) {
            readlistener.onSuccess(receive_result);
        }
    }
    void setListener(ReadListener listener) {
        this.readlistener = listener;
    }
    interface ReadListener {
        void onSuccess(String receive_result);
    }
}
