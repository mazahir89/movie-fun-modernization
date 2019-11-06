package org.superbiz.moviefun.albums;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.superbiz.moviefun.blobstore.BlobStore;
import org.superbiz.moviefun.blobstore.S3Store;

@SpringBootApplication
public class AlbumServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlbumServiceApplication.class, args);
    }

    @Bean
    public BlobStore blobStore(
            @Value("${vcap.services.photo-storage.credentials.endpoint:#{null}}") String endpoint,
            @Value("${vcap.services.photo-storage.credentials.access_key_id}") String accessKey,
            @Value("${vcap.services.photo-storage.credentials.secret_access_key}") String secretKey,
            @Value("${vcap.services.photo-storage.credentials.bucket}") String bucket
    ) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        if (endpoint != null) {
            s3Client.setEndpoint(endpoint);
        }

        return new S3Store(s3Client, bucket);
    }
}
