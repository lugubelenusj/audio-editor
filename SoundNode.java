public class SoundNode {

    private float data;
    private SoundNode next;
    private SoundNode nextChannel;

    public SoundNode(float d) {
        data = d;
        next = null;
        nextChannel = null;
    }

    public SoundNode(float data, SoundNode next) {
        this.data = data;
        this.next = next;
    }

    public float data() {
        return data;
    }

    public SoundNode next() {
        return next;
    }

    public SoundNode nextChannel() {
        return nextChannel;
    }

    public void setNext(SoundNode newNext) {
        this.next = newNext;
    }

    public void setNextChannel(SoundNode newNextChannel) {
        this.nextChannel = newNextChannel;
    }

    public void setData(float newData) {
        this.data = newData;
    }

}