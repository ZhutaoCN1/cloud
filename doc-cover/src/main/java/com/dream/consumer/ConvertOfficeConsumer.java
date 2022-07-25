package com.dream.consumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dream.config.RabbitMQConfig;
import com.dream.service.ConvertOfficeService;
import com.dream.task.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertOfficeConsumer {

    @Autowired
    private Task task;
    @Autowired
    private ConvertOfficeService convertOfficeService;

    /**
     * 队列消费者-转换MP4文件。启动多线程任务，处理队列中的消息
     *
     * @param strData 队列中放入的JSON字符串
     */
    @RabbitListener(queues  = RabbitMQConfig.QUEUE_RECEIVE)
    public void receiveTodoRequestByMap(String strData){
        try{
            if(RabbitMQConfig.consumer){
//                JSONObject jsonData = JSONObject.fromObject(strData);
                JSONObject jsonData = JSONUtil.parseObj(strData);
                task.doTask(convertOfficeService, jsonData);
                //	      Thread.currentThread().join();
            }
         } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
