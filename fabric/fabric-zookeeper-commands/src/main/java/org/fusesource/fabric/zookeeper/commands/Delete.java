/**
 * Copyright (C) FuseSource, Inc.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.fabric.zookeeper.commands;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;

@Command(name = "delete", scope = "zk", description = "Delete a node")
public class Delete extends ZooKeeperCommandSupport {

    @Option(name = "-v", aliases = {"--version "}, description = "Version to delete")
    int version = -1;

    @Option(name = "-r", aliases = {"--recursive"}, description = "Automatically delete parents")
    boolean recursive;

    @Argument(description = "Path of the node to delete")
    String path;

    @Override
    protected Object doExecute() throws Exception {
        checkZooKeeperConnected();
        if (recursive) {
            if (version >= 0) {
                throw new UnsupportedOperationException("Unable to delete a version recursively");
            }
            getZooKeeper().deleteWithChildren(path);
        } else {
            getZooKeeper().delete(path, version);
        }
        return null;
    }
}
