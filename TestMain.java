import java.io.*;
import java.util.Iterator;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.Encoding;

public class TestMain {
	//
	protected static final int SAMPLE_RATE = 16 * 1024;



	public static boolean approxEqual(float n1, float n2)
	{
		return Math.abs(n1 - n2) < 0.01;
	}



	
	
	public static boolean testMakeMono(int numChannels, boolean play)
	{
		boolean success = true;
		try
		{
			float frequency[] = new float[numChannels];
			float sampleRate = 10000;
			float time = 1;
			SoundLinkedList[] listOfLists = new SoundLinkedList[numChannels];
			boolean[]  channelsToUse = new boolean[numChannels];
			float[] angleFrequency = new float[numChannels];
			for (int i = 0; i < numChannels; i++)
			{

				channelsToUse[i] = true;
				frequency[i] = 100 + 50*i;
				angleFrequency[i] = (float) ( frequency[i] * Math.PI * 2);
				listOfLists[i] = SoundUtil.createSineWave(time, frequency[i], sampleRate, numChannels, channelsToUse);
				channelsToUse[i] = false;
			}
			for (int i = 1; i < numChannels; i++)
			{
				listOfLists[0].combine(listOfLists[i], true);
			}
			listOfLists[0].makeMono(true);

			Iterator<float[]> it1= listOfLists[0].iterator();
			double currentTime = 0;

			while (it1.hasNext())
			{
				float nextSample = it1.next()[0];

				float nextCorrectSample = 0;
				for (int i = 0; i < numChannels; i++)
				{
					nextCorrectSample += (float) Math.sin(currentTime *angleFrequency[i]);
				}
				nextCorrectSample = Math.max(-1, Math.min(1, nextCorrectSample));

				if (!approxEqual(nextSample, nextCorrectSample))
				{
					if (success)
					{
						System.out.println("Error:  Samples don't match");
					}
					success = false;
				}
				currentTime +=  1.0 / (double) sampleRate;
			}


			if (play)
			{
				SoundUtil.play(listOfLists[0]);
			}

		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return false;
		}

		return success;
	}

	public static boolean testMultiChannelSineWave(int numChannels, boolean play)
	{
		boolean success = true;
		try
		{
			float frequency[] = new float[numChannels];
			float sampleRate = 10000;
			float time = 1;
			SoundLinkedList[] listOfLists = new SoundLinkedList[numChannels];
			boolean[]  channelsToUse = new boolean[numChannels];
			float[] angleFrequency = new float[numChannels];
			for (int i = 0; i < numChannels; i++)
			{

				channelsToUse[i] = true;
				frequency[i] = 100 + 50*i;
				angleFrequency[i] = (float) ( frequency[i] * Math.PI * 2);
				listOfLists[i] = SoundUtil.createSineWave(time, frequency[i], sampleRate, numChannels, channelsToUse);
				channelsToUse[i] = false;
			}
			for (int i = 1; i < numChannels; i++)
			{
				listOfLists[0].combine(listOfLists[i], true);
			}

			Iterator<float[]> it1= listOfLists[0].iterator();
			double currentTime = 0;
			while (it1.hasNext())
			{
				float[] nextSample = it1.next();

				for (int i = 0; i < numChannels; i++)
				{
					float nextCorrectSample = (float) Math.sin(currentTime *angleFrequency[i]);
					if (!approxEqual(nextSample[i], nextCorrectSample))
					{
						if (success)
						{
							System.out.println("Error:  Samples don't match:  Multiple Iterator");
						}
						success = false;
					}
				}
				currentTime +=  1.0 / (double) sampleRate;

			}
			
			boolean singleSuceess = true;
			for (int chan = 0; chan < numChannels; chan++)
			{
				 currentTime = 0;
				Iterator<Float> it = listOfLists[0].iterator(chan);
				while (it.hasNext())
				{
					Float nextSample = it.next();
				
						float nextCorrectSample = (float) Math.sin(currentTime *angleFrequency[chan]);
						if (!approxEqual(nextSample, nextCorrectSample))
						{
							if (singleSuceess)
							{
								System.out.println("Error:  Samples don't match:  Single iterator");
							}
							singleSuceess = false;
							success = false;
						}
						currentTime +=  1.0 / (double) sampleRate;
				}
	
			}			


			if (play)
			{
				SoundUtil.play(listOfLists[0]);
			}
		} 
		catch(Exception e)
		{
			System.out.println(e.toString());
			return false;
		}
		return success;
	}



