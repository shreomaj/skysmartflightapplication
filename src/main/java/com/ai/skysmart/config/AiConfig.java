//package com.ai.skysmart.config;
//
//import org.slf4j.Logger;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AiConfig {
//
//    private Logger logger = org.slf4j.LoggerFactory.getLogger(AiConfig.class);
//    public  ChatClient chatClient(ChatClient.Builder builder, JdbcChatMemoryRepository jdbcChatMemoryRepository){
//        //chat memory using DataBase , create bean of chatmemory
//        var chatMemory=MessageWindowChatMemory.builder()
//                .chatMemoryRepository(jdbcChatMemoryRepository)
//                .maxMessages(15).build();
//
//        logger.info("ChatClient bean created.");
//        logger.info("Chatmemorybean created.{}"+ chatMemory.getClass().getName());
//        return  builder
//                .defaultSystem("Summerize the response within 400 words")
//                .defaultAdvisors(new SimpleLoggerAdvisor(),
//                        MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
//        //SimpleLoggerAdvisor use to see logs, to API call
//    }
//}
