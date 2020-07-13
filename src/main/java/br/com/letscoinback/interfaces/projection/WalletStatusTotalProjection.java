package br.com.letscoinback.interfaces.projection;

import java.time.LocalDateTime;

public interface WalletStatusTotalProjection {
	String getStatus();
	Float getVlrTotal();
	String getTransactionType();
	String getMovimentationType();
	String getDescription();
	LocalDateTime getDate();
}
