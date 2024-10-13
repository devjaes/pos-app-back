package dev.jeep.Lookpay.services;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;

import dev.jeep.Lookpay.models.StoreModel;
import dev.jeep.Lookpay.models.vm.AssetModel;

@Service
public class S3Service {

    @Value("${env.AWS_BUCKET_NAME}")
    private String BUCKET_NAME;

    @Autowired
    private AmazonS3Client s3Client;

    public String putObject(MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = String.format("%s.%s", multipartFile.getName(), extension);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        try {
            TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
            Upload upload = transferManager.upload(BUCKET_NAME, key, multipartFile.getInputStream(), metadata);

            // Esperar a que termine la carga
            upload.waitForCompletion();

            // Liberar recursos del TransferManager
            transferManager.shutdownNow();

            return key;
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public AssetModel getObject(String key) {
        S3Object s3Object = s3Client.getObject(BUCKET_NAME, key);
        ObjectMetadata metadata = s3Object.getObjectMetadata();
        try {
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new AssetModel(bytes, metadata.getContentType());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteObject(String key) {
        s3Client.deleteObject(BUCKET_NAME, key);
    }

    public String getObjectURL(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET_NAME, key);
    }

    public String setStoreSignature(StoreModel storeModel, MultipartFile storeSignature) {
        if (storeModel.getElectronicSignatureKey() != null) {
            s3Client.deleteObject(BUCKET_NAME, storeModel.getElectronicSignatureKey());
        }

        String extension = StringUtils.getFilenameExtension(storeSignature.getOriginalFilename());

        if (extension == null) {
            extension = ".p12";
        }
        String key = String.format("e_signature/%s_sign.%s", storeModel.getId(), extension);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(storeSignature.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, storeSignature.getInputStream(),
                    metadata).withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(putObjectRequest);

            return key;

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public HashMap<String, String> setProductImages(Long productId, MultipartFile productImage) {
        HashMap<String, String> imagesRes = new HashMap<>();
        String extension = StringUtils.getFilenameExtension(productImage.getOriginalFilename());

        if (extension == null) {
            extension = ".jpg";
        }

        String key = String.format("products/%s.%s", productId, extension);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(productImage.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, productImage.getInputStream(),
                    metadata).withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(putObjectRequest);

            imagesRes.put("imageKey", key);
            imagesRes.put("imageUrl", getObjectURL(key));

            return imagesRes;

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
