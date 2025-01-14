package com.jsp.food.delivery.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryHelper {

    @Value("${CLOUDINARY_URL}")
    String url;

    public String saveImage(MultipartFile image) {
        Cloudinary cloudinary = new Cloudinary(url);
        Map map = ObjectUtils.asMap("folder", "food-items");
        try {
            Map x = cloudinary.uploader().upload(image.getBytes(), map);
            return (String) x.get("url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
