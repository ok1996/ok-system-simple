package cn.iosd.starter.s3.config;

import cn.iosd.starter.s3.properties.S3Properties;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
public class AmazonS3AutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(AmazonS3AutoConfiguration.class);

    @Bean
    public AmazonS3 amazonS3Simple(S3Properties s3Properties) {
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTP);
        config.disableSocketProxy();

        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withClientConfiguration(config)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        s3Properties.getAccessKey(), s3Properties.getSecretKey())))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        s3Properties.getEndpoint(), Regions.CN_NORTH_1.getName()))
                .enablePathStyleAccess()
                .build();
        log.info("AmazonS3Simple 完成配置,endpoint:{}", s3Properties.getEndpoint());
        return client;
    }
}
