/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.totime.mail.demos.rocketmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;

import com.totime.mail.demos.rocketmq.Foo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Configuration
public class RocketMQProduceRunners {

    @Bean(name = "customRunner1")
    public CustomRunner customRunner() {
        return new CustomRunner("output1");
    }

    @Bean(name = "customRunner2")
    public CustomRunner customRunner2() {
        return new CustomRunner("output3");
    }


    @Bean(name = "customRunnerWithTransactional")
    public CustomRunnerWithTransactional customRunnerWithTransactional() {
        return new CustomRunnerWithTransactional();
    }

    public static class CustomRunner implements CommandLineRunner {

        private final String bindingName;

        public CustomRunner(String bindingName) {
            this.bindingName = bindingName;
        }

        @Autowired
        private SenderService senderService;

        @Autowired
        private MySource mySource;

        @Override
        public void run(String... args) throws Exception {
            if (this.bindingName.equals("output1")) {
                int count = 5;
                for (int index = 1; index <= count; index++) {
                    String msgContent = "msg-" + index;
                    if (index % 3 == 0) {
                        senderService.send(msgContent);
                    } else if (index % 3 == 1) {
                        senderService.sendWithTags(msgContent, "tagStr");
                    } else {
                        senderService.sendObject(new Foo(index, "foo"), "tagObj");
                    }
                }
            } else if (this.bindingName.equals("output3")) {
                int count = 20;
                for (int index = 1; index <= count; index++) {
                    String msgContent = "pullMsg-" + index;
                    mySource.output3()
                            .send(MessageBuilder.withPayload(msgContent).build());
                }
            }

        }

    }

    public static class CustomRunnerWithTransactional implements CommandLineRunner {

        @Autowired
        private SenderService senderService;

        @Override
        public void run(String... args) throws Exception {
            // COMMIT_MESSAGE message
            senderService.sendTransactionalMsg("transactional-msg1", 1);
            // ROLLBACK_MESSAGE message
            senderService.sendTransactionalMsg("transactional-msg2", 2);
            // ROLLBACK_MESSAGE message
            senderService.sendTransactionalMsg("transactional-msg3", 3);
            // COMMIT_MESSAGE message
            senderService.sendTransactionalMsg("transactional-msg4", 4);
        }

    }

}
