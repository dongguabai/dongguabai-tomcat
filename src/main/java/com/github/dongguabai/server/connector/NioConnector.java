package com.github.dongguabai.server.connector;

import com.github.dongguabai.server.engine.Engine;
import com.github.dongguabai.server.exp.ServletException;
import com.github.dongguabai.server.servlet.Filter;
import com.github.dongguabai.server.servlet.FilterChain;
import com.github.dongguabai.server.servlet.HttpServlet;
import com.github.dongguabai.server.servlet.HttpServletRequest;
import com.github.dongguabai.server.servlet.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author dongguabai
 * @date 2024-01-27 23:22
 */
public class NioConnector extends AbstractConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(NioConnector.class);

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private ByteBuffer buffer;

    public NioConnector(Engine servletEngine, int serverPort, int soTimeout) throws IOException {
        super(servletEngine, serverPort, soTimeout);
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(serverPort));
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        this.buffer = ByteBuffer.allocate(1024);
    }

    public void process(ExecutorService executorService) {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                        executorService.submit(() -> handleRequest(channel));
                    }
                    keyIterator.remove();
                }
            } catch (IOException e) {
                LOGGER.error("Error processing connection", e);
            }
        }
    }

    private void handleRequest(SocketChannel channel) {
        try {
            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                buffer.flip();
                byte[] bytes = new byte[bytesRead];
                buffer.get(bytes);
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                HttpServletRequest request = new HttpServletRequest(bais);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                HttpServletResponse response = new HttpServletResponse(baos);
                String url = request.getUrl();
                HttpServlet servlet = servletEngine.match(url);
                if (servlet != null) {
                    List<Filter> filters = servletEngine.getFilters(url);
                    FilterChain filterChain = new FilterChain(servlet);
                    filterChain.addFilters(filters);
                    filterChain.doFilter(request, response);
                    channel.write(ByteBuffer.wrap(baos.toByteArray()));
                } else {
                    sendNotFound(channel);
                }
                buffer.clear();
                bytesRead = channel.read(buffer);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotFound(SocketChannel channel) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        channel.write(ByteBuffer.wrap(response.getBytes()));
    }
}