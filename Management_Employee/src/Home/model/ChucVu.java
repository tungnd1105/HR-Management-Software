
package Home.model;

public class ChucVu {
    private String MaCV;
    private String TenCV;
    private Double PhuCap;
    
    public ChucVu(){
    }
    
    public ChucVu(String MaCV,String TenCV,Double PhuCap){
        this.MaCV = MaCV;
        this.TenCV = TenCV;
        this.PhuCap = PhuCap;
    }

    public String getMaCV() {
        return MaCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public Double getPhuCap() {
        return PhuCap;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public void setPhuCap(Double PhuCap) {
        this.PhuCap = PhuCap;
    }

    @Override
    public String toString() {
        return TenCV;
    }
 
}
