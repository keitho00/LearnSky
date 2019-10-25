local ov = tonumber(redis.call('GET', KEYS[1])) or 0
local ti = tonumber(ARGV[1]) or 0
if (ov + ti > tonumber(ARGV[2]))
  then ti = tonumber(ARGV[2]) - ov
end
if (ti > 0)
  then redis.call('SET', KEYS[1], tostring(ov + ti), 'EX', tonumber(ARGV[3]))
end
return ov..'-'..ti..'-'..(ov + ti)