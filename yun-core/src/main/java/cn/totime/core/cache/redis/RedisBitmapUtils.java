package cn.totime.core.cache.redis;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author JanYork
 * @date 2023/1/14 12:21
 * @description Redis位图操作通用工具类
 */
@Component
public class RedisBitmapUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 将指定param的值设置为1，{@param param}会经过hash计算进行存储。
     *
     * @param key   bitmap结构的key
     * @param param 要设置偏移的key，该key会经过hash运算。
     * @param value true：即该位设置为1，否则设置为0
     * @return 返回设置该value之前的值。
     */
    public Boolean setBit(String key, String param, boolean value) {
        return redisTemplate.opsForValue().setBit(key, param.hashCode(), value);
    }

    /**
     * 将指定param的值设置为0，{@param param}会经过hash计算进行存储。
     *
     * @param key   bitmap结构的key
     * @param param 要移除偏移的key，该key会经过hash运算。
     * @return 若偏移位上的值为1，那么返回true。
     */
    public Boolean getBit(String key, String param) {
        return redisTemplate.opsForValue().getBit(key, param.hashCode());
    }


    /**
     * 将指定offset偏移量的值设置为1；
     *
     * @param key    bitmap结构的key
     * @param offset 指定的偏移量。
     * @param value  true：即该位设置为1，否则设置为0
     * @return 返回设置该value之前的值。
     */
    public Boolean setBit(String key, Long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将指定offset偏移量的值设置为0；
     *
     * @param key    bitmap结构的key
     * @param offset 指定的偏移量。
     * @return 若偏移位上的值为1，那么返回true。
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 统计位图中值为<i>true</i>的位数
     *
     * @param key 键
     * @return 值
     */
    public Long bitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    /**
     * 统计位图中值为<i>true</i>的位数(指定范围)
     *
     * @param key   键
     * @param start 开始位置，该参数的单位是byte（1byte=8bit），{@code setBit(key,7,true);}进行存储时，单位是bit。那么只需要统计[0,1]便可以统计到上述set的值
     * @param end   结束位置，同{@param start}
     * @return 值
     */
    public Long bitCount(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    /**
     * 对一个或多个保存二进制的字符串key进行元操作，并将结果保存到saveKey上。
     * <p>
     * bitop and saveKey key [key...]，对一个或多个key逻辑并，结果保存到saveKey。
     * bitop or saveKey key [key...]，对一个或多个key逻辑或，结果保存到saveKey。
     * bitop xor saveKey key [key...]，对一个或多个key逻辑异或，结果保存到saveKey。
     * bitop xor saveKey key，对一个或多个key逻辑非，结果保存到saveKey。
     * <p>
     *
     * @param op      元操作类型；
     * @param saveKey 元操作后将结果保存到saveKey所在的结构中。
     * @param desKey  需要进行元操作的类型。
     * @return 1：返回元操作值。
     */
    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }

    /**
     * 对一个或多个保存二进制的字符串key进行元操作，并将结果保存到saveKey上，并返回统计之后的结果。
     *
     * @param op      元操作类型；
     * @param saveKey 元操作后将结果保存到saveKey所在的结构中。
     * @param desKey  需要进行元操作的类型。
     * @return 返回saveKey结构上value=1的所有数量值。
     */
    public Long bitOpResult(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        bitOp(op, saveKey, desKey);
        return bitCount(saveKey);
    }


    /**
     * 获取Hash值
     *
     * @param key 键
     * @return 值
     */
    private long hash(String key) {
        Charset charset = StandardCharsets.UTF_8;
        return Math.abs(Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asInt());
    }
}