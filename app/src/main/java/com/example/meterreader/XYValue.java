package com.example.meterreader;

public class XYValue {
    private String xCustname;
    private String yMeterno;
    private String zRead;
    private String uReadDate;

    public XYValue(String xCustname, String yMeterno, String zRead, String uReadDate) {
        this.xCustname = xCustname;
        this.yMeterno = yMeterno;
        this.zRead = zRead;
        this.uReadDate = uReadDate;
    }

    public String getXCustname() {
        return xCustname;
    }

    public void setXCustname(String xCustname) {
        this.xCustname = xCustname;
    }

    public String getYMeterno() {
        return yMeterno;
    }

    public void setYMeterno(String yMeterno) {
        this.yMeterno = yMeterno;
    }
    
    public String getZRead() {
        return zRead;
    }

    public void setZRead(String zRead) {
        this.zRead = zRead;
    }

    public String getUReadDate() {
        return uReadDate;
    }

    public void setUReadDate(String zRead) {
        this.uReadDate = uReadDate;
    }
}
