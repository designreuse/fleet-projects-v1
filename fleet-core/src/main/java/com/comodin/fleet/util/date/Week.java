package com.comodin.fleet.util.date;

/**
 * The enum Week.
 */
public enum Week {

    /**
     * Monday week.
     */
    MONDAY("星期一", "Monday", "Mon.", 1),
    /**
     * Tuesday week.
     */
    TUESDAY("星期二", "Tuesday", "Tues.", 2),
    /**
     * Wednesday week.
     */
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
    /**
     * Thursday week.
     */
    THURSDAY("星期四", "Thursday", "Thur.", 4),
    /**
     * Friday week.
     */
    FRIDAY("星期五", "Friday", "Fri.", 5),
    /**
     * Saturday week.
     */
    SATURDAY("星期六", "Saturday", "Sat.", 6),
    /**
     * Sunday week.
     */
    SUNDAY("星期日", "Sunday", "Sun.", 7);

    /**
     * The Name cn.
     */
    String name_cn;
    /**
     * The Name en.
     */
    String name_en;
    /**
     * The Name en short.
     */
    String name_enShort;
    /**
     * The Number.
     */
    int number;
      
    Week(String name_cn, String name_en, String name_enShort, int number) {  
        this.name_cn = name_cn;  
        this.name_en = name_en;  
        this.name_enShort = name_enShort;  
        this.number = number;  
    }

    /**
     * Gets chinese name.
     *
     * @return the chinese name
     */
    public String getChineseName() {
        return name_cn;  
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name_en;  
    }

    /**
     * Gets short name.
     *
     * @return the short name
     */
    public String getShortName() {
        return name_enShort;  
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;  
    }  
}  