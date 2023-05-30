package tfip.server.repository;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ImageRepository {

    @Autowired
    private AmazonS3 s3;

    private static final String bucketName = "twit";
    private Function<String, String> generateKey = (id) -> "images/%s".formatted(id);

    @Value("${IMAGEDOMAIN}")
    private String imgDomain;

    public void uploadFile(MultipartFile file, String postId) throws IOException {
        Map<String, String> userData = new HashMap<>();
        userData.put("filename", file.getOriginalFilename());
        userData.put("upload-date", (new Date()).toString());

        ObjectMetadata md = new ObjectMetadata();
        md.setContentType(file.getContentType());
        md.setContentLength(file.getSize());
        md.setUserMetadata(userData);

        String key = generateKey.apply(postId);

        PutObjectRequest pReq = new PutObjectRequest(bucketName, key, file.getInputStream(), md);

        pReq = pReq.withCannedAcl(CannedAccessControlList.PublicRead);

        try {
            PutObjectResult pRes = s3.putObject(pReq);
        } catch (AmazonClientException e) {
            throw new IOException("Error encountered uploading file.");
        }
    }

    public String getFileUrl(String postId) {
        StringBuilder sb = new StringBuilder()
                .append("https://")
                .append(bucketName)
                .append(".")
                .append(imgDomain)
                .append(generateKey.apply(postId));

        return sb.toString();
    }

}
