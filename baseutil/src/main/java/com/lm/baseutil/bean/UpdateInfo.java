package com.lm.baseutil.bean;

/**
 * @author
 * @description 版本检测更新类
 * @Date 2019/2/21
 */
public class UpdateInfo {
    /**
     * android : {"ver":"2.5","info":"1、更新服务器，使用新接口地址；","url":"http://211.149.191.112:8887/krtAppCenter/upload/appdownload/krt015-pro-5510549246.apk?_=0.6074199614688077","betaver":"1.3.9","betainfo":"更新内容：\r\n1.优化界面","betaurl":"itms-services://?action=download-manifest&url=https://dn-createsoftware-appdownload.qbox.me/krt015-beta-0890717881.plist"}
     * ios : {"ver":"0","info":"五一前发布","url":"itms-services://?action=download-manifest&url=https://dn-createsoftware-appdownload.qbox.me/krt015-pro-6405991418.plist","betaver":"2.0","betainfo":"上架商店版"}
     */

    private AndroidBean android;
    private IosBean ios;

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public IosBean getIos() {
        return ios;
    }

    public void setIos(IosBean ios) {
        this.ios = ios;
    }

    public static class AndroidBean {
        /**
         * ver : 2.5
         * info : 1、更新服务器，使用新接口地址；
         * url : http://211.149.191.112:8887/krtAppCenter/upload/appdownload/krt015-pro-5510549246.apk?_=0.6074199614688077
         * betaver : 1.3.9
         * betainfo : 更新内容：
         1.优化界面
         * betaurl : itms-services://?action=download-manifest&url=https://dn-createsoftware-appdownload.qbox.me/krt015-beta-0890717881.plist
         */

        private String ver;
        private String info;
        private String url;
        private String betaver;
        private String betainfo;
        private String betaurl;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBetaver() {
            return betaver;
        }

        public void setBetaver(String betaver) {
            this.betaver = betaver;
        }

        public String getBetainfo() {
            return betainfo;
        }

        public void setBetainfo(String betainfo) {
            this.betainfo = betainfo;
        }

        public String getBetaurl() {
            return betaurl;
        }

        public void setBetaurl(String betaurl) {
            this.betaurl = betaurl;
        }
    }

    public static class IosBean {
        /**
         * ver : 0
         * info : 五一前发布
         * url : itms-services://?action=download-manifest&url=https://dn-createsoftware-appdownload.qbox.me/krt015-pro-6405991418.plist
         * betaver : 2.0
         * betainfo : 上架商店版
         */

        private String ver;
        private String info;
        private String url;
        private String betaver;
        private String betainfo;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBetaver() {
            return betaver;
        }

        public void setBetaver(String betaver) {
            this.betaver = betaver;
        }

        public String getBetainfo() {
            return betainfo;
        }

        public void setBetainfo(String betainfo) {
            this.betainfo = betainfo;
        }
    }
}
