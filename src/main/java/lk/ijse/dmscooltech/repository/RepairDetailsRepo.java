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
            System.out.println(isOrderSaved);
            if (isOrderSaved) {
                boolean isOrderDetailsSaved = OrderDetailsRepo.saveOrderDetails(repairDetails.getOrderDetails());
                System.out.println(isOrderDetailsSaved);
                if (isOrderDetailsSaved) {
                    boolean isItemUpdated = ItemRepo.updateItemQty(repairDetails.getOrderDetails());
                    System.out.println(isItemUpdated+"Item");
                    if (isItemUpdated) {
                        boolean isRepairSaved = RepairRepo.saveRepair(repairDetails.getRepair());
                        System.out.println(isRepairSaved+"Repair");
                        if (isRepairSaved) {
                            boolean isPaymentSaved = PaymentRepo.savePayment(repairDetails.getPayment());
                            System.out.println(isPaymentSaved+"Payment");
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
