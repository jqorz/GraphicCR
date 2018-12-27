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

package com.by_syk.graphiccr;

import com.by_syk.graphiccr.core.*;
import com.by_syk.graphiccr.util.ExtraUtil;

import java.io.File;

public class Test {
    public static final String ROOT = "D:/Android/Coding_Java/GraphicCR";

    public static void main(String[] args) {
        testGraphicC8();

//        String result = GraphicCTranslator.translate("D:/B7NC.jpeg", GraphicCTranslator.TYPE_8);
//        System.out.println(result);
    }

    private static void testGraphicC1() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/1/test");

        for (int i = 0; i < 10; ++i) {
            ExtraUtil.downloadFile("http://jwpt.neuq.edu.cn/ACTIONVALIDATERANDOMPICTURE.APPPROCESS",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DOWNLOAD TEST DONE");

        GraphicC1Translator translator = GraphicC1Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC2() {
        GraphicC2Translator translator = GraphicC2Translator.getInstance();
        File rawDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/2/raw");
        File trainFile = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/2/train/train.png");
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/2/test");

        translator.train(rawDir, trainFile);

        System.out.println("TRAIN DONE");

        for (int i = 0; i < 10; ++i) {
            ExtraUtil.downloadFile("http://61.139.105.138/CheckCode.aspx",
                    new File(testDir, System.currentTimeMillis() + ".gif"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DOWNLOAD TEST DONE");

        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".gif"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC3() {
        GraphicC3Translator translator = GraphicC3Translator.getInstance();
        File rawDir = new File("D:/Coding/Coding_JAVA/GraphicCR/train/raw/graphicc3_raw");
        File trainFile = new File("D:/Coding/Coding_JAVA/GraphicCR/train/out/3/train.png");
        File testDir = new File("D:/Coding/Coding_JAVA/GraphicCR/train/test/3");

        translator.train(rawDir, trainFile);

        System.out.println("TRAIN DONE");

//        for (int i = 0; i < 100; ++i) {
//            ExtraUtil.downloadFile("http://211.70.128.23/JWWEB/sys/ValidateCode.aspx",
//                    new File(testDir, System.currentTimeMillis() + ".jpg"));
//            System.out.println("DOWNLOAD TEST " + (i + 1));
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        System.out.println("DOWNLOAD TEST DONE");

        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC4() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/4/test");

        for (int i = 0; i < 100; ++i) {
            ExtraUtil.downloadFile("http://211.64.127.221/validateCodeAction.do?random=0.44780089727138145",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DOWNLOAD TEST DONE");

        GraphicC4Translator translator = GraphicC4Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC5() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/5/test");

        for (int i = 0; i < 5; ++i) {
            ExtraUtil.downloadFile("http://218.64.56.18/jsxsd/verifycode.servlet?t=0.7339572516226678",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DOWNLOAD TEST DONE");

        GraphicC5Translator translator = GraphicC5Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC6() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/6/test");

        for (int i = 0; i < 10; ++i) {
            ExtraUtil.downloadFile("http://211.70.149.135:88/CheckCode.aspx",
                    new File(testDir, System.currentTimeMillis() + ".gif"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DOWNLOAD TEST DONE");

        GraphicC6Translator translator = GraphicC6Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".gif"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC7() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/7/test");

        for (int i = 0; i < 0; ++i) {
            ExtraUtil.downloadFile("http://jwglxt.qau.edu.cn/verifycode.servlet?t=0.3780019717034797",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("DOWNLOAD TEST DONE");

        GraphicC7Translator translator = GraphicC7Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }

    private static void testGraphicC8() {
        GraphicC8Translator translator = GraphicC8Translator.getInstance();
        File rawDir = new File(ROOT + "/train/raw/graphicc8_raw");
        File trainFile = new File(ROOT + "/train/out/8/train.png");
        File testDir = new File(ROOT + "/train/test/8");

        translator.train(rawDir, trainFile);

        System.out.println("TRAIN DONE");

//        for (int i = 0; i < 10; ++i) {
//            ExtraUtil.downloadFile("http://jiaowu.ahmu.edu.cn//SercurityCode",
//                    new File(testDir, System.currentTimeMillis() + ".jpg"));
//            System.out.println("DOWNLOAD TEST " + (i + 1));
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("DOWNLOAD TEST DONE");

        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }

        System.out.println("TRANSLATE DONE");
    }
}
