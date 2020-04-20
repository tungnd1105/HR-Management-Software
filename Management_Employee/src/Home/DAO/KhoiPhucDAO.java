package Home.DAO;

import Home.helper.JDBCMaster;
import java.sql.CallableStatement;

public class KhoiPhucDAO {

    public boolean restoreDBWithDifferential(String fullPath, String diffPath) {
        boolean success = true;
        try {
            String sql = "{Call SP_RESTOREDB(?,?,?)}";
            CallableStatement cstm = JDBCMaster.callableStatement(sql, fullPath, diffPath);
            cstm.registerOutParameter(3, java.sql.Types.BIT);
            cstm.execute();
            if (!cstm.getBoolean(3)) {
                throw new Exception();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            success = false;
        }
        return success;
    }
    
    public boolean restoreDBOnlyFullBackup(String fullPath) {
        boolean success = true;
        try {
            String sql = "{Call SP_RESTOREQLNSOnlyFullBackup(?,?)}";
            CallableStatement cstm = JDBCMaster.callableStatement(sql, fullPath);
            cstm.registerOutParameter(2, java.sql.Types.BIT);
            cstm.execute();
            if (!cstm.getBoolean(2)) {
                throw new Exception();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            success = false;
        }
        return success;
    }
}
