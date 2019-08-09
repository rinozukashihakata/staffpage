package e.tightlink.staffpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class z100_BackPress extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //ダイアログ表示
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // ダイアログの設定
        alertDialog.setTitle("アプリの終了");      //タイトル設定
        alertDialog.setMessage("終了してもよろしいですか。");  //内容(メッセージ)設定
        // OK(肯定的な)ボタンの設定
        alertDialog.setPositiveButton("いいえ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // NG(否定的な)ボタンの設定
        alertDialog.setNegativeButton("はい", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getActivity().moveTaskToBack(true);
            }
        });
        AlertDialog dialog = alertDialog.create();
        return dialog;
    }
}
