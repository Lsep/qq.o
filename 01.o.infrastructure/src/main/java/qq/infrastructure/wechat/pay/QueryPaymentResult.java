package qq.infrastructure.wechat.pay;

import java.math.BigDecimal;

public interface QueryPaymentResult {
    String getRequestUuid();
    String getTradeNumber();
    BigDecimal getPayAmount();
    PaymentStatus getStatus();
}
