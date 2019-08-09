package e.tightlink.staffpage;

public class User {
    public String fire_id;
    public String fire_name;
    public String fire_text;
    public String fire_created;

    // 空のコンストラクタの宣言が必須
    public User() {
    }

    public User(String _fire_id, String _fire_name, String _fire_text, String _fire_created) {
        fire_id = _fire_id;
        fire_name = _fire_name;
        fire_text = _fire_text;
        fire_created = _fire_created;
    }
    public String getFire_id(){
        return fire_id;
    }
    public String getFire_name(){
        return fire_name;
    }
    public String getFire_text(){
        return fire_text;
    }
    public String getFire_created(){
        return fire_created;
    }
}
