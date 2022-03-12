package kr.timehub.beplan.utils;

import android.media.AudioRecord;
import android.util.Log;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class AudioReader {
    private static final float FUDGE = 0.6f;
    private static final float MAX_16_BIT = 32768.0f;
    private static final String TAG = "WindMeter";
    private short[] audioData;
    private AudioRecord audioInput;
    private short[][] inputBuffer = null;
    private int inputBufferWhich = 0;
    private int inputBufferIndex = 0;
    private int inputBlockSize = 0;
    private long sleepTime = 0;
    private Listener inputListener = null;
    private boolean running = false;
    private Thread readerThread = null;
    private long audioSequence = 0;
    private long audioProcessed = 0;
    private int audioCheckCount = 0;

    /* loaded from: classes.dex */
    public static abstract class Listener {
        public static final int ERR_INIT_FAILED = 1;
        public static final int ERR_OK = 0;
        public static final int ERR_READ_FAILED = 2;

        public abstract void onReadComplete(int i);

        public abstract void onReadError(int i);
    }

    public int calculatePowerDb(short[] sArr, int i, int i2) {
        long j;
        double d = 0.0d;
        double d2 = 0.0d;
        for (int i3 = 0; i3 < i2; i3++) {
            d += sArr[i + i3];
            d2 += j * j;
        }
        double d3 = i2;
        return (int) ((Math.log10(((d2 - ((d * d) / d3)) / d3) / 1.073741824E9d) * 10.0d) + 0.6000000238418579d);
    }

    public void startReader(int i, int i2, Listener listener) {
        Log.i(TAG, "Reader: Start Thread");
        synchronized (this) {
            this.audioInput = new AudioRecord(1, i, 2, 2, AudioRecord.getMinBufferSize(i, 2, 2) * 2);
            this.inputBlockSize = i2;
            this.sleepTime = 1000.0f / (i / i2);
            this.inputBuffer = (short[][]) Array.newInstance(short.class, 2, this.inputBlockSize);
            this.inputBufferWhich = 0;
            this.inputBufferIndex = 0;
            this.audioCheckCount = 0;
            this.inputListener = listener;
            this.running = true;
            this.readerThread = new Thread(new Runnable() { // from class: kr.timehub.beplan.utils.AudioReader.1
                @Override // java.lang.Runnable
                public void run() {
                    AudioReader.this.readerRun();
                }
            }, "Audio Reader");
            this.readerThread.start();
        }
    }

    public void stopReader() {
        Log.i(TAG, "Reader: Signal Stop");
        synchronized (this) {
            this.running = false;
        }
        try {
            if (this.readerThread != null) {
                this.readerThread.join();
            }
        } catch (InterruptedException unused) {
        }
        this.readerThread = null;
        synchronized (this) {
            if (this.audioInput != null) {
                this.audioInput.release();
                this.audioInput = null;
            }
        }
        Log.i(TAG, "Reader: Thread Stopped");
    }

    public void readerRun() {
        boolean z;
        for (int i = 200; i > 0; i -= 50) {
            try {
                if (this.audioInput.getState() == 1) {
                    break;
                }
                Thread.sleep(50L);
            } catch (InterruptedException unused) {
            }
        }
        if (this.audioInput.getState() != 1) {
            Log.e(TAG, "Audio reader failed to initialize");
            readError(1);
            this.running = false;
            return;
        }
        try {
            Log.i(TAG, "Reader: Start Recording");
            this.audioInput.startRecording();
            while (true) {
                if (!this.running) {
                    break;
                }
                long currentTimeMillis = System.currentTimeMillis();
                if (!this.running) {
                    break;
                }
                int i2 = this.inputBlockSize;
                int i3 = this.inputBlockSize - this.inputBufferIndex;
                if (i2 > i3) {
                    i2 = i3;
                }
                short[] sArr = this.inputBuffer[this.inputBufferWhich];
                int i4 = this.inputBufferIndex;
                synchronized (sArr) {
                    int read = this.audioInput.read(sArr, i4, i2);
                    if (this.running) {
                        if (read < 0) {
                            Log.e(TAG, "Audio read failed: error " + read);
                            readError(2);
                            this.running = false;
                        } else {
                            int i5 = this.inputBufferIndex + read;
                            if (i5 >= this.inputBlockSize) {
                                this.inputBufferWhich = (this.inputBufferWhich + 1) % 2;
                                this.inputBufferIndex = 0;
                                z = true;
                            } else {
                                this.inputBufferIndex = i5;
                                z = false;
                            }
                            if (z) {
                                readDone(sArr);
                                long currentTimeMillis2 = this.sleepTime - (System.currentTimeMillis() - currentTimeMillis);
                                if (currentTimeMillis2 < 5) {
                                    currentTimeMillis2 = 5;
                                }
                                try {
                                    sArr.wait(currentTimeMillis2);
                                } catch (InterruptedException unused2) {
                                }
                            }
                        }
                    }
                }
                break;
            }
        } finally {
            Log.i(TAG, "Reader: Stop Recording");
            if (this.audioInput.getState() == 3) {
                this.audioInput.stop();
            }
        }
    }

    private void readDone(short[] sArr) {
        synchronized (this) {
            this.audioData = sArr;
            this.audioSequence++;
            short[] sArr2 = null;
            if (this.audioData != null && this.audioSequence > this.audioProcessed) {
                this.audioProcessed = this.audioSequence;
                sArr2 = this.audioData;
            }
            if (sArr2 != null) {
                int length = sArr2.length;
                if (this.audioCheckCount > 15) {
                    this.inputListener.onReadComplete(calculatePowerDb(sArr2, 0, length));
                } else {
                    this.audioCheckCount++;
                }
                sArr2.notify();
            }
        }
    }

    private void readError(int i) {
        this.inputListener.onReadError(i);
    }
}
