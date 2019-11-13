package com.uranus.platform.utils.jd.security;

import com.wangyin.aks.security.sign.SignEnvelopService;
import com.wangyin.aks.security.sign.SignEnvelopServiceImpl;
import com.wangyin.aks.security.sign.util.Base64;
import com.wangyin.aks.security.sign.util.FileUtil;
import lombok.NonNull;

/**
 * @author 李亚斌
 * @date 2019/07/30 18:51
 * @since v1.1
 */
public class SignEnvelopServiceKey {
    private SignEnvelopService signEnvelopService;
    private final String priCert;
    private final String pubCert;
    private final String password;

    public SignEnvelopServiceKey(String priCertFilePath,String pubCertFilePath,String password){
        signEnvelopService = new SignEnvelopServiceImpl();
        priCert = Base64.encode(FileUtil.readFile(priCertFilePath));
        pubCert = new String(FileUtil.readFile(pubCertFilePath));
        this.password = password;
    }

    /**
     * 数据加密
     * @param srcData 源数串
     * @return 加密后的数据串
     */
    public String signEnvelop(@NonNull String srcData){
        return signEnvelopService.signEnvelop(priCert, password,pubCert, srcData.getBytes());
    }

    /**
     * 数据解密
     * @param signedEvpData 被加密过的数据串
     * @return 解密后的数据串
     */
    public String verifyEnvelop(@NonNull String signedEvpData){
        return new String(signEnvelopService.verifyEnvelop(priCert, password, signedEvpData));
    }

}
