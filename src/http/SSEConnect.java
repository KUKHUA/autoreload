/*
 * ChangeDetector -  detects modifications, deletions, or creations of files and folders.
 * Copyright (C) 2025 KUKHUA
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;

public class SSEConnect implements HttpHandler {
    public void handle(HttpExchange t){
        try {
            Headers headers = t.getResponseHeaders();
            headers.set("Cache-Control", "no-cache");
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "*");
            headers.set("Access-Control-Allow-Headers", "*");
            headers.set("Content-Type", "text/event-stream");
            headers.set("Connection", "keep-alive");

            t.sendResponseHeaders(200, 0);
            ClientStore.get().addClient(t);
            System.out.println("New SSE client connected.");
        } catch(IOException e ){
            System.out.println("Client IO failure with the SSE server. Maybe client crashed?" + e.getMessage());
        }
    }

}