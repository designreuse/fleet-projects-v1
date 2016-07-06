package com.comodin.fleet.util.date;

/**
 * The enum Date style.
 */
public enum DateStyle {

    /**
     * Yyyy mm date style.
     */
    YYYY_MM("yyyy-MM", false),
    /**
     * Yyyy mm dd date style.
     */
    YYYY_MM_DD("yyyy-MM-dd", false),
    /**
     * The Yyyy mm dd hh mm.
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", false),
    /**
     * The Yyyy mm dd hh mm ss.
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", false),

    /**
     * Yyyy mm en date style.
     */
    YYYY_MM_EN("yyyy/MM", false),
    /**
     * Yyyy mm dd en date style.
     */
    YYYY_MM_DD_EN("yyyy/MM/dd", false),
    /**
     * The Yyyy mm dd hh mm en.
     */
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm", false),
    /**
     * The Yyyy mm dd hh mm ss en.
     */
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss", false),

    /**
     * Yyyy mm cn date style.
     */
    YYYY_MM_CN("yyyy年MM月", false),
    /**
     * Yyyy mm dd cn date style.
     */
    YYYY_MM_DD_CN("yyyy年MM月dd日", false),
    /**
     * Yyyy mm dd hh mm cn date style.
     */
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm", false),
    /**
     * Yyyy mm dd hh mm ss cn date style.
     */
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss", false),

    /**
     * Hh mm date style.
     */
    HH_MM("HH:mm", true),
    /**
     * Hh mm ss date style.
     */
    HH_MM_SS("HH:mm:ss", true),

    /**
     * Mm dd date style.
     */
    MM_DD("MM-dd", true),
    /**
     * The Mm dd hh mm.
     */
    MM_DD_HH_MM("MM-dd HH:mm", true),
    /**
     * The Mm dd hh mm ss.
     */
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", true),

    /**
     * Mm dd en date style.
     */
    MM_DD_EN("MM/dd", true),
    /**
     * The Mm dd hh mm en.
     */
    MM_DD_HH_MM_EN("MM/dd HH:mm", true),
    /**
     * The Mm dd hh mm ss en.
     */
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss", true),

    /**
     * Mm dd cn date style.
     */
    MM_DD_CN("MM月dd日", true),
    /**
     * Mm dd hh mm cn date style.
     */
    MM_DD_HH_MM_CN("MM月dd日 HH:mm", true),
    /**
     * Mm dd hh mm ss cn date style.
     */
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss", true);
      
    private String value;  
      
    private boolean isShowOnly;  
      
    DateStyle(String value, boolean isShowOnly) {  
        this.value = value;  
        this.isShowOnly = isShowOnly;  
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;  
    }

    /**
     * Is show only boolean.
     *
     * @return the boolean
     */
    public boolean isShowOnly() {
        return isShowOnly;  
    }  
}  