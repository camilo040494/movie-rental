package com.github.camilo.movierental.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertFileUtils {
    
    public static File parseContentToFile(String contentFile) {
        try {
            File file = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
            byte[] fileContentByte = Base64.decodeBase64(contentFile);
            FileUtils.writeByteArrayToFile(file.getAbsoluteFile(), fileContentByte);    
            return file;
            
        } catch (IOException e) {
            log.error("Check content file failed for corrupted file!", e);
            throw new RuntimeException();
        } catch (Exception e) {
            log.error("Unrecognized exception occurred with message [{}]", e.getMessage(), e);
            throw new RuntimeException();
        }
    }
    
}
