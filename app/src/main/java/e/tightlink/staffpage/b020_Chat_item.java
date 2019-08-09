package e.tightlink.staffpage;

/***************************************
 * Created by Tightlink on 2019/7/11. *
 ***************************************/

public class b020_Chat_item {
    private String mId = null;
    private String mName = null;
    private String mText = null;

    /**
     * 空のコンストラクタ
     */
    public b020_Chat_item() {};

    /**
     * コンストラクタ
     * @param id
     * @param name
     * @param text
     */
    public b020_Chat_item(String id, String name, String text) {
        mId = id;
        mName = name;
        mText = text;
    }

    /**
     * IDを設定
     * @param id
     */
    public void setmId(String id) {
        mId = id;
    }

    /**
     * 名前を設定
     * @param name
     */
    public void setmName(String name) {
        mName = name;
    }

    /**
     * テキストを設定
     * @param text
     */
    public void setmText(String text) {
        mText = text;
    }

    /**
     * IDを取得
     */
    public String getId() {
        return mId;
    }

    /**
     * 名前を取得
     * @return 名前
     */
    public String getName() {
        return mName;
    }

    /**
     * テキストを取得
     * @return テキスト
     */
    public String getmText() {
        return mText;
    }

}