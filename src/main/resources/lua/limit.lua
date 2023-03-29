-- 获取传入注解中的参数
local key = KEYS[1]
local count = tonumber(ARGV[1])
local time = tonumber(ARGV[2])
-- 从Redis中用key获取 ，call方法则是获得执行结果
local current = redis.call('get', key)
-- 如果能获取到，就返回当前的执行结果
if current and tonumber(current) > count then
    return tonumber(current)
end
-- 将结果自增
current = redis.call('incr', key)
-- 如果结果为1 就设置过期时间
if tonumber(current) == 1 then
    redis.call('expire', key, time)
end
return tonumber(current)