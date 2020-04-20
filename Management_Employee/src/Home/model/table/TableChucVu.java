
package Home.model.table;

import Home.helper.Picture;
import javafx.scene.control.Button;

public class TableChucVu {
    private Button Delete;
    private Button Update;
    private String MaCV;
    private String TenCV;
    private String PhuCap;
    
    public TableChucVu(){
    }
    
    public TableChucVu(String MaCV,String TenCV,String PhuCap){
        this.MaCV = MaCV;
        this.TenCV = TenCV;
        this.PhuCap = PhuCap;
        
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
    
    
    /*
    *Getter
    */

    public String getMaCV() {
        return MaCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public String getPhuCap() {
        return PhuCap;
    }
    
    /*
    *Setter
    */

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public void setPhuCap(String PhuCap) {
        this.PhuCap = PhuCap;
    }
}
