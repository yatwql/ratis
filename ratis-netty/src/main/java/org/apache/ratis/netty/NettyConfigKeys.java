/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ratis.netty;

import org.apache.ratis.conf.RaftProperties;
import org.apache.ratis.thirdparty.io.netty.util.NettyRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static org.apache.ratis.conf.ConfUtils.*;

public interface NettyConfigKeys {
  String PREFIX = "raft.netty";

  interface Server {
    Logger LOG = LoggerFactory.getLogger(Server.class);
    static Consumer<String> getDefaultLog() {
      return LOG::info;
    }

    String PREFIX = NettyConfigKeys.PREFIX + ".server";

    String PORT_KEY = PREFIX + ".port";
    int PORT_DEFAULT = 0;

    static int port(RaftProperties properties) {
      return getInt(properties::getInt,
          PORT_KEY, PORT_DEFAULT, getDefaultLog(), requireMin(0), requireMax(65536));
    }

    static void setPort(RaftProperties properties, int port) {
      setInt(properties::setInt, PORT_KEY, port);
    }
  }

  interface DataStream {
    Logger LOG = LoggerFactory.getLogger(Server.class);
    static Consumer<String> getDefaultLog() {
      return LOG::info;
    }

    String PREFIX = NettyConfigKeys.PREFIX + ".dataStream";

    String PORT_KEY = PREFIX + ".port";
    int PORT_DEFAULT = 0;

    static int port(RaftProperties properties) {
      return getInt(properties::getInt,
          PORT_KEY, PORT_DEFAULT, getDefaultLog(), requireMin(0), requireMax(65536));
    }

    static void setPort(RaftProperties properties, int port) {
      setInt(properties::setInt, PORT_KEY, port);
    }

    String CLIENT_EVENT_LOOP_THREADS_KEY = PREFIX + ".client.eventLoopThreads";
    int CLIENT_EVENT_LOOP_THREADS_DEFAULT = Math.max(1, NettyRuntime.availableProcessors() * 2);

    static int clientEventLoopThreads(RaftProperties properties) {
      return getInt(properties::getInt, CLIENT_EVENT_LOOP_THREADS_KEY,
          CLIENT_EVENT_LOOP_THREADS_DEFAULT, getDefaultLog(), requireMin(1), requireMax(65536));
    }

    static void setClientEventLoopThreads(RaftProperties properties, int eventLoopThreads) {
      setInt(properties::setInt, CLIENT_EVENT_LOOP_THREADS_KEY, eventLoopThreads);
    }
  }

  static void main(String[] args) {
    printAll(NettyConfigKeys.class);
  }
}