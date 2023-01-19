#!/bin/sh

ip=101.42.312.21
password=325vdfvdfvregrr
dir=/root/docker/cert # 证书生成位置
validity_period=10    # 证书有效期10年

# 将此shell脚本在安装docker的机器上执行，作用是生成docker远程连接加密证书
if [ ! -d "$dir" ]; then
  echo ""
  echo "$dir , not dir , will create"
  echo ""
  mkdir -p $dir
else
  echo ""
  echo "$dir , dir exist , will delete and create"
  echo ""
  rm -rf $dir
  mkdir -p $dir
fi

cd $dir || exit
# 创建根证书RSA私钥
openssl genrsa -aes256 -passout pass:"$password" -out ca-key.pem 4096
# 创建CA证书
openssl req -new -x509 -days $validity_period -key ca-key.pem -passin pass:"$password" -sha256 -out ca.pem -subj "/C=NL/ST=./L=./O=./CN=$ip"
# 创建服务端私钥
openssl genrsa -out server-key.pem 4096
# 创建服务端签名请求证书文件
openssl req -subj "/CN=$ip" -sha256 -new -key server-key.pem -out server.csr

echo subjectAltName = IP:$ip,IP:0.0.0.0 >>extfile.cnf

echo extendedKeyUsage = serverAuth >>extfile.cnf
# 创建签名生效的服务端证书文件
openssl x509 -req -days $validity_period -sha256 -in server.csr -CA ca.pem -CAkey ca-key.pem -passin "pass:$password" -CAcreateserial -out server-cert.pem -extfile extfile.cnf
# 创建客户端私钥
openssl genrsa -out key.pem 4096
# 创建客户端签名请求证书文件
openssl req -subj '/CN=client' -new -key key.pem -out client.csr

echo extendedKeyUsage = clientAuth >>extfile.cnf

echo extendedKeyUsage = clientAuth >extfile-client.cnf
# 创建签名生效的客户端证书文件
openssl x509 -req -days $validity_period -sha256 -in client.csr -CA ca.pem -CAkey ca-key.pem -passin "pass:$password" -CAcreateserial -out cert.pem -extfile extfile-client.cnf
# 删除多余文件
rm -f -v client.csr server.csr extfile.cnf extfile-client.cnf

chmod -v 0400 ca-key.pem key.pem server-key.pem

chmod -v 0444 ca.pem server-cert.pem cert.pem