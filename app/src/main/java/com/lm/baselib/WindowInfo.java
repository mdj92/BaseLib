package com.lm.baselib;

/**
 * @author
 * @description
 * @Date 2019/5/23
 */
public class WindowInfo {

    /**
     * winNum : 2012
     * winName : 二楼东厅12号
     * orgName : 区公安局
     * orgCode : 360721000201026000
     * id : 1
     */

    private String winNum;
    private String winName;
    private String orgName;
    private String orgCode;
    private int id =0;

    public String getWinNum() {
        return winNum;
    }

    public void setWinNum(String winNum) {
        this.winNum = winNum;
    }

    public String getWinName() {
        return winName;
    }

    public void setWinName(String winName) {
        this.winName = winName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
