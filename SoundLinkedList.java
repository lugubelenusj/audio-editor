public class SoundLinkedList implements SoundList {

  private int numChannels;
  private float sampleRate;
  private int numSamples;

  private SoundNode head;
  private SoundNode tail;

  public SoundLinkedList(int numChannels, float sampleRate) {
    this.numChannels = numChannels;
    this.sampleRate = sampleRate;
    this.numSamples = 0;

    this.head = new SoundNode(null);
    this.tail = head;
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
    if (numChannels > 1) {
      throw Exception;
    }
    tail.setNext(new SoundNode(sample));
    tail = tail.next();
    numSamples++;
  }

	/**
	 * Adds a single sample for each channel to the end of the SoundList.  Throws an exception if the size of the sample
	 * array is not the same as the number of channels in the sound list
	 * @param sample Array of samples (one for each channel) to add to the end of the SoundList
	 */
	public void addSample(float sample[]) {
    if (sample.length != numChannels) {
      throw Exception;
    }
    // is there a dummy element?
    SoundNode current = head;
    while (current.nextChannel() != null) {
      // while current.nextChannel().next() != null?
    }
    numSamples++;
  }

	/**
	 * Return an iterator that traverses the entire sample, returning an array floats (one for each channel)
	 * @return iterator
	 */
	public Iterator<float[]> iterator() {

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

  }

	/**
	 * Returns a clone of this SoundList
	 * @return The cloned SoundList
	 */
	public SoundList clone() {

  }

}
