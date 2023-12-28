package org.choongang.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {   // 파일 설정을 분리해서 관리하기 위해
    private String path;    // 파일 경로
    private String url;     // 정적 경로
}
