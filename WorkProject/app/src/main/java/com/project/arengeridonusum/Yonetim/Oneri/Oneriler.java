package com.project.arengeridonusum.Yonetim.Oneri;

public class Oneriler {
    private String tarih, baslik, aciklama, key, okundu;

    public Oneriler(String tarih, String baslik, String aciklama, String key, String okundu) {
        this.tarih = tarih;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.key = key;
        this.okundu = okundu;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOkundu() {
        return okundu;
    }

    public void setOkundu(String okundu) {
        this.okundu = okundu;
    }
}
