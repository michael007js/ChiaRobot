package sample.bean;

public class KeyBean {
    private String fingerprint;
    private String masterPublicKey;
    private String farmerPublicKey;
    private String poolPublicKey;
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
