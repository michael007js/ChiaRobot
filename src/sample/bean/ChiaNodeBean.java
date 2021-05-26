package sample.bean;

import java.util.List;

public class ChiaNodeBean {

    private String validated_at;
    private List<NodesBean> nodes;

    public String getValidated_at() {
        return validated_at;
    }

    public void setValidated_at(String validated_at) {
        this.validated_at = validated_at;
    }

    public List<NodesBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesBean> nodes) {
        this.nodes = nodes;
    }

    public static class NodesBean {
        /**
         * block_height : 294987
         * geo : {"continent":"北美洲","country":"美国","country_iso":"US"}
         * ip : 24.183.162.100
         * port : 8444
         */

        private String block_height;
        private GeoBean geo;
        private String ip;
        private String port;

        public String getBlock_height() {
            return block_height;
        }

        public void setBlock_height(String block_height) {
            this.block_height = block_height;
        }

        public GeoBean getGeo() {
            return geo;
        }

        public void setGeo(GeoBean geo) {
            this.geo = geo;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public static class GeoBean {
            /**
             * continent : 北美洲
             * country : 美国
             * country_iso : US
             */

            private String continent;
            private String country;
            private String country_iso;

            public String getContinent() {
                return continent;
            }

            public void setContinent(String continent) {
                this.continent = continent;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getCountry_iso() {
                return country_iso;
            }

            public void setCountry_iso(String country_iso) {
                this.country_iso = country_iso;
            }
        }
    }
}
