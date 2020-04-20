
package Home.model.table;

import Home.helper.Picture;
import javafx.scene.control.Button;

public class TablePhongBan {
    private Button Delete;
    private Button Update;
    private String MaPB;
    private String TenPB;
    
    public TablePhongBan(){
    
    }
    
    public TablePhongBan(String MaPB,String TenPB){
        this.MaPB = MaPB;
        this.TenPB = TenPB;
        
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

    public String getMaPB() {
        return MaPB;
    }

    public void setMaPB(String MaPB) {
        this.MaPB = MaPB;
    }

    public String getTenPB() {
        return TenPB;
    }

    public void setTenPB(String TenPB) {
        this.TenPB = TenPB;
    }
}
