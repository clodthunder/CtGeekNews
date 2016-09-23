package gt.lskj.com.geeknew.https.response;

/**
 * Created by Home on 16/9/22.
 */

public abstract class BaseResponse<T> {
    //返回的数据
    private int code;
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
