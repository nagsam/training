package victor.training.jpa.util;

public interface TransactionUtil {
	boolean hasTransaction();
	boolean isTransactionRollbacked();
	boolean isTransactionNew();
	Object getTransactionOpaqueIdentity();
	void setTransactionRollbackOnly();
	
	void executeWith_SUPPORTS(Runnable runnable);
	void executeWith_REQUIRED(Runnable runnable);
	void executeInSeparateTransaction(Runnable runnable);
	void executeWith_MANDATORY(Runnable runnable);
	void executeWith_NOT_SUPPORTED(Runnable runnable);



}
