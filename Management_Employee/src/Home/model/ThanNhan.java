
package Home.model;

public class ThanNhan {
    private int MaTN;
    private String HoTen;
    private String NgheNghiep;
    private String MoiQuanHe;
    private String MaNV;
    private Boolean GiamTruPhuThuoc;
    
    public ThanNhan(){
    }
    
    public ThanNhan(int MaTN,String HoTen,String NgheNghiep,
            String MoiQuanHe,String MaNV,Boolean GiamTruPhuThuoc){
        this.MaTN = MaTN;
        this.HoTen = HoTen;
        this.NgheNghiep = NgheNghiep;
        this.MoiQuanHe = MoiQuanHe;
        this.MaNV = MaNV;
        this.GiamTruPhuThuoc = GiamTruPhuThuoc;
    
    }
    
    public int getMaTN() {
        return MaTN;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getNgheNghiep() {
        return NgheNghiep;
    }

    public String getMoiQuanHe() {
        return MoiQuanHe;
    }

    public String getMaNV() {
        return MaNV;
    }

    public Boolean getGiamTruPhuThuoc() {
        return GiamTruPhuThuoc;
    }

    public void setMaTN(int MaTN) {
        this.MaTN = MaTN;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public void setNgheNghiep(String NgheNghiep) {
        this.NgheNghiep = NgheNghiep;
    }

    public void setMoiQuanHe(String MoiQuanHe) {
        this.MoiQuanHe = MoiQuanHe;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public void setGiamTruPhuThuoc(Boolean GiamTruPhuThuoc) {
        this.GiamTruPhuThuoc = GiamTruPhuThuoc;
    }
}
