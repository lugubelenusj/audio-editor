import java.util.Iterator;

public class SoundLinkedList implements SoundList {

    private float sampleRate;
    private int numChannels;
    private int numSamples;

    private SoundNode head;
    private SoundNode tail;

    public SoundLinkedList(float sampleRate, int numChannels) {
        this.sampleRate = sampleRate;
        this.numChannels = numChannels;
        this.numSamples = 0;

        this.head = null;
        this.tail = null;
    }

    /**
    * The number of channels in the SoundList
    * @return The number f channels in the SoundList
    */
    public int getNumChannels() {
        return numChannels;
    }

    /**
    * Returns the sample rate, in samples per second
    * @return The sample rate, in samples per second
    */
    public float getSampleRate() {
        return sampleRate;
    }

    /**
    * Returns the number of samples in the MusicList
    * @return The number of samples in the MusicList.
    */
    public int getNumSamples() {
        return numSamples;
    }

    /**
    * Returns The duration of the sound, in seconds.
    * @return  the duration of the sound, in seconds.
    */
    public float getDuration() {
        return numSamples / sampleRate;
    }

    /**
    * Fade in.  Smoothly ramp the volume up for fadeDuration sections.
    * @param fadeDuration The time (in seconds) to fade in.
    */
    public void fadeIn(float fadeDuration) {
        float sampleLength = 1 / sampleRate;
        SoundNode current = head;

        while (current.next() != null) {
            //current.setData(current.data() * timePassed / fadeDuration);
            while(current.nextChannel() != null) {

            }
        }
    }

    /**
    * Fade out.  Smoothly ramp the volume down for fadeDuration sections.
    * @param fadeDuration The time (in seconds) to fade out.
    * @param startTime The time (in seconds) to start to fade out
    */
    public void fadeOut(float fadeDuration, float startTime) {

    }

    /**
    * Remove the silence at the front and back of the clip.  All samples at the beginning of the clip
    * whose absolute value is less than or equal to maxLevel are removed, until a sample whose absolute value
    * is that is greater than maxLevel appears.  Then retain all samples until the end, at which point all samples
    * whose absolute value is less than or equal to  maxLevel are removed.
    * Note that a value greater than maxLevel on any channel is sufficient to keep the sample.
    * @param maxLevel The level at which the sample is kept.
    */
    public void trimSilence(float maxLevel) {

    }

    /**
    * Add an echo effect to the SoundList.
    * @param delay The time (in seconds) before the echo starts
    * @param percent The percent falloff of the echo (0.5 is 50 percent volume, 0.25 is
    *        25 percent volume, and so on.  All samples should be clipped to the range -1 .. 1
    */
    public void addEcho(float delay, float percent) {

    }

    /**
    * Reverse the SoundList.
    */
    public void reverse() {
        SoundNode current = head;
        SoundNode next = null;
        SoundNode prev = null;

        while (current != null) {
            next = current.next();
            current.setNext(prev);
            prev = current;
            current = next;
        }

        head = prev;
    }

    /**
    * Change the speed of the sound.
    * @param percentChange  How much to change the speed.  1.0 is no change, 2.0 doubles the speed (and the pitch), 0.5
    * cuts the speed in half (and lowers the pitch)  The sample rate should remain unchanged!  This is probably the hardest
    * function to implement in the entire project.
    */
    public void changeSpeed(float percentChange) {

    }


    /**
    * Add a single sample to the end of the SoundList.  Throws an exception if the soundlist has more than 1 channel
    * @param sample The sample to add
    */
    public void addSample(float sample) {

        if (numChannels != 1) {
            throw new IllegalArgumentException("SoundList has more than 1 channel!");
        }

        if (head == null) {
            head = new SoundNode(sample);
            tail = head;
        }

        else {
            tail.setNext(new SoundNode(sample));
            tail = tail.next();
        }

        numSamples++;
    }

    /**
    * Adds a single sample for each channel to the end of the SoundList.  Throws an exception if the size of the sample
    * array is not the same as the number of channels in the sound list
    * @param sample Array of samples (one for each channel) to add to the end of the SoundList
    */
    public void addSample(float sample[]) {

        if (sample.length != numChannels) {
            System.out.println("sample.length: " + sample.length + ", numChannels: " + numChannels);//
            throw new IllegalArgumentException("Sample array size is not the same as the number of channels in the sound list!");
        }

        if (sample.length == 1) {
            addSample(sample[0]);
        }

        if (head == null) {
            head = new SoundNode(sample[0]);
            tail = head;
            SoundNode current = head;
            for (int i = 1; i < numChannels; i++) {
                current.setNextChannel(new SoundNode(sample[i]));
                current = current.nextChannel();
            }
        }

        else {
            tail.setNext(new SoundNode(sample[0]));
            tail = tail.next();
            SoundNode current = tail;
            for (int i = 1; i < numChannels; i++) {
                current.setNextChannel(new SoundNode(sample[i]));
                current = current.nextChannel();
            }
        }

        numSamples += sample.length;
    }

