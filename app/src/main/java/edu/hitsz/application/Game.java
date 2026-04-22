package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.FireProp;
import edu.hitsz.prop.SuperFireProp;
import edu.hitsz.record.RecordDao;
import edu.hitsz.strategy.DirectShoot;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public abstract class Game extends Pane {

    private int backGroundTop = 0;

    //游戏逻辑更新间隔
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;

    //新增道具self
    private final List<AbstractProp> props;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 30;
    private int shootCounter = 0;

    //当前玩家分数和游戏难度
    private int score = 0;
    protected int difficulty;

    //生成boss相关
    private boolean bossExisting = false;
    private int bossScoreThreshold = 500;

    //游戏结束标志
    private boolean gameOverFlag = false;

    //画布和画笔
    private final Canvas canvas;
    private final GraphicsContext gc;

    //音乐开关
    protected MusicThread bgmThread;
    protected MusicThread bossBgmThread;

    //JavaFX 提供的动画定时器
    private AnimationTimer timer;
    //上一帧时刻(纳秒)
    private long lastNanoTime = 0L;
    //累加器，记录两帧之间经过的实际时间
    private long nanoAccumulator = 0L;


    protected Image backgroundImage;

    public Game() {
        setPrefSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        setPickOnBounds(true);

        canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);

        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();

        //新增道具
        props = new LinkedList<>();

        // 启动英雄机鼠标监听（移动/拖拽即跟随）
        new HeroController(this, heroAircraft);

        this.backgroundImage = getBackgroundImage();

        bgmThread = new MusicThread("src/main/resources/videos/bgm.wav", true);
        bgmThread.start();
    }

   protected abstract Image getBackgroundImage();


    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        if (timer != null) {
            return;
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOverFlag) {
                    stop();
                    return;
                }
                if (lastNanoTime == 0L) {
                    lastNanoTime = now;
                    return;
                }

                //固定时间步长
                long delta = now - lastNanoTime;
                lastNanoTime = now;
                nanoAccumulator += delta;

                long stepNanos = timeInterval * 1_000_000L;
                while (nanoAccumulator >= stepNanos && !gameOverFlag) {
                    nanoAccumulator -= stepNanos;
                    tick();
                }
                //重绘界面
                render();
            }
        };
        timer.start();

    }

    private void tick() {
        enemySpawnCounter++;
        if (enemySpawnCounter >=enemySpawnCycle) {
            enemySpawnCounter = 0;

            //生成boss
            if (score >= bossScoreThreshold && !bossExisting) {
                bossExisting = true;
                bossScoreThreshold += 500;

                EnemyFactory bossFactory = new edu.hitsz.factory.BossFactory();
                enemyAircrafts.add(bossFactory.createEnemy());

                System.out.println("WARNING: Boss Enemy Approaching!");

                if (bgmThread != null) {
                    bgmThread.stopMusic();
                }
                bossBgmThread = new MusicThread("src/main/resources/videos/bgm_boss.wav", true);
                bossBgmThread.start();
            }

            // 产生普通敌机
            if (enemyAircrafts.size() < enemyMaxNumber) {
                double randomValue = Math.random();
                EnemyFactory enemyFactory;

                if (randomValue < 0.05) {
                    enemyFactory = new AceFactory();
                } else if (randomValue < 0.15) {
                    enemyFactory = new AwakeFactory();
                } else if (randomValue < 0.35) {
                    enemyFactory = new EliteFactory();
                } else {
                    enemyFactory = new MobFactory();
                }

                enemyAircrafts.add(enemyFactory.createEnemy());
            }
        }
        // 飞机射出子弹
        shootAction();
        // 子弹移动
        bulletsMoveAction();
        // 飞机移动
        aircraftMoveAction();
        // 让道具往下掉self
        propsMoveAction();
        // 撞击检测
        crashCheckAction();
        // 后处理
        postProcessAction();
        // 游戏结束检查
        checkResultAction();
    }

    //***********************
    //      Action 各部分
    //***********************
    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            //英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            //敌机射击
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    new MusicThread("src/main/resources/videos/bullet_hit.wav", false).start();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();

                    if (enemyAircraft.notValid()) {
                        score += 10;

                        if (enemyAircraft instanceof BossEnemy) {
                            score += 50;
                            bossExisting = false;
                            for (int i = 0; i < 3; i++) {
                                int x = enemyAircraft.getLocationX() + (i - 1) * 30;
                                int y = enemyAircraft.getLocationY();

                                String[] types = {"Blood", "Bomb", "Freeze", "Fire", "SuperFire"};
                                String randomType = types[(int) (Math.random() * 5)];

                                props.add(PropFactory.createProp(randomType, x, y, 0, 5));
                            }
                            System.out.println("Boss Defeated! Waiting for the next one...");
                            if (bossBgmThread != null) {
                                bossBgmThread.stopMusic();
                            }
                            bgmThread = new MusicThread("src/main/resources/videos/bgm.wav", true);
                            bgmThread.start();
                        }

                        if (Math.random() < 0.8) {
                            int x = enemyAircraft.getLocationX();
                            int y = enemyAircraft.getLocationY();
                            String propType = "";

                            if (enemyAircraft instanceof AceEnemy) {
                                double rand = Math.random();
                                if (rand < 0.2) propType = "Blood";
                                else if (rand < 0.4) propType = "Bomb";
                                else if (rand < 0.6) propType = "Freeze";
                                else if (rand < 0.8) propType = "Fire";
                                else propType = "SuperFire";

                            } else if (enemyAircraft instanceof AwakeEnemy) {
                                double rand = Math.random();
                                if (rand < 0.25) propType = "Blood";
                                else if (rand < 0.5) propType = "Bomb";
                                else if (rand < 0.75) propType = "Fire";
                                else propType = "SuperFire";

                            } else if (enemyAircraft instanceof EliteEnemy) {
                                double rand = Math.random();
                                if (rand < 0.33) propType = "Blood";
                                else if (rand < 0.66) propType = "Fire";
                                else propType = "SuperFire";
                            }

                            if (!propType.isEmpty()) {
                                props.add(PropFactory.createProp(propType, x, y, 0, 5));
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                prop.active();
                new MusicThread("src/main/resources/videos/get_supply.wav", false).start();

                //这里还有问题，不确定爆炸声什么时候响
                if (prop instanceof edu.hitsz.prop.BombProp) {
                    new MusicThread("src/main/resources/videos/bomb_explosion.wav", false).start();
                }

                if (prop instanceof edu.hitsz.prop.FireProp || prop instanceof edu.hitsz.prop.SuperFireProp) {
                    System.out.println("火力加强！多线程计时器启动：10秒");

                    Runnable firepowerTask = () -> {
                        try {
                            Thread.sleep(10000);
                            heroAircraft.setShootNum(1);
                            heroAircraft.setShootStrategy(new DirectShoot());
                            System.out.println("火力加强结束，已恢复直射模式！");
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    };

                    new Thread(firepowerTask, "Firepower-Thread").start();
                }
                prop.vanish();
            }
        }

    }

    // 新增道具移动方法self
    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        //  删除无效道具
        props.removeIf(AbstractFlyingObject::notValid);
    }


    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            gameOverFlag = true;
            System.out.println("Game Over!");

            if (bgmThread != null) bgmThread.stopMusic();
            if (bossBgmThread != null) bossBgmThread.stopMusic();
            new MusicThread("src/main/resources/videos/game_over.wav", false).start();

            javafx.application.Platform.runLater(() -> {

                edu.hitsz.ui.ScoreBoard scoreBoard = new edu.hitsz.ui.ScoreBoard(this.difficulty);
                scoreBoard.show();

                TextInputDialog dialog = new TextInputDialog("testUserName");
                dialog.setTitle("保存战绩");
                dialog.setHeaderText("🎮 游戏结束！你的最终得分为: " + score);
                dialog.setContentText("请输入你的游戏昵称：");

                dialog.showAndWait().ifPresent(userName -> {
                    String finalName = userName.trim().isEmpty() ? "testUserName" : userName;

                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm");

                    edu.hitsz.record.Record newRecord = new edu.hitsz.record.Record(finalName, score, now.format(formatter));

                    scoreBoard.addRecordAndRefresh(newRecord);
                });
            });
        }
    };


    //***********************
    //      Render 各部分
    //***********************

    private void render() {
        // 背景滚动
        gc.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        gc.drawImage(this.backgroundImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT);
        gc.drawImage(this.backgroundImage, 0, this.backGroundTop);

        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        paintImageWithPositionRevised(enemyBullets);
        paintImageWithPositionRevised(heroBullets);
        paintImageWithPositionRevised(enemyAircrafts);

        // 绘制道具
        paintImageWithPositionRevised(props);

        // 绘制英雄机
        gc.drawImage(
                ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2.0,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2.0
        );

        paintScoreAndLife();
    }

    private void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Image image = object.getImage();
            if (image == null) {
                throw new IllegalStateException(objects.getClass().getName() + " has object without image: " + object.getClass().getName());
            }
            gc.drawImage(
                    image,
                    object.getLocationX() - image.getWidth() / 2.0,
                    object.getLocationY() - image.getHeight() / 2.0
            );
        }
    }

    private void paintScoreAndLife() {
        int x = 10;
        int y = 25;
        gc.setFill(Color.rgb(255, 0, 0));
        gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 22));
        gc.fillText("SCORE:" + this.score, x, y);
        y = y + 20;
        gc.fillText("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}