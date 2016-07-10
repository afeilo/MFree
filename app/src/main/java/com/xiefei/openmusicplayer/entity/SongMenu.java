package com.xiefei.openmusicplayer.entity;

import java.util.List;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenu {

    /**
     * error_code : 22000
     * total : 6087
     * havemore : 1
     */
    private int error_code;
    private int total;
    private int havemore;
    /**
     * listid : 6722
     * listenum : 34404
     * collectnum : 365
     * title : 我们一起追过的那些男团
     * pic_300 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_4b47c1b70ff20ade81542ab0eaa98609.jpg
     * tag : 组合,好听,经典
     * desc : 以前的smap、神话、小虎队，现在的Bigbang、EXO、TFBoys等，每一个都存放着我们的青春记忆
     * pic_w300 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_40d4c313734c51e4cec3f3eddd62e644.jpg
     * width : 447
     * height : 447
     * songIds : ["244226308","2089531","121939484","246838726","461360","107995304","18608843","244143658","14948047","259817183","2495475","12313695","118694801","24521226"]
     */

    private List<ContentBean> content;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getHavemore() {
        return havemore;
    }

    public void setHavemore(int havemore) {
        this.havemore = havemore;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String listid;
        private String listenum;
        private String collectnum;
        private String title;
        private String pic_300;
        private String tag;
        private String desc;
        private String pic_w300;
        private String width;
        private String height;
        private List<String> songIds;

        public String getListid() {
            return listid;
        }

        public void setListid(String listid) {
            this.listid = listid;
        }

        public String getListenum() {
            return listenum;
        }

        public void setListenum(String listenum) {
            this.listenum = listenum;
        }

        public String getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(String collectnum) {
            this.collectnum = collectnum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_300() {
            return pic_300;
        }

        public void setPic_300(String pic_300) {
            this.pic_300 = pic_300;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPic_w300() {
            return pic_w300;
        }

        public void setPic_w300(String pic_w300) {
            this.pic_w300 = pic_w300;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public List<String> getSongIds() {
            return songIds;
        }

        public void setSongIds(List<String> songIds) {
            this.songIds = songIds;
        }
    }
}
