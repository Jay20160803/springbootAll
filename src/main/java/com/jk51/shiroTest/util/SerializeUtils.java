package com.jk51.shiroTest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/4
 * 修改记录:
 */
public class SerializeUtils {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);


    public static Object deserialize(byte[] bytes){
        Object result = null;

        if(isEmpty(bytes)){
            return result;
        }

        try{

            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
            result = objectInputStream.readObject();

        }catch (Exception e){

            logger.error("failed to deserialize, error info:{}",e);
        }

        return result;

    }


    public static byte[] serialize(Object object){

        byte[] result = new byte[0];

        if(object == null){
            return result;
        }

        try{

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);

            if(!(object instanceof Serializable)){
                throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload "
                        + "but received an object of type [" + object.getClass().getName() + "]");
            }

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result = byteArrayOutputStream.toByteArray();

        }catch (Exception e){
            logger.error("failed serialize error info:{}",e);
        }

        return result;
    }

    private static boolean isEmpty(byte[] bytes){
        return bytes == null || bytes.length == 0;
    }
}