    /**
    * Return an iterator that traverses the entire sample, returning an array floats (one for each channel)
    * @return iterator
    */
    public Iterator<float[]> iterator() {
        Iterator<float[]> it = new Iterator<float[]>() {

            private SoundNode current = head;
            private SoundNode rowPointer = current;

            @Override
            public boolean hasNext() {
                return current != null && current.next() != null;
            }

            @Override
            public float[] next() {
                current = current.next();
                rowPointer = current;
                float[] data = new float[numChannels];

                // Fill up the data array with each channel of this node.
                for (int i = 0; i < numChannels; i++) {
                    data[i] = rowPointer.data();
                    if (rowPointer.nextChannel() == null) {break;}
                    rowPointer = rowPointer.nextChannel();
                    // System.out.println(i);// is this all being skipped
                }

                //System.out.println("numChannels: " + numChannels);//
                //System.out.println("data.length: " + data.length);//
                return data;
            }

        };
        return it;
    }

    /**
    * Change the volume of all tracks in the sound list, by multiplying every value by the percent to change.
    * If allowClipping is true, values greater than 1.0 are set to 1.0, and values less than -1.0 are set to -1.0
    * If allowClipping is false, then if any values are greater than 1.0 or less than -1.0 in any clip, the entire sample
    * is rescaled to fit in the range.
    * @param percentToChange The percent to increase the volume.  A value of 1 will leave the clip unchanged, 2.0
    * will make the volume twice as loud, and 0.5 will make the volume 50% as loud.
    * @param allowClipping If allowClipping is true, then values greater than 1.0 or less than -1.0 after the
    * volume change are clipped to fit in the range.  If allowClipping is false, then if any values are greater than 1.0
    * or less than -1.0, the entire sample is rescaled  to fit in the range.
    */
    public void changeVolume(float percentToChange, boolean allowClipping) {

    }

    /**
    * Return an iterator that traverses a single channel of the list
    * @param channel The channel to traverse
    * @return the iterator to traverse the list
    */
    public Iterator<Float> iterator(int channel) {
        Iterator<Float> it = new Iterator<Float>() {

            // Gets to the correct channel.
            // The problem is that there is no node.nextChannel() for some reason!
            private SoundNode current = head;
            private boolean positioned = false;

            @Override
            public boolean hasNext() {
                return current != null && current.next() != null;
            }

            @Override
            public Float next() {
                if (!positioned) {goToChannel();}
                current = current.nextChannel();
                return current.data();
            }

            // Goes to the correct channel.
            public void goToChannel() {
                for (int i = 0; i < channel; i++) {
                    current = current.nextChannel();
                }
                positioned = true;
            }

        };
        return it;
    }

    /**
    * Trim the SoundList, by removing all samples before the startTime, and all samples past the end time.
    * Note that if a SoundList represents an 8 second sound, and we call clip(4,7), the new SoundList will be
    * a 3-second sound (from seconds 4-7 in the old SoundList)
    * @param startTime Time to start (in seconds)
    * @param endTime Time to end clip (as measured from the front of the original clip, in seconds)
    */
    public void clip(float startTime, float endTime) {

    }

    /**
    * Splice a new SoundList into this soundList.  Both SoundLists will be modified.  If the sampleRate of the
    * clipToSplice is not the same as this SoundList, an exception is thrown.
    * @param startSpliceTime Time to start the splice
    * @param clipToSplice The other SoundClip to splice in.
    */
    public void spliceIn(float startSpliceTime, SoundList clipToSplice) {

    }

