package lk.ijse.dmscooltech.repository;

import lk.ijse.dmscooltech.db.DbConnection;
import lk.ijse.dmscooltech.model.RepairDetails;

import java.sql.Connection;
import java.sql.SQLException;

public class RepairDetailsRepo {
    public static boolean addNewRepair(RepairDetails repairDetails) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.saveOrder(repairDetails.getOrder());
            if (isOrderSaved) {
                boolean isOrderDetailsSaved = OrderDetailsRepo.saveOrderDetails(repairDetails.getOrderDetails());
                if (isOrderDetailsSaved) {
                    boolean isItemUpdated = ItemRepo.updateItemQty(repairDetails.getOrderDetails());
                    if (isItemUpdated) {
                        boolean isRepairSaved = RepairRepo.saveRepair(repairDetails.getRepair());
                        if (isRepairSaved) {
                            boolean isPaymentSaved = PaymentRepo.savePayment(repairDetails.getPayment());
                            if (isPaymentSaved) {
                                connection.commit();
                                return true;
                            }
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }
    }
}
