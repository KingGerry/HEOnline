package com.hy.server.timer;

import com.hy.business.backentity.Role;
import com.hy.business.context.Contexts;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
public class Timers {

    @Scheduled(cron = "*/2 * * * * ?")
    public void AutoRecovery(){
        for (Channel channel : Contexts.channelGroup) {
            Role r = channel.attr(Contexts.ATTR_ROLE_KEY).get();
            if(r != null) {
                if (r.getNowHp() <= r.getMaxHp()) {
                    double hf = r.getMaxHp() * 0.1;
                    hf = r.getNowHp() + hf;
                    //r.setNowHp(hf > r.getMaxHp() ? r.getMaxHp() : (int)hf);
                }
            }
        }
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void TimerSecondAdd(){
        Contexts.setNowTimeSeconds(Contexts.getNowTimeSeconds() + 1);
    }
}
