package edu.hitsz.application;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * 音频播放线程类
 * 支持音效的单次播放和 BGM 的循环播放
 */
public class MusicThread extends Thread {
    private String filename;
    private boolean loop;
    private volatile boolean playing = true;

    public MusicThread(String filename, boolean loop) {
        this.filename = filename;
        this.loop = loop;
    }

    @Override
    public void run() {
        try {
            do {
                File file = new File(filename);
                if (!file.exists()) {
                    System.err.println("⚠️ 找不到音频文件，请检查路径: " + filename);
                    return;
                }

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

                line.open(format);
                line.start();

                int bufferSize = 1024 * 1024;
                byte[] buffer = new byte[bufferSize];
                int bytesRead = 0;

                while (playing && (bytesRead = audioInputStream.read(buffer)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }

                line.drain();
                line.close();
                audioInputStream.close();

            } while (loop && playing);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        this.playing = false;
    }
}