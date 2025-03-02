package br.com.motta.ecommerce.model;

public enum UserRole {

    ADMIN("ADMIN"),
    MEMBRO("MEMBRO");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
