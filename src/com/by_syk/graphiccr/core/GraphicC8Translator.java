/**
 * Copyright 2016 By_syk
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.by_syk.graphiccr.core;

import com.by_syk.graphiccr.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 第8类图形验证码识别
 * <br />针对截至 2018/12/26 为止安徽医科大学新系统登录用的验证码
 * <br />图形尺寸为 200*80
 *
 * @author By_jqorz
 */
public class GraphicC8Translator {
    /**
     * 元字符宽度，应该为分离元字符的最大宽度
     */
    private static final int UNIT_W = 63;
    /**
     * 元字符高度
     */
    private static final int UNIT_H = 63;
    /**
     * 训练元字符数
     */
    private static final int TRAIN_NUM = 33;
    /**
     * 所有元字符
     */
    private static final char[] TRAIN_CHARS = {/*'0', '1', */'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', /*'I', */'J', 'K', 'L', 'M', 'N',
            /*'O', */'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    /**
     * 有效像素颜色值
     */
    private static final int TARGET_COLOR = Color.BLACK.getRGB();
    /**
     * 无效像素颜色值
     */
    private static final int USELESS_COLOR = Color.WHITE.getRGB();
    private static GraphicC8Translator translator = null;
    private BufferedImage trainImg = null;

    private GraphicC8Translator() {
    }

    public static GraphicC8Translator getInstance() {
        if (translator == null) {
            translator = new GraphicC8Translator();
        }

        return translator;
    }

    /**
     * 目标像素判断
     * <br />（基于明度）
     *
     * @param colorInt
     * @return
     */
    private boolean isTarget(int colorInt) {
        Color color = new Color(colorInt);
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return hsb[2] < 0.7f; // b小于0.7
    }

    /**
     * 去噪
     *
     * @param picFile 图形验证码文件
     * @return
     * @throws Exception
     */
    private BufferedImage denoise(File picFile) throws Exception {
        BufferedImage img = ImageIO.read(picFile);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (x > 1 && x < width - 1 && y > 8 && y < height - 3
                        && isTarget(img.getRGB(x, y))) {
                    img.setRGB(x, y, TARGET_COLOR);
                } else {
                    img.setRGB(x, y, USELESS_COLOR);
                }
            }
        }

        for (int x = 1; x < width - 1; ++x) {
            for (int y = 13; y < height - 1; ++y) {
                if (img.getRGB(x, y) == TARGET_COLOR) {
                    int shotNum = 0;
                    for (int i = 0; i < 9; ++i) {
                        shotNum += img.getRGB(x - 1 + (i % 3), y - 1 + (i / 3)) == TARGET_COLOR ? 1 : 0;
                    }
                    if (shotNum <= 3) {
                        img.setRGB(x, y, USELESS_COLOR);
                    }
                }
            }
        }

