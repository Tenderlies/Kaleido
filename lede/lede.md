# LEDE

## KoolShare 固件地址

`http://firmware.koolshare.cn`

## Terminal 配置

```shell
vi /etc/config/network

/etc/init.d/network restart
```

## 破解离线安装限制

```shell
sed -i 's/\tdetect_package/\t# detect_package/g' /koolshare/scripts/ks_tar_install.sh
```
