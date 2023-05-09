package Interface;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Transfer.TransferController;

import java.sql.SQLException;

public class TransferManagerInterface extends AInterface{

    private final TransferController controller = TransferController.getInstance();

    public TransferManagerInterface() throws SQLException {
    }

    @Override
    public void logIn() throws Exception {
        controller.startTransferSystem();
    }
}
