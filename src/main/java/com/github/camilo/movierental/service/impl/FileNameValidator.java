package com.github.camilo.movierental.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.exception.InvalidFileException;
import com.github.camilo.movierental.service.chainofresponsabilities.FileValidator;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileNameValidator extends FileValidator{
    
    private static final List<String> SUPPORTED_EXTENSIONS = ListUtils.union(
            Arrays.asList(ContentType.PNG.getFileExtensions()),
            Arrays.asList(ContentType.JPEG.getFileExtensions()));
    private static final String LOG_IO_READING = "Error when reading the file with message: [{}]";
    private static final String LOG_CHECKING_FILE_TYPE = "Checking file type";
    private static final String LOG_CORRUPTED_FILE = "Could not recognized file extension [{}]";
    private static final String LOG_INVALID_FILE_EXTENSION = "Invalid file extension [{}]";

    @Override
    public void validate(File file) {
        log.info(LOG_CHECKING_FILE_TYPE);
        ContentInfoUtil util = new ContentInfoUtil();
        ContentInfo info = null;
        try {
            info = util.findMatch(file.getAbsolutePath());
        } catch (IOException e) {
            log.error(LOG_IO_READING, e.getCause());
            throw new InvalidFileException(LOG_IO_READING);
        }

      if (Objects.isNull(info)) {
          log.error(LOG_CORRUPTED_FILE, file.getName());
          throw new InvalidFileException(LOG_CORRUPTED_FILE);
      }

      String contentType = info.getContentType().toString();

      if (!checkContentType(contentType.toLowerCase())) {
          log.error(LOG_INVALID_FILE_EXTENSION, contentType.toLowerCase());
          throw new InvalidFileException(LOG_INVALID_FILE_EXTENSION);
      }

        checkNext(file);
    }
    
    private static final String SEPARATOR = "|";
    private static final String PATTERN = "^(%s)$";
    
    public boolean checkContentType(String contentType) {
        String join = StringUtils.join(SUPPORTED_EXTENSIONS, SEPARATOR);
        String patternString = String.format(PATTERN, join);
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(contentType);

        return matcher.matches();
    }
    
}
