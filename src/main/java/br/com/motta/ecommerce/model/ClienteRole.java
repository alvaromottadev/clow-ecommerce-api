package br.com.motta.ecommerce.model;

public enum ClienteRole {

    ADMIN("ADMIN"),
    MEMBRO("MEMBRO");

    private String role;

    ClienteRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
