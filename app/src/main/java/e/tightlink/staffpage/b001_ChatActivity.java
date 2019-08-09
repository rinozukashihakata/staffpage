package e.tightlink.staffpage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class b001_ChatActivity extends AppCompatActivity {

    private b040_UploadTask task;
    private b030_ReadTask readtask;
    private TextView textView, terminal_id;
    // wordを入れる
    private EditText editText;
    private GridView gridView1;
    private b010_Chat_Adapter adapter;
    static String telnumber, telname, tablename;
    private int checking = 0;
    private int checked = 0;
    ArrayList<b020_Chat_item> listItems1;
    private RelativeLayout.LayoutParams textLayoutParams;


    // phpがPOSTで受け取ったwordを入れて作成するHTMLページ(適宜合わせてください)
    String url = "https://android.knilthgit.work/insert.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b001_chat);


        //初期化
        listItems1 = new ArrayList<>();


        //端末の電話番号を調べる
        terminal_id = findViewById(R.id.terminal_id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 111);
            }
            telnumber = telephonyManager.getDeviceId();
        }

        telnum_check();         /*端末id毎に名前を設定*/
        terminal_id.setText(telname);   /*使用端末名を画面左上に表示*/

        editText = findViewById(R.id.uriname);
        textView = findViewById(R.id.text_view);
        // レイアウトからグリッドビューを取得
        gridView1 = findViewById(R.id.Gridview1);

        data_receive();         /*起動時のメッセージ受信*/

        //グリッドビュー押下時
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View message_view = adapter.getView(position, null, gridView1);
                TextView textView = message_view.findViewById(R.id.text);
                String message = textView.getText().toString();
                Toast ts = Toast.makeText(b001_ChatActivity.this, "" + message, Toast.LENGTH_SHORT);
                ts.setGravity(Gravity.CENTER, 0, 0);
                ts.show();
            }
        });

        final Button post = findViewById(R.id.post);
        // ボタンをタップして非同期処理（送信）を開始
        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String param0 = editText.getText().toString();
                post.setEnabled(false);
                TextView textpost = (TextView)findViewById(R.id.post);
                textpost.setText("送信中");
                if (param0.length() != 0) {
                    task = new b040_UploadTask();
                    task.setListener(createListener());
                    task.execute(param0);
                }
                data_receive();         /*データ受信*/
                post.setEnabled(true);
                textpost.setText("送信");
            }
        });

        Button receive_get = findViewById(R.id.receiveget);
        // ボタンをタップして非同期処理（受信）を開始
        receive_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_receive();         /*データ受信*/
            }
        });
    }

    @Override
    protected void onDestroy() {
        task.setListener(null);
        super.onDestroy();
    }

    //    送信後
    private b040_UploadTask.Listener createListener() {
        return new b040_UploadTask.Listener() {
            @Override
            public void onSuccess(String result) {
                textView.setText(result);
                editText.setText("");
            }
        };
    }

    //    受信後
    private b030_ReadTask.ReadListener create2Listener() {
        return new b030_ReadTask.ReadListener() {
            @Override
            public void onSuccess(String receive_result) {

                String grid[];
                grid = receive_result.split("=", 0);        /*"="で改行*/
                //初期化
                listItems1 = new ArrayList<>();

                // 出力結果をグリッドビューに表示
                b020_Chat_item item;
                int cut_length = 21;                                        /*日時の文字数*/
                for (int i = 0; i < grid.length; i++) {
                    int size = grid[i].length();                           /*文字数を取得*/
                    int text_size = size - cut_length;                     /*日時を除いた文字数を計算*/
                    if (size != 0) {
                        item = new b020_Chat_item(grid[i].substring(text_size), "", grid[i].substring(0, text_size));
                        listItems1.add(item);
                    }
                }
                if (!receive_result.equals("")) {
                    adapter = new b010_Chat_Adapter(getApplicationContext(), R.layout.b020_chat_item, listItems1);
                    gridView1.setAdapter(adapter);
                    textView.setText("");
                }
            }
        };
    }

    //データ受信
    public void data_receive() {
        try {
            readtask = new b030_ReadTask();
            readtask.execute(new URL("https://android.knilthgit.work/select.php"));
            readtask.setListener(create2Listener());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //バックキー押下
    public void onBackPressed() {
        z100_BackPress dialog = new z100_BackPress();
        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "z100_BackPress");
    }

    private void telnum_check() {
        switch (telnumber) {
            case "354631091939987":
                telname = "おとうさん";
                tablename = "takahashi";
                break;
            case "09098003855":
                telname = "おかあさん";
                tablename = "takahashi";
                break;
            case "356110070624033":
                telname = "永奈";
                tablename = "takahashi";
                break;
            case "356110070397291":
                telname = "恒太";
                tablename = "takahashi";
                break;
            case "359657080365541":
                telname = "高橋";
                tablename = "company";
                break;
            default:
                telname = "その他";
                tablename = "company";
        }
    }
}