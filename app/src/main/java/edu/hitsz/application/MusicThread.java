package edu.hitsz.application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends Thread {

    // 音频文件名 (全路径)
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;

    private boolean isLoop;

    private volatile boolean isPlaying = true;

    public MusicThread(String filename, boolean isLoop) {
        this.filename = filename;
        this.isLoop = isLoop;
        reverseMusic();
    }

    public void stopMusic() {
        this.isPlaying = false;
    }

    public void reverseMusic() {
        try {
            // 定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            // 用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("音频格式不支持！请确保文件是标准的 .wav 格式：" + filename);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("音频文件读取失败！请检查路径是否正确：" + filename);
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            System.err.println("音频数据流加载进内存时发生异常！");
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        // 源数据行SoureDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        // 获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            System.err.println("声卡线路被占用或不可用！无法播放：" + filename);
            e.printStackTrace();
        }

        if (dataLine == null) return; // 防止空指针

        dataLine.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1 && isPlaying) {
                // 从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead = source.read(buffer, 0, buffer.length);
                // 通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException ex) {
            System.err.println("向混频器写入音频数据时发生异常！");
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();
    }

    @Override
    public void run() {
        do {
            InputStream stream = new ByteArrayInputStream(samples);
            play(stream);
        } while (isLoop && isPlaying);
    }
}