	public static boolean testRescale(boolean play)
	{
		try
		{
			float frequency = 100;
			float sampleRate = 10000;
			float time = 1;
			SoundLinkedList mList1 = SoundUtil.createSineWave(time, frequency, sampleRate);
			SoundLinkedList mList2 = SoundUtil.createSineWave(time, frequency, sampleRate * 2);

			System.out.println("Trying o combine two SoundLists with different sample rates ... " );

			try
			{
				mList1.combine(mList2, false);
			
			}
			catch (Exception e)
			{
				System.out.println("Successfully thrown exception when combining sounds of different rates" );
				return true;
			}
		
		System.out.println("Should have thrown an exception here ..." );
		return false;
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong:" + e.toString() );
			return false;

		}
	}

	public static boolean testReverse(int numChannels)
	{
		boolean success = true;

		try
		{

			SoundLinkedList mL = new SoundLinkedList(10000, numChannels);
			float[] samples = new float[numChannels];
			for (int i = 0; i < 1000; i++)
			{
				for (int j = 0; j < numChannels; j++)
				{
					samples[j] = 1/(float)(i+j+1);
				}
				mL.addSample(samples);		  
			}

			mL.reverse();
			mL.reverse();
			mL.reverse();  // reversing 3 times should be same as reversing once ...
			Iterator<float[]> it = mL.iterator();
			int i = 999;
			while (it.hasNext())
			{
				samples = it.next();
				for (int j = 0; j < numChannels; j++)
				{
					if (!approxEqual(samples[j],  1/(float) (i+j+1)))
					{
						if (success)
						{
							System.out.println("Error:  Samples don't match");
						}
						success = false;
					}
				}
				i--;
			}
			if (mL.getNumSamples() != 1000)
			{
		
					System.out.println("Error:  NumSamples don't match");
				
				success = false;

			}


		}
		catch(Exception e)
		{
			System.out.println("Failed: " + e.toString());
			return false;
		}

		return success;
	}

	


	public static boolean testFadeIn(int numChannels, float time, float fadeTime) {

		boolean success = true;
		try
		{
			float frequency[] = new float[numChannels];
			float sampleRate = 10000;

			SoundLinkedList[] listOfLists = new SoundLinkedList[numChannels];
			boolean[]  channelsToUse = new boolean[numChannels];
			float[] angleFrequency = new float[numChannels];
			for (int i = 0; i < numChannels; i++)
			{
				channelsToUse[i] = true;
				frequency[i] = 100 + 50*i;
				angleFrequency[i] = (float) ( frequency[i] * Math.PI * 2);
				listOfLists[i] = SoundUtil.createSineWave(time, frequency[i], sampleRate, numChannels, channelsToUse);
				channelsToUse[i] = false;
			}
			for (int i = 1; i < numChannels; i++)
			{
				listOfLists[0].combine(listOfLists[i], true);
			}

			listOfLists[0].fadeIn(fadeTime);
			Iterator<float[]> it1= listOfLists[0].iterator();
			double currentTime = 0;
			while (it1.hasNext())
			{
				float[] nextSample = it1.next();

				for (int i = 0; i < numChannels; i++)
				{
					float nextCorrectSample = (float) Math.sin(currentTime *angleFrequency[i]);
					if (currentTime < fadeTime)
					{
						double ratio  = currentTime / fadeTime;
						nextCorrectSample = (float) (nextCorrectSample  * ratio);
						
					}
					if (!approxEqual(nextSample[i], nextCorrectSample))
					{
						if (success)
						{
							System.out.println("Error:  Samples don't match for Fade In");
						}
						success = false;
					}
				}
				currentTime +=  1.0 / (double) sampleRate;

			}
		}
		catch (Exception e)
		{
			
			System.out.println("Exceotion thrown.  This is a problem" + e.getMessage());
			return false;
		}
			
		
		
		return success;
	}
	
	
	
