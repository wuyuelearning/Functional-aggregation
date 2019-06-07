package utils.web;

import java.util.List;

/**
 * Created by Steve on 2017/3/16.
 */

public class HybridConfig {


    /**
     * version : e32ff332fd9f6b97798d1e707ebd863a datas : [{"bName":"hotel123","bVersion":"123","bUrl":"","pUrl":"123","openType":"H5Online","md5":"a6217368a2a76e4edbbb98fd81ab0f47"},{"bName":"hotel","bVersion":"1.0.0","bUrl":"https://pics.lvjs.com.cn/pics/m.lvmama.com","pUrl":"http://m.lvmama.com","openType":"H5Local","md5":"12312312313"},{"bName":"hotel","bVersion":"1","bUrl":"https://pics.lvjs.com.cn/pics/h5local/2017/01/60690.7z","pUrl":"2","openType":"H5Local","md5":"a6217368a2a76e4edbbb98fd81ab0f47"}]
     * msg : success! code : 1
     */

    private String version;
    private String msg;
    private String code;
    private List<DatasEntity> datas;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DatasEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasEntity> datas) {
        this.datas = datas;
    }

    public static class DatasEntity {

        /**
         * bName : hotel123
         * bVersion : 123
         * bUrl :
         * pUrl : 123
         * openType : H5Online
         * md5 : a6217368a2a76e4edbbb98fd81ab0f47
         */

        private String bName;
        private String bVersion;
        private String bUrl;
        private String pUrl;
        private String openType;
        private String md5;
        private boolean isFS;

        public boolean isFS() {
            return isFS;
        }

        public void setFS(boolean FS) {
            isFS = FS;
        }

        public String getBName() {
            return bName;
        }

        public void setBName(String bName) {
            this.bName = bName;
        }

        public String getBVersion() {
            return bVersion;
        }

        public void setBVersion(String bVersion) {
            this.bVersion = bVersion;
        }

        public String getBUrl() {
            return bUrl;
        }

        public void setBUrl(String bUrl) {
            this.bUrl = bUrl;
        }

        public String getPUrl() {
            return pUrl;
        }

        public void setPUrl(String pUrl) {
            this.pUrl = pUrl;
        }

        public String getOpenType() {
            return openType;
        }

        public void setOpenType(String openType) {
            this.openType = openType;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }
}
