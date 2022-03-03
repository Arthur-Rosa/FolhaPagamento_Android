package com.example.folha_de_pagamento.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    //Atributos da classe User
    private String planoS;
    private String salarioBase;
    private String inss;
    private String irss;
    private String valeTransp;
    private String valeAlimen;
    private String valeRefei;
    private String salarioLiquido;
    private String porcetagem;


    protected User(Parcel in) {
        planoS = in.readString();
        salarioBase = in.readString();
        inss = in.readString();
        irss = in.readString();
        valeTransp = in.readString();
        valeAlimen = in.readString();
        valeRefei = in.readString();
        salarioLiquido = in.readString();
        porcetagem = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(planoS);
        dest.writeString(salarioBase);
        dest.writeString(inss);
        dest.writeString(irss);
        dest.writeString(valeTransp);
        dest.writeString(valeAlimen);
        dest.writeString(valeRefei);
        dest.writeString(salarioLiquido);
        dest.writeString(porcetagem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {

    }

    public String getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setSalarioLiquido(String salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }

    public String getPlanoS() {
        return planoS;
    }

    public void setPlanoS(String planoS) {
        this.planoS = planoS;
    }

    public String getValeRefei() {
        return valeRefei;
    }

    public void setValeRefei(String valeRefei) {
        this.valeRefei = valeRefei;
    }

    public String getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(String salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public String getIrss() {
        return irss;
    }

    public void setIrss(String irss) {
        this.irss = irss;
    }

    public String getValeTransp() {
        return valeTransp;
    }

    public void setValeTransp(String valeTransp) {
        this.valeTransp = valeTransp;
    }

    public String getValeAlimen() {
        return valeAlimen;
    }

    public void setValeAlimen(String valeAlimen) {
        this.valeAlimen = valeAlimen;
    }

    public String getPorcetagem() {
        return porcetagem;
    }

    public void setPorcetagem(String porcetagem) {
        this.porcetagem = porcetagem;
    }

}
