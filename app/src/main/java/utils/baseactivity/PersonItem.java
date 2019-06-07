package utils.baseactivity;

import android.text.TextUtils;

import java.io.Serializable;

//MemoryPayload使用
public class PersonItem implements Serializable {
    private static final long serialVersionUID = 1L;
    //7.8.0周边游玩人新增时存放时间戳唯一标示游玩人
    public long key;
    private String lastName;//姓
    private String firstName;//名
    private String certNo;//证件号码
    /**
     * "ID_CARD", "HUZHAO", "GANGAO", "TAIBAO","HUIXIANG","TAIBAOZHENG","CUSTOMER_SERVICE_ADVICE","GANGAORESIDENCE","TAIBAORESIDENCE"
     */
    private String certType;//证件类型
    private String validatity;//证件有效期
    private String issued;//证件签发地
    private String birthday;
    private String receiverGender;//性别 M F
    private String mobileNumber;
    private String receiverId;//联系人编号
    private String receiverName;//游玩人姓名
    private String email;
    /**
     * PEOPLE_TYPE_ADULT;PEOPLE_TYPE_CHILD
     */
    private String peopleType;//旅客类型(ADULT-成人、CHILD-儿童))

    public boolean isCheck = false;
    public boolean currentVisible = false;
    //周边游游玩人需要按点选顺序来选择，这里新建一个变量用于保存选择的时间（原来的的逻辑跟需求有冲突，造成这种奇葩的写法-_-！）
    public long checkTime;

    public boolean isNeedCompletion;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getReceiverGender() {
        return receiverGender;
    }

    public void setReceiverGender(String receiverGender) {
        this.receiverGender = receiverGender;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getMobileNumber() {
        if (!TextUtils.isEmpty(mobileNumber)) {
            mobileNumber = mobileNumber.trim().replaceAll(" ", "");
        }
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getValidatity() {
        return validatity;
    }

    public void setValidatity(String validatity) {
        this.validatity = validatity;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public boolean isCurrentVisible() {
        return currentVisible;
    }

    public void setCurrentVisible(boolean isVisible) {
        this.currentVisible = isVisible;
    }

    public static enum PersonType {
        ID_CARD(), HUZHAO(), GANGAO(), TAIBAO(), TAIBAOZHENG(), HUIXIANG(), CUSTOMER_SERVICE_ADVICE(),GANGAORESIDENCE(),TAIBAORESIDENCE();
    }

    public boolean isNotEmpty() {
        return !TextUtils.isEmpty(receiverId) || !TextUtils.isEmpty(receiverName)
                || !TextUtils.isEmpty(firstName) || !TextUtils.isEmpty(lastName)
                || !TextUtils.isEmpty(certType) || !TextUtils.isEmpty(certNo);
    }

    public void copy(PersonItem pi) {
        if (null == pi) return;
        if (!TextUtils.isEmpty(pi.birthday)) {
            birthday = pi.birthday;
        }
        if (!TextUtils.isEmpty(pi.certType)) {
            certType = pi.certType;
        }
        if (!TextUtils.isEmpty(pi.certNo)) {
            certNo = pi.certNo;
        }
        if (!TextUtils.isEmpty(pi.email)) {
            email = pi.email;
        }
        if (!TextUtils.isEmpty(pi.receiverName)) {
            receiverName = pi.receiverName;
        }
        if (!TextUtils.isEmpty(pi.lastName)) {
            lastName = pi.lastName;
        }
        if (!TextUtils.isEmpty(pi.firstName)) {
            firstName = pi.firstName;
        }
        if (!TextUtils.isEmpty(pi.issued)) {
            issued = pi.issued;
        }
        if (!TextUtils.isEmpty(pi.peopleType)) {
            peopleType = pi.peopleType;
        }
        if (!TextUtils.isEmpty(pi.mobileNumber)) {
            mobileNumber = pi.mobileNumber;
        }
        if (!TextUtils.isEmpty(pi.validatity)) {
            validatity = pi.validatity;
        }
        if (!TextUtils.isEmpty(pi.receiverGender)) {
            receiverGender = pi.receiverGender;
        }
    }
}