package com.example.bx_web.pojo;

import java.math.BigDecimal;

public class Total {
    private BigDecimal total;

    public Total() {
    }

    public Total(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
