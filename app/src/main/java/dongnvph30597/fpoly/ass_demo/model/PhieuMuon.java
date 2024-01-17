package dongnvph30597.fpoly.ass_demo.model;

import java.util.Date;

public class PhieuMuon {
    public int maPm;
    public String maTT;
    public int maTV;
    public int maSach;
    public Date ngay;
    public int tienThue;
    public int traSach;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPm, String maTT, int maTV, int maSach, Date ngay, int tienThue, int traSach) {
        this.maPm = maPm;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngay = ngay;
        this.tienThue = tienThue;
        this.traSach = traSach;
    }

    public int getMaPm() {
        return maPm;
    }

    public void setMaPm(int maPm) {
        this.maPm = maPm;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }
}
