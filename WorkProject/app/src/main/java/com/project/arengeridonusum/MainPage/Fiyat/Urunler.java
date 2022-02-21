package com.project.arengeridonusum.MainPage.Fiyat;

public class Urunler {

    private String urunImage;
    private String baslik, fiyat;

    public Urunler(String urunImage, String baslik, String fiyat) {
        this.urunImage = urunImage;
        this.baslik = baslik;
        this.fiyat = fiyat;
    }

    public String getUrunImage() {
        return urunImage;
    }

    public void setUrunImage(String urunImage) {
        this.urunImage = urunImage;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
}
