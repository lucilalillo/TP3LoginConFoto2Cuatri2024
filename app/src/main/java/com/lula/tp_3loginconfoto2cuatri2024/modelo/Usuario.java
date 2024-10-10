package com.lula.tp_3loginconfoto2cuatri2024.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    public String Documento;
    public String Apellido;
    public String Nombre;
    public String Mail;
    public String Password;
    private String Foto;

    public Usuario() {
    }

    public Usuario(String documento, String apellido, String nombre, String mail, String password, String foto) {
        Documento = documento;
        Apellido = apellido;
        Nombre = nombre;
        Mail = mail;
        Password = password;
        Foto = foto;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