	public static boolean testTrimSilence()
	{
		float startSilence = 1.3f;
		float nonSilence = 1.0f;
		float endSilence = 1.8f;
		int numChannels = 4;
		
		float sampleRate = 1000;
		float silenceThreshold = 0.05f;
		double timePerSample = 1/sampleRate;
		boolean success = true;
		
		double currentTime = 0.0f;
		
		try {
		SoundLinkedList soundList = new SoundLinkedList(sampleRate, numChannels);
			
		float nextSample[] = new float[numChannels];
		
		while (currentTime < startSilence)
		{
			for (int i = 0; i < numChannels; i++)
			{
				nextSample[i] = silenceThreshold / 2.0f;
			}
			soundList.addSample(nextSample);
			currentTime += 1 / sampleRate;
		}
		while (currentTime < nonSilence/2.0f + startSilence)
		{
			nextSample[numChannels - 1] = silenceThreshold / 2.0f;
			for (int i = 0; i < numChannels - 1; i++)
			{
				nextSample[i] = silenceThreshold * 2.0f;
			}
			soundList.addSample(nextSample);
			currentTime += 1 / sampleRate;
		}
		while (currentTime < 3 * nonSilence/4.0f + startSilence)
		{
			for (int i = 0; i < numChannels; i++)
			{
				nextSample[i] = silenceThreshold / 2.0f;
			}
			soundList.addSample(nextSample);
			currentTime += 1 / sampleRate;
		}
		while (currentTime < nonSilence + startSilence)
		{
			nextSample[numChannels-1] = silenceThreshold / 2.0f;
			for (int i = 1; i < numChannels - 1; i++)
			{
				nextSample[i] = silenceThreshold * 2.0f;
			}
			soundList.addSample(nextSample);
			currentTime += 1 / sampleRate;
		}
		while (currentTime < nonSilence + startSilence + endSilence)
		{
			for (int i = 0; i < numChannels; i++)
			{
				nextSample[i] = silenceThreshold / 2.0f;
			}
			soundList.addSample(nextSample);
			currentTime += 1 / sampleRate;
		}
		if (!approxEqual(soundList.getDuration(), startSilence + nonSilence + endSilence))
		{
			System.out.println("Something went wrong with testSilence.  Built clip should be  " + (startSilence + nonSilence + endSilence) + " but is " + soundList.getDuration()  );
			success = false;
		}
		soundList.trimSilence(silenceThreshold);
		if (!approxEqual(soundList.getDuration(),  nonSilence))
		{
			System.out.println("Something went wrong with testSilence.  After trim, should be  " + ( nonSilence ) + " but is " + soundList.getDuration()  );
			success = false;
		}	
		} catch (Exception e)
		{
			System.out.println("Something went wrong with testSilence: " + e.getMessage());
			return false;
			
		}
		return success;
				
	}
	public static boolean testSingleChanelSineWave(boolean play)
	{
		boolean success = true;
		float frequency = 100;
		float sampleRate = 10000;
		float angleFrequency =  (float) ( frequency * Math.PI * 2);
		float time = 1;

		try 
		{
			SoundLinkedList mList = SoundUtil.createSineWave(time, frequency, sampleRate);
			double currentTime = 0.0f;
			Iterator<float[]> it1= mList.iterator();
			while (it1.hasNext())
			{
				float nextSample = it1.next()[0];
				float nextCorrectSample = (float) Math.sin(currentTime *angleFrequency);
				currentTime +=  1.0 / (double) sampleRate;

				if (!approxEqual(nextSample, nextCorrectSample))
				{
					if (success)
					{
						System.out.println("Error:  Samples don't match");
					}
					success = false;
				}
			}
			if (!approxEqual((float) currentTime, time))
			{
				success = false;
				System.out.println("Error:  Times don't match");
			}
			if (!approxEqual(time, mList.getDuration()))
			{
				success = false;
				System.out.println("Error:  Duration don't match");
			}


			if (play)
			{
				SoundUtil.play(mList);
			}

		}
		catch (Exception e)
		{
			System.out.println("Failed: " + e.toString());
			return false;
		}
		return success;
	}


	public static void printResult(boolean success)
	{
		if (success)
		{
			System.out.println("Success!");
		}
		else
		{
			System.out.println("Falure!");
		}
	}



