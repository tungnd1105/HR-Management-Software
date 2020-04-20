
package Home.model.table;

import java.util.Date;

public class TableBangLuong {
    private String MaNV;
    private String HoTen;
    private String TenPB;
    private String TenCV;
    private Date NgayPhatLuong;
    private Integer LuongChinh;
    private Integer NgayCong;
    private Integer PC_TrachNhiem;
    private Integer ThuNhap;
    private Integer BHXH;
    private Integer BHYT;
    private Integer BHTN;
    private Integer PhuThuoc;
    private Integer TNCN;
    private Integer ThucLanh;
    private Boolean TrangThai;

    public TableBangLuong(String MaNV, String HoTen, String TenPB, String TenCV, Date NgayPhatLuong, Integer LuongChinh, Integer NgayCong, Integer PC_TrachNhiem, Integer ThuNhap, Integer BHXH, Integer BHYT, Integer BHTN, Integer PhuThuoc, Integer TNCN, Integer ThucLanh, Boolean TrangThai) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.TenPB = TenPB;
        this.TenCV = TenCV;
        this.NgayPhatLuong = NgayPhatLuong;
        this.LuongChinh = LuongChinh;
        this.NgayCong = NgayCong;
        this.PC_TrachNhiem = PC_TrachNhiem;
        this.ThuNhap = ThuNhap;
        this.BHXH = BHXH;
        this.BHYT = BHYT;
        this.BHTN = BHTN;
        this.PhuThuoc = PhuThuoc;
        this.TNCN = TNCN;
        this.ThucLanh = ThucLanh;
        this.TrangThai = TrangThai;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getTenPB() {
        return TenPB;
    }

    public void setTenPB(String TenPB) {
        this.TenPB = TenPB;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public Date getNgayPhatLuong() {
        return NgayPhatLuong;
    }

    public void setNgayPhatLuong(Date NgayPhatLuong) {
        this.NgayPhatLuong = NgayPhatLuong;
    }

    public Integer getLuongChinh() {
        return LuongChinh;
    }

    public void setLuongChinh(Integer LuongChinh) {
        this.LuongChinh = LuongChinh;
    }

    public Integer getNgayCong() {
        return NgayCong;
    }

    public void setNgayCong(Integer NgayCong) {
        this.NgayCong = NgayCong;
    }

    public Integer getPC_TrachNhiem() {
        return PC_TrachNhiem;
    }

    public void setPC_TrachNhiem(Integer PC_TrachNhiem) {
        this.PC_TrachNhiem = PC_TrachNhiem;
    }

    public Integer getThuNhap() {
        return ThuNhap;
    }

    public void setThuNhap(Integer ThuNhap) {
        this.ThuNhap = ThuNhap;
    }

    public Integer getBHXH() {
        return BHXH;
    }

    public void setBHXH(Integer BHXH) {
        this.BHXH = BHXH;
    }

    public Integer getBHYT() {
        return BHYT;
    }

    public void setBHYT(Integer BHYT) {
        this.BHYT = BHYT;
    }

    public Integer getBHTN() {
        return BHTN;
    }

    public void setBHTN(Integer BHTN) {
        this.BHTN = BHTN;
    }

    public Integer getPhuThuoc() {
        return PhuThuoc;
    }

    public void setPhuThuoc(Integer PhuThuoc) {
        this.PhuThuoc = PhuThuoc;
    }

    public Integer getTNCN() {
        return TNCN;
    }

    public void setTNCN(Integer TNCN) {
        this.TNCN = TNCN;
    }

    public Integer getThucLanh() {
        return ThucLanh;
    }

    public void setThucLanh(Integer ThucLanh) {
        this.ThucLanh = ThucLanh;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    
}
