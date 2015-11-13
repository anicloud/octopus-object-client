package com.ani.octopus.service.agent.service.account.dto;


/**
 * Created by zhaoyu on 15-10-19.
 */
public class AccountInfoDto extends AbstractDto {
    private static final long serialVersionUID = 8160120708096274327L;

    public String phoneNumber;
    public String address;
    public String company;
    public String photoPath;

    public AccountInfoDto() {
        super();
    }

    public AccountInfoDto(String phoneNumber, String address, String company, String photoPath) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.company = company;
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "AccountInfoDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                ", photoPath='" + photoPath + '\'' +
                "} " + super.toString();
    }
}
