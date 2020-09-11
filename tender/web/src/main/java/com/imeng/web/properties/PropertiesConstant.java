package com.imeng.web.properties;

/**
 * @author cicada-tx
 * @date 2020/6/23 11:07
 * @description TODO
 */
public class PropertiesConstant {

    /**
     * 通用状态
     */
    public static final int UnChainCode=-1;      //未上链
    public static final int UnPass     =0 ;      //未通过
    public static final int Pass       =1 ;      //通过
    /**
     * json字符串
     */
    public static final String companyName = "companyName";
    public static final String legalRep = "legalRep";
    public static final String contactNO = "contactNO";
    public static final String contactAddr = "contactAddr";
    public static final String uniSocialCreditCode = "uniSocialCreditCode";
    //身份证正面
    public static final String name = "name";
    public static final String ID = "ID";
    public static final String sex = "sex";
    public static final String people = "people";
    public static final String birthDate = "birthDate";
    //活体身份识别
    public static final String frServiceProvider = "frServiceProvider";
    public static final String frTechnic = "frTechnic";
    public static final String frTime = "frTime";
    //放款凭证
    public static final String disburseAmt = "disburseAmt";
    public static final String paymentServiceProvider = "paymentServiceProvider";
    public static final String payeeAccountName = "payeeAccountName";
    public static final String PayeeAccountNO = "PayeeAccountNO";
}
