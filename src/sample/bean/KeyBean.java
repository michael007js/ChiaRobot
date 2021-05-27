package sample.bean;

public class KeyBean {
    /**
     * 公共指纹
     */
    private String fingerprint;
    /**
     * 公钥
     */
    private String masterPublicKey;
    /**
     *农田公钥
     */
    private String farmerPublicKey;
    /**
     * 池公钥
     */
    private String poolPublicKey;
    /**
     * 钱包地址
     */
    private String firstWalletAddress;

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getMasterPublicKey() {
        return masterPublicKey;
    }

    public void setMasterPublicKey(String masterPublicKey) {
        this.masterPublicKey = masterPublicKey;
    }

    public String getFarmerPublicKey() {
        return farmerPublicKey;
    }

    public void setFarmerPublicKey(String farmerPublicKey) {
        this.farmerPublicKey = farmerPublicKey;
    }

    public String getPoolPublicKey() {
        return poolPublicKey;
    }

    public void setPoolPublicKey(String poolPublicKey) {
        this.poolPublicKey = poolPublicKey;
    }

    public String getFirstWalletAddress() {
        return firstWalletAddress;
    }

    public void setFirstWalletAddress(String firstWalletAddress) {
        this.firstWalletAddress = firstWalletAddress;
    }


    @Override
    public String toString() {
        return fingerprint;
    }
}
