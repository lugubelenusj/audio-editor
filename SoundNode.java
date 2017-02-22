public class SoundNode {

    private Object data;
    private ListNode next;

    public SoundNode(Object d) {
        data = d;
        next = null;
        nextChannel = null;
    }

    public SoundNode(Object data, ListNode next) {
        this.data = data;
        this.next = next;
    }

    public Object data() {
      return data;
    }

    public SoundNode next() {
      return next;
    }

    public SoundNode nextChannel() {
      return nextChannel;
    }

    public void setNext(SoundNode newnext) {
      this.next = newnext;
    }

    public void setNextChannel(SoundNode newNextChannel) {
      this.nextChannel = newNextChannel;
    }

    public void setData(Object newData) {
      this.data = newData;
    }

}
