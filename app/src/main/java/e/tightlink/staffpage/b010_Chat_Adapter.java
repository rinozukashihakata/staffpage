package e.tightlink.staffpage;

/***************************************
 * Created by Tightlink on 2019/7/11. *
 ***************************************/

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static e.tightlink.staffpage.b001_ChatActivity.telname;

public class b010_Chat_Adapter extends ArrayAdapter<b020_Chat_item> {

    private int mResource;
    private List<b020_Chat_item> mItems;
    private LayoutInflater mInflater;

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * コンストラクタ
     *
     * @param context  コンテキスト
     * @param resource リソースID
     * @param items    リストビューの要素
     */
    public b010_Chat_Adapter(Context context, int resource, List<b020_Chat_item> items) {
        super(context, resource, items);

        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        view = mInflater.inflate(mResource, null);
        if (convertView != null) {
            view = convertView;
        } else {
            //view = mInflater.inflate(mResource, null);
        }

        // リストビューに表示する要素を取得
        final b020_Chat_item item = mItems.get(position);

        // IDを設定
        TextView id = (TextView) view.findViewById(R.id.id);
        id.setText(item.getId());

        // 名前を設定
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(item.getName());

        // チャットレイアウト編集
        LinearLayout linearLayout = view.findViewById(R.id.text_layout);
        TextView text = view.findViewById(R.id.text);
        text.setText(item.getmText());
        String wordget = item.getmText().substring(0, 2);
        switch (wordget) {
            case "おと":
                text.setTextColor(Color.DKGRAY);
                if (telname.equals("おとうさん")) {
                    linearLayout.setGravity(Gravity.RIGHT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_beiju, null);
                    setBackground(text, text_drawable);
                } else {
                    linearLayout.setGravity(Gravity.LEFT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_white, null);
                    setBackground(text, text_drawable);
                }
                break;
            case "おか":
                text.setTextColor(Color.rgb(167, 87, 168)); /*あずき色っぽい*/
                if (telname.equals("おかあさん")) {
                    linearLayout.setGravity(Gravity.RIGHT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_beiju, null);
                    setBackground(text, text_drawable);
                } else {
                    linearLayout.setGravity(Gravity.LEFT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_white, null);
                    setBackground(text, text_drawable);
                }
                break;
            case "永奈":
                text.setTextColor(Color.MAGENTA); /*ピンクっぽい色*/
                if (telname.equals("永奈")) {
                    linearLayout.setGravity(Gravity.RIGHT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_beiju, null);
                    setBackground(text, text_drawable);
                } else {
                    linearLayout.setGravity(Gravity.LEFT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_white, null);
                    setBackground(text, text_drawable);
                }
                break;
            case "恒太":
                text.setTextColor(Color.BLUE);
                if (telname.equals("恒太")) {
                    linearLayout.setGravity(Gravity.RIGHT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_beiju, null);
                    setBackground(text, text_drawable);
                } else {
                    linearLayout.setGravity(Gravity.LEFT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_white, null);
                    setBackground(text, text_drawable);
                }
                break;
            case "高橋":
                text.setTextColor(Color.BLACK);
                if (telname.equals("高橋")) {
                    linearLayout.setGravity(Gravity.RIGHT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_beiju, null);
                    setBackground(text, text_drawable);
                } else {
                    linearLayout.setGravity(Gravity.LEFT);
                    Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_white, null);
                    setBackground(text, text_drawable);
                }
                break;
            default:
                text.setTextColor(Color.BLACK);
                linearLayout.setGravity(Gravity.LEFT);
                Drawable text_drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_white, null);
                setBackground(text, text_drawable);
        }
        return view;
    }

    //APIレベル対応
    public void setBackground(View v, Drawable d) {
        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            //API LEVEL 16以下の時。
            v.setBackgroundDrawable(d);
        } else {
            //API LEVEL 16以上の時。
            v.setBackground(d);
        }
    }

    private RelativeLayout.LayoutParams createParam(int w, int h) {
        return new RelativeLayout.LayoutParams(w, h);
    }
}



