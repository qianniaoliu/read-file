package com.read.file.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Component
@Slf4j
public class ReadFileDataScheduled implements InitializingBean, ResourceLoaderAware {

    private final List<String> data = new ArrayList<>();

    private final static String PATH = "file:D:\\CV\\Pro_CV\\traffic_log.txt";
//    private final static String PATH = "file:/Users/shenlong/Documents/workspace/dev/temp/traffic_log.txt";

    private ResourceLoader resourceLoader;

    private final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    private void start() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            List<String> tempData = new ArrayList<>();
            Resource resource = resourceLoader.getResource(PATH);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    tempData.add(line);
                }
            } catch (Exception ex) {
                log.error("读取文件数据失败", ex);
            }
            data.clear();
            data.addAll(tempData);
        }, 0, 60, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> getData() {
        return data;
    }
}