        return img;
    }

    /**
     * 分割元字符
     *
     * @param img
     * @return
     * @throws Exception
     */
    private List<BufferedImage> split(BufferedImage img) throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        subImgs.add(fix(img.getSubimage(1, 8, 63, UNIT_H)));
        subImgs.add(fix(img.getSubimage(58, 8, 50, UNIT_H)));
        subImgs.add(fix(img.getSubimage(105, 8, 53, UNIT_H)));
        subImgs.add(fix(img.getSubimage(150, 8, 50, UNIT_H)));
        return subImgs;
    }

    /**
     * 纠偏
     */
    private BufferedImage fix(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int xOffset = 0;
        int yOffset = 0;
        outter1:
        for (int i = 0, times = 0, last = -1; i < w; ++i) {
            for (int j = 0, count = 0; j < h; ++j) {
                if (img.getRGB(i, j) == TARGET_COLOR && ++count > 1) {
                    if (i == last + 1) {
                        if (++times == 4) {
                            xOffset = i - 3;
                            break outter1;
                        }
                    } else {
                        times = 1;
                    }
                    last = i;
                    break;
                }
            }
        }
        outter2:
        for (int i = 0, times = 0, last = -1; i < h; ++i) {
            for (int j = 0, count = 0; j < w; ++j) {
                if (img.getRGB(j, i) == TARGET_COLOR && ++count > 1) {
                    if (i == last + 1) {
                        if (++times == 4) {
                            yOffset = i - 3;
                            break outter2;
                        }
                    } else {
                        times = 1;
                    }
                    last = i;
                    break;
                }
            }
        }

        BufferedImage newImg = new BufferedImage(UNIT_W, UNIT_H, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < UNIT_W; ++i) {
            for (int j = 0; j < UNIT_H; ++j) {
                if (xOffset + i < w && yOffset + j < h) {
                    newImg.setRGB(i, j, img.getRGB(xOffset + i, yOffset + j));
                } else {
                    newImg.setRGB(i, j, USELESS_COLOR);
                }
            }
        }
        return newImg;
    }

    /**
     * 取出训练数据
     *
     * @return
     * @throws Exception
     */
    private BufferedImage loadTrainData() throws Exception {
        if (trainImg == null) {
            File file = new File(Test.ROOT+"/train/out/8/train.png");
            trainImg = ImageIO.read(file);
        }

        return trainImg;
    }

    /**
     * 将训练元字符装在一起
     *
     * @param trainImg
     * @param smallImg
     * @param ch
     * @return
     */
    private boolean addTrainImg(BufferedImage trainImg, BufferedImage smallImg, char ch) {
        int which = Arrays.binarySearch(TRAIN_CHARS, ch);
        int x = -1;
        int y = -1;
        for (int i = 0; i < TRAIN_NUM; ++i) {
            try {
                if (trainImg.getRGB(i * UNIT_W, which * (UNIT_H + 1) + UNIT_H) != TARGET_COLOR) {
                    x = i * UNIT_W;
                    y = which * (UNIT_H + 1);
                    break;
                }
            } catch (Exception e) {
                System.out.println("which=" + ch);
            }

        }

        if (x == -1 || y == -1) {
            return false;
        }

        for (int i = 0; i < UNIT_W; ++i) {
            for (int j = 0; j < UNIT_H; ++j) {
                trainImg.setRGB(x + i, y + j, smallImg.getRGB(i, j));
            }
        }
        trainImg.setRGB(x, y + UNIT_H, TARGET_COLOR);
        return true;
    }

    /**
     * 训练
     *
     * @param rawDir
     * @param targetTrainFile
     * @return
     */
    public boolean train(File rawDir, File targetTrainFile) {
        try {
            BufferedImage trainImg = new BufferedImage(UNIT_W * TRAIN_NUM, (UNIT_H + 1) * TRAIN_CHARS.length, BufferedImage.TYPE_INT_ARGB);
            for (File file : rawDir.listFiles()) {
                BufferedImage img = denoise(file);
                List<BufferedImage> listImg = split(img);
                String[] parts = file.getName().split("\\.");
                char[] chars = parts[0].toCharArray();
                char[] addFlags;//如果在测试时字母标为0，则不将该字母加到训练集
                if (parts.length > 2) {
                    addFlags = parts[1].toCharArray();
                } else {
                    addFlags = new char[]{'1', '1', '1', '1'};
                }
                for (int i = 0, len = listImg.size(); i < len; ++i) {
                    if (addFlags[i] == '1') {
                        addTrainImg(trainImg, listImg.get(i), chars[i]);
                    }
                }
            }
            return ImageIO.write(trainImg, "PNG", targetTrainFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 单元识别
     *
     * @param img
     * @param trainImg
     * @return
     */
    private char recognize(BufferedImage img, BufferedImage trainImg) {
        char result = ' ';
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height; // 最小差异像素数
        for (int i = 0; i < TRAIN_NUM; ++i) {
            for (int j = 0; j < TRAIN_CHARS.length; ++j) {
                int startX = UNIT_W * i;
                int startY = (UNIT_H + 1) * j;
                if (trainImg.getRGB(startX, startY + UNIT_H) != TARGET_COLOR) {
                    continue;
                }
                int count = 0; // 差异像素数
                for (int x = 0; x < UNIT_W; ++x) {
                    for (int y = 0; y < UNIT_H; ++y) {
                        count += (img.getRGB(x, y) != trainImg.getRGB(startX + x, startY + y) ? 1 : 0);
                        if (count >= min) {
                            break;
                        }
                    }
                }
                if (count < min) {
                    min = count;
                    result = TRAIN_CHARS[j];
                }
            }
        }

        return result;
    }

    /**
     * 识别
     *
     * @param picFile 图形验证码文件
     * @return
     */
    public String translate(File picFile) {
        String result = "";
        try {
            BufferedImage img = denoise(picFile);
            List<BufferedImage> listImg = split(img);
            BufferedImage trainImg = loadTrainData();
            for (BufferedImage bi : listImg) {
                result += recognize(bi, trainImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}