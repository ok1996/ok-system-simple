package cn.iosd.starter.redisson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.redisson")
public class RedissonProperties {
    private Config config;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class Config {
        private Standalone standalone;
        private MasterSlave masterSlave;
        private Sentinel sentinel;
        private Cluster cluster;

        public Standalone getStandalone() {
            return standalone;
        }

        public void setStandalone(Standalone standalone) {
            this.standalone = standalone;
        }

        public MasterSlave getMasterSlave() {
            return masterSlave;
        }

        public void setMasterSlave(MasterSlave masterSlave) {
            this.masterSlave = masterSlave;
        }

        public Sentinel getSentinel() {
            return sentinel;
        }

        public void setSentinel(Sentinel sentinel) {
            this.sentinel = sentinel;
        }

        public Cluster getCluster() {
            return cluster;
        }

        public void setCluster(Cluster cluster) {
            this.cluster = cluster;
        }
    }

    public static class Standalone {
        private String address;
        private String password;
        private int database;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }

    public static class MasterSlave {
        private String masterAddress;
        private List<String> slaveAddresses;
        private String password;
        private int database;

        public String getMasterAddress() {
            return masterAddress;
        }

        public void setMasterAddress(String masterAddress) {
            this.masterAddress = masterAddress;
        }

        public List<String> getSlaveAddresses() {
            return slaveAddresses;
        }

        public void setSlaveAddresses(List<String> slaveAddresses) {
            this.slaveAddresses = slaveAddresses;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }

    public static class Sentinel {
        private String sentinelMasterName;
        private List<String> sentinelAddresses;
        private String password;
        private int database;

        public String getSentinelMasterName() {
            return sentinelMasterName;
        }

        public void setSentinelMasterName(String sentinelMasterName) {
            this.sentinelMasterName = sentinelMasterName;
        }

        public List<String> getSentinelAddresses() {
            return sentinelAddresses;
        }

        public void setSentinelAddresses(List<String> sentinelAddresses) {
            this.sentinelAddresses = sentinelAddresses;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }

    public static class Cluster {
        private List<String> clusterAddresses;
        private String password;

        public List<String> getClusterAddresses() {
            return clusterAddresses;
        }

        public void setClusterAddresses(List<String> clusterAddresses) {
            this.clusterAddresses = clusterAddresses;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }



    public Standalone getStandaloneConfig() {
        return config.getStandalone();
    }

    public MasterSlave getMasterSlaveConfig() {
        return config.getMasterSlave();
    }

    public Sentinel getSentinelConfig() {
        return config.getSentinel();
    }

    public Cluster getClusterConfig() {
        return config.getCluster();
    }
}