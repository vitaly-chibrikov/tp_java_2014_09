package main;

import reflection.ReflectionHelper;
import sax.ReadXMLFileSAX;
import time.TimeHelper;
import time.TimeService;
import vfs.VFS;
import vfs.VFSImpl;

import java.io.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        randomExample();
        //timeExample();
        //timerExample();
        //vfsExample();

        //writeToBinFile();
        //readFromBinFile();
        //reflectionExample();
        //factoryExample();
        //saxExample();
    }

    private static void saxExample() {
        SerializationObject object = (SerializationObject) ReadXMLFileSAX.readXML("test.xml");
        if (object != null)
            System.out.append(object.toString() + '\n');
    }

    private static void factoryExample() {
        SerializationObject object = (SerializationObject) ObjectFactory.readObject("test.bin");
        System.out.append(object.toString() + '\n');
    }

    private static void reflectionExample() {
        SerializationObject object = (SerializationObject) ReflectionHelper.createIntance("main.SerializationObject");
        System.out.append(object.toString() + '\n');

        ReflectionHelper.setFieldValue(object, "name", "Kaylee");
        ReflectionHelper.setFieldValue(object, "age", "24");
        System.out.append(object.toString() + '\n');
    }

    private static void readFromBinFile() {
        System.out.append("Read from bin file\n");
        try {
            FileInputStream fileOut = new FileInputStream("test.bin");
            ObjectInputStream in = new ObjectInputStream(fileOut);
            SerializationObject object = (SerializationObject) in.readObject();
            System.out.append(object.toString() + '\n');
            in.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeToBinFile() {
        System.out.append("Write to bin file\n");
        try {
            SerializationObject object = new SerializationObject("Zoe", 31);
            FileOutputStream fileOut = new FileOutputStream("test.bin");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static void vfsExample() {
        VFS vfs = new VFSImpl("");
        System.out.append("Current path: " + vfs.getAbsolutePath(""));
        Iterator<String> iter = vfs.getIterator("./");
        while (iter.hasNext()) {
            System.out.append(iter.next() + "\n");
        }
    }


    private static void timerExample() {
        System.out.append("ENGLISH full time: " + TimeHelper.getUserTimeFull(Locale.ENGLISH) + '\n');
        final int timeMs = 10000;
        TimeService.instance().start();
        TimeService.instance().scheduleTask(new TimerTask() {
            public void run() {
                System.out.append("Timer run!\n");
                System.out.append("ENGLISH full time: " + TimeHelper.getUserTimeFull(Locale.ENGLISH) + '\n');
                TimeService.instance().stop();
            }

        }, timeMs);
    }


    private static void randomExample() {
        Random rnd = new Random();
        System.out.append("R0: " + rnd.nextInt(100) + '\n');
        System.out.append("R1: " + rnd.nextInt(100) + '\n');
        System.out.append("R2: " + rnd.nextInt(100) + '\n');
        System.out.append('\n');

        Random rndOnLong = new Random(100500L);
        System.out.append("R(100500L)0: " + rndOnLong.nextInt(100) + '\n');
        System.out.append("R(100500L)1: " + rndOnLong.nextInt(100) + '\n');
        System.out.append("R(100500L)2: " + rndOnLong.nextInt(100) + '\n');
        System.out.append('\n');

        Random rndOnTime = new Random(TimeHelper.getTimeInMs());
        System.out.append("R(Time)0: " + rndOnTime.nextInt(100) + '\n');
        System.out.append("R(Time)1: " + rndOnTime.nextInt(100) + '\n');
        System.out.append("R(Time)2: " + rndOnTime.nextInt(100) + '\n');
        System.out.append('\n');

        System.out.append("Math.random(): " + Math.random() + '\n');
    }

    private static void timeExample() {
        System.out.append("milliseconds: " + TimeHelper.getTimeInMs() + '\n');
        System.out.append("POSIX: " + TimeHelper.getPOSIX() + '\n');
        System.out.append('\n');
        System.out.append("ENGLISH full date: " + TimeHelper.getUserDateFull(Locale.ENGLISH) + '\n');
        System.out.append("GERMAN full date: " + TimeHelper.getUserDateFull(Locale.GERMAN) + '\n');
        System.out.append("FRENCH full date: " + TimeHelper.getUserDateFull(Locale.FRENCH) + '\n');
        System.out.append('\n');
        System.out.append("ENGLISH short date: " + TimeHelper.getUserDateShort(Locale.ENGLISH) + '\n');
        System.out.append("GERMAN short date: " + TimeHelper.getUserDateShort(Locale.GERMAN) + '\n');
        System.out.append("FRENCH short date: " + TimeHelper.getUserDateShort(Locale.FRENCH) + '\n');
        System.out.append('\n');
        System.out.append("ENGLISH full time: " + TimeHelper.getUserTimeFull(Locale.ENGLISH) + '\n');
        System.out.append("GERMAN full time: " + TimeHelper.getUserTimeFull(Locale.GERMAN) + '\n');
        System.out.append("FRENCH full time: " + TimeHelper.getUserTimeFull(Locale.FRENCH) + '\n');
        System.out.append('\n');
    }


}
