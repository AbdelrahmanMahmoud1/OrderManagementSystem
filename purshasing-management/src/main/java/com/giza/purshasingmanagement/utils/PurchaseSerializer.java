package com.giza.purshasingmanagement.utils;

import com.giza.purshasingmanagement.entity.Purchase;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PurchaseSerializer implements Serializer<Purchase> {

    @Override
    public byte[] serialize(String topic, Purchase data) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(data);
            objectStream.flush();
            objectStream.close();
            return byteStream.toByteArray();
        }
        catch (IOException e) {
            throw new IllegalStateException("Can't serialize purchase: " + data, e);
        }
    }
}
