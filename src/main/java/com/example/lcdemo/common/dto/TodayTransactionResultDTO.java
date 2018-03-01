package com.example.lcdemo.common.dto;

/**
 * Created by tsy
 */
public class TodayTransactionResultDTO {
    private int jyje;
    private int jybs;

    public TodayTransactionResultDTO() {
    }

    public TodayTransactionResultDTO(int jyje, int jybs) {
        this.jyje = jyje;
        this.jybs = jybs;
    }

    public int getJyje() {
        return jyje;
    }

    public void setJyje(int jyje) {
        this.jyje = jyje;
    }

    public int getJybs() {
        return jybs;
    }

    public void setJybs(int jybs) {
        this.jybs = jybs;
    }
}
