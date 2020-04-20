
package Home.model.table;

import Home.helper.Picture;
import javafx.scene.control.Button;

public class TableNhanVien {
    private Button Delete;
    private Button Update;
    private String MaNV;
    private String HoTen;
    private String GioiTinh;
    private String NgaySinh;
    private String SoCM;
    private String DienThoai;
    private String Email;
    private String DiaChi;
    private String TrinhDoHV;
    private String MaHD;
    private String PhongBan;
    private String ChucVu;  
    private String NgayVaoLam;
    private String NgayKetThuc;
    private String HeSoLuong;
    private String TrangThai;
    
    public TableNhanVien(String MaNV, String HoTen, String GioiTinh, String NgaySinh, String SoCM, String DienThoai, 
            String Email, String DiaChi, String TrinhDoHV, String MaHD, String PhongBan, String ChucVu, String NgayVaoLam, 
            String NgayKetThuc, String HeSoLuong, String TrangThai) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SoCM = SoCM;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.DiaChi = DiaChi;
        this.TrinhDoHV = TrinhDoHV;
        this.MaHD = MaHD;
        this.ChucVu = ChucVu;
        this.PhongBan = PhongBan;
        this.NgayVaoLam = NgayVaoLam;
        this.NgayKetThuc = NgayKetThuc;
        this.HeSoLuong = HeSoLuong;
        this.TrangThai = TrangThai;
        
        Picture picture = new Picture();
        this.Delete = new Button("", picture.createImageView("delete.png"));
        this.Update = new Button("", picture.createImageView("edit.png"));
    }

    public Button getDelete() {
        return Delete;
    }

    public void setDelete(Button Delete) {
        this.Delete = Delete;
    }

    public Button getUpdate() {
        return Update;
    }

    public void setUpdate(Button Update) {
        this.Update = Update;
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

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSoCM() {
        return SoCM;
    }

    public void setSoCM(String SoCM) {
        this.SoCM = SoCM;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getTrinhDoHV() {
        return TrinhDoHV;
    }

    public void setTrinhDoHV(String TrinhDoHV) {
        this.TrinhDoHV = TrinhDoHV;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public String getPhongBan() {
        return PhongBan;
    }

    public void setPhongBan(String PhongBan) {
        this.PhongBan = PhongBan;
    }

    public String getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(String NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public String getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(String NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public String getHeSoLuong() {
        return HeSoLuong;
    }

    public void setHeSoLuong(String HeSoLuong) {
        this.HeSoLuong = HeSoLuong;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

}
