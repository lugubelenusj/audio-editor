import java.util.Iterator;

public class TestMain2 {

    public static void main(String[] args) {

        // SoundLinkedList list = new SoundLinkedList(1, 1);
        // list.addSample(1.0f);
        // list.addSample(1.2f);
        // list.addSample(1.3f);
        // list.addSample(1.4f);
        // list.addSample(1.5f);
        //
        // Iterator<Float> it = list.iterator(0);
        // while (it.hasNext()) {
        //     System.out.print(it.next() + ", ");
        // }
        // System.out.println();
        // System.out.println();

        //---------------

        int samples = 4;
        int channels = 4;

        SoundLinkedList list = new SoundLinkedList(samples, channels);
        float[] a = {1.0f, 1.1f, 1.2f, 1.3f};
        float[] b = {2.0f, 2.1f, 2.2f, 2.3f};
        float[] c = {3.0f, 3.1f, 3.2f, 3.3f};
        float[] d = {4.0f, 4.1f, 4.2f, 4.3f};
        float[] e = {5.0f, 5.1f, 5.2f, 5.3f};
        float[] f = {6.0f, 6.1f, 6.2f, 6.3f};

        list.addSample(a);
        list.addSample(b);
        list.addSample(c);
        list.addSample(d);
        list.addSample(e);
        list.addSample(f);

        // Iterator<float[]> it = list.iterator();
        // float data;
        // while (it.hasNext()) {
        //     float[] col = it.next();
        //     for (int i = 0; i < channels; i++) {
        //         System.out.print(col[i] + ", ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();

        Iterator<Float> it = list.iterator(0);
        while (it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.println();

    }

}
