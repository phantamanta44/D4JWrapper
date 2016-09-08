package io.github.phantamanta44.discord4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VirtualByteStack {

    private boolean outputting = false;
    private ByteNode root, node;
    private InputStream streamIn;
    private OutputStream streamOut;

    public VirtualByteStack() {
        this.streamIn = new VirtualInputStream();
        this.streamOut = new VirtualOutputStream();
        reset();
    }

    public VirtualByteStack flip() {
        if (outputting) {
            while (node.next != null)
                node = node.next;
            outputting = false;
        } else {
            node = root;
            outputting = true;
        }
        return this;
    }

    public VirtualByteStack reset() {
        root = node = new ByteNode((short)-1);
        return this;
    }

    public InputStream getInputStream() {
        return streamIn;
    }

    public OutputStream getOutputStream() {
        return streamOut;
    }

    private class VirtualOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            if (outputting)
                throw new IllegalStateException();
            node = (node.next = new ByteNode((short)b));
        }

    }

    private class VirtualInputStream extends InputStream {

        @Override
        public int read() throws IOException {
            if (!outputting)
                throw new IllegalStateException();
            if (node.next != null) {
                node = node.next;
                return node.value;
            }
            return -1;
        }

    }

    private static class ByteNode {

        private final int value;
        private ByteNode next;

        private ByteNode(short value) {
            this.value = value;
        }

    }

}
