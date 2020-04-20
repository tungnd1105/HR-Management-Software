
package Home.model.table;

import Home.model.NhanVien;
import Home.model.PhongBan;

public class TableTaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private NhanVien nhanVien;
    private PhongBan phongBan;

    public TableTaiKhoan(String tenTaiKhoan, String matKhau, NhanVien nhanVien, PhongBan phongBan) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
        this.phongBan = phongBan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public PhongBan getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(PhongBan phongBan) {
        this.phongBan = phongBan;
    }
    
    
}
