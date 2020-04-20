package Home.model.table;

public class TableNhanThan {

    private int MaTN;
    private String HoTen;
    private String NgheNghiep;
    private String MoiQuanHe;
    private String MaNV;
    private String GiamTruPhuThuoc;

    public TableNhanThan(int MaTN, String HoTen, String NgheNghiep, String MoiQuanHe, String MaNV, String GiamTruPhuThuoc) {
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

    public void setMaTN(int MaTN) {
        this.MaTN = MaTN;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getNgheNghiep() {
        return NgheNghiep;
    }

    public void setNgheNghiep(String NgheNghiep) {
        this.NgheNghiep = NgheNghiep;
    }

    public String getMoiQuanHe() {
        return MoiQuanHe;
    }

    public void setMoiQuanHe(String MoiQuanHe) {
        this.MoiQuanHe = MoiQuanHe;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getGiamTruPhuThuoc() {
        return GiamTruPhuThuoc;
    }

    public void setGiamTruPhuThuoc(String GiamTruPhuThuoc) {
        this.GiamTruPhuThuoc = GiamTruPhuThuoc;
    }

}
