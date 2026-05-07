package edu.hitsz.application;

/**
 * 全局音频管理类 (统管 BGM 和 音效)
 */
public class MusicManager {

    private static MusicThread bgmThread;
    private static MusicThread bossBgmThread;

    public static void playBGM() {
        stopAllBGM();
        bgmThread = new MusicThread("src/main/resources/videos/bgm.wav", true);
        bgmThread.start();
    }

    public static void playBossBGM() {
        stopAllBGM();
        bossBgmThread = new MusicThread("src/main/resources/videos/bgm_boss.wav", true);
        bossBgmThread.start();
    }

    public static void stopAllBGM() {
        if (bgmThread != null) {
            bgmThread.stopMusic();
            bgmThread = null;
        }
        if (bossBgmThread != null) {
            bossBgmThread.stopMusic();
            bossBgmThread = null;
        }
    }

    public static void playSoundEffect(String fileName) {
        new MusicThread("src/main/resources/videos/" + fileName, false).start();
    }
}