	public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {


		boolean play = false;
		
		boolean finalResult = true;

		System.out.println("Testing one channel simple sine wave");
		boolean result = testSingleChanelSineWave(play);
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing 2 channel simple sine wave");
		result = testMultiChannelSineWave(2, play);
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing 10 channel simple sine wave");
		result = testMultiChannelSineWave(2, false);  // Can't play 10 channel sounds anyway!
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing make mono (2 channel)");
		result = testMakeMono(2, play);
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing make mono (10 channel)");
		result = testMakeMono(10, play);
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing reverse (1 channel)");
		result = testReverse(1);
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing reverse (10 channels)");
		result = testReverse(10);
		finalResult = result && finalResult;
		printResult(result);
		
		System.out.println("Testing Combining lists with different sample rates (should throw exception)");
		result = testRescale(play);
		finalResult = result && finalResult;
		printResult(result);
		
		System.out.println("Testing fade in.  2 Channel, 3 second, 1 second fade in");
		result = testFadeIn(2,3,1);
		finalResult = result && finalResult;
		printResult(result);
		System.out.println("Testing fade in.  5 Channel, 7 second, 2.7 second fade in");
		result = testFadeIn(5,7,2.7f);
		finalResult = result && finalResult;
		printResult(result);

		System.out.println("Testing Trim Silence.");
		result = testTrimSilence();
		finalResult = result && finalResult;
		printResult(result);

		SoundLinkedList m = SoundUtil.readWAVFile("test2.wav");
		SoundLinkedList echo = (SoundLinkedList) m.clone();
		SoundLinkedList mono = (SoundLinkedList) m.clone();
		SoundLinkedList rev = (SoundLinkedList) m.clone();
		SoundLinkedList speedup = (SoundLinkedList) m.clone();
		SoundLinkedList slowdown = (SoundLinkedList) m.clone();
		SoundLinkedList trim = SoundUtil.readWAVFile("SilenceTrimmed.wav");
		SoundLinkedList fadein = (SoundLinkedList) m.clone();
		fadein.spliceIn(fadein.getDuration(), m.clone());
		SoundLinkedList fadeout = (SoundLinkedList) m.clone();
		fadeout.spliceIn(fadeout.getDuration(), m.clone());

		echo.addEcho(0.3f, 0.4f);
		mono.makeMono(true);
		rev.reverse();
		System.out.println("Playing normal version");
		SoundUtil.play(m);
		System.out.println("Playing mono version");
		SoundUtil.play(mono);
		System.out.println("Playing echo version");
		SoundUtil.play(echo);
		System.out.println("Playing reversed version");
		SoundUtil.play(rev);
		
		SoundLinkedList one_four = SoundUtil.readWAVFile("one_four.wav");
		SoundLinkedList two_three = SoundUtil.readWAVFile("two_three.wav");
		one_four.spliceIn(1, two_three);
		System.out.println("Playing one two three four");		
		SoundUtil.play(one_four);
		one_four.clip(1.0f,1.0f);
		System.out.println("Playing two");		
		SoundUtil.play(one_four);

		speedup.changeSpeed(1.8f);
		slowdown.changeSpeed(0.7f);
		
		System.out.println("Playing 1.8x speed");		
		SoundUtil.play(speedup);
		System.out.println("Playing 0.7x speed");		
		SoundUtil.play(slowdown);
		
		System.out.println("Playing Silence Trimmed 2x  (Shound not be silence between them!)");		
		trim.trimSilence(0.02f);
		SoundUtil.play(trim);
		SoundUtil.play(trim);
		
		fadein.fadeIn(1.5f);
		fadeout.fadeOut(1.5f, fadeout.getDuration() - 2.0f);
		System.out.println("Playing Fade in!");		
		SoundUtil.play(fadein);
		System.out.println("Playing Fade out!");		
		SoundUtil.play(fadeout);
		
		if 	(!finalResult)
		{
			System.out.println("AT LEAST ONE ERROR DETECTED!");		

		}
		else
		{
			System.out.println("Testing done.  Be sure to check audio to see that it sounds OK!");		
			
		}
		
	}

}