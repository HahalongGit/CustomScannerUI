package posapp.com.qgsypos.sdk.aidl.reader;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 读卡记录数据模型
 *
 * @author runningDigua
 * @date 2019/12/25
 */
public class SinopecICRec implements Parcelable {
    /**
     * 卡号
     */
    private String SN;
    /**
     * 交易日期+时间（YYYY-MM-DD HH：mm：ss）
     */
    private String Date;
    /**
     * 交易金额，元为单位
     */
    private float Amounts;
    /**
     * 终端机编号
     */
    private String Terminal_No;
    /**
     * 返回的消息，比如错误消息等内容
     */
    private String Msg;
    /**
     * 状态，对应上述的常量值
     */
    private short Status;
    /**
     * 交易的类型
     */
    private int Type;
    /**
     * 卡交易序号
     */
    private int CTC;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SN);
        dest.writeString(this.Date);
        dest.writeFloat(this.Amounts);
        dest.writeString(this.Terminal_No);
        dest.writeString(this.Msg);
        dest.writeInt(this.Status);
        dest.writeInt(this.Type);
        dest.writeInt(this.CTC);
    }

    public SinopecICRec() {}

    protected SinopecICRec(Parcel in) {
        this.SN = in.readString();
        this.Date = in.readString();
        this.Amounts = in.readFloat();
        this.Terminal_No = in.readString();
        this.Msg = in.readString();
        this.Status = (short) in.readInt();
        this.Type = in.readInt();
        this.CTC = in.readInt();
    }

    public static final Parcelable.Creator<SinopecICRec> CREATOR = new Parcelable.Creator<SinopecICRec>() {
        @Override
        public SinopecICRec createFromParcel(Parcel source) {return new SinopecICRec(source);}

        @Override
        public SinopecICRec[] newArray(int size) {return new SinopecICRec[size];}
    };
}
