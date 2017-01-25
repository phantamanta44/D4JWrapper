package io.github.phantamanta44.discord4j.data.audio;

public class AudioBuffer {

    private byte[] buffer, next;
    private int position;

    private AudioBuffer() {
        this.position = 0;
    }

    public byte[] next() {
        System.arraycopy(buffer, position, next, 0, next.length);
        return next;
    }

    public boolean hasNext() {
        return buffer != null && position + next.length < buffer.length;
    }

    public void setBuffer(byte[] audioData, int frameSize) {
        reset();
        buffer = audioData;
        next = new byte[frameSize];
    }

    public void reset() {
        this.position = 0;
    }

    public void clear() {
        reset();
        buffer = null;
        next = null;
    }

}
