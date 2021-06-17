package com.douzhenbo.config.serializer;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 9:18
 **/
public class MyStringRedisSerializer implements RedisSerializer<Object> {
    private final Charset charset;
    public static final MyStringRedisSerializer US_ASCII;
    public static final MyStringRedisSerializer ISO_8859_1;
    public static final MyStringRedisSerializer UTF_8;

    public MyStringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public MyStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return bytes == null ? null : new String(bytes, this.charset);
    }




    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o==null){
            return new byte[0];
        }
        if(o instanceof String){
            return o.toString().getBytes(charset);
        }else{
            String str= JSON.toJSONString(o);
            return str.getBytes(charset);
        }
    }



    static {
        US_ASCII = new MyStringRedisSerializer(StandardCharsets.US_ASCII);
        ISO_8859_1 = new MyStringRedisSerializer(StandardCharsets.ISO_8859_1);
        UTF_8 = new MyStringRedisSerializer(StandardCharsets.UTF_8);
    }


}

