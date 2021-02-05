package yuan.springcloud.config.client.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 自动刷新配置的方法
 */
@ConditionalOnProperty("config.refresh.interval.duration")
@Configuration
@Slf4j
public class AutoRefreshConfiguration implements SchedulingConfigurer {

    //配置的间隔刷新时间
    @Value("${config.refresh.interval.duration}")
    private long intervalDuration;

    //刷新的端点
    @Autowired
    private RefreshEndpoint refreshEndpoint;

    //执行定时任务
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        log.info("refresh duration {}, start refresh config", intervalDuration);
        scheduledTaskRegistrar.addFixedDelayTask(new IntervalTask(new Runnable() {
            @Override
            public void run() { refreshEndpoint.refresh();
            }
        }, intervalDuration, intervalDuration));
        log.info("refresh duration success", intervalDuration);
    }

    //开始定时任务
    @EnableScheduling
    protected static class scheduling {
    }
}
