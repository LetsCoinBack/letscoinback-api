package br.com.letscoinback.interfaces.projection;

public interface WalletStatusTotalProjection {
	String getStatus();
	Float getVlrTotal();
	String getTransactionType();
	String getMovimentationType();
}
