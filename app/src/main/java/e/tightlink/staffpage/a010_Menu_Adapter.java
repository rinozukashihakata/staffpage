package e.tightlink.staffpage;

/***************************************
 * Created by Tightlink on 2019/7/11. *
 ***************************************/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class a010_Menu_Adapter extends ArrayAdapter<b020_Chat_item> {

    private int mResource;
    private List<b020_Chat_item> mItems;
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    public a010_Menu_Adapter(Context context, int resource, List<b020_Chat_item> items) {
        super(context, resource, items);

        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        }
        else {
            view = mInflater.inflate(mResource, null);
        }

        // リストビューに表示する要素を取得
        final b020_Chat_item item = mItems.get(position);

        // IDを設定
        TextView id = (TextView)view.findViewById(R.id.id);
        id.setText(item.getId());

        // 名前を設定
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(item.getName());

        // テキストを設定
        TextView text = (TextView)view.findViewById(R.id.text);
        text.setText(item.getmText());
        String wordget = item.getmText().substring(0,2);

        return view;
    }
}