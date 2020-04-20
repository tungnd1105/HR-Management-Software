package Home.model;

import Home.helper.XDate;
import java.time.LocalDate;
import java.util.Date;

public class NhanVien {

    private String MaNV;
    private String HoTen;
    private Boolean GioiTinh;
    private Date NgaySinh;
    private String SoCM;
    private String DienThoai;
    private String Email;
    private String DiaChi;
    private String Hinh;
    private String TrinhDoHV;
    private String MaHD;
    private String MaCV;
    private String MaPB;
    private Date NgayVaoLam;
    private Date NgayKetThuc;
    private double HeSoLuong;

    private Boolean TrangThai;

    public NhanVien() {
        this.NgaySinh = XDate.toDate(LocalDate.now().minusYears(18));
        this.NgayVaoLam = XDate.toDate(LocalDate.now());
        this.NgayKetThuc = XDate.toDate(LocalDate.now().plusYears(10));
    }

    public NhanVien(String MaNV, String HoTen, Boolean GioiTinh, Date NgaySinh, String SoCM, String DienThoai,
            String Email, String DiaChi, String Hinh, String TrinhDoHV, String MaHD, String MaCV, String MaPB,
            Date NgayVaoLam, Date NgayKetThuc, double HeSoLuong, boolean TrangThai) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SoCM = SoCM;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.DiaChi = DiaChi;
        this.Hinh = Hinh;
        this.TrinhDoHV = TrinhDoHV;
        this.MaHD = MaHD;
        this.MaCV = MaCV;
        this.MaPB = MaPB;
        this.NgayVaoLam = NgayVaoLam;
        this.NgayKetThuc = NgayKetThuc;
        this.HeSoLuong = HeSoLuong;

        this.TrangThai = TrangThai;
    }

    public NhanVien(String MaNV) {
        this.MaNV = MaNV;
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

    public Boolean getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(Boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
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

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
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

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public String getMaPB() {
        return MaPB;
    }

    public void setMaPB(String MaPB) {
        this.MaPB = MaPB;
    }

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public double getHeSoLuong() {
        return HeSoLuong;
    }

    public void setHeSoLuong(double HeSoLuong) {
        this.HeSoLuong = HeSoLuong;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    @Override
    public String toString() {
        return HoTen;
    }

}
