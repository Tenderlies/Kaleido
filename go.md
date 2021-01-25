# GO

## GO环境变量

```conf
setx -m GOROOT "D:\env\go\go_1.14.6"
setx -m GOENV "D:\env\go\goenv"
setx -m Path "%Path%;%GOROOT%\bin"
```

## 开发环境配置

### 配置Cygwin-Mingw环境变量

```
setx -m CYGWIN "D:\env\cygwin"
setx -m Path "%CYGWIN%\bin;%Path%"
setx -m Path "%CYGWIN%\usr\x86_64-w64-mingw32\sys-root\mingw\bin;%Path%"
```

### 配置 goenv

`cd.>%GOENV%` 创建 goenv 文件

```goenv
GOCACHE=D:\env\go\gocache
GOPATH=D:\env\go\packages

GO111MODULE=on
GOPROXY=https://mirrors.aliyun.com/goproxy/

AR=D:\env\cygwin\bin\x86_64-w64-mingw32-ar.exe
CC=D:\env\cygwin\bin\x86_64-w64-mingw32-gcc.exe
CXX=D:\env\cygwin\bin\x86_64-w64-mingw32-g++.exe
PKG_CONFIG=pkgconf
```
### 配置 GTK

```bash
cd /usr/x86_64-w64-mingw32/sys-root/mingw/lib/pkgconfig
grep -nsr "Wl,-luuid" .

# 注意 gdk-*.0.pc、gdk-broadway-*.0.pc、gdk-win32-*.0.pc 中匹配的位置
# 1. Libs 的 "Wl,-luuid" 改为 "-luuid"
# 2. 新增 LDFLAGS 行， 添加 "-Wl"
```

### 复制 PKG_CONFIG 文件

`xcopy D:\env\cygwin\usr\x86_64-w64-mingw32\sys-root\mingw\lib\pkgconfig D:\env\cygwin\lib\pkgconfig`
