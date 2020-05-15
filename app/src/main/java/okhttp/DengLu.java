package okhttp;

/**
 * Created by Administrator on 2017/9/2 0002.
 */
public class DengLu  {
    String data;
    String rt_code;
    String type;

    public DengLu(){

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRt_code() {
        return rt_code;
    }

    public void setRt_code(String rt_code) {
        this.rt_code = rt_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DengLu{" +
                "data='" + data + '\'' +
                ", rt_code='" + rt_code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
