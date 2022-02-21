package com.project.arengeridonusum.Yonetim.UrunEkleme;

public class UrunlerModel {
    private String resim, isim, fiyat;

    public UrunlerModel(String resim, String isim, String fiyat) {
        this.resim = resim;
        this.isim = isim;
        this.fiyat = fiyat;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
}
