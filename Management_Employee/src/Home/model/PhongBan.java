
package Home.model;

public class PhongBan {
    private String MaPB;
    private String TenPB;
    
    public PhongBan(){
    
    }
    
    public PhongBan(String MaPB,String TenPB){
        this.MaPB = MaPB;
        this.TenPB = TenPB;
    }

    public String getMaPB() {
        return MaPB;
    }

    public String getTenPB() {
        return TenPB;
    }

    public void setMaPB(String MaPB) {
        this.MaPB = MaPB;
    }

    public void setTenPB(String TenPB) {
        this.TenPB = TenPB;
    }

    @Override
    public String toString() {
        return TenPB;
    }

}
