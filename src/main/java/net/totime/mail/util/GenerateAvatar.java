package net.totime.mail.util;

import lombok.Data;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/18
 * @description 生成头像
 * @since 1.0.0
 */
public class GenerateAvatar {
    /**
     * 背景颜色
     */
    private final static Color BACK_GROUND_COLOR = new Color(238, 238, 238);
    /**
     * 图片宽
     */
    private final static int IMG_WIDTH = 100;
    /**
     * 图片高
     */
    private final static int IMG_HEIGHT = 100;
    /**
     * 图片边缘内边距
     */
    private final static int PADDING = 10;
    /**
     * 填充比率，越接近1，有色色块出现几率越高
     */
    private final static double RADIO = 0.45;
    /**
     * 每边矩形数量（建议>=5）
     */
    private static final int BLOCK_NUM = 8;
    /**
     * 颜色差值评价值（越大颜色越鲜艳）
     */
    private final static int COLOR_DIFF_EVALUATION = 100;
    /**
     * 基色阶数极限
     */
    private final static int COLOR_LIMIT = 256;

    /**
     * 获取头像
     *
     * @param hash 哈希值
     * @return {@link byte[]} 头像
     * @throws IOException IO异常
     */
    public static byte[] generateAvatar(Long hash) throws IOException {
        BufferedImage bi = new BufferedImage
                (IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(BACK_GROUND_COLOR);
        g2.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
        Color mainColor = getRandomColor(hash);
        List<Point> pointList = getRandomPointList(hash);
        fillGraph(g2, pointList, mainColor);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", out);
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }

    /**
     * 填充图形
     *
     * @param g2        画笔
     * @param pointList 填充块坐标
     * @param mainColor 填充颜色
     */
    private static void fillGraph(Graphics2D g2, List<Point> pointList, Color mainColor) {
        int rowBlockLength = (IMG_HEIGHT - 2 * PADDING) / BLOCK_NUM;
        int colBlockLength = (IMG_WIDTH - 2 * PADDING) / BLOCK_NUM;
        // 填充
        g2.setColor(mainColor);
        // 遍历points
        for (Point point : pointList) {
            g2.fillRect(PADDING + point.x * rowBlockLength,
                    PADDING + point.y * colBlockLength,
                    rowBlockLength, colBlockLength);
        }
    }

    /**
     * 获取颜色位置列表
     *
     * @param hash 哈希
     * @return List<Point> 列表
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    private static List<Point> getRandomPointList(long hash) {
        Random random = new Random(hash);
        ArrayList<Point> points = new ArrayList<>();

        for (int i = 0; i < BLOCK_NUM / 2; i++) {
            for (int j = 0; j < BLOCK_NUM; j++) {
                if (random.nextDouble() < GenerateAvatar.RADIO) {
                    points.add(new Point(i, j));
                }
            }
        }

        addReversePoints(points);

        //如果是奇数，中间一列也要填充
        //noinspection ConstantValue
        if (BLOCK_NUM % 2 == 1) {
            for (int i = 0; i < BLOCK_NUM; i++) {
                if (Math.random() < GenerateAvatar.RADIO) {
                    points.add(new Point(BLOCK_NUM / 2, i));
                }
            }
        }
        return points;
    }

    /**
     * 获取颜色
     *
     * @param hash 哈希
     * @return Color对象
     */
    private static Color getRandomColor(long hash) {
        Random random = new Random(hash);
        int r, g, b;
        do {
            r = random.nextInt(COLOR_LIMIT);
            g = random.nextInt(COLOR_LIMIT);
            b = random.nextInt(COLOR_LIMIT);
        } while (evaluateColor(r, g, b));
        return new Color(r, g, b);
    }

    /**
     * 颜色品质，只需任意两种颜色差值大于某个规定值即可
     *
     * @return boolean
     */
    private static boolean evaluateColor(int r, int g, int b) {
        int rg = Math.abs(r - g);
        int rb = Math.abs(r - b);
        int gb = Math.abs(g - b);
        int max = rg > rb ? (Math.max(rg, gb)) : (Math.max(rb, gb));
        return max < COLOR_DIFF_EVALUATION;
    }

    /**
     * 添加对称坐标
     *
     * @param points point的列表
     */
    private static void addReversePoints(List<Point> points) {
        ArrayList<Point> pointListCopy = new ArrayList<>(points);
        for (Point point : pointListCopy) {
            points.add(new Point((BLOCK_NUM - 1 - point.x), point.y));
        }
    }

    /**
     * 坐标
     */
    @Data
    private static class Point {
        private final int x;
        private final int y;
    }
}