    /**
    * Combine all channels into a single channel, by adding together all channels into a single channel.
    * @param allowClipping If allowClipping is true, then values greater than 1.0 or less than -1.0 after the
    * addition are clipped to fit in the range.  If allowClipping is false, then if any values are greater than 1.0
    * or less than -1.0, the entire sample is rescaled  to fit in the range.
    */
    public void makeMono(boolean allowClipping) {
        // Ensures that there are multiple channels.
        if (numChannels <= 1) {
            throw new IllegalArgumentException("This SoundLinkedList only has 1 channel.");
        }

        SoundNode current = head;

        //----------------------------------------
        // Part 1: Compress all channels into 1.
        //----------------------------------------
        // This will be the new SoundLinkedList.
        SoundNode head2 = new SoundNode(0.0f, null);
        SoundNode current2 = head2;

        // Iterates through the length of the list.
        // Can't use while (current.next() != null), because it would skip the head.
        for (int i = 0; i < numSamples; i++) {
            float sum = current.data();

            // Iterates through the number of channels per node, accumulating the data.
            // Don't need to break here because it's always checking the next.
            while (current.nextChannel() != null) {
                sum += current.nextChannel().data();
                current = current.nextChannel();
            }

            // Gives the new list's node its new value and then moves onto the next.
            current2.setData(sum);

            // This is to prevent setting a null Node at the end of the list.
            if (current.next() == null) {break;}
            current = current.next();
            current2.setNext(new SoundNode(0.0f, null));
            current2 = current2.next();
        }

        // Point the head to the new mono-list.
        head = head2;
        current = head;

        // Now there is only 1 channel.
        numChannels = 1;

        //----------------------------------------
        // Part 2: Scale the linked list.
        //----------------------------------------
        if (allowClipping == true) {
            for (int i = 0; i < numSamples; i++) {

                if (current.data() < -1.0f) {
                    current.setData(-1.0f);
                }
                else if (current.data() > 1.0f) {
                    current.setData(1.0f);
                }

                if (current.next() == null) {break;}
                current = current.next();
            }
        }

        // if (allowClipping == false)
        else {

            float min = current.data();
            float max = current.data();

            // Finds the min and max values.
            for (int i = 0; i < numSamples; i++) {

                if (current.data() < min) {
                    min = current.data();
                }
                if (current.data() > max) {
                    max = current.data();
                }

                if (current.next() == null) {break;}
                current = current.next();
            }

            // This is what each node will divide by.
            // Effect will make every node's value inbetween -1.0 and 1.0.
            float divisor = Math.abs(min) < Math.abs(max) ? max : min;

            // Scale every node.
            for (int i = 0; i < numSamples; i++) {
                current.setData(current.data() / divisor);

                if (current.next() == null) {break;}
                current = current.next();
            }
        }
    }

    /**
    * Combines this SoundList with a new SoundList, by adding the samples together.  This SoundList
    * is modified.   If the sampleRate of the clipTocombine is not the same as this SoundList, an exception is thrown.
    * If a SoundList of length 3 seconds and a SoundList of length 7 seconds are combined, the result will be a
    * SoundList of 7 seconds.
    * @param clipToCombine  The clip to combine with this clip
    * @param allowClipping  If allowClipping is true, then values greater than 1.0 or less than -1.0 after the
    * addition are clipped to fit in the range.  If allowClipping is false, then the entire sample is rescaled
    */
    public void combine(SoundList clipToCombine, boolean allowClipping) {

        if (this.sampleRate != clipToCombine.getSampleRate()) {
            throw new IllegalArgumentException("sampleRate of clipToCombine is not the same as this SoundList!");
        }

        SoundNode thisCurrent = head;
        SoundLinkedList otherCurrent = (SoundLinkedList) clipToCombine;

        if (this.numSamples < clipToCombine.getNumSamples()) {
            int diff = clipToCombine.getNumSamples() - this.numSamples;
            for (int i = 0; i < numChannels; i++) {

            }
        }
        else if (this.numSamples > clipToCombine.getNumSamples()) {
            int diff = this.numSamples - clipToCombine.getNumSamples();
            //for (int i = 0; i < )
        }
    }

    /**
    * Returns a clone of this SoundList
    * @return The cloned SoundList
    */
    public SoundList clone() {
        // A current pointer for the original SoundList.
        SoundNode original = head;
        // The cloned SoundLinkedList.
        SoundLinkedList clone = new SoundLinkedList(sampleRate, numChannels);

        // Goes through each node.
        for (int i = 0; i < numSamples; i++) {
            float row[] = new float[numChannels];
            SoundNode rowPointer = original;

            // Goes through each row of channels.
            for (int j = 0; j < numChannels; j++) {
                row[j] = rowPointer.data();

                // Breaks the loop if there are no more channels, prevents null exception.
                if (rowPointer.nextChannel() == null) {break;}
                rowPointer = rowPointer.nextChannel();
            }
            clone.addSample(row);

            // Breaks the loop if there are no more nodes, prevents null exception.
            if (original.next() == null) {break;}
            original = original.next();
        }

        return clone;

    }

}