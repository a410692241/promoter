package com.linayi.enums;

public enum  ProcurementStates {

    WAIT_PROCURE("待采买"),
    BOUGHT("已买");

    private String procurementStatesName;


    /*[采买状态] ：待采买：WAIT_PROCURE   已买：BOUGHT */

    ProcurementStates(String procurementStatesName) {
        this.procurementStatesName = procurementStatesName;
    }

    public String getProcurementStatesName() {
        return procurementStatesName;
    }

    public void setProcurementStatesName(String procurementStatesName) {
        this.procurementStatesName = procurementStatesName;
    }
}
