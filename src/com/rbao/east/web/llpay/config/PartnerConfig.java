package com.rbao.east.web.llpay.config;

/**
* 商户配置信息
* @author guoyx e-mail:guoyx@lianlian.com
* @date:2013-6-25 下午01:45:40
* @version :1.0
*
*/
public interface PartnerConfig{
    // 银通公钥
    String YT_PUB_KEY     = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
    // 商户私钥
    String TRADER_PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANUimsjPFDvu0gOBryI5rIBbUyiddBgiY4f7OaW2G2Bg4mTIfdShoa7/brCPU0p+9aoLU79zkaPWrEe0MYWtHHWr1vKEIZeuul+nrCG5HzwVBWdKdgkgRcCjuiTbbj3idOXHziIqTuUh73OY7mBxQ76IFh3Pk72FK/q4RuM5nubvAgMBAAECgYARouZp/XCwMOfh8GY8j8cta+EquDS2Fopj4y72/mWviPTS4CBBawqE/4/6PjHnNTBA3OUWL4r6orXozQlYBWYYU+olPGQyJ84LssHftcfd7kzLzc1AC/QEuOIAuKGqpOHro7u6wW9SR5GovkEhw0pdWzESDW/fID6NAjPnW+aHQQJBAPNbRfBrS5eDHUpKG8v+lZudgvQ4cEVfMwa5MCZUaPupYvNFtefYaOr6JmDVK2DG8Dth80uYo0iAV/G4zSTxPqECQQDgNWB0fQKXIrNi1PLS8q9XVfLZJCj3aP/njjdJHycoAepSppyja8hpMEt6ASGYb467zq88X1eZimQAt6YFrAuPAkBFPNS7QGuzYdR1jHw4H+jE652UinI26o0D/rBaV22/HERQ3qaQnhvLrvw4VK4QzMkQvB+ShI0AVbOvzFphhlnBAkEAm8zbovb/ptCJQUNjHa1gpNrn73ZONWa0dU8BIgHQ7t8t2pNGw9TxJg9WU4iXtNrIn79HTDegouRlC46SgtyEBwJBAMHIptdxN6wCNlbgPty03NlW+RZO4VjaKj/0norCMsHB7UICg/R54ds6278K0WtaWNyWGVGoELMr0ffYwlYaTzs=";
/*    String TRADER_PRI_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMlGNh/WsyZSYnQcHd9t5qUkhcOhuQmozrAY9DM4+7fhpbJenmYee4chREW4RB3m95+vsz9DqCq61/dIOoLK940/XmhKkuVjfPqHJpoyHJsHcMYy2bXCd2fI++rERdXtYm0Yj2lFbq1aEAckciutyVZcAIHQoZsFwF8l6oS6DmZRAgMBAAECgYAApq1+JN+nfBS9c2nVUzGvzxJvs5I5qcYhY7NGhySpT52NmijBA9A6e60Q3Ku7vQeICLV3uuxMVxZjwmQOEEIEvXqauyYUYTPgqGGcwYXQFVI7raHa0fNMfVWLMHgtTScoKVXRoU3re6HaXB2z5nUR//NE2OLdGCv0ApaJWEJMwQJBAPWoD/Cm/2LpZdfh7oXkCH+JQ9LoSWGpBDEKkTTzIqU9USNHOKjth9vWagsR55aAn2ImG+EPS+wa9xFTVDk/+WUCQQDRv8B/lYZD43KPi8AJuQxUzibDhpzqUrAcu5Xr3KMvcM4Us7QVzXqP7sFc7FJjZSTWgn3mQqJg1X0pqpdkQSB9AkBFs2jKbGe8BeM6rMVDwh7TKPxQhE4F4rHoxEnND0t+PPafnt6pt7O7oYu3Fl5yao5Oh+eTJQbyt/fwN4eHMuqtAkBx/ob+UCNyjhDbFxa9sgaTqJ7EsUpix6HTW9f1IirGQ8ac1bXQC6bKxvXsLLvyLSxCMRV/qUNa4Wxu0roI0KR5AkAZqsY48Uf/XsacJqRgIvwODstC03fgbml890R0LIdhnwAvE4sGnC9LKySRKmEMo8PuDhI0dTzaV0AbvXnsfDfp";
*/    // MD5 KEY
    String MD5_KEY        = "sjdioasjodjasdq0u904e823908409283094809238jfdsiojfiosdjofjsdoi2380948239082390849032809423809";
/*    String MD5_KEY        = "201408071000001543test_20140812";
*/    // 接收异步通知地址    
    String NOTIFY_URL     = "/web/pay/recvFromLianLian.do";
    // 支付结束后返回地址
    String URL_RETURN     = "/web/pay/rechargeResult.do";
    // 商户编号
    /*String OID_PARTNER    = "201408071000001543";*/
    String OID_PARTNER    = "201510141000538622";
    // 签名方式 RSA或MD5
    String SIGN_TYPE      = "MD5";
    // 接口版本号，固定1.0
    String VERSION        = "1.1";

    // 业务类型，连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售）

    String BUSI_PARTNER   = "101001";
}
