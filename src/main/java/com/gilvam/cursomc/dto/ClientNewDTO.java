package com.gilvam.cursomc.dto;
import com.gilvam.cursomc.services.validation.ValidationClientInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ValidationClientInsert
public class ClientNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //client
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cpfOrCnpj;

    private Integer type;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

    //address
    @NotEmpty(message = "Preenchimento obrigatório")
    private String addressName;

    @NotNull(message = "Preenchimento obrigatório")
    private Integer addressNumber;

    private String addressComplement;
    private String addressDistrict;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String addressZipCode;


    //phones
    @NotEmpty(message = "Preenchimento obrigatório")
    private String phone1;

    private String phone2;
    private String phone3;


    //city
    private Integer cityId;


    public ClientNewDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public void setCpfOrCnpj(String cpfOrCnpj) {
        this.cpfOrCnpj = cpfOrCnpj;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}