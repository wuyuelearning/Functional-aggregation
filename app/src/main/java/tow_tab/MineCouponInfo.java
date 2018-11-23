package tow_tab;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MineCouponInfo implements Parcelable {
    public MineCouponData data;
    public String message;
    public String errorMessage;
    public String version;
    public String code;// 1返回正确，-1返回错误

    protected MineCouponInfo(Parcel in) {
        data = in.readParcelable(MineCouponData.class.getClassLoader());
        message = in.readString();
        errorMessage = in.readString();
        version = in.readString();
        code = in.readString();
    }

    public static final Creator<MineCouponInfo> CREATOR = new Creator<MineCouponInfo>() {
        @Override
        public MineCouponInfo createFromParcel(Parcel in) {
            return new MineCouponInfo(in);
        }

        @Override
        public MineCouponInfo[] newArray(int size) {
            return new MineCouponInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeString(message);
        dest.writeString(errorMessage);
        dest.writeString(version);
        dest.writeString(code);
    }


    public static class MineCouponData implements Parcelable {
        public boolean hasNext;
        public List<MineCouponBean> list = new ArrayList<>();


        protected MineCouponData(Parcel in) {
            hasNext = in.readByte() != 0;
            list = in.createTypedArrayList(MineCouponBean.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (hasNext ? 1 : 0));
            dest.writeTypedList(list);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MineCouponData> CREATOR = new Creator<MineCouponData>() {
            @Override
            public MineCouponData createFromParcel(Parcel in) {
                return new MineCouponData(in);
            }

            @Override
            public MineCouponData[] newArray(int size) {
                return new MineCouponData[size];
            }
        };
    }

    public static class MineCouponBean implements Parcelable {
        public String gotTime="111222";
        public boolean onlyWireless;
        public boolean isDiscount;
        public String code="111222";
        public String expiredDate="111222";
        public String name="111222";
        public String price="111222";
        public String couponType="111222";
        public String userType="111222";
        public String maxCoupon="111222";
        public String useLimit="111222";
        public String platform="111222";
        public String useScope="111222";
        public String discountAmount="111222";
        public String handledExpireDate="111222";
        public String validInfo;   //用户输入券码时不可用原因
        public boolean isSelect =false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public MineCouponBean() {
        }


        protected MineCouponBean(Parcel in) {
            gotTime = in.readString();
            onlyWireless = in.readByte() != 0;
            code = in.readString();
            expiredDate = in.readString();
            name = in.readString();
            price = in.readString();
            couponType = in.readString();
            userType = in.readString();
            maxCoupon = in.readString();
            useLimit = in.readString();
            platform = in.readString();
            useScope = in.readString();
            discountAmount = in.readString();
            handledExpireDate = in.readString();
            validInfo = in.readString();
        }

        public static final Creator<MineCouponBean> CREATOR = new Creator<MineCouponBean>() {
            @Override
            public MineCouponBean createFromParcel(Parcel in) {
                return new MineCouponBean(in);
            }

            @Override
            public MineCouponBean[] newArray(int size) {
                return new MineCouponBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(gotTime);
            dest.writeByte((byte) (onlyWireless ? 1 : 0));
            dest.writeString(code);
            dest.writeString(expiredDate);
            dest.writeString(name);
            dest.writeString(price);
            dest.writeString(couponType);
            dest.writeString(userType);
            dest.writeString(maxCoupon);
            dest.writeString(useLimit);
            dest.writeString(platform);
            dest.writeString(useScope);
            dest.writeString(discountAmount);
            dest.writeString(handledExpireDate);
            dest.writeString(validInfo);

        }
    }
}
